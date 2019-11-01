import java.nio.file.Files;
import java.io.BufferedWriter;
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
    
    public static void DumpStringListToFile(List<String> list, String file) throws IOException {
        BufferedWriter out = Files.newBufferedWriter(new File(file).toPath());
        for (String string : list) {
            out.write(string);
            out.newLine();
        }
        out.flush();
        out.close();
    }

    public List<String> GenerateWordVariations(String input) {
        List<String> variationList = new ArrayList<>();
        variationList.add(input);

        //System.out.println("Reversing word");
        variationList = reverseWords(variationList);

        //System.out.println("Repeating words");
        variationList = repeatWords(variationList);

        //System.out.println("Captializing words");
        variationList = capitalizeWords(variationList);

        //System.out.println("Adding numbers");
        variationList = addNumbers(variationList);

        //System.out.println("Adding special characters");
        variationList = addSpecialCharacters(variationList);

        //System.out.println("Adding every possible replacement");
        //variationList = addReplacements(variationList);
        
        //System.out.println("Adding single character replacements");
        variationList = addSingleReplacements(variationList);

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

    private List<String> addSingleReplacements(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String string : inputs) {
            list.add(string);

            for(int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if(replacementChars.containsKey(c)) {
                    List<Character> clist = replacementChars.get(c);
                    for(char d : clist) {
                        list.add(changeCharAt(string, d, i));
                    }
                }
            }
        }
        return list;
    }

    private String changeCharAt(String s, char a, int i) {
        StringBuilder b = new StringBuilder();
        b.append(s);
        b.setCharAt(i, a);
        return b.toString();
    }

    public Map<String, List<String>> CreateCommonVariationList() {
        Map<String, List<String>> wordToVariations = new HashMap<>();
        for (String string : commonWords) {
            wordToVariations.put(string, GenerateWordVariations(string));
        }
        return wordToVariations;
    }

    public static List<String> FlattenMap(Map<String, List<String>> map) {
        List<String> list = new ArrayList<>();
        for (List<String> aList : map.values()) {
            list.addAll(aList);
        }
        return list;
    }

    public List<String> CreatePersonalPasswords(Map<String, String> info) {
        List<String> list = new ArrayList<>();

        return list;
    }

    public List<String> CreateFullInfoPasswordList(Map<String, String> info) {
        List<String> list = new ArrayList<>();

        return list;
    }

    public static void main(String[] args) {
        PasswordTester pt = new PasswordTester();
        List<String> a = FlattenMap(pt.CreateCommonVariationList());
        System.out.println(String.format("Total number of %d permutations", a.size()));
        try {
            DumpStringListToFile(a, "temporary_FILE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}