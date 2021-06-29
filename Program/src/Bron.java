public class Bron extends Przedmiot {
    private Kostka obrazenia;
    private int zasieg;
    private boolean dwureczny;
    private boolean finess;//true jesli bron wykorzystuje zręcznosć a nie siłe (łuki, sztylety rapiery itp)

    public Bron(Kostka obrazenia, int zasieg, double waga, double cena, boolean dwureczny, boolean finess) {
        super(waga, cena);
        this.obrazenia = obrazenia;
        this.zasieg = zasieg;
        this.dwureczny = dwureczny;
        this.finess = finess;

    }

    /*public int atak(){ //zmieniłem koncepcje
        return obrazenia.rzut();
    }*/

    public int getZasieg() {
        return zasieg;
    }

    public boolean isDwureczny() {
        return dwureczny;
    }

    public boolean isFiness() {
        return finess;
    }

    public Kostka getObrazenia() {
        return obrazenia;
    }
}
