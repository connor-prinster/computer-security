package hw2.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


import java.util.ArrayList;

public class ControlsBar extends HBox {
    private Button playPause = new Button("");
    private Button stopBtn = new Button("");
    private Text volText = new Text("Volume:");
    private Slider volSlider = new Slider();
    private Text timeText = new Text("Time:");
    private Slider timeSlider = new Slider();
    private ImageView playGraphic = new ImageView(new Image("file:graphics/playButton.png"));
    private ImageView pauseGraphic = new ImageView(new Image("file:graphics/pauseButton.png"));
    private ImageView stopGraphic = new ImageView(new Image("file:graphics/stopButton.png"));

    private ArrayList<ImageView> viewArr = new ArrayList<ImageView>(0);

    public ControlsBar() {
        //playPause.setGraphic(new Image(""));
        this.getChildren().addAll(playPause, stopBtn, volText, volSlider, timeText, timeSlider);   //sets the objects in the Hbox
        this.setAlignment(Pos.CENTER);  //centers the objects in the middle of the Hbox
        this.spacingProperty().setValue(25);    //sets inital spacing
        this.setPadding(new Insets(5));
        viewArr.add(playGraphic);
        viewArr.add(pauseGraphic);
        viewArr.add(stopGraphic);
        for (ImageView i : viewArr) {
            i.setPreserveRatio(true);
            i.setSmooth(true);
            i.setFitHeight(20);
        }
        playPause.setGraphic(playGraphic);  //will eventually be switched to "pauseGraphic" when clicked
        stopBtn.setGraphic(stopGraphic);
    }

    public void setPlayPauseAction(EventHandler<ActionEvent> e) {
        playPause.setOnAction(e);
    }

    public void setPlayPauseSpaceAction(EventHandler<KeyEvent> e) {
        playPause.setOnKeyPressed(e);
    }

    public void setStopBtnAction(EventHandler<ActionEvent> e) {
        stopBtn.setOnAction(e);
    }

    public void togglePlayPause(Boolean isPlay)   //checks that the button text will change based on when it is touched
    {
        if (!isPlay)  //if already playing
        {
            //playPause.setText("Pause");
            playPause.setGraphic(pauseGraphic);
        } else {
            //playPause.setText("Play");
            playPause.setGraphic(playGraphic);

        }
    }

    public Slider returnVolSlider() {
        return volSlider;   //returning the volumeSlider for analysis
    }

    public Slider returnTimeSlider() {
        return timeSlider;  //returning the timeSlider for analysis
    }
}
