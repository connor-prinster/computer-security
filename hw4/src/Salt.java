import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Salt {

    HashKetchum hk = new HashKetchum();

    Boolean generateSaltPasswords(String username, String password) {
        Salt s = new Salt();
        String salt = s.produceSalt(password);
        String hashPass = s.produceHashPass(password, salt);

        return (s.saveVals(username, hashPass, salt));
    }

    public String produceSalt(String password) {
        return hk.performHash(password);
    }

    public String produceHashPass(String password, String salt) {
        return hk.performHash(password + salt);
    }

    public Boolean saveVals(String username, String password, String salt) {
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
}
