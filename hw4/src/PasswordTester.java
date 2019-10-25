import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PasswordTester {

    private List<String> commonWords;
    private char[] specialCharacters;
    private Map<Character, List<Character>> replacementChars;

    public PasswordTester() {
        try {
            Path pf = new File("Top30ReusedPasswords.txt").toPath();
            commonWords = Files.readAllLines(pf);

            Path cf = new File("SpecialCharacterList.txt").toPath();
            specialCharacters = Files.readAllLines(cf).get(0).toCharArray();

            Path rf = new File("Possible Character Replacements.txt").toPath();
            replacementChars = new HashMap<>();
            for (String s : Files.readAllLines(rf)) {
                char key = s.charAt(0);
                char value = s.charAt(3);
                List<Character> keylist;
                if(replacementChars.containsKey(key)) {
                    keylist = replacementChars.get(key);
                } else {
                    keylist = new ArrayList<Character>();
                }
                keylist.add(value);
                replacementChars.put(key, keylist);
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
        variationList = addNumbers(variationList);
        variationList = addSpecialCharacters(variationList);
        variationList = addReplacements(variationList);

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
        for (String string : inputs) {
            List<String> words = new ArrayList<>();
            words.add(string);

            for(int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);

                if(replacementChars.containsKey(c)) {
                    List<Character> clist = replacementChars.get(c);
                    List<String> moreWords = new ArrayList<>();
                    for(char d : clist) {
                        for(String w : words) {
                            moreWords.add(changeCharAt(w, d, i));
                        }
                    }
                    words.addAll(moreWords);
                }
            }
            list.addAll(words);
        }
        return list;
    }

    private String changeCharAt(String s, char a, int i) {
        StringBuilder b = new StringBuilder();
        b.append(s);
        b.setCharAt(i, a);
        return b.toString();
    }


    public static void main(String[] args) {
        PasswordTester pt = new PasswordTester();
        List<String> a = pt.GenerateWordVariations("some");
        System.out.println(String.format("The string %s has a total number of %d permutations", "some", a.size()));
    }
}