package hw2.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.util.ArrayList;

public class TopMenu extends MenuBar {
    //==============================================================//
    //            GLOBAL OBJECTS OUTSIDE FOR CONSTRUCTOR            //
    //==============================================================//
    //-----------------------------------//
    //       Menu Tool Bar Buttons       //
    //-----------------------------------//
    private Menu fileMenuOpt = new Menu("File");
    //buttons under the "file" option
    //-----------------------------------------------------
    private MenuItem openItem = new MenuItem("Open");
    private MenuItem closeItem = new MenuItem("Close");
    private MenuItem exitItem = new MenuItem("Exit");
    private ImageView openGraphic = new ImageView(new Image("file:graphics/openFolder.png"));
    private ImageView closeGraphic = new ImageView(new Image("file:graphics/closeIcon.png"));
    private ImageView exitGraphic = new ImageView(new Image("file:graphics/exitIcon.png"));
    private ArrayList<ImageView> iconArr = new ArrayList<>(0);
    //-----------------------------------------------------
    private Menu helpMenuOpt = new Menu("Help");
    //buttons under the "help" option
    //------------------------------------------------------------
    private MenuItem aboutItem = new MenuItem("About");
    private MenuItem docItem = new MenuItem("Documentation");
    //------------------------------------------------------------
    private FileChooser fc = new FileChooser();
    //==============================================================//


    //==============================================//
    //            TopMenuBar CONSTRUCTOR            //
    //==============================================//
    public TopMenu() {
        iconArr.add(openGraphic);
        iconArr.add(closeGraphic);
        iconArr.add(exitGraphic);
        for (ImageView i : iconArr) {
            i.setPreserveRatio(true);
            i.setSmooth(true);
            i.setFitWidth(20);
        }
        openItem.setGraphic(openGraphic);
        closeItem.setGraphic(closeGraphic);
        exitItem.setGraphic(exitGraphic);

        fileMenuOpt.getItems().addAll(openItem, closeItem, exitItem);   //creating the file drop down
        helpMenuOpt.getItems().addAll(aboutItem, docItem);   //creating the help drop down
        this.getMenus().addAll(fileMenuOpt, helpMenuOpt);
    }
    //==============================================//


    //=====================================================================//
    //            Event Handlers for the Various Dropdown Items            //
    //=====================================================================//
    public void setOnOpenAction(EventHandler<ActionEvent> e) {
        openItem.setOnAction(e);
    }

    public void setOnCloseAction(EventHandler<ActionEvent> e) {
        closeItem.setOnAction(e);
    }

    public void setOnExitAction(EventHandler<ActionEvent> e) {
        exitItem.setOnAction(e);
    }

    public void setOnAboutItemAction(EventHandler<ActionEvent> e) {
        aboutItem.setOnAction(e);
    }

    public void setOnDocItemAction(EventHandler<ActionEvent> e) {
        docItem.setOnAction(e);
    }
    //=====================================================================//
}


