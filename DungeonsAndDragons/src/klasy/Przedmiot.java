package klasy;

public abstract class Przedmiot {
    private String nazwa;
    private double waga;
    private double cena;
    public String opis;

    public Przedmiot(double waga, double cena, String nazwa) {
        this.waga = waga;
        this.cena = cena;
        this.nazwa = nazwa;
    }

    public double getWaga() {
        return waga;
    }

    public double getCena() {
        return cena;
    }

    public String getOpis() {
        return opis;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return  nazwa ;
    }
}
