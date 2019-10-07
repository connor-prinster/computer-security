public class BreakColumnar {

    public static final int MAX_KEY_LENGTH = 50;

    public static void main(String[] args) {
        String plaintext = "I really want to go to work, but I am too sick to drive. Let me help you with your baggage. Please wait outside of the house.";
        String ciphertext = "arPbo yoe L t fo ewhu w  yhI  etve th sb. ea yem  akgltgwd ota  okpiorsl anil  iaheecg  .gewrls, douutmtoett.sIituuotooriaoe ";

        int[] key = Cryptanalize(plaintext, ciphertext);
        for(int k : key) {
            System.out.print(k);
            System.out.print(' ');
        }
        System.out.println();
    }


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
        int maxwidth = Math.min(MAX_KEY_LENGTH, p.length());
        for(int i = 0; i < maxwidth; i++) {
            char test = p.charAt(i);
        //    System.out.println(String.format("Trying at %d", i));
            if(test == first) {
                for(width = 1; width < maxwidth; width++) {
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