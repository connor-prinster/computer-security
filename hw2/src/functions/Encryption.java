package functions;

/**
 * Encryption contains the private data members public_key and plaintext. The method encrypt()
 * uses public_key, a public rsa key, to encrypt the plaintext message into an encoded ciphertext.
 */
public class Encryption {
    String encrypt(double rpn, double m, String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        char[] plainCharacters = plaintext.toCharArray();
        for (char c : plainCharacters) {
            char encode = (char)(Math.pow(c, rpn) % m);
            ciphertext.append(encode);
        }
        return ciphertext.toString();
    }
}