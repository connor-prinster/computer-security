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


public class SQLIGUI extends Application {

    private Label sqlQuery = new Label("SQL Query:");
    private TextArea sqlQueryInput = new TextArea();
    private Label sqlQueryReport = new Label("");

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Create Account");
        Group root = new Group();
        root.getChildren().add(pane);
        primaryStage.setScene(new Scene(root, 800, 400));

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            submit(sqlQueryInput.getText());
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(sqlQuery, 0,0);
        gridPane.add(sqlQueryInput, 1,0);
        gridPane.add(submit, 1,1);
        gridPane.add(sqlQueryReport, 1,2);


        pane.setCenter(gridPane);
        pane.setAlignment(gridPane, Pos.CENTER);

        primaryStage.show();
    }

    private void submit(String query) {
        SQLI checker = new SQLI(query);
        sqlQueryReport.setText(checker.returnThreatString());
    }
}
