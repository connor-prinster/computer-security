package functions;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EncryptionGUI extends Application {

    private Label cipherText = new Label("");
    private TextField plainText = new TextField();
    private int rpn;
    private int m;


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Encryption");
        primaryStage.setScene(new Scene(pane, 300, 275));


        Button getPublicKey = new Button("Grab Public Key from public.key");
        getPublicKey.setOnAction(e -> {
            readPublicKeys();
        });

        Button encrypt = new Button("Encrypt");
        encrypt.setOnAction(e -> {
            encryptStuff();
        });

        HBox hBox = new HBox();
        Label plainTextLabel = new Label("PlainText:");
        hBox.getChildren().addAll(plainTextLabel, plainText);
        hBox.setSpacing(10);

        pane.setCenter(getPublicKey);
        pane.setAlignment(getPublicKey, Pos.CENTER);
        pane.setRight(encrypt);
        pane.setAlignment(encrypt, Pos.CENTER);
        pane.setBottom(cipherText);
        pane.setAlignment(cipherText, Pos.TOP_CENTER);
        pane.setTop(hBox);
        pane.setAlignment(hBox, Pos.BOTTOM_CENTER);



        primaryStage.show();
    }

    private void readPublicKeys() {
        try {
            File publicKeyFile = new File("public.key");
            Scanner readFile = new Scanner(publicKeyFile);
            String publicKeys[] = new String[2];
            int i = 0;
            while (readFile.hasNextLine()) {
                publicKeys[i] = readFile.nextLine();
                System.out.println("Received " + publicKeys[i]);
                i++;
            }
            readFile.close();
            rpn = Integer.parseInt(publicKeys[0]);
            m = Integer.parseInt(publicKeys[1]);
            System.out.println("Successful :)");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unsuccessful...");
        }
    }

    private void encryptStuff() {
        //code to encrypt stuff
        String cipherResult = Encryption.encrypt(rpn, m, plainText.getText());
        cipherText.setText(cipherResult);
        // write the cipher text to a file to copy and paste to the decryption gui
        File publicKeyFile = new File("public.key");
        try {
            FileWriter fileWriter = new FileWriter(publicKeyFile, true);
            fileWriter.write(cipherResult + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Couldn't write the cipher text to public.key");
        }
    }
}
