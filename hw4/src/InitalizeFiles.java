import java.util.Map;

public class InitalizeFiles {

    public static void main(String[] args) {
        Salt salt = new Salt();
        salt.wipeFiles();
        salt.generateSaltPasswords("username", "password");
        salt.generateSaltPasswords("pikachu", "ash");
        salt.generateSaltPasswords("starmie", "misty");
        salt.generateSaltPasswords("onix", "brock");
        salt.generateSaltPasswords("jirachi", "max");
        salt.generateSaltPasswords("mewtwo", "Red");
        Map<String, Map<String, String>> users = salt.getUserPassSalt();
        System.out.println(users);
        System.out.println(users.get("jirachi"));
    }
}
