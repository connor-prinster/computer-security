package com.company.password;


public class Main {

    public static void main(String[] args) { // First argument should be the composite key
        // new 6x6 square to hold the polybius square
        char[][] polybiusSquare = new char[6][6];
        String polybius = "E2RFZMYH30B7OQANUKPXJ4VWD18GC69IS5TL";
        char[] polybius2 = polybius.toCharArray();
        // fill in the polybius square
        for (int i = 0; i < 36; i++) {
            polybiusSquare[i/6][i%6] = polybius2[i];      }
        for (int i = 0; i < 36; i++) {
            if (i%6 == 0) System.out.println();
            System.out.print(polybiusSquare[i/6][i%6]);
        }

        // get the last two integers for the one time pad
        int oneTimePadKey = Integer.parseInt(args[0].substring(args[0].length()-2));
        // a char array of integers to be passed into the composite key function
        char[] columnarComposite = args[0].substring(0, args[0].length()-2).toCharArray();
        System.out.println(columnarComposite);
        System.out.println(oneTimePadKey);

        // get the composite key from the polybius square
        String compositeKey = getCompositeKey(columnarComposite, polybiusSquare);
        System.out.println(compositeKey);

        // get the columnar transposition
        String columnarTrasposed = Transposer.ColumnarTransposition(args[1], compositeKey);
        System.out.println(columnarTrasposed);

        System.out.println(getOneTimePad(columnarTrasposed, polybiusSquare, oneTimePadKey));
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

    private static String getOneTimePad(String columnarCipherText, char[][] polybiusSquare, int padKey) {
        char[] columnarLetters = columnarCipherText.toCharArray();
        String cipherText = "";
        for (char columnarLetter : columnarLetters) {
            int polyNum = getPolybiusNumber(columnarLetter, polybiusSquare);
            System.out.println(polyNum);
            int result = padKey ^ polyNum;
            System.out.println("result is " + result);
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
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (character == polybiusSquare[i][j]) {
                    String cell = Integer.toString(i) + Integer.toString(j);
                    return Integer.parseInt(cell);
                }
            }
        }

        return -1;
    }
}
