package hw2;

import hw2.control.ControlsBar;
import hw2.control.TopMenu;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

import java.io.File;

import java.net.URI;

public class Main extends Application {
    private Stage primaryStage;
    private StackPane mainPane = new StackPane();
    private TopMenu topMenuBar = new TopMenu();    //will be at the top
    private ControlsBar controlsBar = new ControlsBar();    //will be at the bottom

    //------------------------------------//
    //    Objects Used To Access Media    //
    //------------------------------------//
    private String filename;
    private FileChooser fileChooser = new FileChooser();
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView = new MediaView();
    //------------------------------------//

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        //setting up the scene with the three components
        mainPane.getChildren().add(0, mediaView);
        mainPane.getChildren().add(1, controlsBar);
        mainPane.getChildren().add(2, topMenuBar);

        //adjusting the controlsBar to look aesthetically pleasing
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);
        controlsBar.setAlignment(Pos.BOTTOM_CENTER);
        controlsBar.setPadding(new Insets(0, 0, 5, 0));

        //set opacity for opacity's sake
        controlsBar.setOpacity(100);
        topMenuBar.setOpacity(100);
        mediaView.setOpacity(100);

        //make sure the topMenu buttons can be used correctly
        topMenuBar.setOnCloseAction(this::setOnClose);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video File", "*.mp4", "*.m4v", "*.m4a"));
        topMenuBar.setOnOpenAction(this::openWithFileChooser);
        topMenuBar.setOnExitAction(this::setOnExit);
        topMenuBar.setOnDocItemAction(this::setOnDoc);
        topMenuBar.setOnAboutItemAction(this::setOnAbout);

        //adjusting settings for the bottom control bar
        controlsBar.setPlayPauseAction(this::setOnPausePlay);
        controlsBar.setStopBtnAction(this::setOnStop);

        //making sure the mediaView looks pretty
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(mainPane.getWidth());
        mediaView.setFitHeight(mainPane.getHeight());

        //finishing up the primaryStage stuff
        Scene mainScene = new Scene(mainPane, 800, 500);
        primaryStage.setTitle("Video Player");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    //-------------------------------------------------------------------------------//
    //        Method for Setting the MediaViewer When File->Open is Activated        //
    //-------------------------------------------------------------------------------//
    private void initialMediaPlayer() {
        if (media == null) //if media DNE then create a new one since it must be the first time it's running
        {
            media = new Media(new File(filename).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            controlsBar.togglePlayPause(false);
        } else    //if media exists, mediaPlayer also exists. Get rid of it and then essentially start it all again.
        {
            mediaPlayer.dispose();
            media = new Media(new File(filename).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
        initialListeners();
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                primaryStage.setWidth(800);
                primaryStage.setWidth(600);
                mediaView.fitHeightProperty().bind(mainPane.heightProperty());
                mediaView.fitWidthProperty().bind(mainPane.widthProperty());
                primaryStage.setTitle(filename);
                mediaPlayer.play();
            }
        });
    }
    //-------------------------------------------------------------------------------//

    private void initialListeners() {
        controlsBar.returnVolSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume((double) newValue / 100);
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if (!controlsBar.returnTimeSlider().isValueChanging())   //if the slider is not changing set the thumb to a percentage
                {
                    double percentElapse = (mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getMedia().getDuration().toMillis()) * 100;
                    controlsBar.returnTimeSlider().setValue(percentElapse);  //returns a percentage of currenttime/totaltime so we multiply by 100
                }
            }
        });

        controlsBar.returnTimeSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (controlsBar.returnTimeSlider().isValueChanging()) {
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(controlsBar.returnTimeSlider().getValue() / 100));
                }
            }
        });
    }

    //----------------------------------------------------------------//
    //        Method for Handling When File->Open is Activated        //
    //----------------------------------------------------------------//
    private void openWithFileChooser(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            filename = selectedFile.toString();
            initialMediaPlayer();
        }
    }
    //----------------------------------------------------------------//

    //-----------------------------------------------------------------//
    //        Method for Handling When File->Close is Activated        //
    //-----------------------------------------------------------------//
    private void setOnClose(ActionEvent event) {
        if (media != null)   //don't follow anything potentially null
        {
            //removes media view, disposes mediaPlayer, resets mediaView
            mainPane.getChildren().remove(mediaView);
            mediaPlayer.dispose();
            mediaView = new MediaView();
            //adds the new blank mediaView
            mainPane.getChildren().add(0, mediaView);
            //binds properties
            mediaView.fitWidthProperty().bind(mainPane.widthProperty());
            mediaView.fitHeightProperty().bind(mainPane.heightProperty());
        }
    }

    private void setOnDoc(ActionEvent event) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOnAbout(ActionEvent event) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI("https://www.youtube.com/watch?v=2GFTWlhNkm4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOnExit(ActionEvent event) {
        System.exit(0);
    }

    private void setOnPausePlay(ActionEvent event) {
        if (media != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                controlsBar.togglePlayPause(true);
            } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                controlsBar.togglePlayPause(false);
            } else if (mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                mediaPlayer.play();
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(controlsBar.returnTimeSlider().getValue() / 100.0));
                controlsBar.togglePlayPause(false);
            }
        }
    }

    private void setOnStop(ActionEvent event) {
        if (media != null) {
            controlsBar.togglePlayPause(true);
            mediaPlayer.stop();
        }
    }
}
