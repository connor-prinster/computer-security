import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Salt {

    private HashKetchum hk = new HashKetchum();
    private String saltFile = "salts.txt";
    private String passFile = "passwords.txt";

    /**
     * produces and saves a salt and a hashed password value to the "database"
     * @param username the username given by user
     * @param password the password given by user
     * @return returns true if producing the salt and password saved correctly
     */
    Boolean generateSaltPasswords(String username, String password) {
        Salt s = new Salt();

        String salt = s.produceSalt();
        String hashPass = s.produceHashPass(password, salt);

        return (s.saveVals(username, hashPass, salt));
    }

    /**
     * the salt is a hashed random value, this produces a random
     * number and then hashes it
     * @return a stringified random value
     */
    private static String produceLongRandom() {
        int max = (int)(Math.random() * 1000);
        long longer = 0;
        for(int i = 0; i < max; i++) {
            longer += (long)(Math.random() * 1000);
        }
        return Long.toString(longer);
    }

    /**
     * produce a string salt value
     * @return a salt string
     */
    private String produceSalt() {
        return hk.performHash(produceLongRandom());
    }

    /**
     * perform a hashed password with the salt included
     * @param password the password to be hashed with salt
     * @param salt the salt to be hashed with password
     * @return a string value of the hashed password + salt value
     */
    private String produceHashPass(String password, String salt) {
        return hk.performHash(password + salt);
    }

    /**
     * save the username and hashed salty password to a file
     * save the username and salt to a file
     * @param username the user given username
     * @param password the salted and hashed password
     * @param salt the generated salt
     * @return a boolean if everything worked correctly
     */
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

    /**
     * returns the username attached to the hashed password and salt
     * @return a map with username as key attached to a map of salt and password
     */
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

    /**
     * get the salts and username values from the file
     * @return a map of usernames attached to salts
     */
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

    /**
     * get the salty hashed passwords
     * @return a map of salty hashed passwords attached to the username as a key
     */
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

    /**
     * nuke the files
     */
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
}
