import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLI {
    private String query;

    public SQLI(String query) {
        this.query = query.toUpperCase();
    }

    public int checkUnion() {
        String union = "UNION";
        return matchCount(union);
    }
    
    public int checkTautology() {
        Matcher m = (Pattern.compile("\\s(\\w*)=(\\w*)").matcher(query));;
        int count = 0;
        while(m.find()) {
            if(m.group(0).equals(m.group(1))) count++;
        }
        return count;
    }

    public int checkSelect() {
        String select = "SELECT";
        return matchCount(select);
    }

    public int checkUpdate() {
        String update = "UPDATE";
        return matchCount(update);
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
        String charact = "CHAR(";
        return matchCount(charact);
    }

    public int checkAscii() {
        String asc = "ASCII(";
        return matchCount(asc);
    }

    public int checkSubstring() {
        String sub = "SUBSTRING(";
        return matchCount(sub);
    }

    private int matchCount(String regex) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(query);
        int count = 0;
        while(m.find()) count++;
        return count;
    }

    public int containsComments() {
        String comment = "--";
        return matchCount(comment);
    }
}