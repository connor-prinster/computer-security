package com.company.password;

public class Main {

    public static void main(String[] args) { // First argument should be the composite key

        char[][] polybiusSquare = makePolybiusSquare("E2RFZMYH30B7OQANUKPXJ4VWD18GC69IS5TL");

        // get the last two integers for the one time pad
        int oneTimePadKey = Integer.parseInt(args[0].substring(args[0].length()-2));
        // a char array of integers to be passed into the composite key function
        char[] columnarComposite = args[0].substring(0, args[0].length()-2).toCharArray();

        // get the composite key from the polybius square
        String compositeKey = getCompositeKey(columnarComposite, polybiusSquare);

        // take out whitespace from the message
        String message = args[1].replaceAll("\\s", "");
        // get the columnar transposition
        String columnarTransposed = Transposer.ColumnarTransposition(message, compositeKey);

        System.out.println(columnarTransposed);

        String encrypted = getOneTimePadEncryption(columnarTransposed, polybiusSquare, oneTimePadKey);

        System.out.println(encrypted);

        String decrypted = oneTimePadDecrypt(encrypted, polybiusSquare, oneTimePadKey);
        System.out.println(decrypted);

        System.out.println(Transposer.ReverseColumnarTransposition(decrypted, compositeKey));

    }

    private static char[][] makePolybiusSquare(String polybius) {
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
