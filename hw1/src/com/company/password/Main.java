package com.company.password;


public class Main {

    public static void main(String[] args) { // First argument should be the composite key
        // write your code here
        char[][] polybiusSquare = new char[6][6];
        String polybius = "E2RFZMYH30B7OQANUKPXJ4VWD18GC69IS5TL";
        char[] polybius2 = polybius.toCharArray();
        System.out.println(35/6);
        for (int i = 0; i < 36; i++) {
            polybiusSquare[i/6][i%6] = polybius2[i];      }
        for (int i = 0; i < 36; i++) {
            if (i%6 == 0) System.out.println();
            System.out.print(polybiusSquare[i/6][i%6]);
        }

        int oneTimePadKey = Integer.parseInt(args[0].substring(args[0].length()-2));
        char[] columnarComposite = args[0].substring(0, args[0].length()-2).toCharArray();
        System.out.println(columnarComposite);
        System.out.println(oneTimePadKey);

        String compositeKey = getCompositeKey(columnarComposite, polybiusSquare);
        System.out.println(compositeKey);

        String columnarTrasposed = Transposer.ColumnarTransposition(args[1], compositeKey);
        System.out.println(columnarTrasposed);

        System.out.println(getOneTimePad(columnarTrasposed, polybiusSquare, oneTimePadKey));
    }

    private static String getCompositeKey(char[] compositeNums, char[][] polybiusSquare) {
        String compositeKey = "";
        for (int i = 0; i < compositeNums.length; i+=2) {
            int column = Character.getNumericValue(compositeNums[i]);
            int row = Character.getNumericValue(compositeNums[i+1]);
            System.out.println("Column " + column + " Row " + row);
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
