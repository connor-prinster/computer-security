package functions;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.util.Map;

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
//        String publicKey = generatePublicKey();
        //call the private method
//        String privateKey = generatePrivateKey();
        Map<String, Map<String, Integer>> keys = GenerateKeys.getKeys();

        String publicDirectory = "public.key";
        String privateDirectory = "private.key";

        System.out.println("rpn: " + keys.get("public").get("rpn"));
        System.out.println("m: " + keys.get("public").get("m"));
        System.out.println("pd: " + keys.get("private").get("pd"));

        try{
            FileWriter writePublic = new FileWriter(publicDirectory);
            writePublic.write(keys.get("public").get("rpn").toString() + "\n");
            writePublic.write(keys.get("public").get("m").toString() + "\n");
            writePublic.close();
        }catch(Exception e){System.out.println(e);}
        System.out.println("Success writing public...");

        try{
            FileWriter writePrivate = new FileWriter(privateDirectory);
            writePrivate.write(keys.get("private").get("pd").toString() + "\n");
            writePrivate.write(keys.get("private").get("m").toString() + "\n");
            writePrivate.close();
        }catch(Exception e){System.out.println(e);}
        System.out.println("Success writing private...");

        keysAndMessage.setText(keys.get("public").get("rpn") + "\n" + keys.get("private").get("pd") +
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
