import java.net.URL;
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
    private String[] parsedList;
    private final static double SPEAR_FISHING = 0.4;
    private final static double URLS = 0.4;
    private final static double IMMEDIACY = 0.1;
    private final static double AUTHORITY = 0.033;
    private final static double CONSEQUENCES = 0.033;
    private final static double REDEMPTION = 0.033;
    private final static int COUNT_CHECKS = 5;

    public Phishing(String emailAddress, String emailBody) {
        this.emailAddress = emailAddress;
        this.emailBody = emailBody;
        checkUrlThreat();
        normalizeEmailBody();
        checkEmailThreat();
    }

    public int checkUrlThreat() {
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
        }

        return count;
    }

    public int checkEmailThreat() {
        int count = 0;

        String[] splitAddress = emailAddress.split("@");
        if(splitAddress.length < 1) {
            return -1;
        }
        String user = splitAddress[0];
        String company = splitAddress[1];
        String[] splitCompany = company.split("\\.");
        String companyName = splitCompany[0];
        String domain = splitCompany[1];

        String containsDigits = "[0-9]";
        String containsAlpha = "[A-Za-z]";

        // === Check the Username === //
        if(user.split(containsAlpha).length == 0) { // if there are no letters, probably supersketch
            count++;
        }

        // === Check the Company === //
        if(companyName.split("-").length > 0) { //catches *-validSite.com or whatever
            count++;
        }
        if(companyName.split("_").length > 0) { //catches *_validSite.com or whatever
            count++;
        }

        // === Check the Domain === //
        if(splitCompany.length > 2) { // checks to see if the domain name contains more than just *.com. For example paypal.paymentsnow.com would flag it
            count++;
        }

        if(!reputableDomain(domain)) { // if org/com/net/edu
            count++;
        }
        if(domain.split(containsDigits).length > 0) { // see if there are characters in the domain
            count++;
        }
        if(domain.split(containsAlpha).length == 0) { // if there is nothing other than numbers in the domain
            count++;
        }

        return count;
    }

    private Boolean reputableDomain(String domain) {
        return (domain.equals("com") || domain.equals("org") || domain.equals("edu") || domain.equals("net"));
    }

    private void normalizeEmailBody() {
        String normalized = "";
        String[] parsed = emailBody.split(" ");

        for(String str : parsed) {
            normalized += str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            normalized += " ";
        }

        parsedList = normalized.split(" ");
    }

    public int checkPositionsOfAuthority() {
        String reg = "(president|vice|prophet|mister|judge|caliph|centurion|chief|consort|count|countess|doctor|earl|countess|emperor|empress|esquire|squire|admiral|master|herald|highness|majesty|lady|mandarin|mayor|saint|sergeant|tsar|tsaritsa|prince|king|princess|baron|baroness|darth|bishop|pastor|rabbi|deacon|priest|cardinal|chaplain|church|priestess|pope|vicar|dalai lama|patriarch|archbishop|monk|abbess|nun|apostle|elder|reverend|chaplain|god|saint|imam|mullah|sultan|sultana|witch|priestess|druid|chairman|officer|lord)";
        return matchCount(reg, emailBody);
    }

    public int CheckConsequences() {
        String reg = "(close|closed|compromised|compromise|action|expose|keylogger|bitcoin|misdemeanor|humiliation|sextape|warrant|arrest|unpleasant|illegal)";
        return matchCount(reg, emailBody);
    }
    public int CheckRedemption() {
        String reg = "(congratulations|redeem|claim|take|won|win|prize|contest|winner|winning|offer|gift|surprise|compensation|delivery|inheritance|deposit|fee|success|check|cheque|reward|award|payment)";
        return matchCount(reg, emailBody);
    }

    public int CheckImmediacy() {
        String reg = "(now|urgent|urgently|immediate|immediately|soon|expire|expired|expires)";
        return matchCount(reg, emailBody);
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
        totalThreats += (positionsOfAuthority + spearFishing + consequences + redemption + immediacy);

        double threatLevel = 0;
        double positionsOfAuthorityThreat = positionsOfAuthority * AUTHORITY;
        double spearFishingThreat = spearFishing * SPEAR_FISHING;
        double consequencesThreat = consequences * CONSEQUENCES;
        double redemptionThreat = redemption * REDEMPTION;
        double immediacyThreat = immediacy * IMMEDIACY;

        ArrayList<Double> threatList = new ArrayList<>();
        Collections.addAll(threatList, positionsOfAuthorityThreat,spearFishingThreat, consequencesThreat, redemptionThreat, immediacyThreat);
        double max = Collections.max(threatList);
        Map<Double, String> threats = new HashMap<>();
        threats.put(positionsOfAuthorityThreat, "Positions of Authority");
        threats.put(spearFishingThreat, "Spear Fishing");
        threats.put(consequencesThreat, "Threat of Consequences");
        threats.put(redemptionThreat, "Threat of Redemption Scam");
        threats.put(immediacyThreat, "Threat of Immediacy Scam");
        String largestThreat = threats.get(max);

        threatLevel += positionsOfAuthorityThreat;
        threatLevel += spearFishingThreat;
        threatLevel += consequencesThreat;
        threatLevel += redemptionThreat;
        threatLevel += immediacyThreat;
        threatLevel *= 100;
        int threatPercent = ((int)threatLevel / COUNT_CHECKS);

        return "Total threats: " + totalThreats + "" +
                "\nThreat of phishing: " + threatPercent + "%" +
                "\nThe largest threat is " + largestThreat + "\n";
    }
}