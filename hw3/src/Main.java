import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String message = args[0];
        ArrayList<String> chunks = divideInto224BitChuncks(message);
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

    void hash() {

    }
}
