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
            message = "aaaaaaaaaaaaaaaab";
        } else message = args[0];
        ArrayList<String> chunks = divideInto224BitChunks(message);
        frankensteinTheMfs(chunks);
    }

    private static final int RepetitionCount = 64;

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

    private static final int[] piInQuats = new int[]
            {   0x3243F6A8, 0x885A308D,
                    0x313198A2, 0xE0370734,
                    0x4A409382, 0x2299F31D,
                    0x0082EFA9, 0x8EC4E6C8,
                    0x9452821E, 0x638D0137,
                    0x7BE5466C, 0xF34E90C6};

    private static int[] hashKetchum(int[] blocks) {
        int a = blocks[0];
        int b = blocks[1];
        int c = blocks[2];
        int d = blocks[3];
        int e = blocks[4];
        int f = blocks[5];
        int g = blocks[6];
        for(int i = 0; i < RepetitionCount; i++) {
            int a1 = g;
            int b1 = addMod32(a, piInQuats[i % piInQuats.length]);
            int c1 = addMod32(b, b ^ c ^ d);
            int d1 = c;
            int e1 = addMod32(d, (c | e) ^ a);
            int f1 = addMod32(leftRotate(e, 2), g) ^ blocks[((i + 3) * 10) % 7];
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

    private static int addMod32(int a, int b) {
        long al = (long)a;
        long bl = (long)b;
        long c = al + bl;
        return (int)Long.remainderUnsigned(c, (long)0xFFFFFFFF);
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
}
