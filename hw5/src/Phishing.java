import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phishing {
    private String emailAddress;
    private String emailBody;
    private final static double SPEAR_FISHING = 0.2;
    private final static double URLS = 0.2;
    private final static double CONSEQUENCES = 0.2;
    private final static double REDEMPTION = 0.15;
    private final static double IMMEDIACY = 0.1;
    private final static double AUTHORITY = 0.1;
    private final static double MISSPELLED = 0.05;
    private final static int COUNT_CHECKS = 7;

    public Phishing(String emailAddress, String emailBody) {
        assert(SPEAR_FISHING + URLS + CONSEQUENCES + REDEMPTION + IMMEDIACY + AUTHORITY + MISSPELLED == 1);
        this.emailAddress = emailAddress;
        this.emailBody = emailBody;
        normalizeEmailBody();
    }

    private int checkUrlThreat() {
        int count = 0;

        List<String> urls = new ArrayList<>();

        String[] emailWords = emailBody.split("\b");

        for (String word : emailWords) {
            try {
                new URL(word).toURI();
                urls.add(word);
                count++;
            }
            catch (Exception e) {
                // do nothing
            }
        }

        for (String url : urls) {
            if (!(url.contains("https") || url.contains("shttp"))) {    // not ssl secured
                count++;
            }
            if (url.split(".").length > 3) {    // look for extra strings in the domain name
                count++;
            }
            if (url.matches("[^a-zA-Z/.]")) {     // look for numbers and special characters in the domain name
                count++;
            }
            String reg = "(login|update|verify)";
            count += matchCount(reg, url);
        }

        return count;
    }

    private int checkEmailThreat() {
        int count = 0;

        String[] splitAddress = emailAddress.split("@");
        if(splitAddress.length < 1) {
            return 0;
        }
        String user = splitAddress[0];
        String company = splitAddress[1];
        String[] splitCompany = company.split("\\.");
        String companyName = splitCompany[0];
        String domain = splitCompany[1];

        String containsDigits = "[0-9]";
        String containsAlpha = "[A-Za-z]";
        String containsHyphen = "[-]";
        String containsUnderscore = "[_]";
        String reputableDomain = "(com|org|edu|net|gov)";

        // === Check the Username === //
        if(matchCount(containsAlpha, user) == 0) { // if there are no letters in the username, probs sketch
            count++;
        }

        if(matchCount(containsHyphen, companyName) > 0) { // catches login-microsoft or whatever
            count++;
        }

        if(matchCount(containsUnderscore, companyName) > 0) { //catches *_validSite.com or whatever
            count++;
        }

        // === Check the Domain === //
        if(splitCompany.length > 2) { // checks to see if the domain name contains more than just *.com. For example paypal.paymentsnow.com would flag it
            count++;
        }

        if(matchCount(reputableDomain, domain) == 0) {
            count++;
        }

        if(matchCount(containsAlpha, domain) == 0) { // see if no characters in domain (for example *.111 probably isn't legit)
            count++;
        }

        if(matchCount(containsDigits, domain) > 0) { // see if there are ANY numbers in domain (*.paypal2 probably isn't valid)
            count++;
        }

        return count;
    }

    private void normalizeEmailBody() {
        String normalized = "";
        String[] parsed = emailBody.split(" ");

        for(String str : parsed) {
            normalized += str.replaceAll("[^a-zA-Z0-9@!$]", "").toLowerCase();
            normalized += " ";
        }

        emailBody = normalized;
    }

    private int checkPositionsOfAuthority() {
        String reg = "(president|vice|prophet|mister|judge|caliph|centurion|chief|consort|count|countess|doctor|earl|countess|emperor|empress|esquire|squire|admiral|master|herald|highness|majesty|lady|mandarin|mayor|saint|sergeant|tsar|tsaritsa|prince|king|princess|baron|baroness|darth|bishop|pastor|rabbi|deacon|priest|cardinal|chaplain|church|priestess|pope|vicar|dalai lama|patriarch|archbishop|monk|abbess|nun|apostle|elder|reverend|chaplain|god|saint|imam|mullah|sultan|sultana|witch|priestess|druid|chairman|officer|lord)";
        return matchCount(reg, emailBody);
    }

    private int CheckConsequences() {
        String reg = "(close|closed|compromised|compromise|action|expose|keylogger|bitcoin|misdemeanor|humiliation|sextape|warrant|arrest|unpleasant|illegal)";
        return matchCount(reg, emailBody);
    }

    private int CheckRedemption() {
        String reg = "(congratulations|redeem|claim|take|won|win|prize|contest|winner|winning|offer|gift|surprise|compensation|delivery|inheritance|deposit|fee|success|check|cheque|reward|award|payment)";
        return matchCount(reg, emailBody);
    }

    private int CheckImmediacy() {
        String reg = "(now|urgent|urgently|immediate|immediately|soon|expire|expired|expires)";
        return matchCount(reg, emailBody);
    }

    private int checkDomainMisspellings() {
        List<String> wordList;
        try {
            Path path = new File("scams.txt").toPath();
            wordList = Files.readAllLines(path);
            String reg = "(";
            for(String str : wordList) {
                reg += (str + "|");
            }
            reg = reg.substring(0, reg.length() - 1);
            reg += ")";
            return matchCount(reg, emailBody);
        }
        catch (IOException ie) {
            System.out.println("Cannot get data from scams file");
        }
        return 0;
    }

    private int matchCount(String regex, String test) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(test);
        int count = 0;
        while(m.find()) count++;
        return count;
    }

    public String returnThreatString() {
        int totalThreats = 0;
        int positionsOfAuthority = checkPositionsOfAuthority();
        int spearFishing = checkEmailThreat();
        int consequences = CheckConsequences();
        int redemption = CheckRedemption();
        int immediacy = CheckImmediacy();
        int url = checkUrlThreat();
        int misspelled = checkDomainMisspellings();
        totalThreats += (positionsOfAuthority + spearFishing + consequences + redemption + immediacy + url + misspelled);

        double threatLevel = 0;
        double positionsOfAuthorityThreat = positionsOfAuthority * AUTHORITY;
        double spearFishingThreat = spearFishing * SPEAR_FISHING;
        double consequencesThreat = consequences * CONSEQUENCES;
        double redemptionThreat = redemption * REDEMPTION;
        double immediacyThreat = immediacy * IMMEDIACY;
        double urlThreat = url * URLS;
        double misspelledThreat = misspelled * MISSPELLED;

        ArrayList<Double> threatList = new ArrayList<>();
        Collections.addAll(threatList, positionsOfAuthorityThreat,spearFishingThreat, consequencesThreat, redemptionThreat, immediacyThreat);
        double max = Collections.max(threatList);
        Map<Double, String> threats = new HashMap<>();
        threats.put(positionsOfAuthorityThreat, "Positions of Authority");
        threats.put(spearFishingThreat, "Spear Fishing");
        threats.put(consequencesThreat, "Threat of Consequences");
        threats.put(redemptionThreat, "Threat of Redemption Scam");
        threats.put(immediacyThreat, "Threat of Immediacy Scam");
        threats.put(urlThreat, "Threat of Sketchy URL");
        threats.put(misspelledThreat, "Threat of Misspelled Words");
        String largestThreat = threats.get(max);
        if(max == 0) {
            largestThreat = "nothing";
        }

        threatLevel += positionsOfAuthorityThreat;
        threatLevel += spearFishingThreat;
        threatLevel += consequencesThreat;
        threatLevel += redemptionThreat;
        threatLevel += immediacyThreat;
        threatLevel += urlThreat;
        threatLevel += misspelledThreat;
        threatLevel *= 100;
        int threatPercent = ((int)threatLevel / COUNT_CHECKS);

        return "Total threats: " + totalThreats + "" +
                "\nThreat of phishing: " + threatPercent + "%" +
                "\nThe largest threat is " + largestThreat + "\n";
    }
}