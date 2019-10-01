import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Decryption extends Application {

    private Label plainText = new Label("");
    private TextField cipherText = new TextField();


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Decryption");
        primaryStage.setScene(new Scene(pane, 300, 275));


        Button getPrivateKey = new Button("Grab Private Key from private.key");
        getPrivateKey.setOnAction(e -> {
            //code for grabbing public key from public.key
        });

        Button decrypt = new Button("Decrypt");
        decrypt.setOnAction(e -> {
            decryptStuff();
        });

        HBox hBox = new HBox();
        Label cipherTextLabel = new Label("CipherText:");
        hBox.getChildren().addAll(cipherTextLabel, cipherText);
        hBox.setSpacing(10);

        pane.setCenter(getPrivateKey);
        pane.setAlignment(getPrivateKey, Pos.CENTER);
        pane.setRight(decrypt);
        pane.setAlignment(decrypt, Pos.CENTER);
        pane.setBottom(plainText);
        pane.setAlignment(plainText, Pos.TOP_CENTER);
        pane.setTop(hBox);
        pane.setAlignment(hBox, Pos.BOTTOM_CENTER);



        primaryStage.show();
    }

    private void decryptStuff() {
        //code to encrypt stuff
        plainText.setText(cipherText.getText());
    }
}
