import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phishing {
    private String emailAddress;
    private String emailBody;
    private ArrayList<String> religiousFigures;
    private String[] parsedList;

    public Phishing(String emailAddress, String emailBody) {
        this.emailAddress = emailAddress;
        this.emailBody = emailBody;
        normalizeEmailBody();
        generateReligiousFigures();
        checkEmailThreat();
    }

    private void generateReligiousFigures() {
        ArrayList<String> arr = new ArrayList<>();
        // selections from https://en.wikipedia.org/wiki/List_of_religious_titles_and_styles and https://en.wikipedia.org/wiki/List_of_titles
        Collections.addAll(arr, "president", "vice", "prophet", "mister", "judge", "caliph", "centurion", "chief", "consort", "count", "countess", "doctor", "earl", "countess", "emperor", "empress", "esquire", "squire", "admiral", "master", "herald", "highness", "majesty", "lady", "mandarin", "mayor", "saint", "sergeant", "tsar", "tsaritsa", "prince", "king", "princess", "baron", "baroness", "darth", "bishop", "pastor", "rabbi", "deacon", "priest", "cardinal", "chaplain", "church", "priestess", "pope", "vicar", "dalai lama", "patriarch", "archbishop", "monk", "abbess", "nun", "apostle", "elder", "reverend", "chaplain", "god", "saint", "imam", "mullah", "sultan", "sultana", "witch", "priestess", "druid", "chairman", "officer", "lord");
        religiousFigures = arr;
    }

    public int checkReligiousFigures() {
        int count = 0;

        for(String word : parsedList) {
            if(religiousFigures.contains(word)) {
                count++;
            }
        }

        return count;
    }

    public int checkEmailThreat() {
        int count = 0;

        String[] splitAddress = emailAddress.split("@");
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

    public Boolean reputableDomain(String domain) {
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

    public int CheckConsequences(String body) {
        String reg = "(close|closed|compromised|compromise|action|expose|keylogger|bitcoin|misdemeanor|humiliation|sextape|warrent|arrest|unpleasant|illegal)";
        return matchCount(reg, body);
    }
    public int CheckRedemption(String body) {
        String reg = "(congratulations|redeem|claim|take|won|win|prize|contest|winner|winning|offer|gift|surprise|compensation|delivery|inheritance|deposit|fee|success|check|cheque|reward|award|payment)";
        return matchCount(reg, body);
    }

    private int matchCount(String regex, String test) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(test);
        int count = 0;
        while(m.find()) count++;
        return count;
    }
}