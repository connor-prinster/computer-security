package functions;

/**
 * Decryption contains the private data members private_key and ciphertext. The method decrypt()
 * uses private_key, a private rsa key, to decrypt the encoded ciphertext into a plaintext message.
 */
public class Decryption {
    private String private_key;
    private String ciphertext;

    public Decryption(String private_key, String ciphertext) {
        this.private_key = private_key;
        this.ciphertext = ciphertext;
    }
    String decrypt(String private_key, String ciphertext) {
        String plaintext = ciphertext;
        return plaintext;
    }
}