package klasy;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.ArrayList;

public class Plansza{//upgrade!
    private int wiersze;
    private int kolumny;
    private int[][] plansza;
    private ArrayList<Postac> uczestnicy = new ArrayList<>();
    private Postac postac;

    public Plansza(int kolumny, int wiersze, Postac postac) {//na planszy 1=postac 2,3,...=kolejni wrogtowie 0=wolna przestrzen -1=przeszkoda
        this.plansza = new int[kolumny][wiersze];
        uczestnicy.add(postac);//indeksy w liscie beda przesuniete o 1 w stosunku do numerow na plaszy
        this.postac = postac;
        for(int i = 0; i < kolumny; i++){
            for(int j = 0; j < wiersze; j++){
                plansza[i][j] = 0;
            }
        }
        plansza[postac.getPozycja().getX()][postac.getPozycja().getY()]=1;
    }

    public void przemiesc(Postac postac, int x, int y){
        plansza[postac.getPozycja().getX()][postac.getPozycja().getY()]=0;
        plansza[x][y] = uczestnicy.indexOf(postac)+1;
        postac.ruch(x, y);
    }
    public void dodajUczestnika(Przeciwnik przeciwnik){//nie dodałem żadnego sprawdzania czy nie dodaje sie w tym samy miejscu
        uczestnicy.add(przeciwnik);
        plansza[przeciwnik.getPozycja().getX()][przeciwnik.getPozycja().getY()]=uczestnicy.indexOf(przeciwnik)+1;

    }
    public void usunUczestnika(Przeciwnik przeciwnik){//w przypadku smierci
        int ind = uczestnicy.indexOf(przeciwnik);//sie przesuwaja indeksy po usunieciu wiec trzeba zmienic na planszy
        plansza[przeciwnik.getPozycja().getX()][przeciwnik.getPozycja().getY()]=0;
        uczestnicy.remove(przeciwnik);
        for(int i = ind; i<uczestnicy.size();i++){
            plansza[uczestnicy.get(i).getPozycja().getX()][uczestnicy.get(i).getPozycja().getY()]=i+1;
        }
    }

    public ArrayList<Postac> getUczestnicy() {
        return uczestnicy;
    }

    public Postac getBohater(){
        return postac;
    }

    public Postac getPostac(Pozycja pozycja){
        return uczestnicy.get(plansza[pozycja.getX()][pozycja.getY()]-1);
    }

    public int[][] getPlansza() {
        return plansza;
    }

    public int getWiersze() {
        return wiersze;
    }

    public int getKolumny() {
        return kolumny;
    }
}
