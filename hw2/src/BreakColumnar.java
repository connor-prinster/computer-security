public class BreakColumnar {

    public static final int MAX_KEY_LENGTH = 50;

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
    //    System.out.println(String.format("Looking for %s within %s", b, a.substring(start, start+width)));
        for(int i = start; i <= width + start; i++) {
            char test = a.charAt(i);
            if(test == b) {
    //            System.out.println(String.format("Found at %d", i - start));
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
    //        System.out.println(String.format("Testing %c with %c", cipherChar, plainChar));
            if(cipherChar != plainChar) return false;
        }
        return true;
    }
    private static int getKeyWidth(String p, String c) {
        char first = c.charAt(0);
        int width; 
        for(int i = 0; i <= MAX_KEY_LENGTH; i++) {
            char test = p.charAt(i);
        //    System.out.println(String.format("Trying at %d", i));
            if(test == first) {
                for(width = 1; width <= MAX_KEY_LENGTH; width++) {
        //            System.out.println(String.format("Trying width %d", width));
                    if(checkWidth(p, c, 0, i, width)) {
        //                System.out.println(String.format("Key width is %d", width));
                        return width;
                    }
                }
            }
        }
        System.out.println("This isn't the right cipher for the plaintext");
        return 0;
    }
}