package klasy;

public class Statystyki { //Na bazie dnd siła, zrecznosc i kon są z zakresu 1-20 i dają one modyfiaktor w ze wzoru (stata-10)/2 zaokraglone w dół
    private int sila; // 10 to przeciętna wartość
    private int zrecznosc; // np 13 zr daje mod +1, 20 +5 a 6 -2
    private int kondycja;

    private int silaMod;
    private int zrecznoscMod;
    private int kondycjaMod;


    private int hp;
    private int maxhp;

    private int obrona;

    private int predkosc=6;

    private int poziom;
    private int proficiency;//bonus zalezny od poziomu postaci

    public Statystyki(int sila, int zrecznosc, int kondycja, int poziom) { //chyba ciut to przerobie bo ciut masakrycznie
        this.sila = sila;
        this.zrecznosc = zrecznosc;
        this.kondycja = kondycja;
        this.poziom = poziom;
        silaMod = (int) Math.floor(((sila-10)/2));
        zrecznoscMod = (int) Math.floor(((zrecznosc-10)/2));
        kondycjaMod = (int) Math.floor(((kondycja-10)/2));
        maxhp = poziom*(kondycjaMod+6);
        hp = maxhp;
        proficiency= (int) (2 + Math.floor(poziom/4));
        obrona = 10 + zrecznoscMod;
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
        silaMod = (int) Math.floor(((sila-10)/2));
    }

    public int getSilaMod() {
        return silaMod;

    }

    public void zwiekszStr(int str){
        sila = sila + str;
        silaMod = (int) Math.floor(((sila-10)/2));
    }

    public int getZrecznosc() {
        return zrecznosc;
    }

    public void setZrecznosc(int zrecznosc) {
        this.zrecznosc = zrecznosc;
        zrecznoscMod = (int) Math.floor(((zrecznosc-10)/2));
    }

    public int getZrecznoscMod() {
        return zrecznoscMod;
    }

    public void zwiekszDex(int dex){
        zrecznosc = zrecznosc + dex;
        zrecznoscMod = (int) Math.floor(((zrecznosc-10)/2));
    }

    public int getKondycja() {
        return kondycja;
    }

    public void setKondycja(int kondycja) {
        this.kondycja = kondycja;
        kondycjaMod = (int) Math.floor(((kondycja-10)/2));
    }

    public int getKondycjaMod() {
        return kondycjaMod;
    }

    public void zwiekszCon(int con){
        maxhp=maxhp-kondycjaMod*poziom;
        kondycja = kondycja + con;
        kondycjaMod = (int) Math.floor(((kondycja-10)/2));
        maxhp=maxhp+kondycjaMod*poziom;

    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public void zwiekszMaxhp(int hpMod){

        maxhp = maxhp + hpMod*poziom;
        hp = hp + hpMod*poziom;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void zadajDMG(int dmg) {
        if(hp - dmg > maxhp){
            hp = maxhp;
        }else {
            hp = hp - dmg;
        }
    }

    public int getObrona() {
        return obrona;
    }

    public void setObrona(int obrona) {
        this.obrona = obrona;
    }

    public void zwiekszObrona(int def){
        obrona = obrona + def;
    }

    public int getPoziom() {
        return poziom;
    }

    public void setPoziom(int poziom) {
        this.poziom = poziom;
        proficiency= (int) (2 + Math.floor(poziom/4));
    }

    public int getPredkosc() {
        return predkosc;
    }


    public void setPredkosc(int predkosc) {
        this.predkosc = predkosc;
    }

    public void zwiekszPredkosc(int predkosc) {
        this.predkosc = this.predkosc + predkosc;
    }

    public int getProficiency() {
        return proficiency;
    }
}
