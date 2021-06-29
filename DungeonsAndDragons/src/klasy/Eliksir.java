package klasy;

public class Eliksir extends Przedmiot {
    private Kostka leczenieKostka;
    private int leczeniaBonus;
    private boolean pelny;

    public Eliksir(String nazwa, double waga, double cena, Kostka leczenieKostka, int leczeniaBonus) {
        super(waga, cena, nazwa);
        this.leczenieKostka = leczenieKostka;
        this.leczeniaBonus = leczeniaBonus;
        this.pelny = true;
        this.opis = "Przywraca punkty życia.\nRzut wykonywany jest kostką " + leczenieKostka.getIlosc() + "d" + leczenieKostka.getOczka() + ".\nBonus do leczenia: " + leczeniaBonus;
    }

    public boolean isPelny() {
        return pelny;
    }

    public int wypij(){
        return leczeniaBonus + leczenieKostka.rzut();
    }

    public String getOpis(){
        return opis;
    }


}
