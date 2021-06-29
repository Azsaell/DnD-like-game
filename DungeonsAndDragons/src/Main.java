import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Main extends Application {
    ///////////MediaPlayer mediaPlayer;      to musi być w takim miejscu bo jak jest w metodzie to garbage collektor wyłacza po paru s

    //Tworzenie okna i ustawienie sceny
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("plikiFXML/menuStartowe.fxml"));
        primaryStage.setTitle("Dungeons&Dragons");
        primaryStage.setScene(new Scene(root));

        //String path = "Music/Two_Steps_From_Hell_Victory.mp3";
        //Media media = new Media(new File(path).toURI().toString());
        //////Media media = new Media(getClass().getResource("Music/Two_Steps_From_Hell_Victory.mp3").toExternalForm());    nazwy plików nie mogą mieć spacji
        //Media media = new Media("Music/Two_Steps_From_Hell_Victory.mp3");
        //Media media = new Media(Paths.get(path).toUri().toString());

        //Instantiating MediaPlayer class
        ////////mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played
       ///////// mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);// pętla
       /////// mediaPlayer.setAutoPlay(true);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
