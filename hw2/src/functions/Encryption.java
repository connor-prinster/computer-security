/**
 * Encryption contains the private data members public_key and plaintext. The method encrypt()
 * uses public_key, a public rsa key, to encrypt the plaintext message into an encoded ciphertext.
 */
public class Encryption {
    private String public_key;
    private String plaintext;

    public Encryption(String public_key, String plaintext) {
        this.public_key = public_key;
        this.plaintext = plaintext;
    }

    String encrypt(String public_key, String plaintext) {
        String ciphertext = plaintext;
        return ciphertext;
    }
}