import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Salt salt = new Salt();
//        salt.wipeFiles();
        salt.generateSaltPasswords("username", "password");
        salt.generateSaltPasswords("pikachu", "ash");
        salt.generateSaltPasswords("starmie", "misty");
        salt.generateSaltPasswords("onix", "brock");
        salt.generateSaltPasswords("jirachi", "max");
        salt.generateSaltPasswords("mewtwo", "Red");
        Map<String, Map<String, String>> users = salt.getUserPassSalt();
        System.out.println(users);
        for(Map.Entry<String, Map<String, String>> entry : users.entrySet()) {
            String user = entry.getKey();
            Map<String, String> vals = entry.getValue();
            System.out.println("user: " + user + " -> vals: " + vals);
            System.out.println();
        }
    }
}
