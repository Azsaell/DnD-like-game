public class Tarcza extends Przedmiot {
    private int obrona;

    public Tarcza(double waga, double cena, int obrona) {
        super(waga, cena);
        this.obrona = obrona;
    }

    public int getObrona() {
        return obrona;
    }
}
