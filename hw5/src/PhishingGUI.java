import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;


public class PhishingGUI extends Application {

    private Label emailId = new Label("Email ID:");
    private Label emailBody = new Label("Email Body:");
    private TextField emailIdInput = new TextField();
    private TextArea emailBodyInput = new TextArea();
    private Label emailPhishingReport = new Label("");

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Create Account");
        Group root = new Group();
        root.getChildren().add(pane);
        primaryStage.setScene(new Scene(root, 600, 400));

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            submit(emailIdInput.getText(), emailBodyInput.getText());
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(emailId, 0,0);
        gridPane.add(emailIdInput, 1,0);
        gridPane.add(emailBody, 0,1);
        gridPane.add(emailBodyInput, 1,1);
        gridPane.add(submit, 1,2);
        gridPane.add(emailPhishingReport, 1,3);


        pane.setCenter(gridPane);
        pane.setAlignment(gridPane, Pos.CENTER);

        primaryStage.show();
    }

    private void submit(String id, String body) {
        Phishing phishing = new Phishing(id, body);
        emailPhishingReport.setText(phishing.returnThreatString());
    }
}
