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

        String a = "a";
        String b = "b";
        String ab = a+b;
        System.out.println(ab);

        int one = 1;
        byte juan = (byte)one;
        System.out.println(juan);

        int num1 = 5;
        int num2 = 6;
        String combinedNums = Integer.toString(num1) + Integer.toString(num2);
        System.out.println(combinedNums);

        String compositeKey = getCompositeKey(columnarComposite, polybiusSquare);
        System.out.println(compositeKey);

        getOneTimePad(compositeKey, polybiusSquare, oneTimePadKey);
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
        String binaryPadKey = "000000" + Integer.toBinaryString(padKey);
        binaryPadKey = binaryPadKey.substring(binaryPadKey.length()-6,binaryPadKey.length());
        System.out.println(binaryPadKey);
        for (char columnarLetter : columnarLetters) {
            int polyNum = getPolybiusNumber(columnarLetter, polybiusSquare);
            System.out.println(polyNum);
            String binaryNumber = "000000" + Integer.toBinaryString(polyNum);
            binaryNumber = binaryNumber.substring(binaryNumber.length()-6,binaryNumber.length());
        }


        return null;
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
