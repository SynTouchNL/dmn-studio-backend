package nl.syntouch.dmn.studio.service;

import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Credential Encryption Service Tests")
class CredentialEncryptionServiceTest {

    private static final String KEY = Base64.getEncoder().encodeToString(new byte[32]);

    private final CredentialEncryptionService service = new CredentialEncryptionService(Optional.of(KEY));

    @Test
    @DisplayName("Should decrypt what it encrypted")
    void encryptDecryptRoundTrip() {
        String encrypted = service.encrypt("s3cret wachtwoord ");

        assertTrue(encrypted.startsWith("v1:"));
        assertFalse(encrypted.contains("s3cret"));
        assertEquals("s3cret wachtwoord ", service.decrypt(encrypted));
    }

    @Test
    @DisplayName("Should use a random IV per encryption")
    void encryptUsesRandomIv() {
        assertNotEquals(service.encrypt("same"), service.encrypt("same"));
    }

    @Test
    @DisplayName("Should reject a tampered ciphertext")
    void decryptRejectsTamperedCiphertext() {
        byte[] payload = Base64.getDecoder().decode(service.encrypt("secret").substring(3));
        payload[payload.length - 1] ^= 1;
        String tampered = "v1:" + Base64.getEncoder().encodeToString(payload);

        assertThrows(WebApplicationException.class, () -> service.decrypt(tampered));
    }

    @Test
    @DisplayName("Should not decrypt with a different key")
    void decryptRejectsOtherKey() {
        byte[] otherKey = new byte[32];
        otherKey[0] = 1;
        var other = new CredentialEncryptionService(Optional.of(Base64.getEncoder().encodeToString(otherKey)));

        assertThrows(WebApplicationException.class, () -> other.decrypt(service.encrypt("secret")));
    }

    @Test
    @DisplayName("Should fail fast on a key that is not 32 bytes")
    void constructorRejectsInvalidKeyLength() {
        String shortKey = Base64.getEncoder().encodeToString(new byte[16]);

        assertThrows(IllegalStateException.class, () -> new CredentialEncryptionService(Optional.of(shortKey)));
    }

    @Test
    @DisplayName("Should fail fast on a key that is not base64")
    void constructorRejectsNonBase64Key() {
        assertThrows(IllegalStateException.class, () -> new CredentialEncryptionService(Optional.of("not base64!")));
    }

    @Test
    @DisplayName("Should start without a key but refuse to encrypt")
    void missingKeyDisablesEncryption() {
        var unconfigured = new CredentialEncryptionService(Optional.empty());

        var exception = assertThrows(WebApplicationException.class, () -> unconfigured.encrypt("secret"));
        assertEquals(500, exception.getResponse().getStatus());
    }
}
