import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        
=======
        String message = args[0];
        ArrayList<String> chunks = divideInto224BitChuncks(message);
        frankensteinTheMfs(chunks);

//        System.out.println("hello");
    }

    private static ArrayList<String> divideInto224BitChuncks(String message) {
        ArrayList<String> chunks = new ArrayList<>();
        while (message.length() / 28 != 0) {
            chunks.add(message.substring(0, 28));
            message = message.substring(28);
        }
        if (message.length() % 28 == 0) {
            return chunks;
        } else {
            while (message.length() < 28) {
                message += "0";
            }
            chunks.add(message);
            return chunks;
        }
    }

    private static void frankensteinTheMfs(ArrayList<String> chunks) {
        // adding to howManyMoreChunks will make sure that there are exactly 7 different blocks
        int howManyMoreChunks = 0;
        while((chunks.size() + howManyMoreChunks) % 7 != 0) {
            howManyMoreChunks++;
        }

        // add howManyMoreChunks 28-count of zeroes so it will be divisible by 7
        for(int i = 0; i < howManyMoreChunks; i++) {
            String zeroes = "0000000000000000000000000000";
            chunks.add(zeroes);
        }

        ArrayList<String> adjustedChunks = adjustChunks(generateSevenChunks(chunks));

    }

    private static ArrayList<String> generateSevenChunks(ArrayList<String> chunks) {
        ArrayList<String> sevenChunks = new ArrayList<>(Arrays.asList("", "", "", "", "", "", ""));
        int numChunks = chunks.size() / 7;
        for(int i = 0; i < numChunks; i++) {
            int sevenVal = 7 * i;
            sevenChunks.set(0, sevenChunks.get(0) + chunks.get(sevenVal));
            sevenChunks.set(1, sevenChunks.get(1) + chunks.get(sevenVal + 1));
            sevenChunks.set(2, sevenChunks.get(2) + chunks.get(sevenVal + 2));
            sevenChunks.set(3, sevenChunks.get(3) + chunks.get(sevenVal + 3));
            sevenChunks.set(4, sevenChunks.get(4) + chunks.get(sevenVal + 4));
            sevenChunks.set(5, sevenChunks.get(5) + chunks.get(sevenVal + 5));
            sevenChunks.set(6, sevenChunks.get(6) + chunks.get(sevenVal + 6));
        }
        return sevenChunks;
>>>>>>> 7cd8e58b9cdfd59484f729c5e3de80633a87c263
    }

    private static ArrayList<String> adjustChunks(ArrayList<String> sevenChunks) {
        StringXORer xor = new StringXORer();

        ArrayList<String> adjusted = (ArrayList<String>) sevenChunks.clone();

        for(int i = 0; i < 12; i++) {
            ArrayList<String> prevAdjusted = (ArrayList<String>) adjusted.clone(); // prevAdjusted is from the adjusted from the last runthrough
            adjusted.set(0, prevAdjusted.get(6));                                   // spot [0] is set to 6
            adjusted.set(1, xor.encode(prevAdjusted.get(0), prevAdjusted.get(2)));  // [1] 1 comes from 0 ^ 2
            adjusted.set(2, xor.encode(prevAdjusted.get(1), prevAdjusted.get(6)));  // [2] comes from 1 ^ 6
            adjusted.set(3, xor.encode(prevAdjusted.get(2), prevAdjusted.get(4)));  // [3] comes from 2 ^ 5
            adjusted.set(4, prevAdjusted.get(3));                                   // [4] comes from 3 << 2
            adjusted.set(5, xor.encode(prevAdjusted.get(4), prevAdjusted.get(6)));  // [5] comes from 4 ^ 6
            adjusted.set(6, prevAdjusted.get(5));                                   // [6] comes from spot 5
        }

        return adjusted;
    }

    static int RotateLeftTwice(int a) {
        int b = a << 2;
        int c =  (a & 0xC0000000) / (0xC0000000);
        return b + c;
    }
}
