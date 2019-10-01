package GUIs;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GeneratePublicPrivateKeys extends Application {

    private Label keysAndMessage = new Label("");

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Generate Public and Private Key");
        primaryStage.setScene(new Scene(pane, 300, 275));


        Button generateKeys = new Button("Generate Public and Private Key");
        generateKeys.setOnAction(e -> {
            generatePublicPrivate();
        });

        pane.setCenter(generateKeys);
        pane.setAlignment(generateKeys, Pos.CENTER);
        pane.setBottom(keysAndMessage);
        pane.setAlignment(keysAndMessage, Pos.TOP_CENTER);



        primaryStage.show();
    }

    private void generatePublicPrivate() {
        //call the public method
        String publicKey = generatePublicKey();
        //call the private method
        String privateKey = generatePrivateKey();


        String publicDirectory = "./whateverwesayitis.yomama";
        String privateDirectory = "./alsowhateverwesay.yodaddy";

        keysAndMessage.setText(publicKey + "\n" + privateKey +
            "\nThe Public Key has been stored in " + publicDirectory +
            "\nThe Private Key has been stored in " + privateDirectory +
            "\n");
    }

    private String generatePublicKey() {
        return "Public Key is " + Math.random();
    }

    private String generatePrivateKey() {
        return "Private Key is " + Math.random();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
