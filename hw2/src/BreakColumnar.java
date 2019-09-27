public class BreakColumnar {

    public static final int MAX_KEY_LENGTH = 20;

    public static int[] Cryptanalize(String p, String c) {
        int[] keys;

        int width = getKeyWidth(p, c);
        int i = 0;
        int column = 1;

        keys = new int[width];
        while(i < c.length()) {
            int pos = -1;
            do {
                pos++;
                pos = getPositionWithinWidth(p, c.charAt(i), width, pos);
            } while (!checkWidth(p, c, i, pos, width));
            keys[pos] = column;
            column++;

            i += getColumnHeight(p, width, pos);
        }
        return keys;
    }

    private static int getPositionWithinWidth(String a, char b, int width, int start) {
        for(int i = start; i <= width; i++) {
            char test = a.charAt(i);
            if(test == b) {
                return i;
            }
        }
        return -1;
    }

    private static int getColumnHeight(String p, int width, int start) {
        return (p.length() / width) + (start < (p.length() % width) ? 1 : 0);
    }

    private static boolean checkWidth(String p, String c, int cipherStart, int plainStart, int width) {
        for(int i = 0; i <= p.length(); i++) {
            if(cipherStart + i >= c.length()) return true;
            char cipherChar = c.charAt(cipherStart + i);
            if(plainStart + (i * width) >= p.length()) return true;
            char plainChar = p.charAt(plainStart + (i * width));
            if(cipherChar != plainChar) return false;
        }
        return true;
    }
    private static int getKeyWidth(String p, String c) {
        char first = c.charAt(0);
        int width; 
        for(int i = 1; i <= MAX_KEY_LENGTH; i++) {
            char test = p.charAt(i);
            if(test == first) {
                for(width = 1; width <= MAX_KEY_LENGTH; width++) {
                    if(checkWidth(p, c, 0, i, width)) {
                        return width;
                    }
                }
            }
        }
        System.out.println("This isn't the right cipher for the plaintext");
        return 0;
    }
}