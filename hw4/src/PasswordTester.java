import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PasswordTester {

    private List<String> commonWords;
    private char[] specialCharacters;
    private List<CharacterPair> replacementChars;

    private class CharacterPair {
        public char Key;
        public char Value;

        public CharacterPair(char key, char value) {
            this.Key = key;
            this.Value = value;
        }
    }

    public PasswordTester() {
        try {
            Path pf = new File("Top30ReusedPasswords.txt").toPath();
            commonWords = Files.readAllLines(pf);

            Path cf = new File("SpecialCharacterList.txt").toPath();
            specialCharacters = Files.readAllLines(cf).get(0).toCharArray();

            Path rf = new File("Possible Character Replacements.txt").toPath();
            replacementChars = new ArrayList<>();
            for (String s : Files.readAllLines(rf)) {
                replacementChars.add(new CharacterPair(s.charAt(0), s.charAt(3)));
            }

        } catch(IOException e) {
            System.err.println("Password tester could not find password list");
        }
    }

    public List<String> GenerateWordVariations(String input) {
        List<String> variationList = new ArrayList<>();
        variationList.add(input);

        variationList = reverseWords(variationList);
        variationList = repeatWords(variationList);
        variationList = capitalizeWords(variationList);

        return variationList;
    }

    private List<String> reverseWords(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String string : inputs) {
            list.add(string);
            list.add(new StringBuilder().append(string).reverse().toString());
        }
        return list;
    }

    private List<String> repeatWords(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String string : inputs) {
            list.add(string);
            list.add(string + string);
            list.add(string + string + string);
        }
        return list;
    }

    private List<String> capitalizeWords(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String string : inputs) {
            list.add(string);
            list.add(string.substring(0, 1).toUpperCase() + string.substring(1));
        }
        return list;
    }

    private List<String> addNumbers(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String string : inputs) {
            list.add(string);
            for(int i = 0; i < 10; i++) {
                list.add(string + Integer.toString(i));
            }
        }
        return list;
    }

    private List<String> addSpecialCharacters(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String string : inputs) {
            list.add(string);
            for (char c : specialCharacters) {
                list.add(string + c);
            }
        }
        return list;
    }

    private List<String> addReplacements(List<String> inputs) {
        List<String> list = new ArrayList<>();

        return list;
    }


    public static void main(String[] args) {
        PasswordTester pt = new PasswordTester();
        List<String> a = pt.GenerateWordVariations("something");
        for (String string : a) {
            System.out.println(string);
        }
    }
}