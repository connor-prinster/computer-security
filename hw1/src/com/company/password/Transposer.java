package com.company.password;

public class Transposer {
    public static int[] getKeyColumns(String key) {
        int[] columns = new int[key.length()];
        for(int i = 0; i < key.length(); i++) {
            columns[i] = -1;
        }
        for(int i = 0; i < key.length(); i++) {
            char least = 'z';
            int position = 0;
            for(int j = 0; j < key.length(); j++) {
                char current = key.charAt(j);
                if(current < least && columns[j] == -1) {
                    position = j;
                    least = current;
                }
            }
            columns[position] = i;
        }
        return columns;
    }

    public static String ColumnarTransposition(String input, String key) {
        int[] order = getKeyColumns(key);
        int width = key.length();
        int height = (int)Math.ceil((double)input.length() / (double)key.length());
        char[][] matrix = new char[height][width];
        for(int i = 0; i < input.length(); i++) {
            matrix[i / width][i % width] = input.charAt(i);
        }
        String output = "";
        for(int i = 0; i < width; i++) {
            int k = order[i];
            for(int j = 0; j < height; j++) {
                output += matrix[j][k];
            }
        }
        return output;
    }
    public static String ReverseColumnarTransposition(String input, String key) {
        int[] order = getKeyColumns(key);
        int width = key.length();
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