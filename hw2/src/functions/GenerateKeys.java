package functions;

import java.util.*;

public class GenerateKeys {
//    public static void main(String[] args) {
//        KeyTuple publicKey = generatePublic(7, 17);
//        KeyTuple privateKey = generatePrivate( publicKey );
//
//        System.out.println(publicKey);
//        System.out.println(privateKey);
//    }

    public Map<String, Map<String, Integer>> getKeys() {
        KeyTuple publicKey = generatePublic(
                generatePrimes(Math.random()),
                generatePrimes((Math.random()))
        );
        KeyTuple privateKey = generatePrivate(publicKey);

        Map<String, Integer> publicKeyMap = new HashMap<>();
        publicKeyMap.put("rpn", publicKey.getRpn());
        publicKeyMap.put("m", publicKey.getM());

        Map<String, Integer> privateKeyMap = new HashMap<>();
        privateKeyMap.put("pd", privateKey.getPd());
        privateKeyMap.put("m", privateKey.getM());

        Map<String, Map<String, Integer>> keys = new HashMap<>();
        keys.put("public", publicKeyMap);
        keys.put("private", privateKeyMap);
        return keys;
    }

    private static KeyTuple generatePublic(int p, int q) {
        int m = p * q;
        int fn = (p - 1) * (q - 1);
        int rpn = lcm(fn);
        return new KeyTuple(rpn, m, fn);
    }

    private static KeyTuple generatePrivate(KeyTuple publicKt) {
        int pd = satisfyPdFunction(publicKt.getRpn(), publicKt.getFn());
        return new KeyTuple(
                publicKt.getRpn(), publicKt.getM(), publicKt.getFn(), pd
        );
    }

    private static int satisfyPdFunction(int rpn, int fn) {
        int pd = 0;
        // function: (rpn * pd) % fn = 1
        int mult = rpn * pd;
        while(!((mult % fn) == 1)) {
            pd++;
            mult = (rpn * pd) % fn;
        }
        assert(((rpn * pd) % fn) == 1);
        return pd;
    }

    private static int lcm(int fn) {
        return 7;
    }

    private static int generatePrimes(double val) {
        // make sure that the multiplication of the two primes is less than 10,000
        int n = (int)(val * 100) % 100;
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
        return
                primeNumbers.size() == 0 ? 0 : primeNumbers.get(primeNumbers.size() - 1);
    }
}
