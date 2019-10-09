package PACKAGE_NAME;

public class Main {
    public static void main(String[] args) {
        
    }

    void hash() {

    }

    static int RotateLeftTwice(int a) {
        int b = a << 2;
        int c =  (a & 0xC0000000) / (0xC0000000);
        return b + c;
    }
}
