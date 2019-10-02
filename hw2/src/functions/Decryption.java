package functions;

/**
 * Decryption contains the private data members private_key and ciphertext. The method decrypt()
 * uses private_key, a private rsa key, to decrypt the encoded ciphertext into a plaintext message.
 */
public class Decryption {
    String decrypt(double pd, double m, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        char[] plainCharacters = ciphertext.toCharArray();
        for (char c : plainCharacters) {
            char encode = (char)(Math.pow(c, pd) % m);
            plaintext.append(encode);
        }
        return plaintext.toString();
    }
}