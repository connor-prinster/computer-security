public class Main {
    public static void main(String[] args) {
        String cipherText = "ntneudihyyeodkoefte";
        String plainText = "youneedtofindthekey";
        String plainText2 = "youneedtofindthethekey";
        String cipherText2 = "nfeyuoheyddeottkeitenh";

        int[] columnKey = BreakColumnar.Cryptanalize(plainText, cipherText);

        for(int i = 0; i < columnKey.length; i++) {
            System.out.print(columnKey[i]);
        }
    }
}