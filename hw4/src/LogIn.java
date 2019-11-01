import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class LogIn extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
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

        final int attempt = 0;

        passwordInput.setOnAction(e -> {
            if (authenticatePassword(usernameInput.getText(), passwordInput.getText())) {
                //log in
            }
            else {
                //wrong password
                String message = lockOut(attempt);
                outputMessage.setText("Wrong Password");
            }
        });

        primaryStage.show();
    }

    private static boolean authenticatePassword(String username, String password) {
        return false;
    }

    private static String lockOut(int attempt) {
        return "Wrong Password" + attempt;
    }

}
