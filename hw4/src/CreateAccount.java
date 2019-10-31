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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Create Account");
        Group root = new Group();
        root.getChildren().add(pane);
        primaryStage.setScene(new Scene(root, 350, 400));


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

        ArrayList<String> personalInfo = new ArrayList();
        personalInfo.add(firstNameInput.getText());
        personalInfo.add(lastNameInput.getText());
        String fullDob = dobInput.getText();
        personalInfo.add(fullDob);
        personalInfo.addAll(Arrays.asList(fullDob.split("/")));
        String fullPhone = phoneNumberInput.getText();
        personalInfo.add(fullPhone);
        personalInfo.addAll(Arrays.asList(fullPhone.split("-")));
        personalInfo.add(streetInput.getText());
        personalInfo.add(aptNoInput.getText());
        personalInfo.add(cityInput.getText());
        personalInfo.add(stateInput.getText());
        personalInfo.add(zipcodeInput.getText());
        personalInfo.add(emailInput.getText());


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

        root.getChildren().add(pane);
    }

    private void submit() {

    }

//    private void readPublicKeys() {
//        try {
//            File publicKeyFile = new File("public.key");
//            Scanner readFile = new Scanner(publicKeyFile);
//            String publicKeys[] = new String[2];
//            int i = 0;
//            while (readFile.hasNextLine()) {
//                publicKeys[i] = readFile.nextLine();
//                System.out.println("Received " + publicKeys[i]);
//                i++;
//            }
//            readFile.close();
//            rpn = Integer.parseInt(publicKeys[0]);
//            m = Integer.parseInt(publicKeys[1]);
//            System.out.println("Successful :)");
//        } catch (Exception e) {
//            System.out.println(e);
//            System.out.println("Unsuccessful...");
//        }
//    }
//
//    private void encryptStuff() {
//        //code to encrypt stuff
//        String cipherResult = Encryption.encrypt(rpn, m, plainText.getText());
//        cipherText.setText(cipherResult);
//        // write the cipher text to a file to copy and paste to the decryption gui
//        File publicKeyFile = new File("public.key");
//        try {
//            FileWriter fileWriter = new FileWriter(publicKeyFile, true);
//            fileWriter.write(cipherResult + "\n");
//            fileWriter.close();
//        } catch (IOException e) {
//            System.out.println(e);
//            System.out.println("Couldn't write the cipher text to public.key");
//        }
//    }
}
