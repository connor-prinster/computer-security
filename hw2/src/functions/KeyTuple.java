package functions;

import java.util.HashMap;
import java.util.Map;

public class KeyTuple {
    private int rpn;
    private int m;
    private int fn;
    private int pd;

    public KeyTuple(int rpn, int m, int fn) {
        this.rpn = rpn;
        this.m = m;
        this.fn = fn;
        this.pd = -1;
    }

    public KeyTuple(int rpn, int m, int fn, int pd) {
        this.rpn = rpn;
        this.m = m;
        this.fn = fn;
        this.pd = pd;
    }



    public int getRpn() {
        return rpn;
    }

    public int getM() {
        return m;
    }

    public int getFn() {
        return fn;
    }

    public int getPd() {
        return pd;
    }

    @Override
    public String toString() {
        // if 'pd' is -1, it is a public key
        return (
                (pd == -1) ?
                        "\nPublic Key:\n(rpn: " + rpn + ", m: " + m + ", fn: " + fn + ")\n"
                        :
                        "\nPrivate Key:\n(rpn: " + rpn + ", pd: " + pd  + ", m:" + m + ", fn: " + fn + ")\n"
        );
    }
}
