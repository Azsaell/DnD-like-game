public class Rasa { //Zrobiłbym omże raczej że zamiast klasa statystyki by korzystała z ras to raczej klasa postać by z obu korzystała i lączyła je
    private int strBonus;           //acz jeszcze pewney nie jestem
    private int dexBonus;
    private int conBonus;  // jakoś z angielskiego mi bardziej pasują
    private int speedBonus;
    private int defBonus;
    private int hpBonus;

    public Rasa(int strBonus, int dexBonus, int conBonus, int speedBonus, int defBonus, int hpBonus) {
        this.strBonus = strBonus;
        this.dexBonus = dexBonus;
        this.conBonus = conBonus;
        this.speedBonus = speedBonus;
        this.defBonus = defBonus;
        this.hpBonus = hpBonus; // to raczej będzie hp co level więc trzeba bedzei to uwzglednic przy liczeniu max hp
    }

    public int getStrBonus() {
        return strBonus;
    }

    public int getDexBonus() {
        return dexBonus;
    }

    public int getConBonus() {
        return conBonus;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }

    public int getDefBonus() {
        return defBonus;
    }

    public int getHpBonus() {
        return hpBonus;
    }
}
