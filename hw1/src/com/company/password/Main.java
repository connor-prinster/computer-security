package com.company.password;

import java.util.Scanner;

public class Main {

    // 1422555515 "attack at dawn"

    // First argument should be the composite key,
    // second should be en/decrypted line,
    // third should be e, d, or null for encryption, decryption, or both respectively
    public static void main(String[] args) {

        // generate the polybius square
        char[][] polybiusSquare = makePolybiusSquare();

        if(args.length > 0) {
            withArguments(args, polybiusSquare);
        } else {
            System.out.println("No arguments given\n");
        }
    }

    private static void withArguments(String args[], char[][] polybiusSquare) {
        // get the last two integers for the one time pad
        int oneTimePadKey = Integer.parseInt(args[0].substring(args[0].length()-2));

        // a char array of integers to be passed into the composite key function
        char[] columnarComposite = args[0]
                .substring(0, args[0].length() - 2)
                .toCharArray();

        // get the composite key from the polybius square
        String compositeKey = getCompositeKey(columnarComposite, polybiusSquare);

        // will be used in all the below
        String encrypted, decrypted;
        // if there are only two inputs (no e or d), don't attempt to access args[2]
        // and just run the program looking for encrypting AND decrypting
        if(args.length > 2) {
            if(args[2].toLowerCase().equals("e")) {
                encrypted = encrypt(args, polybiusSquare, compositeKey);
                System.out.println("Encrypted: " + encrypted);
            } else if (args[2].toLowerCase().equals("d")) {
                decrypted = Transposer.ReverseColumnarTransposition(
                        oneTimePadDecrypt(args[1], polybiusSquare, oneTimePadKey),
                        compositeKey
                );
                System.out.println("Decrypted: " + decrypted);
            }
            else {
                doBoth(args, polybiusSquare, compositeKey, oneTimePadKey);
            }
        } else {
            doBoth(args, polybiusSquare, compositeKey, oneTimePadKey);
        }
    }

    private static void doBoth(String[] args, char[][] polybiusSquare, String compositeKey, int oneTimePadKey) {
        System.out.println("No specific instruction given. Will encrypt and decrypt\n");
        String encrypted = encrypt(args, polybiusSquare, compositeKey);
        System.out.println("Encrypted: " + encrypted);
        String decrypted = Transposer.ReverseColumnarTransposition(
                oneTimePadDecrypt(encrypted, polybiusSquare, oneTimePadKey),
                compositeKey
        );
        System.out.println("Decrypted: " + decrypted);
    }

    private static String encrypt(String[] args, char[][] polybiusSquare, String compositeKey) {
        // get the last two integers for the one time pad
        int oneTimePadKey = Integer.parseInt(args[0].substring(args[0].length()-2));

        // take out whitespace from the message
        String message = args[1].replaceAll("\\s", "");
        // get the columnar transposition
        String columnarTransposed = Transposer.ColumnarTransposition(message, compositeKey);

        return getOneTimePadEncryption(columnarTransposed, polybiusSquare, oneTimePadKey);
    }

    private static char[][] makePolybiusSquare() {
        String polybius = "E2RFZMYH30B7OQANUKPXJ4VWD18GC69IS5TL";
        // new 6x6 square to hold the polybius square
        char[][] polybiusSquare = new char[6][6];
        char[] polybius2 = polybius.toCharArray();
        // fill in the polybius square
        for (int i = 0; i < 36; i++) {
            polybiusSquare[i/6][i%6] = polybius2[i];
        }

        return polybiusSquare;
    }

    private static String getCompositeKey(char[] compositeNums, char[][] polybiusSquare) {
        String compositeKey = "";
        // get two integers from the composite number (e.g. 123456) would give us
        // 12, 34, 56
        for (int i = 0; i < compositeNums.length; i+=2) {
            // from the two numbers retrieved from the array, use the first number as the column and the
            // second as the row
            int column = Character.getNumericValue(compositeNums[i]);
            int row = Character.getNumericValue(compositeNums[i+1]);
            compositeKey += polybiusSquare[column][row];
        }
        return compositeKey;
    }

    private static String oneTimePadDecrypt(String cipherText, char[][] polybiusSquare, int padKey) {
        String undone = "";
        for(int i = 0; i < cipherText.length() / 2; i++) {
            int a = Integer.parseInt(cipherText.substring(i * 2, (i * 2) + 2));
            int b = padKey ^ a;

            if(b == 63) {
                undone += Character.MIN_VALUE;
            } else {
                int row = b / 10;
                int col = b % 10;
                undone += polybiusSquare[row][col];
            }
        }
        return undone;
    }

    private static String getOneTimePadEncryption(String columnarCipherText, char[][] polybiusSquare, int padKey) {
        char[] columnarLetters = columnarCipherText.toCharArray();
        String cipherText = "";
        for (char columnarLetter : columnarLetters) {
            // get the polybius number
            int polyNum = getPolybiusNumber(columnarLetter, polybiusSquare);
            // xor the padKey
            int result = padKey ^ polyNum;
            if (result < 10) {
                cipherText += ("0" + result);
            }
            else {
                cipherText += result;
            }
        }

        return cipherText;
    }

    private static int getPolybiusNumber(char character, char[][] polybiusSquare) {
        if(character == Character.MIN_VALUE) return 63;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (Character.toUpperCase(character) == polybiusSquare[i][j]) {
                    String cell = Integer.toString(i) + Integer.toString(j);
                    return Integer.parseInt(cell);
                }
            }
        }

        return -1;
    }
}
