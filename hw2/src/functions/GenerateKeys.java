package functions;

import java.util.*;

public class GenerateKeys {
    public static void main(String[] args) {
        GenerateKeys gk = new GenerateKeys();
        System.out.println(gk.getKeys());
    }

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
        System.out.println("return keys");
        return keys;
    }

    private static KeyTuple generatePublic(int p, int q) {
        int m = p * q;
        int fn = (p - 1) * (q - 1);
        int rpn = genFn(fn);
        return new KeyTuple(rpn, m, fn);
    }

    private static KeyTuple generatePrivate(KeyTuple publicKt) {
        int rpn = publicKt.getRpn();
        int fn = publicKt.getFn();
        System.out.println("rpn: " + rpn + "\n fn: " + fn + "\n");
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
            mult = rpn * pd;
            System.out.println("mult: " + mult + "\npd: " + pd + "\n");
        }
        assert(((rpn * pd) % fn) == 1);
        return pd;
    }

    private static int genFn(int fn) {

        // lets just not mess with this
        if (fn == 1 || ((fn + 1) < 0)) {
            return 1;
        }

        int returnVal;
        if (fn % 7 != 0) {
            returnVal = 7;
            return returnVal;
        } else {
            returnVal = 8;
            while ((fn % returnVal) == 0) {
                returnVal++;
            }
            return returnVal;
        }
    }

    private static int generatePrimes(double val) {
        Random rand = new Random();
        ArrayList<Integer> arr = new ArrayList<>();
        Collections.addAll(arr, 2,3,5,7,11,13,17,19,23,29,31,37,41,43,47);//,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991,997,1009,1013,1019,1021,1031,1033,1039,1049,1051,1061,1063,1069,1087,1091,1093,1097,1103,1109,1117,1123,1129,1151,153,1163,1171,1181,1187,1193,1201,1213,1217,1223,1229,1231,1237,1249,1259,1277,1279,1283,1289,1291,1297,1301,1303,1307,1319,1321,1327,1361,1367,1373,1381,1399,1409,1423,1427,1429,1433,1439,1447,1451);
        int max = arr.size() - 1;
        int min = 0;
        int randIdx = rand.nextInt((max - min) + 1) + min;
        return arr.get(randIdx);
    }
}
