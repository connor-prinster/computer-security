import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Salt {

    HashKetchum hk = new HashKetchum();
    String saltFile = "salts.txt";
    String passFile = "passwords.txt";

    Boolean generateSaltPasswords(String username, String password) {
        Salt s = new Salt();

        String salt = s.produceSalt(produceLong());
        String hashPass = s.produceHashPass(password, salt);

        return (s.saveVals(username, hashPass, salt));
    }

    private static String produceLong() {
        int max = (int)(Math.random() * 1000);
        long longer = 0;
        for(int i = 0; i < max; i++) {
            longer += (long)(Math.random() * 1000);
        }
        return Long.toString(longer);
    }

    private String produceSalt(String password) {
        return hk.performHash(password);
    }

    private String produceHashPass(String password, String salt) {
        return hk.performHash(password + salt);
    }

    private Boolean saveVals(String username, String password, String salt) {
        try {
            // ----- Saves the username and salt value to a file ----- //
            // the FileWriter requires the 'true' to append to the existing file
            BufferedWriter saltWriter = new BufferedWriter(new FileWriter(saltFile, true));
            String saltComb = username + " " + salt + "\n";
            saltWriter.write(saltComb);
            saltWriter.close();

            // ----- Saves the username and hashed password to a file ----- //
            BufferedWriter passWriter = new BufferedWriter(new FileWriter(passFile, true));
            String passComb = username + " " + password + "\n";
            passWriter.write(passComb);
            passWriter.close();

        } catch(IOException e) {
            System.out.println("Error with saving salt");
            return false;
        }
        return true;
    }

    public void wipeFiles() {
        String saltFile = "salts.txt";
        String passFile = "passwords.txt";

        File salt = new File(saltFile);
        File pass = new File(passFile);

        if(salt.delete() && pass.delete()) {
            System.out.println("Files deleted successfully");
        }
        else {
            System.out.println("Something went wrong, check the files: "
                    + saltFile + " and " + passFile);
        }

    }

    public Map<String, Map<String, String>> getUserPassSalt() {
        Map<String, Map<String, String>> userPassSalt = new HashMap<>();

        Map<String, String> salts = getSalts();
        Map<String, String> hashPass = getPassHash();

        for(Map.Entry<String, String> saltEntry : salts.entrySet()) {
            Map<String, String> userVals = new HashMap<>();

            String user = saltEntry.getKey();
            String saltVal = saltEntry.getValue();
            String passVal = hashPass.get(user);

            userVals.put("salt", saltVal);
            userVals.put("password", passVal);

            userPassSalt.put(user, userVals);
        }

        return userPassSalt;
    }

    private Map<String, String> getSalts() {
        BufferedReader inSalt;
        Map<String, String> userSalt = new HashMap<>();
        try{
            inSalt = new BufferedReader(new FileReader(saltFile));
            String in;
            while((in = inSalt.readLine()) != null) {
                String[] res = in.split(" ");
                userSalt.put(res[0], res[1]);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

        return userSalt;
    }

    private Map<String, String> getPassHash() {
        BufferedReader inSalt;
        Map<String, String> userPassHash = new HashMap<>();
        try{
            inSalt = new BufferedReader(new FileReader(passFile));
            String in = "";
            while((in = inSalt.readLine()) != null) {
                String[] res = in.split(" ");
                userPassHash.put(res[0], res[1]);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

        return userPassHash;
    }
}
