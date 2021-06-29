import java.util.Random;

public class Kostka {
    private int ilosc;
    private int oczka;
    private Random rand = new Random();

    public Kostka(int ilosc, int oczka) {
        this.ilosc = ilosc;
        this.oczka = oczka;
    }

    public int rzut(){
        int suma = 0;
        for(int i = 0; i < ilosc ; i++){
            suma = suma + rand.nextInt(oczka) + 1;
        }
        return suma;
    }

}
