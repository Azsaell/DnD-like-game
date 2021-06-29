public class Postac {
    private Rasa rasa;
    private Statystyki statystyki;
    private Ekwipunek ekwipunek;
    private Pozycja pozycja;

    private int ataki;// Liczba ataków-wykorzystać w planszy albo grze :V


    private Kostka d20 = new Kostka(1,20); //nie mam jeszcze pomyslu gdzie umieścić kostke do rzucania aby nie tworzyć nowej przy każdym wywoalaniu metody :V
//klasy postaci?

    public Postac(Rasa rasa, Statystyki statystyki,/* Ekwipunek ekwipunek,*/ Pozycja pozycja) {
        this.rasa = rasa;
        this.statystyki = statystyki;
        this.ekwipunek = new Ekwipunek(statystyki.getSilaMod());

        this.pozycja = pozycja;
        this.statystyki.zwiekszCon(rasa.getConBonus());
        this.statystyki.zwiekszDex(rasa.getDexBonus());
        this.statystyki.zwiekszStr(rasa.getStrBonus());
        this.statystyki.zwiekszMaxhp(rasa.getHpBonus());
        this.statystyki.zwiekszObrona(rasa.getDefBonus());
        this.statystyki.zwiekszPredkosc(rasa.getSpeedBonus());

        /*if(!(ekwipunek.getPancerz()==null)){
            this.statystyki.zwiekszObrona(ekwipunek.getPancerz().def(statystyki.getZrecznoscMod())- statystyki.getZrecznoscMod());
        }
        if (ekwipunek.getLewaReka() instanceof Tarcza){
            this.statystyki.zwiekszObrona(((Tarcza) ekwipunek.getLewaReka()).getObrona());
        }
        */
    }

    public void ubierz(Przedmiot co, int naCo){ //1=pancer, 2=prawa 3 = lewa
        switch (naCo){//zastanawiam się czy potrzeba funkcji opowiadającej za zdejmowanie rzeczy :V
            case 1:
                if(co instanceof Pancerz) {
                    if(!(ekwipunek.getPancerz() == null)) {
                        statystyki.zwiekszObrona(-ekwipunek.getPancerz().def(statystyki.getZrecznoscMod()));//zdejmuje bonus z pancerza
                    }
                    else{
                        statystyki.zwiekszObrona(-statystyki.getZrecznoscMod());//przypadek gdy nie ma żadnej zbroii
                    }

                    ekwipunek.zalozPancerz((Pancerz) co);
                    statystyki.zwiekszObrona(ekwipunek.getPancerz().def(statystyki.getZrecznoscMod()));
                }
                break;
            case 2:
                if(((Bron) co).isDwureczny()) {
                    if(ekwipunek.getLewaReka() instanceof Tarcza){
                        statystyki.zwiekszObrona(-((Tarcza) ekwipunek.getLewaReka()).getObrona());//usuwa bonus z tarczy
                    }
                }
                ekwipunek.zalozPrawaReka((Bron) co);
                break;
            case 3:
                if(co instanceof Tarcza){
                    statystyki.zwiekszObrona(((Tarcza) co).getObrona());
                }
                if(ekwipunek.getLewaReka() instanceof Tarcza){
                    statystyki.zwiekszObrona(-((Tarcza) ekwipunek.getLewaReka()).getObrona());
                }
                ekwipunek.zalozLewaReka(co);
                break;

        }
    }

    public int atakuj(Przeciwnik przeciwnik){ // przy robieniu gui można będzie przerobić aby zwracało więcej info aby moz a było wyświetlic w logu
        int mod = statystyki.getSilaMod();
        int armor = przeciwnik.getStatystyki().getObrona();

        if(ekwipunek.getPrawaReka().isFiness()){mod = statystyki.getZrecznoscMod();}//zmienia modyfikator z sily na zrecznosc
        int atak = d20.rzut()+ mod +statystyki.getProficiency();
        if(atak>=armor){
            System.out.println("Trafiono wyrzucając: "+ atak);

            if (ekwipunek.getPrawaReka().isDwureczny()){//dla dwurecznych broni
                int dmg = ekwipunek.getPrawaReka().getObrazenia().rzut() + mod;
                przeciwnik.getStatystyki().zadajDMG(dmg);
                return dmg;
            }
            if(ekwipunek.getLewaReka() instanceof Bron){//dla dwoch broni
                int dmg = ((Bron) ekwipunek.getLewaReka()).getObrazenia().rzut() + ekwipunek.getPrawaReka().getObrazenia().rzut() + mod;
                przeciwnik.getStatystyki().zadajDMG(dmg);
                return dmg;
            }
            else {//dla jednej broni
                int dmg =ekwipunek.getPrawaReka().getObrazenia().rzut() + mod;
                przeciwnik.getStatystyki().zadajDMG(dmg);
                return dmg;
            }

        }
        System.out.println("Nie trafiono wyrzucając: "+ atak);
        return 0;
    }

    public void ruch(int x, int y){// zrobiłbym tak ze ta fukcja odpowiadałaby za ruch o 1 pole a funkcja w klasie plansza opowiadałaby za pętle i sprawdzanie poprawnosci ruchu
        pozycja.setX(x);
        pozycja.setY(y);
    }
    public void dash(){//nie wiem jeszcze gdzie przechowywać aktulaną wartośc rucju do wykorzystania
        //użycie akcji na zwiększenie prędkości ruchu
    }

    public Statystyki getStatystyki() {
        return statystyki;
    }

    public int getAtaki() {
        return ataki;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public static void main(String[] args) {
        //Czy tarcza działa
        Tarcza tarcza = new Tarcza(10,10,2);
        Bron sztylet = new Bron(new Kostka(1,4),1,12,12,false,true);
        Ekwipunek ekwipunek = new Ekwipunek(3);
        ekwipunek.zalozLewaReka(tarcza);
        System.out.println("Tarcza dziala: " + ((Tarcza) ekwipunek.getLewaReka()).getObrona());


        //tworzenie postaci i ekwipowanie
        Statystyki statystyki = new Statystyki(16,14,13,4);
        Rasa elf = new Rasa(0,2,0,1,0,0);
        Postac postac = new Postac(elf,statystyki,new Pozycja(0,0));

        Statystyki statystyki1 = new Statystyki(10,10,10,1);
        Rasa goblin = new Rasa(0,0,0,0,2,0);
        Przeciwnik gobos = new Przeciwnik(goblin, statystyki1, new Pozycja(1,1));
        gobos.ubierz(tarcza,3);

        postac.ubierz(sztylet,2);
        postac.ubierz(sztylet,3);



        System.out.println("Zadano: "+ postac.atakuj(gobos)+" obrazeń!");

        //demo planszy
        Postac[][] plansza___ = new Postac[5][5];

        Plansza plansza = new Plansza(plansza___);

        plansza.przemiesc(postac,0,0);
        plansza.przemiesc(gobos,4,4);
        plansza.przemiesc(postac,2,3);
        plansza.przemiesc(gobos,3,1);

    }

}

