package klasy;

public class Notka extends Przedmiot {

    String tresc;

    public Notka(String nazwa, double waga, double cena, String tresc) {//nie wiem gdzie najlepiej to umiescic :P może nawet nie bedzie uzyteczne
        super(waga, cena, nazwa);
        this.tresc = tresc;
    }

    public String getTresc() {
        return tresc;
    }
}
