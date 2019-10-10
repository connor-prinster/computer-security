import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Main {
    public static void main(String[] args) {
        String message;
        if(args.length < 1) {
            message = "Something something woah ldkfja Soemthing";
        } else message = args[0];
        ArrayList<String> chunks = divideInto224BitChunks(message);
        frankensteinTheMfs(chunks);
    }

    private static ArrayList<String> divideInto224BitChunks(String message) {
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

    private static String frankensteinTheMfs(ArrayList<String> chunks) {
        int[] finalReturn = new int[7];
        for(int i = 0; i < chunks.size(); i++) {
            ArrayList<String> sevenChunksOfFour = generateSevenChunksOfFour(chunks.get(i));
            int[] blocks = new int[sevenChunksOfFour.size()];
            for(int j = 0; j < sevenChunksOfFour.size(); j++) {
                String block = sevenChunksOfFour.get(j);
                int iBlock = ByteBuffer.wrap(block.getBytes(StandardCharsets.UTF_8)).getInt();
                blocks[j] = iBlock;
            }
            System.out.println(intArrayToBase64(blocks));
            int[] hashedBlocks = hashKetchum(blocks);
            System.out.println(intArrayToBase64(hashedBlocks));
            finalReturn = addIntArrayMod(finalReturn, hashedBlocks);
        }
        return intArrayToBase64(finalReturn);
    }

    private static String intArrayToBase64(int[] arr) {
        ByteBuffer bb = ByteBuffer.allocate(arr.length * 4);
        IntBuffer ib = bb.asIntBuffer();
        ib.put(arr);
        return Base64.getEncoder().encodeToString(bb.array());
    }

    private static ArrayList<String> generateSevenChunksOfFour(String chunk) {
        ArrayList<String> sevenChunks = new ArrayList<>();
        sevenChunks.add(chunk.substring(0,  4));
        sevenChunks.add(chunk.substring(4,  8));
        sevenChunks.add(chunk.substring(8,  12));
        sevenChunks.add(chunk.substring(12, 16));
        sevenChunks.add(chunk.substring(16, 20));
        sevenChunks.add(chunk.substring(20, 24));
        sevenChunks.add(chunk.substring(24));
        return sevenChunks;
    }

    private static int[] hashKetchum(int[] blocks) {
        int a = blocks[0];
        int b = blocks[1];
        int c = blocks[2]; 
        int d = blocks[3];
        int e = blocks[4];
        int f = blocks[5];
        int g = blocks[6];
        for(int i = 0; i < 28; i++) {
            int a1 = g;
            int b1 = (int)((((long)(a ^ c)) + ((long)b)) % (long)Math.pow(2, 32));
            int c1 = b ^ g;
            int d1 = c ^ e;
            int e1 = leftRotate(d, 2);
            int f1 = d ^ g;
            int g1 = f;

            a = a1;
            b = b1;
            c = c1;
            d = d1;
            e = e1;
            f = f1;
            g = g1;
        }
        return new int[]{a, b, c, d, e, f, g};
    }


    private static int leftRotate(int n, int d) {
        return (n << d) | (n >> (32 - d));
    }

    private static int[] addIntArrayMod(int[] a, int[] b) {
        int[] output = new int[a.length];
        for(int i = 0; i < a.length; i++) {
            long c = a[i];
            long d = b[i];
            long e = (c + d) % (long)Math.pow(2, 32);
            output[i] = (int)e;
        }
        return output;
    }

//    private static ArrayList<String> adjustChunks(ArrayList<String> sevenChunksOfFour) {
//        StringXORer xor = new StringXORer();
//
//        // initially set the adjusted to be the original value of the sevenChunksOfFour arraylist
//        ArrayList<String> adjusted = (ArrayList<String>) sevenChunksOfFour.clone();
//        for(int i = 0; i < 12; i++) {
//            // as we are messing with the adjusted list, make sure there is a copy of this original thin we're messing with
//            ArrayList<String> prevAdjusted = (ArrayList<String>) adjusted.clone(); // prevAdjusted is from the adjusted from the last run-through
//            adjusted.set(0, prevAdjusted.get(6));                                   // spot [0] is set to 6
//            adjusted.set(1, xor.encode(prevAdjusted.get(0), prevAdjusted.get(2)));  // [1] 1 comes from 0 ^ 2
//            adjusted.set(2, xor.encode(prevAdjusted.get(1), prevAdjusted.get(6)));  // [2] comes from 1 ^ 6
//            adjusted.set(3, xor.encode(prevAdjusted.get(2), prevAdjusted.get(4)));  // [3] comes from 2 ^ 5
//            adjusted.set(4, prevAdjusted.get(3));                                   // [4] comes from 3 << 2
//            adjusted.set(5, xor.encode(prevAdjusted.get(4), prevAdjusted.get(6)));  // [5] comes from 4 ^ 6
//            adjusted.set(6, prevAdjusted.get(5));                                   // [6] comes from spot 5
//        }
//
//        return adjusted;
//    }
}
