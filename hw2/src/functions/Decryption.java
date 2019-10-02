package functions;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Decryption contains the private data members private_key and ciphertext. The method decrypt()
 * uses private_key, a private rsa key, to decrypt the encoded ciphertext into a plaintext message.
 */
public class Decryption {
    static String decrypt(int pd, int m, String ciphertext) {
        BigInteger exponent = new BigInteger(Integer.toString(pd));
        BigInteger modulus = new BigInteger(Integer.toString(m));

        char[] cipherCharacters = new String(Base64.getDecoder().decode(ciphertext), Charset.defaultCharset()).toCharArray();

        StringBuilder plainText = new StringBuilder();

        for (char c : cipherCharacters) {
            BigInteger encrypted = new BigInteger((Integer.toString((int)c)));
            BigInteger decrypted = encrypted.modPow(exponent, modulus);
            plainText.append((char)decrypted.intValue());
        }


        return plainText.toString();
    }
}