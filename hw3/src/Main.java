import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String message = args[0];
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

    private static void frankensteinTheMfs(ArrayList<String> chunks) {
        for(int i = 0; i < chunks.size(); i++) {
            ArrayList<String> sevenChunksOfFour = generateSevenChunksOfFour(chunks.get(i));
            hashKetchum(sevenChunksOfFour);
        }
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

    private static void hashKetchum(ArrayList<String> sevenChunksOfFour) {

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
