package klasy;

public class Tarcza extends Przedmiot {
    private int obrona;
    private String sciezkaDoImg;

    public Tarcza(String nazwa, double waga, double cena, int obrona, String sciezkaDoImg) {
        super(waga, cena, nazwa);
        this.obrona = obrona;
        this.sciezkaDoImg = sciezkaDoImg;
    }

    public int getObrona() {
        return obrona;
    }

    public String getSciezkaDoImg() {
        return sciezkaDoImg;
    }
}
