package functions;

public class KeyTuple {
    private int val1;
    private int val2;

    public KeyTuple(int a, int b) {
        val1 = a;
        val2 = b;
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    @Override
    public String toString() {
        return "(" + val1 + ", " + val2 + ")";
    }
}
