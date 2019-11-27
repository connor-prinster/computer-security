import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLI {
    private String query;
    private final static double COMMENTS = 0.167;
    private final static double ALTERNATE_ENCODINGS = 0.33;
    private final static double FIRST_APOSTROPHE = 0.167;
    private final static double STATEMENTS = 0.33;
    private final static double SPACES = 0.05;
    private final static double TAUTOLOGY = 0.2;
    private final static double PIGGY = 0.3;
    private final static double HEX = 0.02;
    private final static double INFERENCE = 0.2;
    private final static double SEMICOLON = 0.33;
    private final static double ALLIGATORS = 0.05;
    private final static double ALL_APOSTROPHE = 0.05;

    public SQLI(String query) {
        this.query = query.toUpperCase();
    }

    public int checkUnion() {
        return matchCount("UNION");
    }
    
    public int checkTautology() {
        Matcher m = (Pattern.compile("\\s(\\w*)=(\\w*)").matcher(query));
        int count = 0;
        while(m.find()) {
            String[] split = m.group().split("=");
            if(split.length > 1) {
                if(split[0].trim().equals(split[1].trim())) {
                    count++;
                }
            }
        }
        return count;
    }

    public int checkPiggyBack() {
        return matchCount("\' --") + matchCount("\'--") + matchCount("\';");
    }

    public int checkAlternateEncodings() {
        return matchCount("CHAR") + matchCount("EXEC") + matchCount("CONVERT") + checkChar() + checkAscii() + checkSubstring();
    }

    public int checkSpaces() {
        return matchCount(" ");
    }

    public int checkFrom() {
        return matchCount("FROM");
    }

    public int checkSelect() {
        return matchCount("SELECT");
    }

    public int checkUpdate() {
        return matchCount("UPDATE");
    }

    public int checkDrop() {
        return matchCount("DROP");
    }

    public int checkWhere() {
        return matchCount("WHERE");
    }

    public int checkFirstApostrophe() {
        Character apostrophe = '\'';
        return apostrophe.equals(query.charAt(0)) ? 1 : 0;
    }

    public int checkAllApostrophe() {
        String apostrophe = "\'";
        return matchCount(apostrophe);
    }

    public int checkChar() {
        String charact = "CHAR";
        return matchCount(charact);
    }

    public int checkAscii() {
        return matchCount("ASCII");
    }

    public int checkSubstring() {
        return matchCount("SUBSTRING");
    }

    private int matchCount(String regex) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(query);
        int count = 0;
        while(m.find()) count++;
        return count;
    }

    public int containsComments() {
        return matchCount("--");
    }

    public int containsHex() {
        String pattern = "0X[0-9a-fA-f]+";
        return matchCount(pattern);
    }

    public int containsInference() {
        return matchCount("WAITFOR") + matchCount("[0|1]=[0|1][\\w\\s]+[0|1]=[0|1]");
    }

    public int checkSemicolon() {
        return matchCount(";");
    }

    public int checkAlligators() {
        return matchCount("<") + matchCount(">");
    }

    public String returnThreatString() {
        int totalThreats = 0;
        int comments = containsComments();
        int alternateEncodings = checkAlternateEncodings();
        int firstApostrophe = checkFirstApostrophe();
        int allApostrophe = checkAllApostrophe();
        int statements = checkSelect() + checkFrom() + checkUnion() + checkUpdate() + checkDrop() + checkWhere();
        int spaces = checkSpaces();
        int tautology = checkTautology();
        int piggy = checkPiggyBack();
        int hex = containsHex();
        int inference = containsInference();
        int semicolon = checkSemicolon();
        int alligators = checkAlligators();
        totalThreats += (comments + alternateEncodings + firstApostrophe + allApostrophe + statements + spaces + tautology + piggy + hex + inference + semicolon + alligators);


        double threatLevel = 0;
        ArrayList<Double> threatList = new ArrayList<>();
        Map<Double, String> threats = new HashMap<>();

        double commentsThreat = comments * COMMENTS;
        double alternateEncodingsThreat = alternateEncodings * ALTERNATE_ENCODINGS;
        double allApostropheThreat = allApostrophe * ALL_APOSTROPHE;
        double firstApostropheThreat = firstApostrophe * FIRST_APOSTROPHE;
        double statementsThreat = statements * STATEMENTS;
        double spacesThreat = spaces * SPACES;
        double tautologyThreat = tautology * TAUTOLOGY;
        double piggyThreat = piggy * PIGGY;
        double hexThreat = hex * HEX;
        double inferenceThreat = inference * INFERENCE;
        double semicolonThreat = checkSemicolon() * SEMICOLON;
        double alligatorsThreat = checkAllApostrophe() * ALLIGATORS;
        Collections.addAll(threatList, commentsThreat, alternateEncodingsThreat, firstApostropheThreat, statementsThreat, spacesThreat, tautologyThreat, piggyThreat, hexThreat, inferenceThreat, semicolonThreat, alligatorsThreat);
        double max = Collections.max(threatList);
        threatLevel += (commentsThreat + alternateEncodingsThreat + allApostropheThreat + firstApostropheThreat + statementsThreat + spacesThreat + tautologyThreat + piggyThreat + hexThreat + inferenceThreat + semicolonThreat + alligatorsThreat);

        threats.put(commentsThreat, "Comments (\"--\")");
        threats.put(alternateEncodingsThreat, "Alternate Encodings ex. CHAR(), CONVERT(), ASCII(), EXEC(), EXEC(CHAR()), SUBSTRING()");
        threats.put(firstApostropheThreat, "Apostrophe at the beginning of the input");
        threats.put(allApostropheThreat, "Apostrophies throughout the input that could close the input early");
        threats.put(statementsThreat, "Standard SQL Statements such as SELECT, FROM, WHERE, UNION, UPDATE, DROP, etc.");
        threats.put(spacesThreat, "Spaces throughout the input");
        threats.put(tautologyThreat, "Tautology, such as 1=1, 5>A, etc.");
        threats.put(piggyThreat, "Piggy-backed query where the input contains an apostrophe followed by a comment or semicolon");
        threats.put(hexThreat, "Concern of users putting a hex encoded string");
        threats.put(inferenceThreat, "Concern of an inference attack");
        threats.put(semicolonThreat, "There are semicolons in this query");
        threats.put(alligatorsThreat, "No sane human uses semicolons in their queries unless they're shifty");
        String largestThreat = threats.get(max);

        threatLevel *= 100;
        threatLevel %= 100;
        String threatPercent = threatLevel + "%";

        return "Total threats: " + totalThreats + "" +
                "\nThreat of SQLI Attack: " + threatPercent +
                "\nThe largest threat is " + largestThreat + "\n";
    }
}