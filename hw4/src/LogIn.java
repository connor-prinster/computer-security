import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Map;

public class LogIn extends Application {

    private static int attempt;
    private static final int ATTEMPT_NUM = 3;
    private static long timeUnlocked = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.TOP_CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(loginGrid, 500, 300);
        primaryStage.setScene(scene);

        Label username = new Label("Username:");
        loginGrid.add(username, 0, 1);

        TextField usernameInput = new TextField();
        loginGrid.add(usernameInput, 1, 1);

        Label password = new Label("Password:");
        loginGrid.add(password, 0, 2);

        PasswordField passwordInput = new PasswordField();
        loginGrid.add(passwordInput, 1, 2);

        Button signInButton = new Button("Sign in");
        HBox hb_signInButton = new HBox(10);
        hb_signInButton.setAlignment(Pos.BOTTOM_RIGHT);
        hb_signInButton.getChildren().add(signInButton);
        loginGrid.add(hb_signInButton, 1, 4);

        final Text outputMessage = new Text();
        loginGrid.add(outputMessage, 1, 6);

        setAttempt(0);

        signInButton.setOnAction(e -> {
            if (timeUnlocked < System.currentTimeMillis() && authenticatePassword(usernameInput.getText(), passwordInput.getText())) {
                //log in
                outputMessage.setText("Login Succcccccccessssfulllllll");
                setAttempt(0);
            }
            else {
                //wrong password
                if (timeUnlocked < System.currentTimeMillis()) {
                    incrementAttempt();
                }
                String message = lockOut(getAttempt());
                outputMessage.setText(message);
            }
        });

        primaryStage.show();
    }

    private static boolean authenticatePassword(String username, String password) {
        Salt saltyFriend = new Salt();
        Map<String, Map<String, String>> userLoginInfo = saltyFriend.getUserPassSalt();
        Map<String, String> saltPass = userLoginInfo.get(username);
        System.out.println();

        String correctPassword = saltPass.get("password");
        String correctSalt = saltPass.get("salt");
        String inputHashPass = saltyFriend.produceHashPass(password, correctSalt);

        return (inputHashPass.equals(correctPassword));
    }

    private static String lockOut(int attempt) {
        String message = "Wrong Password";
        if (timeUnlocked < System.currentTimeMillis() && getAttempt() % ATTEMPT_NUM == 0) {
            long millisUntilUnlocked = toMillis((int)Math.pow(2, (double)(attempt/ATTEMPT_NUM) - 1));
            timeUnlocked = System.currentTimeMillis() + millisUntilUnlocked;
        }
        else if (getAttempt() % ATTEMPT_NUM == 0) {
            message += ("\nYou have made " + getAttempt() + " incorrect attempts.\n");
            DecimalFormat twoDecimalPlaces = new DecimalFormat("###.##");
            double minutesLeft = toMinutes(timeUnlocked - System.currentTimeMillis());
            message += ("Please wait " + twoDecimalPlaces.format(minutesLeft) + " minutes before trying again.");
        }
        return message;
    }

    private static double toMinutes(long millis) {
        return ((double) millis)/60000;
    }

    private static long toMillis(int minutes) {
        return (long)minutes*60000;
    }

    private static int getAttempt() {
        return attempt;
    }

    private static void setAttempt(int a) {
        attempt = a;
    }

    private static void incrementAttempt() {
        attempt++;
    }
}
