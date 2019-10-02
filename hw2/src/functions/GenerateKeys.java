package functions;

import java.util.*;

public class GenerateKeys {
    public static void main(String[] args) {
        GenerateKeys gk = new GenerateKeys();
        System.out.println(gk.getKeys());
    }

    /**
     * generates map containing public and private keys
     * @return a map of maps (each map a key)
     */
    public Map<String, Map<String, Integer>> getKeys() {

        // generate the private key
        KeyTuple publicKey = generatePublic(
                generatePrimes(Math.random()),
                generatePrimes((Math.random()))
        );

        // using the public key, generate the private one
        KeyTuple privateKey = generatePrivate(publicKey);

        // generate public key map
        Map<String, Integer> publicKeyMap = new HashMap<>();
        publicKeyMap.put("rpn", publicKey.getRpn());
        publicKeyMap.put("m", publicKey.getM());

        // generate private key map
        Map<String, Integer> privateKeyMap = new HashMap<>();
        privateKeyMap.put("pd", privateKey.getPd());
        privateKeyMap.put("m", privateKey.getM());

        // generate map of private and public maps
        Map<String, Map<String, Integer>> keys = new HashMap<>();
        keys.put("public", publicKeyMap);
        keys.put("private", privateKeyMap);
        return keys;

    }

    /**
     * will generate a public keytuple
     * @param p the first prime number
     * @param q the second prime number
     * @return returns a KeyTuple holding the e-value and the n-value for public keys
     */
    private static KeyTuple generatePublic(int p, int q) {
        // public key is very easily generated using the algorithms shown below
        int m = p * q;
        int fn = (p - 1) * (q - 1);
        int rpn = genRpn(fn);
        return new KeyTuple(rpn, m, fn);
    }

    /**
     * generates a private Keytuple
     * @param publicKt requires data from the public key generated prior
     * @return a Keytuple holding the e-value and n-value for private keys
     */
    private static KeyTuple generatePrivate(KeyTuple publicKt) {
        // many variables required are from the public key already.
        // Don't repeat anything that doesn't need to be done again
        int rpn = publicKt.getRpn();
        int fn = publicKt.getFn();
        int pd = satisfyPdFunction(rpn, fn);
        int m = publicKt.getM();
        return new KeyTuple( rpn, m, fn, pd );
    }

    /**
     *
     * @param rpn relatively private number generated in the publickey generation function
     * @param fn the (p-1) * (p-q) value generated in the public key generation
     * @return an int to satisfy 'pd' in the equation: (r * pd) % fn == 1
     */
    private static int satisfyPdFunction(int rpn, int fn) {
        int pd = 0;
        // function to replicate: (rpn * pd) % fn = 1
        int mult = rpn * pd;
        while(!((mult % fn) == 1)) {
            pd++;
            mult = rpn * pd;
        }
        return pd;
    }

    /**
     * Generate the relative prime value
     * @param fn the fn value of the public key ( (p - 1) * (q - 1) )
     * @return the relatively prime value given the fn
     */
    private static int genRpn(int fn) {
        // if this is a 1 or the bounds would be weird, just return a 1
        if (fn == 1 || fn < 0) {
            return 1;
        }

        int returnVal;
        if (fn % 7 != 0) { // return an arbitrary 7 if 7 is a relative prime
            returnVal = 7;
            return returnVal;
        } else { // return something 8 or greater otherwise
            returnVal = 8;
            while ((fn % returnVal) == 0) {
                returnVal++;
            }
            return returnVal;
        }
    }

    /**
     * will generate an integer prime value given a double value from Math.random()
     * @param val the randomly generated value (will be less than 1)
     * @return a randomly selected prime value
     */
    private static int generatePrimes(double val) {
        ArrayList<Integer> arr = new ArrayList<>();
        // list of prime values. This is cut down to avoid too large of an 'm' value
        Collections.addAll(arr, 2,3,5,7,11,13,17,19,23,29,31,37);//,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991,997,1009,1013,1019,1021,1031,1033,1039,1049,1051,1061,1063,1069,1087,1091,1093,1097,1103,1109,1117,1123,1129,1151,153,1163,1171,1181,1187,1193,1201,1213,1217,1223,1229,1231,1237,1249,1259,1277,1279,1283,1289,1291,1297,1301,1303,1307,1319,1321,1327,1361,1367,1373,1381,1399,1409,1423,1427,1429,1433,1439,1447,1451);
        // as val will be less than 0 due to Math.rand(), multiply it by 1000 and cast to an int
        int randIdx = (int)(val * 1000) % arr.size();
        // get the randomly chosen index
        return arr.get(randIdx);
    }
}
