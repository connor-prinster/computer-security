import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenerateScamSites {

    private List<String> commonWords;
    private char[] specialCharacters;
    private Map<Character, List<Character>> replacementChars;

    public GenerateScamSites() {
        try {
            Path pf = new File("scammable.txt").toPath();
            commonWords = Files.readAllLines(pf);

            Path cf = new File("specialChars.txt").toPath();
            specialCharacters = Files.readAllLines(cf).get(0).toCharArray();

            Path rf = new File("possibleReplacements.txt").toPath();
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
        variationList = addSingleReplacements(variationList);

        return variationList;
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

    public static void generateFile() {
        GenerateScamSites pt = new GenerateScamSites();
        List<String> a = GenerateScamSites.FlattenMap(pt.CreateCommonVariationList());
        try {
            GenerateScamSites.DumpStringListToFile(a, "scams.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}