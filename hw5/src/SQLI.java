import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLI {
    private String statement;

    public SQLI(String statement) {
        this.statement = statement;
    }

    private int checkUnion(String str) {
        String union = "union";
        return matchCount(union, str);
    }

    private int matchCount(String regex, String test) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(test);
        int count = 0;
        while(m.find()) count++;
        return count;
    }
}