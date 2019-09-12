package com.company.password;

public class Main {

    // composite key = 1422555515
    // encrypted 572225253539572544255724
    // message "attack at dawn"

    // First argument should be the composite key,
    // second should be en/decrypted line,
    // third should be e, d, or null for encryption, decryption, or both respectively
    public static void main(String[] args) {

        // make sure there are actually arguments done
        if(args.length > 0) {
            withArguments(args);
        } else {
            System.out.println("No arguments given\n");
        }

    }

    /***
     * the user passed in at least a few arguments
     * @param args there were arguments given by the user
     */
    private static void withArguments(String[] args) {
        // generate the polybius square
        char[][] polybiusSquare = makePolybiusSquare();

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
            // should encrypt it
            if(args[2].toLowerCase().equals("e")) {
                encrypted = encrypt(args, polybiusSquare, compositeKey);
                System.out.println("Encrypted: " + encrypted);
            }
            // will decrypt it
            else if (args[2].toLowerCase().equals("d")) {
                decrypted = Transposer.ReverseColumnarTransposition(
                        oneTimePadDecrypt(args[1], polybiusSquare, oneTimePadKey),
                        compositeKey
                );
                System.out.println("Decrypted: " + decrypted);
            }
            // will both encrypt and decrypt
            else {
                doBoth(args, polybiusSquare, compositeKey, oneTimePadKey);
            }
        } else {
            doBoth(args, polybiusSquare, compositeKey, oneTimePadKey);
        }
    }

    /***
     * the user did not give any indication of if they wanted to encrypt or decrypt the text, so we assume they want both
     * @param args arguments given by user
     * @param polybiusSquare 2x2 square of chars
     * @param compositeKey composite key
     * @param oneTimePadKey one time pad key to use in both encryption and decryption
     */
    private static void doBoth(String[] args, char[][] polybiusSquare, String compositeKey, int oneTimePadKey) {
        System.out.println("No specific instruction given. Will encrypt and decrypt\n");

        // grab encrypted text
        String encrypted = encrypt(args, polybiusSquare, compositeKey);
        System.out.println("Encrypted: " + encrypted);

        // grab decrypted text
        String decrypted = Transposer.ReverseColumnarTransposition(
                oneTimePadDecrypt(encrypted, polybiusSquare, oneTimePadKey),
                compositeKey
        );
        System.out.println("Decrypted: " + decrypted);
    }

    /***
     * the user wishes to encrypt the plaintext
     * @param args the arguments passed in from the user
     * @param polybiusSquare the 6x6 polybius square
     * @param compositeKey the composite key
     * @return the completely encrypted key
     */
    private static String encrypt(String[] args, char[][] polybiusSquare, String compositeKey) {
        // get the last two integers for the one time pad
        int oneTimePadKey = Integer.parseInt(args[0].substring(args[0].length()-2));

        // take out whitespace from the message
        String message = args[1].replaceAll("\\s", "");
        message = message.replaceAll("[^a-zA-Z0-9]","");
        // get the columnar transposition
        String columnarTransposed = Transposer.ColumnarTransposition(message, compositeKey);

       return getOneTimePadEncryption(columnarTransposed, polybiusSquare, oneTimePadKey);
    }

    /***
     * Using the string "E2RFZMYH30B7OQANUKPXJ4VWD18GC69IS5TL", make an square array
     * @return a polybius square
     */
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

    /***
     * get the composite key from the integer passed in
     * @param compositeNums from the composite key
     * @param polybiusSquare the 2 dimensional polybius square
     * @return the text version of the integer composite key
     */
    private static String getCompositeKey(char[] compositeNums, char[][] polybiusSquare) {
        StringBuilder compositeKey = new StringBuilder();
        // get two integers from the composite number (e.g. 123456) would give us 12, 34, 56
        for (int i = 0; i < compositeNums.length; i+=2) {
            // from the two numbers retrieved from the array, use the first number as the column and the
            // second as the row
            int column = Character.getNumericValue(compositeNums[i]);
            int row = Character.getNumericValue(compositeNums[i+1]);
            compositeKey.append(polybiusSquare[column][row]);
        }
        return compositeKey.toString();
    }

    /***
     * undo the one-time padding done based on the pad key
     * @param cipherText the encoded text
     * @param polybiusSquare the 2x2 polybius square
     * @param padKey the pad key
     * @return the result of undoing the pad
     */
    private static String oneTimePadDecrypt(String cipherText, char[][] polybiusSquare, int padKey) {
        StringBuilder undone = new StringBuilder();
        for(int i = 0; i < cipherText.length() / 2; i++) {
            int a = Integer.parseInt(cipherText.substring(i * 2, (i * 2) + 2));
            // xor the previously padded key
            int b = padKey ^ a;
            // 63 is the ASCII equivalent of the empty space
            if(b == 63) {
                // empty space
                undone.append(Character.MIN_VALUE);
            } else {
                // get the cell by only getting the number in the 10s place
                int row = b / 10;
                // modulo the cell to get the number in the 1s place
                int col = b % 10;
                undone.append(polybiusSquare[row][col]); // add the text equivalent found in the square
            }
        }
        // return the unpadded string
        return undone.toString();
    }

    /***
     * pad the columnar transposed text by the padKey
     * @param columnarCipherText the text that has been put through a columnar transposition
     * @param polybiusSquare 2x2 polybius square
     * @param padKey the int that we will pad the columnarCipherText with
     * @return the final ciphertext
     */
    private static String getOneTimePadEncryption(String columnarCipherText, char[][] polybiusSquare, int padKey) {
        char[] columnarLetters = columnarCipherText.toCharArray();
        StringBuilder cipherText = new StringBuilder();
        for (char columnarLetter : columnarLetters) {
            // get the polybius number
            int polyNum = getPolybiusNumber(columnarLetter, polybiusSquare);
            // xor the padKey
            int result = padKey ^ polyNum;
            if (result < 10) {
                cipherText.append("0").append(result);
            }
            else {
                cipherText.append(result);
            }
        }

        return cipherText.toString();
    }

    /***
     * given a character, search for the polybius square index (xy)
     * @param character the char to look for in the polybius square
     * @param polybiusSquare the 2x2 polybius square
     * @return the 2 digit polybius number based on the cell
     */
    private static int getPolybiusNumber(char character, char[][] polybiusSquare) {
        if(character == Character.MIN_VALUE) return 63;
        // go through the whole square to search for a character
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (Character.toUpperCase(character) == polybiusSquare[i][j]) {
                    String cell = i + Integer.toString(j);
                    return Integer.parseInt(cell);
                }
            }
        }
        return -1;
    }
}
