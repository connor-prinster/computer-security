package com.company.password;

public class Transposer {

    // composite key = 1422555515
    // encrypted 572225253539572544255724
    // message "attack at dawn"

    /**
     * Takes an input key and returns an array of integers representing the order the characters appear in the
     * alphabet, or the columns in transposition.
     * @param key The string key
     * @return an array of integers representing the order of columns in the transposition
     */
    public static int[] getKeyColumns(String key) {
        key.replaceAll("\\[A-Za-z]", "");

        int[] columns = new int[key.length()]; // Initialize the integer array
        // Initialize the array to -1, since we need the 0 for the first column
        for(int i = 0; i < key.length(); i++) {
            columns[i] = -1;
        }
        // Sort through the key, assigning the appearance in the alphabet to the int array
        for(int i = 0; i < key.length(); i++) {
            char least = 'z' + 1; // z is the last possible index
            int position = 0; // start at 0
            // Go through each letter in the key and see if it's the least
            for(int j = 0; j < key.length(); j++) {
                char current = key.charAt(j);
                // If it is so far, mark it as such
                if(current < least && columns[j] == -1) {
                    position = j;
                    least = current;
                }
            }
            // Assign the position to the character
            columns[position] = i;
        }
        return columns;
    }

    /**
     *
     * @param input plaintext, what you want to be transposed
     * @param key the key to transform by
     * @return the Columnar Transposed version of the input
     */
    public static String ColumnarTransposition(String input, String key) {
        int[] order = getKeyColumns(key);   // Order of the columns
        int width = key.length();           // Width of the matrix
        int height = (int)Math.ceil((double)input.length() / (double)key.length()); // Height of the matrix
        char[][] matrix = new char[height][width]; // A piece of pie
        // Initialize characters to ''
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                matrix[i][j] = Character.MIN_VALUE;
            }
        }
        // Assign characters to the matrix according to the width
        for(int i = 0; i < input.length(); i++) {
            matrix[i / width][i % width] = input.charAt(i);
        }
        String output = ""; // Initialize output
        for(int i = 0; i < width; i++) {
            int k = order[i]; // Current column given by the key
            // Loop through the matrix height first and add it to the output
            for(int j = 0; j < height; j++) {
                output += matrix[j][k];
            }
        }
        return output;
    }

    /***
     *
     * @param input the unpadded ciphertext
     * @param key the composite text
     * @return the plaintext
     */
    public static String ReverseColumnarTransposition(String input, String key) {
        // from the key, make sure the width and height are done correctly
        int[] order = getKeyColumns(key);
        int width = key.length();
        // the height of the matrix can be found by the length of the unpadded ciphertext divided by the length of the key
        int height = (int)Math.ceil((double)input.length() / (double)key.length());
        char[][] matrix = new char[height][width];
        for(int i = 0; i < width; i++) {
            int k = order[i];
            for(int j = 0; j < height; j++) {
                matrix[j][k] = input.charAt((i * height) + j);
            }
        }
        String output = "";
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                output += matrix[i][j];
            }
        }
        return output;
    }
}