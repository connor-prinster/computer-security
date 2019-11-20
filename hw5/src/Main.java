public class Main {


    private final static double SPEAR_FISHING = 0.4;
    private final static double URLS = 0.4;
    private final static double IMMEDIACY = 0.1;
    private final static double AUTHORITY = 0.033;
    private final static double CONSEQUENCES = 0.033;
    private final static double REDEMPTION = 0.033;
    private final static int COUNT_CHECKS = 5;

    public static void main(String[] args) {
        String email = "he11o@paypa1.corn";
        String tragedy = "Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. It's not a story the Jedi would tell you. It's a Sith legend. Darth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force to influence the midichlorians to create life… He had such a knowledge of the dark side that he could even keep the ones he cared about from dying. The dark side of the Force is a pathway to many abilities some consider to be unnatural. He became so powerful… the only thing he was afraid of was losing his power, which eventually, of course, he did. Unfortunately, he taught his apprentice everything he knew, then his apprentice killed him in his sleep. Ironic. He could save others from death, but not himself";

        int totalThreats = 0;
        Phishing tragedyPhishing = new Phishing(email, tragedy);
        int positionsOfAuthority = tragedyPhishing.checkPositionsOfAuthority();
        int spearFishing = tragedyPhishing.checkEmailThreat();
        int consequences = tragedyPhishing.CheckConsequences();
        int redemption = tragedyPhishing.CheckRedemption();
        int immediacy = tragedyPhishing.CheckImmediacy();
        totalThreats += (positionsOfAuthority + spearFishing + consequences + redemption + immediacy);

        double threatLevel = 0;
        threatLevel += positionsOfAuthority * AUTHORITY;
        threatLevel += spearFishing * SPEAR_FISHING;
        threatLevel += consequences * CONSEQUENCES;
        threatLevel += redemption * REDEMPTION;
        threatLevel += immediacy * IMMEDIACY;
        threatLevel *= 100;
        int threatPercent = ((int)threatLevel / COUNT_CHECKS);
        String percentStatement = "Total threats: " + totalThreats + "\nThreat of phishing: " + threatPercent + "%";

        System.out.println(percentStatement);
    }
}



