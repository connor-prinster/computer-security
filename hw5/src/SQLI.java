import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLI {
    private String statement;

    public SQLI(String statement) {
        this.statement = statement.toUpperCase();
    }

    private int checkUnion(String str) {
        String union = "UNION";
        return matchCount(union, str);
    }
    
    private int checkTautology(String str) {
        Matcher m = (Pattern.compile("\\s(\\w*)=(\\w*)").matcher(str));;
        int count = 0;
        while(m.find()) {
            if(m.group(0).equals(m.group(1))) count++;
        }
        return count;
    }

    private int checkSelect(String str) {
        String select = "SELECT";
        return matchCount(select, str);
    }

    private int checkUpdate(String str) {
        String update = "UPDATE";
        return matchCount(update, str);
    }

    private int checkFirstApostrophe(String str) {
        Character apostrophe = '\'';
        return apostrophe.equals(str.charAt(0)) ? 1 : 0;
    }

    private int checkAllApostrophe(String str) {
        String apostrophe = "\'";
        return matchCount(apostrophe, str);
    }

    private int matchCount(String regex, String test) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(test);
        int count = 0;
        while(m.find()) count++;
        return count;
    }
}