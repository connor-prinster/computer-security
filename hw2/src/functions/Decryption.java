package functions;

/**
 * Decryption contains the private data members private_key and ciphertext. The method decrypt()
 * uses private_key, a private rsa key, to decrypt the encoded ciphertext into a plaintext message.
 */
public class Decryption {
    static String decrypt(double pd, double m, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        char[] plainCharacters = ciphertext.toCharArray();
        for (char c : plainCharacters) {
            char encode = (char) (Math.pow(c, pd) % m);
            System.out.println(String.format("%d decrypts to %d", (int)c, (int)encode));
            plaintext.append(encode);
        }
        return plaintext.toString();
    }
}