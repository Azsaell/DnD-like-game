public class Plansza {//nwm czy nawet na ta klasa jest potrzebna :V
    Postac[][] plansza;

    public Plansza(Postac[][] plansza) {//null=puste pola, acz nie wiem jak zrobić przeszkody i sciany bo musiałyby dziedziczyć po klasie postac :V
        this.plansza = plansza;
    }
    void przemiesc(Postac postac, int x, int y){
        plansza[postac.getPozycja().getX()][postac.getPozycja().getY()]=null;
        plansza[x][y] = postac;
        postac.ruch(x, y);
    }
}
