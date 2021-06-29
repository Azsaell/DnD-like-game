//Kontroler kontrolujący zdarzenia w menu startowym gry

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class MenuController {
    MediaPlayer mediaPlayer;

    @FXML
    private Button startGameButton;
    @FXML
    private Button authorsButton;
    @FXML
    private Button exitGameButton;

    public MenuController(){
        //String path = "Music/Two_Steps_From_Hell_Victory.mp3";
        //Media media = new Media(new File(path).toURI().toString());
        Media media = new Media(getClass().getResource("Music/Two_Steps_From_Hell_Victory.wav").toExternalForm());
        //Media media = new Media("Music/Two_Steps_From_Hell_Victory.mp3");
        //Media media = new Media(Paths.get(path).toUri().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);// pętla
        mediaPlayer.setAutoPlay(true);

        System.out.println("Konstruktor");
    }

    @FXML
    void initialize(){

    }

    public void authorsButtonPressed(){
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/OperatorKodu"));
                Desktop.getDesktop().browse(new URI("https://github.com/Azsaell"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }

        System.out.println("Wcisnieto autorow");
    }
    public void exitGameButtonPressed(MouseEvent actionEvent){
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.close();
    }

    public void startGameButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {

        mediaPlayer.stop();

        Parent newRoot = FXMLLoader.load(getClass().getResource("plikiFXML/kreatorPostacivol2.fxml"));

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(new Scene(newRoot));
    }
}
