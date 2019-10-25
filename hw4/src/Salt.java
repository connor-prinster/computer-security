import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Salt {

    HashKetchum hk = new HashKetchum();

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
        String saltFile = "salts.txt";
        String passFile = "passwords.txt";
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
}
