package klasy;


import javafx.geometry.Pos;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Walka extends Thread {
    private Plansza plansza;
    private TreeMap< Double, Postac> inicjatywa = new TreeMap<Double, Postac>(Collections.reverseOrder());
    private int coZrobic =0;
    private Pozycja cel;
    private int ktoWygral=0;//true gracz, false przeciwnik

    private Eliksir eliksir;//troche mi się nie podoba że musze dodac tutaj eliksir ale nie mam innego pomyslu

    private int ataki;
    private int ruch;
    private boolean akcja;

    private Postac gracz;

    private String tekst;

    private boolean czyTuraGracza;

    public Walka(Plansza plansza) {//plansza już ze wszystkimi uczestnikami
        this.plansza = plansza;
        for(int i= 0; i<plansza.getUczestnicy().size();i++ ){
            double liczba = i;
            inicjatywa.put((((double) plansza.getUczestnicy().get(i).inicjatywa()) + liczba/10),plansza.getUczestnicy().get(i));
        }
        gracz = plansza.getUczestnicy().get(0);
        tekst = new String("");
    }

    public void run(){//???? nie wiem czy to dobrze zadziała :V
        boolean tura = true;
        while (plansza.getUczestnicy().size()!=1) {
            for(Map.Entry<Double, Postac> entry : inicjatywa.entrySet()) {
                if (plansza.getUczestnicy().contains(entry.getValue())) {
                    czyTuraGracza =true;
                    Postac aktualnaPostac = entry.getValue();
                    tura = true;
                    ataki = aktualnaPostac.getAtaki();
                    ruch = aktualnaPostac.getStatystyki().getPredkosc();
                    akcja = true;
                    while (tura) {
                        if (!(aktualnaPostac instanceof Przeciwnik)) {
                            if(plansza.getUczestnicy().size()==1){
                                ktoWygral = 2;
                                return;//gracz wygrał
                            }
                            switch (coZrobic) {
                                case 1://ruch
                                    if (ruch > 0) {
                                        if (plansza.getPlansza()[cel.getX()][cel.getY()] == 0 && gracz.getPozycja().getMaxOdl(cel) <= ruch) {
                                            ruch = ruch - gracz.getPozycja().getMaxOdl(cel);
                                            plansza.przemiesc(aktualnaPostac, cel.getX(), cel.getY());

                                        }
                                    }
                                    coZrobic = 0;
                                    break;
                                case 2://atak
                                    if (gracz.getPozycja().getMaxOdl(cel) <= gracz.getZasieg()) {
                                        if (akcja) {
                                            ataki = aktualnaPostac.getAtaki();
                                            akcja = false;
                                        }
                                        if (ataki > 0) {

                                            tekst = aktualnaPostac.atakuj(plansza.getPostac(cel));
                                            if (plansza.getPostac(cel).getStatystyki().getHp() <= 0) {
                                                //inicjatywa.values().remove(plansza.getPostac(cel));
                                                plansza.usunUczestnika((Przeciwnik) plansza.getPostac(cel));
                                            }
                                            --ataki;
                                        }
                                    }
                                    coZrobic = 0;
                                    break;
                                case 3://dash
                                    if (akcja) {
                                        ruch = ruch + aktualnaPostac.getStatystyki().getPredkosc();
                                        akcja = false;
                                    }
                                    coZrobic = 0;
                                    break;
                                case 4://picie potki
                                    if (akcja) {
                                        tekst = gracz.wypij(eliksir);
                                        akcja = false;
                                    }
                                    coZrobic = 0;
                                    break;
                                case 5://koniec
                                    tura = false;
                                    coZrobic = 0;
                                    break;
                            }
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            tekst = "";
                        } else {//AI
                            czyTuraGracza = false;
                            while ((ataki != 0 || akcja) && (ruch != 0 || akcja)) {//chyba dobre logiczne wyrazenie zrobiłem :V
                                if (aktualnaPostac.getPozycja().getMaxOdl(gracz.getPozycja()) > aktualnaPostac.getZasieg()) {//czy gracz znajduje sie w zasiegu?
                                    if (ruch == 0 && akcja) {//czyt skonczyl sie ruch i nalezy zrobic dash?
                                        akcja = false;
                                        ruch = aktualnaPostac.getStatystyki().getPredkosc();
                                    }
                                    //nie mam pomysłu jak zrobić aby wrogowie nie wchodzili sobie na głowy
                                    int ruchX = aktualnaPostac.getPozycja().ruchX(gracz.getPozycja());
                                    int ruchY = aktualnaPostac.getPozycja().ruchY(gracz.getPozycja());
                                    if (plansza.getPlansza()[ruchX][ruchY] == 0 && ruch != 0) {
                                        plansza.przemiesc(aktualnaPostac, ruchX, ruchY);
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {//aby można było zrobić coś na wzór animacji
                                            e.printStackTrace();
                                        }
                                    }//else if(plansza.getPlansza()[ruchX][ruchY]==0)

                                    ruch--;//linijka wyzej rusza o 1 kratke w strone gracza
                                } else {//jeli jest gracz w zasiegu
                                    if (akcja) {
                                        akcja = false;
                                        ataki = aktualnaPostac.getAtaki();//zuzywa akcje by wykonac ataki
                                    }
                                    for (int i = 0; ataki != 0; ataki--) {//atakuje az zuzyje wszystkie ataki
                                        tekst = aktualnaPostac.atakuj(gracz);//to castowanie gracza na przeciwnika mnie zastanawia :V
                                        if (gracz.getStatystyki().getHp() < 0) {
                                            ktoWygral = 1;
                                            return; //gracz nie zyje
                                        }


                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {//aby nie były te ataki natychmiastowe
                                            e.printStackTrace();
                                        }
                                        tekst = "";
                                    }
                                }
                            }
                            tura = false;
                        }
                    }
                }
            }
        }
    }

    public void setCoZrobic(int coZrobic) {//input do myszy jak bedzie battle mapka
        this.coZrobic = coZrobic;
    }//przycisk z boku ekranu decydujący co robić

    public void setCel(Pozycja cel) {
        this.cel = cel;
    }//naciśniecie na plansze zapewne[trzeba będzie najpierw to przesłać a potem co zrobić bo inaczej system może się wyglebić xd]
//I to z 2 powodów: nie zeruje celu po wykonaniu czynności oraz jesli przed 1 wybraniem celu gracz wykona akcje to wszystko zdechnie bo cel będzie pusty
    public int getKtoWygral() {// trzeba będzie pobierać z tego regularnie by sprawdzić czy ktos wygrał
        return ktoWygral;
    }

    public void setEliksir(Eliksir eliksir) {//tutaj wpisać aktualny eliksir z comboboxa [w gui sprawdzić czy combobox ma jakies eliksiry do uzycia]
        this.eliksir = eliksir;
    }

    public String getTekst() {
        return tekst;
    }

    public Eliksir getEliksir() {
        return eliksir;
    }

    public boolean isCzyTuraGracza() {
        return czyTuraGracza;
    }

    public int getRuch() {
        return ruch;
    }
}
