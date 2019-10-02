package functions;

import java.math.BigInteger;
import java.util.Base64;

/**
 * Encryption contains the private data members public_key and plaintext. The method encrypt()
 * uses public_key, a public rsa key, to encrypt the plaintext message into an encoded ciphertext.
 */
public class Encryption {
    static String encrypt(int rpn, int m, String plaintext) {
        char[] plainCharacters = plaintext.toCharArray();
        StringBuilder numberRepresentation = new StringBuilder();

        BigInteger exponent = new BigInteger(Integer.toString(rpn));
        BigInteger modulus = new BigInteger(Integer.toString(m));

        for (char c : plainCharacters) {
            BigInteger message = new BigInteger(Integer.toString(c));

            BigInteger encrypted = message.modPow(exponent, modulus);

            numberRepresentation.append((char)encrypted.intValue());
            
            // char encode = (char)(Math.pow(c, rpn) % m);
            // System.out.println(String.format("%d decrypts to %d", (int)c, (int)encode));
            // ciphertext.append(encode);

        }

        return Base64.getEncoder().encodeToString(numberRepresentation.toString().getBytes());
    }

    public static void main(String[] args) {
        String cipherText = encrypt(17, 3233, "Hello world! I can't believe this works!!");
        System.out.println(cipherText);
        System.out.println(Decryption.decrypt(413, 3233, cipherText));
        // public rpn 17 m 3233
        // private 249 319
    }
}

