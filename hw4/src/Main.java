public class Main {

    public static void main(String[] args) {
        Salt salt = new Salt();
        salt.generateSaltPasswords("username", "password");
        salt.generateSaltPasswords("pikachu", "ash");
        salt.generateSaltPasswords("starmie", "misty");
        salt.generateSaltPasswords("onix", "brock");
        salt.generateSaltPasswords("jirachi", "max");
        salt.generateSaltPasswords("mewtwo", "Red");
    }
}
