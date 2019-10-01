import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Encryption extends Application {

    private Label cipherText = new Label("");
    private TextField plainText = new TextField();


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Encryption");
        primaryStage.setScene(new Scene(pane, 300, 275));


        Button getPublicKey = new Button("Grab Public Key from public.key");
        getPublicKey.setOnAction(e -> {
            //code for grabbing public key from public.key
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

    private void encryptStuff() {
        //code to encrypt stuff
        cipherText.setText(plainText.getText());
    }
}
