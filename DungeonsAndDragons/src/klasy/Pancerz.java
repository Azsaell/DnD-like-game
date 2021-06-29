package klasy;

public class Pancerz extends Przedmiot {
    private int obrona;
    private int maxDexBonus; // niektore pancerze np zbroja plytowa dają duży bonus ale uniemożliwają korzystanie z bonusu z dexa// a taka skorzana zbroja daje maly bonus obrony ale umozliwoa dodanie pelnego bonusu z dexa
    private String sciezkaDoImg;

    public Pancerz(String nazwa, double waga, double cena, int obrona, int maxDexBonus, String sciezkaDoImg) {
        super(waga, cena, nazwa);
        this.obrona = obrona;
        this.maxDexBonus = maxDexBonus;
        this.sciezkaDoImg = sciezkaDoImg;
    }

    int def(int dexMod){
        return obrona + Math.min(dexMod,maxDexBonus);
    }

    public int getObrona() {
        return obrona;
    }

    public int getMaxDexBonus() {
        return maxDexBonus;
    }

    public String getSciezkaDoImg() {
        return sciezkaDoImg;
    }
}
