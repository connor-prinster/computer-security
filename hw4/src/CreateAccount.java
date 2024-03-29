import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;


public class CreateAccount extends Application {

    private Label firstName = new Label("First Name:");
    private Label lastName = new Label("Last Name:");
    private Label dob = new Label("Date of Birth (MM/DD/YYYY):");
    private Label phoneNumber = new Label("Phone Number (XXX-XXX-XXXX):");
    private Label street = new Label("Street Information:");
    private Label aptNo = new Label("APT No:");
    private Label city = new Label("City:");
    private Label state = new Label("State (ex.UT):");
    private Label zipcode = new Label("Zipcode (first 5 digits):");
    private Label email = new Label("Email:");
    private TextField firstNameInput = new TextField();
    private TextField lastNameInput = new TextField();
    private TextField dobInput = new TextField();
    private TextField phoneNumberInput = new TextField();
    private TextField streetInput = new TextField();
    private TextField aptNoInput = new TextField();
    private TextField cityInput = new TextField();
    private TextField stateInput = new TextField();
    private TextField zipcodeInput = new TextField();
    private TextField emailInput = new TextField();

    private Label password = new Label("Password:");
    private Label retypePassword = new Label("Re-Type Password:");
    private TextField passwordInput = new TextField();
    private TextField retypePasswordInput = new TextField();
    private Label passwordError = new Label("");

    private Map<String, List<String>> possibleCommonPasswords;
    private Map<String, List<String>> possiblePersonalPasswords;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Create Account");
        Group root = new Group();
        root.getChildren().add(pane);
        primaryStage.setScene(new Scene(root, 500, 400));


        Button submitInfo = new Button("Submit");
        submitInfo.setOnAction(e -> {
            createInfoListThenPassword(root);
        });

        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(firstName,lastName,dob,phoneNumber,street,aptNo,city,state,zipcode,email);
        vbox1.setAlignment(Pos.TOP_RIGHT);
        vbox1.setSpacing(19);

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(firstNameInput,lastNameInput,dobInput,phoneNumberInput,streetInput,aptNoInput,cityInput,stateInput,zipcodeInput,emailInput);
        vbox2.setSpacing(10);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(vbox1,vbox2);
        hBox.setSpacing(10);

        pane.setCenter(hBox);
        pane.setAlignment(hBox, Pos.CENTER);
        pane.setBottom(submitInfo);
        pane.setAlignment(submitInfo, Pos.CENTER);

        primaryStage.show();
    }

    private void createInfoListThenPassword(Group root) {

        PasswordTester passwordTester = new PasswordTester();
        possibleCommonPasswords = passwordTester.CreateCommonVariationList();
        possiblePersonalPasswords = passwordTester.CreatePersonalPasswords(createMapForPersonInfo());

        root.getChildren().clear();

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(password,retypePassword);
        vBox1.setAlignment(Pos.TOP_RIGHT);
        vBox1.setSpacing(19);
        vBox1.setPadding(new Insets(150,0,0,50));

        VBox vBox2 = new VBox();
        vBox2.getChildren().addAll(passwordInput,retypePasswordInput);
        vBox2.setSpacing(10);
        vBox2.setPadding(new Insets(150,50,0,0));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox1,vBox2);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            submit();
        });

        VBox vbox3 = new VBox();
        vbox3.getChildren().addAll(passwordError,submit);
        vbox3.setSpacing(10);
        vbox3.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(hBox);
        pane.setAlignment(hBox, Pos.CENTER);
        pane.setBottom(vbox3);
        pane.setAlignment(vbox3, Pos.CENTER);

        passwordError.setStyle("-fx-text-fill: red;");

        root.getChildren().add(pane);
    }

    private HashMap<String, String> createMapForPersonInfo() {
        HashMap<String, String> personalInfo = new HashMap<>();
        personalInfo.put("firstName", firstNameInput.getText());
        personalInfo.put("lastName", lastNameInput.getText());
        String fullDob = dobInput.getText();
        personalInfo.put("dob", fullDob);
        String[] dobPieces = fullDob.split("/");
        personalInfo.put("month", dobPieces[0]);
        personalInfo.put("day", dobPieces[1]);
        personalInfo.put("year", dobPieces[2]);
        String fullPhone = phoneNumberInput.getText();
        personalInfo.put("fullPhone", fullPhone);
        String[] phonePieces = fullPhone.split("-");
        personalInfo.put("first 6 phone", (phonePieces[0] + phonePieces[1]));
        personalInfo.put("last 4 phone", phonePieces[2]);
        String street = streetInput.getText();
        personalInfo.put("street", street);
        personalInfo.put("street no space", streetInput.getText().replace(" ", ""));
        String[] streetPieces = street.split(" ");
        for (int i = 0; i < streetPieces.length; i++) personalInfo.put("street" + i, streetPieces[i]);
        personalInfo.put("aptNo", aptNoInput.getText());
        personalInfo.put("city", cityInput.getText());
        personalInfo.put("state", stateInput.getText());
        personalInfo.put("zipcode", zipcodeInput.getText());
        personalInfo.put("email", emailInput.getText());

        return personalInfo;
    }

    private void submit() {
        String password = passwordInput.getText();
        String retypePassword = retypePasswordInput.getText();
        if (!password.equals(retypePassword)) {
            passwordError.setText("Your passwords don't match\n");
            return;
        }
        if (password.length() < 6) {
            passwordError.setText("Please create a password with at least 6 characters");
            return;
        }
        Set<String> keys = possibleCommonPasswords.keySet();
        boolean isInList = false;
        for (String key : keys) {
            List<String> passwords = possibleCommonPasswords.get(key);
            for (int i = 0; i < passwords.size(); i++) {
                if (password.equals(passwords.get(i))) {
                    passwordError.setText("You picked a password prone to dictionary attack with a variation of the word " + key + "\nPlease create a stronger password.");
                    isInList = true;
                    break;
                }
            }
        }
        if (isInList) return;
        keys = possiblePersonalPasswords.keySet();
        for (String key : keys) {
            List<String> passwords = possiblePersonalPasswords.get(key);
            for (int i = 0; i < passwords.size(); i++) {
                if (password.equals(passwords.get(i))) {
                    passwordError.setText("You picked a password prone to targeting attack with your info in the field " + key + "\nPlease pick a stronger password.");
                    isInList = true;
                    break;
                }
            }
        }
        if (!isInList) {
            passwordError.setText("Valid Password, You're Account Has Been Created");
            Salt salt = new Salt();
            salt.generateSaltPasswords(emailInput.getText(), password);
        }
    }
}
