//Kontroler obsługujący zdarzenia w kreatorze postaci

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import klasy.*;

import java.io.IOException;

public class KreatorPostaciController {

    MediaPlayer mediaPlayer;

    @FXML
    private Button backToMenuButton;
    @FXML
    private Button startGameplayButton;

    //Lewe skrzydło
    @FXML
    private Button wyborRasyButton;
    @FXML
    private Button przydzielPunktyUmiejetnosciButton;
    @FXML
    private Button wyborBroniButton;
    @FXML
    private Button wyborPancerzaButton;
    @FXML
    private Button wyborTarczyButton;
    @FXML
    private Button wyborItemaButton;
    @FXML
    private TextField wybranaRasaTextField;
    @FXML
    private TextField wybranaBronTextField;
    @FXML
    private TextField wybranyPancerzTextField;
    @FXML
    private TextField wybranaTarczaTextField;
    @FXML
    private TextField portfelTextField;

    //Prawe skrzydła
    @FXML
    private StackPane praweSkrzydloPoczatek;
    @FXML
    private AnchorPane praweSkrzydloRasa;
    @FXML
    private AnchorPane praweSkrzydloStatystyki;
    @FXML
    private AnchorPane praweSkrzydloBron;
    @FXML
    private AnchorPane praweSkrzydloPancerz;
    @FXML
    private AnchorPane praweSkrzydloTarcza;
    @FXML
    private AnchorPane praweSkrzydloItem;

    //Skrzydło wyboru rasy
    @FXML
    private ComboBox wyborRasyComboBox;
    @FXML
    private ImageView rasaImageView;
    @FXML
    private TextField silaRasyTextField;
    @FXML
    private TextField zrecznoscRasyTextField;
    @FXML
    private TextField kondycjaRasyTextField;
    @FXML
    private TextField obronaRasyTextField;
    @FXML
    private TextField szybkoscRasyTextField;
    @FXML
    private TextField hpRasyTextField;

    //Skrzydło dodawania punktów umjejętności
    @FXML
    private TextField silaRasyStatsTextField;
    @FXML
    private TextField zrecznoscRasyStatsTextField;
    @FXML
    private TextField kondycjaRasyStatsTextField;
    @FXML
    private TextField obronaRasyStatsTextField;
    @FXML
    private TextField szybkoscRasyStatsTextField;
    @FXML
    private TextField hpRasyStatsTextField;
    @FXML
    private Button dodajSileButton;
    @FXML
    private Button odejmijSileButton;
    @FXML
    private Button dodajZrecznoscButton;
    @FXML
    private Button odejmijZrecznoscButton;
    @FXML
    private Button dodajKondycjeButton;
    @FXML
    private Button odejmijKondycjeButton;
    @FXML
    private Button dodajSzybkoscButton;
    @FXML
    private Button odejmijSzybkoscButton;
    @FXML
    private Button dodajObroneButton;
    @FXML
    private Button odejmijObroneButton;
    @FXML
    private Button dodajHpButton;
    @FXML
    private Button odejmijHpButton;
    @FXML
    private TextField punktyDoWydaniaTextField;

    //Skrzydło wyboru broni
    @FXML
    private ComboBox wyborBroniComboBox;
    @FXML
    private ImageView bronImageView;
    @FXML
    private TextField cenaBroniTextField;
    @FXML
    private TextField obrazeniaBroniTextField;
    @FXML
    private TextField zasiegBroniTextField;
    @FXML
    private TextField dwurecznaBronTextField;
    @FXML
    private TextField finessBronTextField;
    @FXML
    private TextField wagaBroniTextField;

    //Skrzydło wybory pancerza
    @FXML
    private ComboBox wyborPancerzaComboBox;
    @FXML
    private ImageView pancerzImageView;
    @FXML
    private TextField cenaPancerzaTextField;
    @FXML
    private TextField dexBonusPancerzaTextField;
    @FXML
    private TextField obronaPancerzaTextField;
    @FXML
    private TextField wagaPancerzaTextField;

    //Skrzydło wyboru tarczy
    @FXML
    private ComboBox wyborTarczyComboBox;
    @FXML
    private ImageView tarczaImageView;
    @FXML
    private TextField cenaTarczyTextField;
    @FXML
    private TextField obronaTarczyTextField;
    @FXML
    private TextField wagaTarczyTextField;

    //Skrzydło wyboru dodatkowego przedmiotu
    @FXML
    private ComboBox wyborItemaComboBox;
    @FXML
    private ImageView itemImageView;
    @FXML
    private TextField cenaItemaTextField;
    @FXML
    private TextArea opisItemaTextArea;
    @FXML
    private TextField wagaItemaTextField;
    @FXML
    private TextField iloscItemowWEqTextField;
    @FXML
    private Button kupItemButton;

    //Tablice z przedmiotami i rasami
    private Rasa rasa[];
    private Bron bron[];
    private Pancerz pancerz[];
    private Tarcza tarcza[];
    private Przedmiot item[];

    //Inty z iloscią przedmiotów  i ras
    private int iloscRas = 3;
    private int iloscBroni = 8;//dodałem 2 :P
    private int iloscPancerzy = 6;
    private int iloscTarcz = 3;
    private int iloscItemow = 3;

    //Zmienne przechowujące wybrane przedmioty i rasę
    private Postac postac;
    private Rasa wybranaRasa;
    private Bron wybranaBron;
    private Pancerz wybranyPancerz;
    private Tarcza wybranaTarcza;
    private Przedmiot wybranyItem;
    private Ekwipunek ekwipunek;

    private boolean wybranoRase = false;
    private boolean przydzielonoPunktyUmiejetnosci = false;
    private boolean wybranoBron = false;
    private boolean wybranoPancerz = false;
    private boolean wybranoTarcze = false;

    int punktyDoPrzydziału = 6;
    int zloto = 1000;
    int iloscPotek[] = new int[] {0,0,0};


    public void setWybranoRase(boolean bool){
        wybranoRase = bool;
    }

    public KreatorPostaciController(){

        Media media = new Media(getClass().getResource("Music/The_Witcher_3_OST_Geralt_of_Rivia.wav").toExternalForm());

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);// pętla
        mediaPlayer.setAutoPlay(true);

        System.out.println("Konstruktor");
    }

    @FXML
    void initialize(){
        System.out.println("Inicjalizacja");

        initializeRasy();
        initializeBronie();
        initializePancerze();
        initializeTarcze();
        initializeItemy();

        initializeButtons();
        //System.out.println(dodajSileButton.getId());

    }

    public void initializeRasy(){
        rasa = new Rasa[3];
        rasa[0] = new Rasa("Elf", -1,2,1,1,0,0, "Pictures/Avatar.png");
        rasa[1] = new Rasa("Człowiek", 1,1,1,0,1,0, "Pictures/Avatar.png");
        rasa[2] = new Rasa("Krasnolud", 2,0,0,-1,1,1, "Pictures/Avatar.png");

        for(int i = 0; i < iloscRas; i++) {
            wyborRasyComboBox.getItems().addAll(rasa[i].getNazwa());
        }
    }

    public void initializeBronie(){
        bron = new Bron[8];
        bron[0] = new Bron("Sztylet", new Kostka(1,4), 1, 1, 10, false, true, "Pictures/Avatar.png");
        bron[1] = new Bron("Krótki miecz", new Kostka(1,6), 1, 2, 30, false, true, "Pictures/Avatar.png");
        bron[2] = new Bron("Długi miecz", new Kostka(1,8), 1, 3, 60, false, false, "Pictures/Avatar.png");
        bron[3] = new Bron("Wielki miecz", new Kostka(2,6), 1, 6, 100, true, false, "Pictures/Avatar.png");
        bron[4] = new Bron("Krótki łuk", new Kostka(1,4), 15, 2, 100, true, true, "Pictures/Avatar.png");
        bron[5] = new Bron("Długi łuk", new Kostka(1,6), 20, 2, 150, true, true, "Pictures/Avatar.png");
        bron[6] = new Bron("Bat", new Kostka(1,4), 2, 1, 15, false, true, "Pictures/Avatar.png");
        bron[7] = new Bron("Glewia", new Kostka(1,10), 2, 6, 80, true, false, "Pictures/Avatar.png");

        for(int i = 0; i < iloscBroni; i++) {
            wyborBroniComboBox.getItems().addAll(bron[i].getNazwa());
        }

    }

    public void initializePancerze(){
        pancerz = new Pancerz[6];
        pancerz[0] = new Pancerz("Skórzany", 9.5, 30, 11, 99, "Pictures/Avatar.png");
        pancerz[1]  = new Pancerz("Ćwiekowany skórzany", 15.0, 60, 12, 99, "Pictures/Avatar.png");
        pancerz[2]  = new Pancerz("Kolcza koszula", 21.0, 30, 13, 2, "Pictures/Avatar.png");
        pancerz[3]  = new Pancerz("Napierśnik", 24.0, 60, 14, 2, "Pictures/Avatar.png");
        pancerz[4]  = new Pancerz("Kolcza zbroja", 30.0, 50, 16, 0, "Pictures/Avatar.png");
        pancerz[5]  = new Pancerz("Płytowa zbroja", 45.0, 100, 18, 0, "Pictures/Avatar.png");

        for(int i = 0; i < iloscPancerzy; i++) {
            wyborPancerzaComboBox.getItems().addAll(pancerz[i].getNazwa());
        }
    }

    public void initializeTarcze(){
        tarcza = new Tarcza[3];
        tarcza[0] = new Tarcza("Puklerz", 2, 15, 1, "Pictures/Avatar.png");
        tarcza[1] = new Tarcza("Tarcza", 6, 40, 2, "Pictures/Avatar.png");
        tarcza[2] = new Tarcza("Paweż", 12, 100, 3, "Pictures/Avatar.png");

        for(int i = 0; i < iloscTarcz; i++) {
            wyborTarczyComboBox.getItems().addAll(tarcza[i].getNazwa());
        }
    }

    public void initializeItemy(){
        item = new Przedmiot[3];
        item[0] = new Eliksir("Mały eliksir życia", 0.1, 100, new Kostka(2, 4), 2);
        item[1] = new Eliksir("Średni eliksir życia", 0.25, 250, new Kostka(4, 4), 4);
        item[2] = new Eliksir("Duży eliksir życia", 0.7, 700, new Kostka(6, 4), 6);


        for(int i = 0; i < iloscItemow; i++) {
            wyborItemaComboBox.getItems().addAll(item[i].getNazwa());

        }
    }

    public void initializeButtons(){
        przydzielPunktyUmiejetnosciButton.setDisable(true);
        startGameplayButton.setDisable(true);
        punktyDoWydaniaTextField.setText(Integer.toString(punktyDoPrzydziału));
        portfelTextField.setText(Integer.toString(zloto));
        System.out.println("Inicjalizacja Przyciskow");

    }

    public void zmienSkrzydlo(Region kontener){
        praweSkrzydloPoczatek.setDisable(true);
        praweSkrzydloStatystyki.setDisable(true);
        praweSkrzydloRasa.setDisable(true);
        praweSkrzydloBron.setDisable(true);
        praweSkrzydloPancerz.setDisable(true);
        praweSkrzydloTarcza.setDisable(true);
        praweSkrzydloItem.setDisable(true);
        kontener.setDisable(false);

        praweSkrzydloPoczatek.setVisible(false);
        praweSkrzydloStatystyki.setVisible(false);
        praweSkrzydloRasa.setVisible(false);
        praweSkrzydloBron.setVisible(false);
        praweSkrzydloPancerz.setVisible(false);
        praweSkrzydloTarcza.setVisible(false);
        praweSkrzydloItem.setVisible(false);
        kontener.setVisible(true);
    }

    public void wyswietlStatystykiRasy(String nazwa){
        for(int i = 0; i < iloscRas; i++){
            if(rasa[i].getNazwa() == nazwa){
                wybranaRasa = rasa[i];
            }
        }

        silaRasyTextField.setText(Integer.toString(wybranaRasa.getStrBonus()));
        zrecznoscRasyTextField.setText(Integer.toString(wybranaRasa.getDexBonus()));
        kondycjaRasyTextField.setText(Integer.toString(wybranaRasa.getConBonus()));
        szybkoscRasyTextField.setText(Integer.toString(wybranaRasa.getSpeedBonus()));
        obronaRasyTextField.setText(Integer.toString(wybranaRasa.getDefBonus()));
        hpRasyTextField.setText(Integer.toString(wybranaRasa.getHpBonus()));
        rasaImageView.setImage(new Image("Pictures/Avatar.png"));
    }

    public void wyswietlStatystykiPostaci(){
        silaRasyStatsTextField.setText(Integer.toString(postac.getStatystyki().getSila()));
        zrecznoscRasyStatsTextField.setText(Integer.toString(postac.getStatystyki().getZrecznosc()));
        kondycjaRasyStatsTextField.setText(Integer.toString(postac.getStatystyki().getKondycja()));
        szybkoscRasyStatsTextField.setText(Integer.toString(postac.getStatystyki().getPredkosc()));
        obronaRasyStatsTextField.setText(Integer.toString(postac.getStatystyki().getObrona()));
        //hpRasyStatsTextField.setText(Integer.toString(postac.getStatystyki().getMaxhp()));
    }

    public void wyswietlStatystykiBroni(String nazwa){
        for(int i = 0; i < iloscBroni; i++){
            if(bron[i].getNazwa() == nazwa){
                wybranaBron = bron[i];
            }
        }

        cenaBroniTextField.setText(Double.toString(wybranaBron.getCena()));
        obrazeniaBroniTextField.setText(kostkaToString(wybranaBron.getObrazenia()));
        zasiegBroniTextField.setText(Double.toString(wybranaBron.getZasieg()));
        dwurecznaBronTextField.setText(boolToString(wybranaBron.isDwureczny()));
        finessBronTextField.setText(boolToString(wybranaBron.isFiness()));
        wagaBroniTextField.setText(Double.toString(wybranaBron.getWaga()));
        bronImageView.setImage(new Image("Pictures/Avatar.png"));
    }

    public void wyswietlStatystykiPancerza(String nazwa){
        for(int i = 0; i < iloscPancerzy; i++){
            if(pancerz[i].getNazwa() == nazwa){
                wybranyPancerz = pancerz[i];
            }
        }

        cenaPancerzaTextField.setText(Double.toString(wybranyPancerz.getCena()));
        dexBonusPancerzaTextField.setText(Integer.toString(wybranyPancerz.getMaxDexBonus()));
        obronaPancerzaTextField.setText(Integer.toString(wybranyPancerz.getObrona()));
        wagaPancerzaTextField.setText(Double.toString(wybranyPancerz.getWaga()));
        pancerzImageView.setImage(new Image("Pictures/Avatar.png"));
    }

    public void wyswietlStatystykiTarczy(String nazwa){
        for(int i = 0; i < iloscTarcz; i++){
            if(tarcza[i].getNazwa() == nazwa){
                wybranaTarcza = tarcza[i];
            }
        }

        cenaTarczyTextField.setText(Double.toString(wybranaTarcza.getCena()));
        obronaTarczyTextField.setText(Integer.toString(wybranaTarcza.getObrona()));
        wagaTarczyTextField.setText(Double.toString(wybranaTarcza.getWaga()));
        tarczaImageView.setImage(new Image("Pictures/Avatar.png"));
    }

    public void wyswietlStatystykiItema(String nazwa){
        for(int i = 0; i < iloscItemow; i++){
            if(item[i].getNazwa() == nazwa){
                wybranyItem = item[i];
                iloscItemowWEqTextField.setText(String.valueOf(iloscPotek[i]));
            }
        }

        cenaItemaTextField.setText(Double.toString(wybranyItem.getCena()));
        opisItemaTextArea.setText(wybranyItem.getOpis());
        wagaItemaTextField.setText(Double.toString(wybranyItem.getWaga()));
        itemImageView.setImage(new Image("Pictures/Avatar.png"));

    }

    public String boolToString(boolean bool){
        if(bool) return "Tak";
        else return  "Nie";
    }

    public String kostkaToString(Kostka kostka){
        String ilosc = Integer.toString(kostka.getIlosc());
        String oczka = Integer.toString(kostka.getOczka());
        return ilosc + 'd' + oczka;
    }

    public void modyfikujStatystyki(String idPrzycisku){
        switch (idPrzycisku) {
            case "dodajSileButton":
                if(punktyDoPrzydziału>0) {
                    postac.zwStr(1);
                    punktyDoPrzydziału -= 1;
                }
                    break;
                case "odejmijSileButton":
                    if(postac.getStatystyki().getSila()>10+wybranaRasa.getStrBonus()) {
                        postac.zwStr(-1);;
                        punktyDoPrzydziału += 1;
                    }
                    break;
                case "dodajZrecznoscButton":
                    if(punktyDoPrzydziału>0) {
                        postac.zwDex(1);
                        punktyDoPrzydziału -= 1;
                    }
                    break;
                case "odejmijZrecznoscButton":
                    if(postac.getStatystyki().getZrecznosc()>10+wybranaRasa.getDexBonus()) {
                        postac.zwDex(-1);
                        punktyDoPrzydziału += 1;
                    }
                    break;
                case "dodajKondycjeButton":
                    if(punktyDoPrzydziału>0) {
                        postac.getStatystyki().zwiekszCon(1);
                        punktyDoPrzydziału -= 1;
                    }
                    break;
                case "odejmijKondycjeButton":
                    if(postac.getStatystyki().getKondycja()>10+wybranaRasa.getConBonus()) {
                        postac.getStatystyki().zwiekszCon(-1);
                        punktyDoPrzydziału += 1;
                    }
                    break;
                case "dodajSzybkoscButton":
                    if(punktyDoPrzydziału>0) {
                        postac.getStatystyki().zwiekszPredkosc(1);
                        punktyDoPrzydziału -= 1;
                    }
                    break;
                case "odejmijSzybkoscButton":
                    if(postac.getStatystyki().getPredkosc()>10+wybranaRasa.getSpeedBonus()) {
                        postac.getStatystyki().zwiekszPredkosc(-1);
                        punktyDoPrzydziału += 1;
                    }
                    break;
                case "dodajObroneButton":
                    if(punktyDoPrzydziału>0) {
                        postac.getStatystyki().zwiekszObrona(1);
                        punktyDoPrzydziału -= 1;
                    }
                    break;
                case "odejmijObroneButton":
                    if(postac.getStatystyki().getObrona()>10+wybranaRasa.getDefBonus()) {
                        postac.getStatystyki().zwiekszObrona(-1);
                        punktyDoPrzydziału += 1;
                    }
                    break;/*
                case "dodajHpButton":
                    if(punktyDoPrzydziału>0) {
                        postac.getStatystyki().zwiekszMaxhp(1);
                        punktyDoPrzydziału -= 1;
                    }
                    break;
                case "odejmijHpButton":
                    postac.getStatystyki().zwiekszMaxhp(-1);
                    punktyDoPrzydziału+=1;
                    break;*/
        }
        punktyDoWydaniaTextField.setText(String.valueOf(punktyDoPrzydziału));
    }

    public void backToMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Wcisnieto powrot");
        mediaPlayer.stop();//stopowanie muzyki
        Parent newRoot = FXMLLoader.load(getClass().getResource("plikiFXML/menuStartowe.fxml"));

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(new Scene(newRoot));
    }

    public void startGameplayButtonPressed(ActionEvent actionEvent) throws IOException {
        postac.getEkwipunek().dodajZłoto(zloto);
        Bron GUN = new Bron("GUN!",new Kostka(2,20),20,1,1,false,true,"Pictures/Avatar.png");
        Pancerz plotArmor = new Pancerz("Armor of plot",1,1,25,5,"Pictures/Avatar.png");
        Bron banHammer = new Bron("Ban Hammer",new Kostka(3,20),1,1,1,true,false,"Pictures/Avatar.png");
        postac.podnies(GUN);
        postac.podnies(plotArmor);
        postac.podnies(banHammer);

        System.out.println("Wcisnieto start");
        mediaPlayer.stop();//stopowanie muzyki
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plikiFXML/gameplay.fxml"));
        Parent newRoot = loader.load();

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(new Scene(newRoot));

        GameplayController GameplayController = loader.getController();
        GameplayController.setPostac(postac);

    }

    public void wyborRasyButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloRasa);
    }

    public void zatwierdzWyborRasyButtonPressed(ActionEvent actionEvent) {
        if(wybranaRasa!=null) {
            postac = new Postac(wybranaRasa, new Statystyki(10, 10, 10, 5), new Pozycja(0, 0), "Gracz");
            zmienSkrzydlo(praweSkrzydloPoczatek);
            //wyborRasyButton.setText("Zmień");
            wybranaRasaTextField.setText((String) wybranaRasa.getNazwa());
            setWybranoRase(true);
            przydzielPunktyUmiejetnosciButton.setDisable(false);
            wyborRasyButton.setDisable(true);
            wyswietlStatystykiPostaci();
            startGameplayButton.setDisable(false);
        }

    }

    public void wyborBroniButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloBron);
    }

    public void zatwierdzWyborBroniButtonPressed(ActionEvent actionEvent) {//przydałaby się opcja od wybrania rzeczy zamiast tylko zamiany
        if(wybranaBron!=null) {
            if (zloto - wybranaBron.getCena() >= 0) {
                zloto -= wybranaBron.getCena();
                zmienSkrzydlo(praweSkrzydloPoczatek);
                //wyborBroniButton.setText("Zmień");
                wybranaBronTextField.setText((String) wybranaBron.getNazwa());
                wyborBroniButton.setDisable(true);
                //postac.ubierz(wybranaBron, 2);
                postac.getEkwipunek().dodajDoPlecaka(wybranaBron);
                postac.ubierz(wybranaBron, 2);
            }
            portfelTextField.setText(Integer.toString(zloto));
        }


    }

    public void wyborPancerzaButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloPancerz);
    }

    public void zatwierdzWyborPancerzaButtonPressed(ActionEvent actionEvent) {
        if(wybranyPancerz!=null) {
            if (zloto - wybranyPancerz.getCena() >= 0) {
                zloto -= wybranyPancerz.getCena();
                zmienSkrzydlo(praweSkrzydloPoczatek);
                //wyborPancerzaButton.setText("Zmień");
                wybranyPancerzTextField.setText((String) wybranyPancerz.getNazwa());
                wyborPancerzaButton.setDisable(true);
                //postac.ubierz(wybranyPancerz, 1);
                portfelTextField.setText(Integer.toString(zloto));
                postac.getEkwipunek().dodajDoPlecaka(wybranyPancerz);
                postac.ubierz(wybranyPancerz, 1);
            }
        }
    }

    public void wyborTarczyButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloTarcza);
    }

    public void zatwierdzWyborTarczyButtonPressed(ActionEvent actionEvent) {
        if(wybranaTarcza!=null) {
            if (zloto - wybranaTarcza.getCena() >= 0) {
                zloto -= wybranaTarcza.getCena();
                zmienSkrzydlo(praweSkrzydloPoczatek);
                //wyborTarczyButton.setText("Zmień");
                wybranaTarczaTextField.setText((String) wybranaTarcza.getNazwa());
                wyborTarczyButton.setDisable(true);
                //postac.ubierz(wybranaTarcza, 3);
                portfelTextField.setText(Integer.toString(zloto));
                postac.getEkwipunek().dodajDoPlecaka(wybranaTarcza);
                postac.ubierz(wybranaTarcza, 3);
            }
        }
    }

    public void wyborItemaButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloItem);
    }

    public void zatwierdzWyborItemaButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloPoczatek);
        //wyborItemaButton.setText("Zmień");
    }

    public void wyborRasyComboBoxPressed(ActionEvent actionEvent) {
        System.out.println("ComboBox ona action");
        wyswietlStatystykiRasy((String) wyborRasyComboBox.getValue());
    }

    public void wyborBroniComboBoxPressed(ActionEvent actionEvent) {
        System.out.println("ComboBox ona action");
        wyswietlStatystykiBroni((String) wyborBroniComboBox.getValue());
    }

    public void wyborPancerzaComboBoxPressed(ActionEvent actionEvent) {
        System.out.println("ComboBox ona action");
        wyswietlStatystykiPancerza((String) wyborPancerzaComboBox.getValue());
    }

    public void wyborTarczyComboBoxPressed(ActionEvent actionEvent) {
        System.out.println("ComboBox ona action");
        wyswietlStatystykiTarczy((String) wyborTarczyComboBox.getValue());
    }

    public void przydzielPunktyUmiejetnosciButtonPressed(ActionEvent actionEvent) {
        zmienSkrzydlo(praweSkrzydloStatystyki);
        punktyDoWydaniaTextField.setText(String.valueOf(punktyDoPrzydziału));
    }

    public void zatwierdzPrzydzialPunktowUmiejetnosciButtonPressed(ActionEvent actionEvent) {
        wyborBroniButton.setDisable(false);
        wyborPancerzaButton.setDisable(false);
        wyborTarczyButton.setDisable(false);
        wyborItemaButton.setDisable(false);
        przydzielPunktyUmiejetnosciButton.setDisable(true);
        zmienSkrzydlo(praweSkrzydloPoczatek);
        postac.getStatystyki().setHp(postac.getStatystyki().getMaxhp());
    }

    public void wyborItemaComboBoxPressed(ActionEvent actionEvent) {
        wyswietlStatystykiItema((String) wyborItemaComboBox.getValue());
    }

    public void kupItemButtonPressed(ActionEvent actionEvent) {
        if(wybranyItem!=null) {
            if (zloto - wybranyItem.getCena() >= 0) {
                zloto -= wybranyItem.getCena();
                postac.getEkwipunek().dodajDoPlecaka(wybranyItem);
                for (int i = 0; i < iloscItemow; i++) {
                    if (item[i].getNazwa() == wybranyItem.getNazwa()) {
                        iloscPotek[i] += 1;
                        iloscItemowWEqTextField.setText(String.valueOf(iloscPotek[i]));
                    }
                }
                portfelTextField.setText(Integer.toString(zloto));
            }
        }
    }

    public void modyfikujStatystykiButtonPressed(ActionEvent actionEvent) {
        Object node = (Node) actionEvent.getSource();
        Button clickedButton = (Button) node;
        modyfikujStatystyki(clickedButton.getId());
        wyswietlStatystykiPostaci();
        punktyDoWydaniaTextField.setText(Integer.toString(punktyDoPrzydziału));
    }
}
