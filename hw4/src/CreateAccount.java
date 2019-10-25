import javafx.application.Application;
import javafx.geometry.Pos;
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
//    private int rpn;
//    private int m;


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Create Account");
        Scene scene = new Scene(pane, 350, 400);
        primaryStage.setScene(scene);


        Button submitInfo = new Button("Submit");
        submitInfo.setOnAction(e -> {
            createInfoListThenPassword(scene);
//            readPublicKeys();
        });

//        Button encrypt = new Button("Encrypt");
//        encrypt.setOnAction(e -> {
//            encryptStuff();
//        });

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
        pane.setAlignment(submitInfo, Pos.TOP_CENTER);

        primaryStage.show();
    }

    private void createInfoListThenPassword(Scene scene) {
//        scene.getChi
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
