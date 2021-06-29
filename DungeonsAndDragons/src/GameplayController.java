import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import klasy.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameplayController {

    @FXML
    private GridPane planszaGridPane;
    @FXML
    private Pane prawaStronaPane;
    @FXML
    private AnchorPane oknoRozgrywkiAnchorPane;
    @FXML
    private Button goToMenuButton;
    @FXML
    private Button atakujButton;
    @FXML
    private Button pokazEqButton;
    @FXML
    private Button zrywButton;
    @FXML
    private Button wypijEliksirButton;
    @FXML
    private ComboBox wybierzEliksirComboBox;
    @FXML
    private Button koniecTuryButton;
    @FXML
    private VBox menu1VBox;
    @FXML
    private VBox menu2VBox;
    @FXML
    private TextArea logiTextArea;
    @FXML
    private ProgressBar hpProgressBar;
    @FXML
    private ProgressBar punktyRuchuProgressBar;
    @FXML
    private StackPane oknoEqStackPane;
    @FXML
    private TextField prawaRekaTextField;
    @FXML
    private TextField lewaRekaTextField;
    @FXML
    private TextField pancerzTextField;
    @FXML
    private Button zdejmijLewaRekaButton;
    @FXML
    private Button zdejmijPrawaRekaButton;
    @FXML
    private Button zdejmijPancerzButton;
    @FXML
    private ComboBox przedmiotyWPlecakuComboBox;
    @FXML
    private Button wezWLewaRekeButton;
    @FXML
    private Button wezWPrawaRekeButton;
    @FXML
    private Button ubierzButton;
    @FXML
    private Button zamknijOknoEqButton;
    @FXML
    private StackPane oknoKoncaGryStackPane;
    @FXML
    private Button zagrajPonownieButton;
    @FXML
    private Button wyjdzZGryButton;
    @FXML
    private Label ktoWygralLabel;


    private ArrayList<ImageView> przeciwnikImageView;

    private boolean rozgrywkaTrwa;
    private Plansza plansza;
    private Walka walka;
    private int planszaIloscWierszy;
    private int planszaIloscKolumn;
    StackPane poleStackPane[][];
    EventHandler<MouseEvent> polePlanszyHandler;

    private ImageView postacImageView;

    Timeline timeline;

    public GameplayController(){
        rozgrywkaTrwa = false;
        planszaIloscWierszy = 30;
        planszaIloscKolumn = 40;
        poleStackPane = new StackPane[planszaIloscKolumn][planszaIloscWierszy];
        przeciwnikImageView = new ArrayList<>();
    }

    @FXML
    void initialize() throws InterruptedException {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.2), e -> {
                    if(plansza!=null) {
                        refresh();

                        System.out.println("pozycja bohatera: x=" + plansza.getBohater().getPozycja().getX() + "|  y=" + plansza.getBohater().getPozycja().getY());
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        polePlanszyHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Integer colIndex = planszaGridPane.getColumnIndex(source);
                Integer rowIndex = planszaGridPane.getRowIndex(source);
                System.out.printf("Kliknieto pole [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
                if(rozgrywkaTrwa) {

                    if (plansza.getPlansza()[colIndex.intValue() - 1][rowIndex.intValue() - 1] == 0) {
                        walka.setCel(new Pozycja(colIndex.intValue() - 1, rowIndex.intValue() - 1));
                        walka.setCoZrobic(1);
                        refresh();
                    } else if (plansza.getPlansza()[colIndex.intValue() - 1][rowIndex.intValue() - 1] > 1) {
                        walka.setCel(new Pozycja(colIndex.intValue() - 1, rowIndex.intValue() - 1));
                        walka.setCoZrobic(2);
                    }
                }
            }
        };
        initializePlanszaGridPane();
    }

    void initializePlanszaGridPane(){
        for(int i = 0; i < planszaIloscKolumn; i++){
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / planszaIloscKolumn);
            planszaGridPane.getColumnConstraints().add(colConst);
        }
        for(int i = 0; i < planszaIloscWierszy; i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / planszaIloscWierszy);
            planszaGridPane.getRowConstraints().add(rowConst);
        }

        for (int i = 1 ; i <= planszaIloscKolumn ; i++) {
            for (int j = 1; j <= planszaIloscWierszy; j++) {
                dodajPole(i - 1, j - 1);
            }
        }
    }

    public void setPostac(Postac postac){
        plansza = new Plansza(planszaIloscKolumn, planszaIloscWierszy, postac);
        plansza.przemiesc(plansza.getBohater(), 0, ThreadLocalRandom.current().nextInt(1, planszaIloscWierszy));
        initializePrzeciwnicy();
        initializePostacPNG();
    }

    void initializePostacPNG(){
        postacImageView = new ImageView();
        postacImageView.setImage(new Image("Pictures/maly_bohater.png"));
        poleStackPane[plansza.getBohater().getPozycja().getX()][plansza.getBohater().getPozycja().getY()].getChildren().add(postacImageView);
        System.out.println("pozycja startowa bohatera: x=" + plansza.getBohater().getPozycja().getX() + "|  y=" + plansza.getBohater().getPozycja().getY());
    }

    void initializePrzeciwnicy(){
        Rasa goblin = new Rasa("Goblin", 0,0,0,0,2,0, "Pictures/cell.png");
        Statystyki statystykiGoblinaTanka = new Statystyki(10,10,16,1);
        Statystyki statystykiGoblinaLucznika = new Statystyki(10,16,10,1);
        Statystyki statystykiGoblinaLucznika2 = new Statystyki(10,16,10,1);
        Pozycja pozycja1 = new Pozycja(planszaIloscKolumn - 1, ThreadLocalRandom.current().nextInt(1, planszaIloscWierszy));
        Pozycja pozycja2;
        Pozycja pozycja3;
        if(pozycja1.getY() + 5 < planszaIloscWierszy) pozycja2 = new Pozycja(pozycja1.getX(), pozycja1.getY() + 5);
        else pozycja2 = new Pozycja(pozycja1.getX(), pozycja1.getY() - 5);
        if(pozycja2.getY() + 7 < planszaIloscWierszy) pozycja3 = new Pozycja(pozycja2.getX(), pozycja2.getY() + 7);
        else pozycja3 = new Pozycja(pozycja2.getX(), pozycja2.getY() - 7);
        Przeciwnik goblinWoj = new Przeciwnik(goblin, statystykiGoblinaTanka, pozycja1, "Zbrojny Goblin");
        goblinWoj.getStatystyki().zwiekszPredkosc(3);
        goblinWoj.ubierz(new Bron("Miecz",new Kostka(1,8),1,1,1,true,false,"Pictures/cell.png"),2);
        Przeciwnik goblinLuk = new Przeciwnik(goblin, statystykiGoblinaLucznika, pozycja2, "Goblin łucznik");
        goblinLuk.ubierz(new Bron("łuk",new Kostka(1,4),15,1,1,true,true,"Pictures/cell.png"),2);
        Przeciwnik goblinLuk2 = new Przeciwnik(goblin, statystykiGoblinaLucznika2, pozycja3, "Goblin łucznik");
        goblinLuk2.ubierz(new Bron("łuk",new Kostka(1,4),15,1,1,true,true,"Pictures/cell.png"),2);
        plansza.dodajUczestnika(goblinWoj);
        plansza.dodajUczestnika(goblinLuk);
        plansza.dodajUczestnika(goblinLuk2);



        przeciwnikImageView.add(new ImageView(new Image("Pictures/maly_goblin.png")));
        przeciwnikImageView.add(new ImageView(new Image("Pictures/maly_goblin.png")));
        przeciwnikImageView.add(new ImageView(new Image("Pictures/maly_goblin.png")));

        poleStackPane[pozycja1.getX()][pozycja1.getY()].getChildren().add(przeciwnikImageView.get(0));
        poleStackPane[pozycja2.getY()][pozycja2.getY()].getChildren().add(przeciwnikImageView.get(1));
        poleStackPane[pozycja3.getY()][pozycja3.getY()].getChildren().add(przeciwnikImageView.get(2));

        //System.out.println("pozycja startowa goblina1: x=" + pozycja1.getX() + "|  y=" + pozycja1.getY());
        //System.out.println("pozycja startowa goblina2: x=" + pozycja2.getX() + "|  y=" + pozycja2.getY());

    }

    void initializeEliksiry(){
        wybierzEliksirComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPasek());

    }

    void initializePrzedmiotyWPlecaku(){
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());
        if(plansza.getBohater().getEkwipunek().getLewaReka()==null) {
            lewaRekaTextField.setText("");
        }else{
            lewaRekaTextField.setText(plansza.getBohater().getEkwipunek().getLewaReka().getNazwa());
        }
        if(plansza.getBohater().getEkwipunek().getPrawaReka()==null) {
            prawaRekaTextField.setText("");
        }else{
            prawaRekaTextField.setText(plansza.getBohater().getEkwipunek().getPrawaReka().getNazwa());
        }
        if(plansza.getBohater().getEkwipunek().getPancerz()==null) {
            pancerzTextField.setText("");
        }else{
            pancerzTextField.setText(plansza.getBohater().getEkwipunek().getPancerz().getNazwa());
        }

    }

    public void refresh(){
        int obiektNaPlanszy;
        for(int x = 0; x < planszaIloscKolumn; x++){
            for(int y = 0; y < planszaIloscWierszy; y++){
                obiektNaPlanszy = plansza.getPlansza()[x][y];
                if(obiektNaPlanszy != 0) {
                    wyswietlObiekt(obiektNaPlanszy, x, y);
                }
            }
        }
        if(plansza.getUczestnicy().size()< przeciwnikImageView.size()+1){
            StackPane stackPane;
            stackPane = (StackPane) (przeciwnikImageView.get( przeciwnikImageView.size()-1)).getParent();
            stackPane.getChildren().remove(przeciwnikImageView.get( przeciwnikImageView.size()-1));
            przeciwnikImageView.remove(przeciwnikImageView.size()-1);
        }
        if(rozgrywkaTrwa) {
            if (walka.getTekst() != "") {
                wyswietlLog(walka.getTekst());
            }
        }
        if(wybierzEliksirComboBox.getItems().size()>plansza.getBohater().getEkwipunek().getPasek().size()) {
            wybierzEliksirComboBox.getItems().clear();
            wybierzEliksirComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPasek());
        }
        if(przedmiotyWPlecakuComboBox.getItems().size()!=plansza.getBohater().getEkwipunek().getPlecak().size()) {
            przedmiotyWPlecakuComboBox.getItems().clear();
            przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());
        }
        int hp = plansza.getBohater().getStatystyki().getHp() / plansza.getBohater().getStatystyki().getMaxhp();

        hpProgressBar.setProgress(((double)plansza.getBohater().getStatystyki().getHp()) /((double) plansza.getBohater().getStatystyki().getMaxhp()));
        if(rozgrywkaTrwa&&walka.isCzyTuraGracza()){
            punktyRuchuProgressBar.setProgress(((double)walka.getRuch()) /(((double) plansza.getBohater().getStatystyki().getPredkosc())));//*2)); nie wiem czy tu miało być to mnożenie przez 2, ale brzydko to wyglądało
        }

        if(rozgrywkaTrwa) {
            if (walka.getKtoWygral() == 2) {
                ktoWygralLabel.setText("Zwycięstwo");
                oknoKoncaGryStackPane.setDisable(false);
                oknoKoncaGryStackPane.setVisible(true);
            } else if (walka.getKtoWygral() == 1) {
                ktoWygralLabel.setText("Porażka");
                oknoKoncaGryStackPane.setDisable(false);
                oknoKoncaGryStackPane.setVisible(true);
            }
        }


    }

    public void wyswietlObiekt(int obiektNaPlanszy, int x, int y){
        StackPane stackPane;
        if(obiektNaPlanszy == 1) {
            stackPane = (StackPane) postacImageView.getParent();
            stackPane.getChildren().remove(postacImageView);
            poleStackPane[x][y].getChildren().add(postacImageView);
        }else if(obiektNaPlanszy > 1){
            stackPane = (StackPane) (przeciwnikImageView.get(obiektNaPlanszy - 2)).getParent();
            stackPane.getChildren().remove(przeciwnikImageView.get(obiektNaPlanszy - 2));
            poleStackPane[x][y].getChildren().add(przeciwnikImageView.get(obiektNaPlanszy - 2));
        }
    }

    public void wyswietlLog(String log){
        logiTextArea.appendText(log + "\n");
    }

    private void dodajPole(int colIndex, int rowIndex) {
        poleStackPane[colIndex][rowIndex] = new StackPane();
        double poleHeight = planszaGridPane.getHeight()/planszaGridPane.getRowCount();
        double poleWidth = planszaGridPane.getWidth()/planszaGridPane.getColumnCount();
        poleStackPane[colIndex][rowIndex].setPrefHeight(poleHeight);
        poleStackPane[colIndex][rowIndex].setPrefWidth(poleWidth);
        poleStackPane[colIndex][rowIndex].addEventHandler(MouseEvent.MOUSE_PRESSED, polePlanszyHandler);
        planszaGridPane.add(poleStackPane[colIndex][rowIndex], colIndex + 1, rowIndex + 1);
    }

    public void goToMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        timeline.stop();
        Parent newRoot = FXMLLoader.load(getClass().getResource("plikiFXML/menuStartowe.fxml"));

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(new Scene(newRoot));
    }

    public void pokazEqButtonPressed(ActionEvent actionEvent) {
        oknoEqStackPane.setVisible(true);
        oknoEqStackPane.setDisable(false);
        planszaGridPane.setDisable(true);
        prawaStronaPane.setDisable(true);
        initializePrzedmiotyWPlecaku();
    }

    public void atakujButtonPressed(ActionEvent actionEvent) {
        menu1VBox.setDisable(true);
        menu2VBox.setDisable(false);
        menu1VBox.setVisible(false);
        menu2VBox.setVisible(true);
        initializeEliksiry();
        walka = new Walka(plansza);
        walka.start();
        rozgrywkaTrwa=true;
    }

    public void zrywButtonPressed(ActionEvent actionEvent) {
        walka.setCoZrobic(3);

    }

    public void wybierzEliksirComboBoxPressed(ActionEvent actionEvent) {
        walka.setEliksir((Eliksir)wybierzEliksirComboBox.getValue());
    }

    public void wypijEliksirButtonPressed(ActionEvent actionEvent) {
        if(plansza.getBohater().getEkwipunek().getPasek().size()>0 && walka.getEliksir() != null) {
            walka.setCoZrobic(4);

        }
    }

    public void koniecTuryButtonPressed(ActionEvent actionEvent) {
        walka.setCoZrobic(5);
    }

    public void zdejmijPrawaRekaButtonPressed(ActionEvent actionEvent) {
        plansza.getBohater().zdejmij(2);
        if(plansza.getBohater().getEkwipunek().getLewaReka()==null) {
            lewaRekaTextField.setText("");
        }else{
            lewaRekaTextField.setText(plansza.getBohater().getEkwipunek().getLewaReka().getNazwa());
        }
        if(plansza.getBohater().getEkwipunek().getPrawaReka()==null) {
            prawaRekaTextField.setText("");
        }else{
            prawaRekaTextField.setText(plansza.getBohater().getEkwipunek().getPrawaReka().getNazwa());
        }
        przedmiotyWPlecakuComboBox.getItems().clear();
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());
    }

    public void zdejmijLewaRekaButtonPressed(ActionEvent actionEvent) {//dla prawej ręki kopia tego jedynie naCo zmien na 2
        plansza.getBohater().zdejmij(3);                            //tak zrobiłem, sprawdź czy dobrze xD
        if(plansza.getBohater().getEkwipunek().getLewaReka()==null) {
            lewaRekaTextField.setText("");
        }else{
            lewaRekaTextField.setText(plansza.getBohater().getEkwipunek().getLewaReka().getNazwa());
        }
        if(plansza.getBohater().getEkwipunek().getPrawaReka()==null) {
            prawaRekaTextField.setText("");
        }else{
            prawaRekaTextField.setText(plansza.getBohater().getEkwipunek().getPrawaReka().getNazwa());
        }
        przedmiotyWPlecakuComboBox.getItems().clear();
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());
    }

    public void zdejmijPancerzButtonPressed(ActionEvent actionEvent) {
        plansza.getBohater().zdejmij(1);
        pancerzTextField.setText("");
        przedmiotyWPlecakuComboBox.getItems().clear();
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());

    }

    public void przedmiotyWPlecakuComboBoxPressed(ActionEvent actionEvent) {

    }

    public void wezWLewaRekeButtonPressed(ActionEvent actionEvent) {
        if (przedmiotyWPlecakuComboBox.getItems().size() > 0){
            plansza.getBohater().ubierz((Przedmiot) przedmiotyWPlecakuComboBox.getValue(), 3);
            if(plansza.getBohater().getEkwipunek().getLewaReka()==null) {
                lewaRekaTextField.setText("");
            }else{
                lewaRekaTextField.setText(plansza.getBohater().getEkwipunek().getLewaReka().getNazwa());
            }
            if(plansza.getBohater().getEkwipunek().getPrawaReka()==null) {
                prawaRekaTextField.setText("");
            }else{
                prawaRekaTextField.setText(plansza.getBohater().getEkwipunek().getPrawaReka().getNazwa());
            }
        }
        przedmiotyWPlecakuComboBox.getItems().clear();
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());

    }

    public void ubierzButtonPressed(ActionEvent actionEvent) {
        if (przedmiotyWPlecakuComboBox.getItems().size() > 0) {
            plansza.getBohater().ubierz((Przedmiot) przedmiotyWPlecakuComboBox.getValue(), 1);
            pancerzTextField.setText(plansza.getBohater().getEkwipunek().getPancerz().getNazwa());
        }
        przedmiotyWPlecakuComboBox.getItems().clear();
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());
    }

    public void wezWPrawaRekeButtonPressed(ActionEvent actionEvent) {
        if (przedmiotyWPlecakuComboBox.getItems().size() > 0) {
            plansza.getBohater().ubierz((Przedmiot) przedmiotyWPlecakuComboBox.getValue(), 2);
            if(plansza.getBohater().getEkwipunek().getLewaReka()==null) {
                lewaRekaTextField.setText("");
            }else{
                lewaRekaTextField.setText(plansza.getBohater().getEkwipunek().getLewaReka().getNazwa());
            }
            if(plansza.getBohater().getEkwipunek().getPrawaReka()==null) {
                prawaRekaTextField.setText("");
            }else{
                prawaRekaTextField.setText(plansza.getBohater().getEkwipunek().getPrawaReka().getNazwa());
            }
        }
        przedmiotyWPlecakuComboBox.getItems().clear();
        przedmiotyWPlecakuComboBox.getItems().addAll(plansza.getBohater().getEkwipunek().getPlecak());
    }

    public void zamknijOknoEqButtonPressed(ActionEvent actionEvent) {
        oknoEqStackPane.setDisable(true);
        oknoEqStackPane.setVisible(false);
        planszaGridPane.setDisable(false);
        prawaStronaPane.setDisable(false);
    }

    public void zagrajPonownieButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("plikiFXML/kreatorPostacivol2.fxml"));

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(new Scene(newRoot));
    }

    public void wyjdzZGryButtonPressed(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.close();
    }
}

