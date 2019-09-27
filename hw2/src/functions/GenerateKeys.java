package functions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GenerateKeys {
    public static void main(String[] args) {

        System.out.println(
                generatePublic(
                        generatePrimes(Math.random()),
                        generatePrimes(Math.random())
                ).toString()
        );
    }

    public static KeyTuple generatePublic(int p, int q) {
        int m = p * q;
        int rpn = lcm(p - 1, q - 1);
        return new KeyTuple(rpn, m);
    }

    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);
        int absHigherNumber = Math.max(absNumber1, absNumber2);
        int absLowerNumber = Math.min(absNumber1, absNumber2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    public static int generatePrimes(double val) {
        int n = (int)(val * 100);
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers.get(primeNumbers.size() - 1);
    }
}
