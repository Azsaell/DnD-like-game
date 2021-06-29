public class Pancerz extends Przedmiot {
    private int obrona;
    private int maxDexBonus; // niektore pancerze np zbroja plytowa dają duży bonus ale uniemożliwają korzystanie z bonusu z dexa
                                // a taka skorzana zbroja daje maly bonus obrony ale umozliwoa dodanie pelnego bonusu z dexa


    public Pancerz(double waga, double cena, int obrona, int maxDexBonus) {
        super(waga, cena);
        this.obrona = obrona;
        this.maxDexBonus = maxDexBonus;
    }

    int def(int dexMod){
        return obrona + Math.min(dexMod,maxDexBonus);
    }

}
