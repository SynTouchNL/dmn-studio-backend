package nl.syntouch.dmn.studio.service;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.subtle.AesGcmJce;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Optional;

/**
 * Encrypts environment passwords with AES-256-GCM (Google Tink) before they are stored in the database.
 * Stored value: {@code v1:} + base64 of Tink's output. The prefix leaves room for key rotation later.
 * Without a key the app still starts, but passwords can't be stored or used. An invalid key fails startup.
 */
@Startup
@ApplicationScoped
public class CredentialEncryptionService {
    private static final String PREFIX = "v1:";
    private static final String INVALID_KEY = "DMN_STUDIO_ENCRYPTION_KEY must be a base64 encoded 32-byte (AES-256) key";

    private final Aead aead;

    public CredentialEncryptionService(@ConfigProperty(name = "dmnstudio.encryption.key") Optional<String> key) {
        this.aead = key.filter(value -> !value.isBlank()).map(CredentialEncryptionService::createAead).orElse(null);
    }

    public String encrypt(String password) {
        try {
            byte[] ciphertext = requireAead().encrypt(password.getBytes(StandardCharsets.UTF_8), null);
            return PREFIX + Base64.getEncoder().encodeToString(ciphertext);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not encrypt password", e);
        }
    }

    public String decrypt(String stored) {
        try {
            byte[] ciphertext = Base64.getDecoder().decode(stored.substring(PREFIX.length()));
            return new String(requireAead().decrypt(ciphertext, null), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new WebApplicationException(
                    "Stored password could not be decrypted; was the encryption key changed? Re-enter the password", e, 500);
        }
    }

    private Aead requireAead() {
        if (aead == null) {
            throw new WebApplicationException("Encryption key is not configured (DMN_STUDIO_ENCRYPTION_KEY)", 500);
        }
        return aead;
    }

    private static Aead createAead(String base64Key) {
        byte[] key;
        try {
            key = Base64.getDecoder().decode(base64Key.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(INVALID_KEY, e);
        }
        if (key.length != 32) {
            throw new IllegalStateException(INVALID_KEY);
        }
        try {
            return new AesGcmJce(key);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(INVALID_KEY, e);
        }
    }
}
