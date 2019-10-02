package functions;

/**
 * Encryption contains the private data members public_key and plaintext. The method encrypt()
 * uses public_key, a public rsa key, to encrypt the plaintext message into an encoded ciphertext.
 */
public class Encryption {
    static String encrypt(double rpn, double m, String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        char[] plainCharacters = plaintext.toCharArray();
        for (char c : plainCharacters) {
            char encode = (char)(Math.pow(c, rpn) % m);
            System.out.println(String.format("%d decrypts to %d", (int)c, (int)encode));
            ciphertext.append(encode);
        }
        return ciphertext.toString();
    }

    public static void main(String[] args) {
        String cipherText = encrypt(7, 55, "Hello world");
        System.out.println(cipherText);
        System.out.println(Decryption.decrypt(23, 55, cipherText));
        // public rpn 9 m 319
        // private 249 319
    }
}

