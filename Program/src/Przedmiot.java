public abstract class Przedmiot {
    private double waga;
    private double cena;

    public Przedmiot(double waga, double cena) {
        this.waga = waga;
        this.cena = cena;
    }

    public double getWaga() {
        return waga;
    }

    public double getCena() {
        return cena;
    }
}
