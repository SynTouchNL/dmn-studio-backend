package nl.syntouch.dmn.studio.service;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

/**
 * AES-256-GCM encryption for credentials stored in the database.
 * Stored format: {@code v1:} + base64(12-byte IV || ciphertext || 128-bit tag). The version prefix allows key
 * rotation later. A missing key only disables encryption; a configured but invalid key fails startup.
 */
@Startup
@ApplicationScoped
public class CredentialEncryptionService {
    static final String KEY_PROPERTY = "dmnstudio.encryption.key";
    private static final String VERSION_PREFIX = "v1:";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_LENGTH_BYTES = 32;
    private static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;

    private final SecretKey key;
    private final SecureRandom random = new SecureRandom();

    public CredentialEncryptionService(@ConfigProperty(name = KEY_PROPERTY) Optional<String> base64Key) {
        this.key = base64Key.map(String::trim).filter(value -> !value.isEmpty()).map(CredentialEncryptionService::parseKey).orElse(null);
    }

    public boolean isConfigured() {
        return key != null;
    }

    public String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[IV_LENGTH_BYTES];
            random.nextBytes(iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, requireKey(), new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            byte[] payload = ByteBuffer.allocate(iv.length + ciphertext.length).put(iv).put(ciphertext).array();
            return VERSION_PREFIX + Base64.getEncoder().encodeToString(payload);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not encrypt credential", e);
        }
    }

    public String decrypt(String stored) {
        if (stored == null || !stored.startsWith(VERSION_PREFIX)) {
            throw new IllegalStateException("Unsupported encrypted credential format");
        }
        try {
            byte[] payload = Base64.getDecoder().decode(stored.substring(VERSION_PREFIX.length()));
            if (payload.length <= IV_LENGTH_BYTES) {
                throw new IllegalStateException("Encrypted credential is truncated");
            }
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, requireKey(), new GCMParameterSpec(TAG_LENGTH_BITS, payload, 0, IV_LENGTH_BYTES));
            byte[] plaintext = cipher.doFinal(payload, IV_LENGTH_BYTES, payload.length - IV_LENGTH_BYTES);
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalStateException("Could not decrypt credential; was the encryption key changed?", e);
        }
    }

    private SecretKey requireKey() {
        if (key == null) {
            throw new IllegalStateException("Encryption key is not configured (DMN_STUDIO_ENCRYPTION_KEY)");
        }
        return key;
    }

    private static SecretKey parseKey(String base64Key) {
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(base64Key);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(KEY_PROPERTY + " must be base64 encoded", e);
        }
        if (bytes.length != KEY_LENGTH_BYTES) {
            throw new IllegalStateException(KEY_PROPERTY + " must decode to exactly 32 bytes (AES-256), got " + bytes.length);
        }
        return new SecretKeySpec(bytes, "AES");
    }
}
