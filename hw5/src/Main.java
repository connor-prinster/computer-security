public class Main {
    public static void main(String[] args) {
        String email = "he11o@paypa1.corn";
        String tragedy = "Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. It's not a story the Jedi would tell you. It's a Sith legend. Darth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force to influence the midichlorians to create life… He had such a knowledge of the dark side that he could even keep the ones he cared about from dying. The dark side of the Force is a pathway to many abilities some consider to be unnatural. He became so powerful… the only thing he was afraid of was losing his power, which eventually, of course, he did. Unfortunately, he taught his apprentice everything he knew, then his apprentice killed him in his sleep. Ironic. He could save others from death, but not himself";

        int count = 0;
        Phishing tragedyPhishing = new Phishing(email, tragedy);
        count += tragedyPhishing.checkReligiousFigures();
        count += tragedyPhishing.checkEmailThreat();
        count += tragedyPhishing.CheckConsequences();
        count += tragedyPhishing.CheckRedemption();
        count += tragedyPhishing.CheckImmediacy();
        System.out.println(count);
    }
}
