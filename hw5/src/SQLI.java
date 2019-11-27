import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLI {
    private String query;
    private final static double COMMENTS = 0.9;
    private final static double ALTERNATE_ENCODINGS = 0.9;
    private final static double FIRST_APOSTROPHE = 0.9;
    private final static double ALL_APOSTROPHE = 0.1;
    private final static double STATEMENTS = 0.9;
    private final static double SPACES = 0.1;
    private final static double TAUTOLOGY = 0.9;
    private final static double PIGGY = 0.9;

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
        System.out.println(count);
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
        String pattern = "0x[0-9a-fA-f]+";
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
        totalThreats += (comments + alternateEncodings + firstApostrophe + allApostrophe + statements + spaces + tautology + piggy);

        double threatLevel = 0;
        ArrayList<Double> threatList = new ArrayList<>();
        Map<Double, String> threats = new HashMap<>();

        if (comments != 0) {
            double commentsThreat = comments * COMMENTS;
            threatLevel+=commentsThreat;
            threats.put(commentsThreat, "Comments (\"--\")");
            Collections.addAll(threatList, commentsThreat);
        }
        if (alternateEncodings != 0) {
            double alternateEncodingsThreat = alternateEncodings * ALTERNATE_ENCODINGS;
            threatLevel+=alternateEncodingsThreat;
            threats.put(alternateEncodingsThreat, "Alternate Encodings ex. CHAR(), CONVERT(), ASCII(), EXEC(), EXEC(CHAR()), SUBSTRING()");
            Collections.addAll(threatList, alternateEncodingsThreat);
        }
        if (firstApostrophe != 0) {
            double firstApostropheThreat = firstApostrophe * FIRST_APOSTROPHE;
            threatLevel+=firstApostropheThreat;
            threats.put(firstApostropheThreat, "Apostrophe at the beginning of the input");
            Collections.addAll(threatList, firstApostropheThreat);
        }
        if (allApostrophe != 0) {
            double allApostropheThreat = allApostrophe * ALL_APOSTROPHE;
            threatLevel+=allApostropheThreat;
            threats.put(allApostropheThreat, "Apostrophies throughout the input that could close the input early");
            Collections.addAll(threatList, allApostropheThreat);
        }
        if (statements != 0) {
            double statementsThreat = statements * STATEMENTS;
            threatLevel+=statementsThreat;
            threats.put(statementsThreat, "Standard SQL Statements such as SELECT, FROM, WHERE, UNION, UPDATE, DROP, etc.");
            Collections.addAll(threatList, statementsThreat);
        }
        if (spaces != 0) {
            double spacesThreat = spaces * SPACES;
            threatLevel+=spacesThreat;
            threats.put(spacesThreat, "Spaces throughout the input");
            Collections.addAll(threatList, spacesThreat);
        }
        if (tautology != 0) {
            double tautologyThreat = tautology * TAUTOLOGY;
            threatLevel+=tautologyThreat;
            threats.put(tautologyThreat, "Tautology, such as 1=1, 5>A, etc.");
            Collections.addAll(threatList, tautologyThreat);
        }
        if (piggy != 0) {
            double piggyThreat = piggy * PIGGY;
            threatLevel+=piggyThreat;
            threats.put(piggyThreat, "Piggy-backed query where the input contains an apostrophe followed by a comment or semicolon");
            Collections.addAll(threatList, piggyThreat);
        }

//        Collections.addAll(threatList, commentsThreat,alternateEncodingsThreat, firstApostropheThreat, allApostropheThreat, statementsThreat, spacesThreat, tautologyThreat, piggyThreat);
        double max = 0;
        if(threatList.size() != 0) {
            max = Collections.max(threatList);
        }
        String largestThreat = threats.get(max);

        threatLevel *= 100;
        int threatPercent = 0;
        if(totalThreats != 0) {
            threatPercent = ((int)threatLevel / totalThreats);
        }

        if (threatPercent > 100) {
            threatPercent = 100;
        }

        return "Total threats: " + totalThreats + "" +
                "\nThreat of SQLI: " + threatPercent + "%" +
                "\nThe largest threat is " + largestThreat + "\n";
    }
}