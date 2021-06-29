package klasy;

public class Postac {
    private Rasa rasa;
    private Statystyki statystyki;
    private Ekwipunek ekwipunek;
    private Pozycja pozycja;

    private String imie;

    private int zasieg;

    private int ataki;// Liczba ataków-wykorzystać w planszy albo grze :V


    private Kostka d20 = new Kostka(1,20); //nie mam jeszcze pomyslu gdzie umieścić kostke do rzucania aby nie tworzyć nowej przy każdym wywoalaniu metody :V
//klasy postaci?

    public Postac(Rasa rasa, Statystyki statystyki,/* klasy.Ekwipunek ekwipunek,*/ Pozycja pozycja, String imie) {
        this.rasa = rasa;
        this.statystyki = statystyki;
        this.ekwipunek = new Ekwipunek(statystyki.getSila());

        zasieg = 1;

        this.imie = imie;

        this.pozycja = pozycja;
        this.statystyki.zwiekszCon(rasa.getConBonus());
        this.statystyki.zwiekszDex(rasa.getDexBonus());
        this.statystyki.zwiekszStr(rasa.getStrBonus());
        this.statystyki.zwiekszMaxhp(rasa.getHpBonus());
        this.statystyki.zwiekszObrona(rasa.getDefBonus());
        this.statystyki.zwiekszPredkosc(rasa.getSpeedBonus());

        ataki= (int) (Math.floor(statystyki.getPoziom()/5)+1);

        /*if(!(ekwipunek.getPancerz()==null)){
            this.statystyki.zwiekszObrona(ekwipunek.getPancerz().def(statystyki.getZrecznoscMod())- statystyki.getZrecznoscMod());
        }
        if (ekwipunek.getLewaReka() instanceof klasy.Tarcza){
            this.statystyki.zwiekszObrona(((klasy.Tarcza) ekwipunek.getLewaReka()).getObrona());
        }
        */
    }

    public void ubierz(Przedmiot co, int naCo){ //1=pancer, 2=prawa 3 = lewa
        switch (naCo){
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
                if(co instanceof Bron) {
                    if (((Bron) co).isDwureczny()) {
                        if (ekwipunek.getLewaReka() instanceof Tarcza) {
                            statystyki.zwiekszObrona(-((Tarcza) ekwipunek.getLewaReka()).getObrona());//usuwa bonus z tarczy
                        }
                    }
                    ekwipunek.zalozPrawaReka((Bron) co);
                    zasieg = ((Bron) co).getZasieg();
                }
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
    public void zdejmij(int naCo){ //1=pancer, 2=prawa 3 = lewa
        switch (naCo){
            case 1:
                if(!(ekwipunek.getPancerz() == null)) {
                    statystyki.zwiekszObrona(-ekwipunek.getPancerz().def(statystyki.getZrecznoscMod()));//zdejmuje bonus z pancerza
                }
                ekwipunek.zdejmijPancerz();
                statystyki.zwiekszObrona(statystyki.getZrecznoscMod());
                break;
            case 2:
                ekwipunek.zdejmijPrawaReka();
                zasieg=1;
                break;
            case 3:
                if(ekwipunek.getLewaReka() instanceof Tarcza){
                    statystyki.zwiekszObrona(-((Tarcza) ekwipunek.getLewaReka()).getObrona());
                }
                ekwipunek.zdejmijLewaReka();
                break;

        }
    }

    public String wypij(Eliksir eliksir){
        int heal =eliksir.wypij();
        statystyki.zadajDMG(-heal);//zadaje ujemne obrazenia == leczy :P
        ekwipunek.usunZPlecaka(eliksir);
        return imie + " uleczył się za " + heal + " punktów życia";
    }

    public void podnies(Przedmiot przedmiot){
        if ((ekwipunek.getAktualnyUdzwig()+przedmiot.getWaga())<=ekwipunek.getMaxUdzwig()){
            ekwipunek.dodajDoPlecaka(przedmiot);
        }
        else{
            System.out.println("Za mało miejsca w plecaku ");
        }


    }
    public void wyrzuc(Przedmiot przedmiot){
        ekwipunek.usunZPlecaka(przedmiot);

    }

    public String atakuj(Postac przeciwnik){ // przy robieniu gui można będzie przerobić aby zwracało więcej info aby moz a było wyświetlic w logu
        int mod = statystyki.getSilaMod();
        int armor = przeciwnik.getStatystyki().getObrona();

        if(ekwipunek.getPrawaReka()!= null && ekwipunek.getPrawaReka().isFiness()){mod = statystyki.getZrecznoscMod();}//zmienia modyfikator z sily na zrecznosc
        int atak = d20.rzut()+ mod +statystyki.getProficiency();
        if(atak>=armor){
            System.out.println(imie + " Trafił wyrzucając: "+ atak);

            if (ekwipunek.getPrawaReka()!= null && ekwipunek.getPrawaReka().isDwureczny()){//dla dwurecznych broni
                int dmg = ekwipunek.getPrawaReka().getObrazenia().rzut() + mod;
                przeciwnik.getStatystyki().zadajDMG(dmg);
                return imie+" Trafił wyrzucając: "+ atak + " Zadano: " + dmg + " obrażeń";
            }
            if(ekwipunek.getLewaReka() instanceof Bron){//dla dwoch broni
                if(ekwipunek.getPrawaReka()==null){
                    int dmg = ((Bron) ekwipunek.getLewaReka()).getObrazenia().rzut() + 1 + mod;
                    przeciwnik.getStatystyki().zadajDMG(dmg);
                    return imie+" Trafił wyrzucając: "+ atak + " Zadano: " + dmg + " obrażeń";
                }
                int dmg = ((Bron) ekwipunek.getLewaReka()).getObrazenia().rzut() + ekwipunek.getPrawaReka().getObrazenia().rzut();
                przeciwnik.getStatystyki().zadajDMG(dmg);
                return imie+" Trafił wyrzucając: "+ atak + " Zadano: " + dmg + " obrażeń";
            }
            else {//dla jednej broni
                if(ekwipunek.getPrawaReka()==null){
                    int dmg = 1 + mod;
                    przeciwnik.getStatystyki().zadajDMG(dmg);
                    return imie+" Trafił wyrzucając: "+ atak + " Zadano: " + dmg + " obrażeń";
                }
                int dmg =ekwipunek.getPrawaReka().getObrazenia().rzut() + mod;
                przeciwnik.getStatystyki().zadajDMG(dmg);
                return imie+" Trafił wyrzucając: "+ atak + " Zadano: " + dmg + " obrażeń";
            }

        }
        System.out.println("Nie trafiono wyrzucając: " + atak);
        return imie +" Nie trafił wyrzucając: " + atak;
    }

    public void ruch(int x, int y){// zrobiłbym tak ze ta fukcja odpowiadałaby za ruch o 1 pole a funkcja w klasie plansza opowiadałaby za pętle i sprawdzanie poprawnosci ruchu
        pozycja.setX(x);
        pozycja.setY(y);
    }


    public int inicjatywa(){
        return d20.rzut() + statystyki.getZrecznoscMod();
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

    public int getZasieg() {
        return zasieg;
    }

    public klasy.Ekwipunek getEkwipunek() {
        return ekwipunek;
    }

    public void zwStr(int str){
        statystyki.zwiekszStr(str);
        ekwipunek.zaaktualizujUdzwig(statystyki.getSila());
    }
    public void zwDex(int dex){
        if(!(ekwipunek.getPancerz() == null)) {
            statystyki.zwiekszObrona(-ekwipunek.getPancerz().def(statystyki.getZrecznoscMod()));//zdejmuje bonus z pancerza
            statystyki.zwiekszDex(dex);
            statystyki.zwiekszObrona(+ekwipunek.getPancerz().def(statystyki.getZrecznoscMod()));
        }
        else{
            statystyki.zwiekszObrona(-statystyki.getZrecznoscMod());//przypadek gdy nie ma żadnej zbroii
            statystyki.zwiekszDex(dex);
            statystyki.zwiekszObrona(statystyki.getZrecznoscMod());
        }
    }

    public static void main(String[] args) {
        /*
        //Czy tarcza działa
        Tarcza tarcza = new Tarcza("tarcza", 10,10,2, "Pictures/Avatar.png");
        Bron sztylet = new Bron("Sztylet", new Kostka(1,4),1,12,12,false,true, "Pictures/Avatar.png");
        Ekwipunek ekwipunek = new Ekwipunek(3);
        ekwipunek.zalozLewaReka(tarcza);
        System.out.println("klasy.Tarcza dziala: " + ((Tarcza) ekwipunek.getLewaReka()).getObrona());


        //tworzenie postaci i ekwipowanie
        Statystyki statystyki = new Statystyki(16,14,13,4);
        Rasa elf = new Rasa("Elf", 0,2,0,1,0,0, "Pictures/Avatar.png");
        Postac postac = new Postac(elf,statystyki,new Pozycja(0,0));

        Statystyki statystyki1 = new Statystyki(10,10,10,1);
        Rasa goblin = new Rasa("Goblin", 0,0,0,0,2,0, "Pictures/Avatar.png");
        Przeciwnik gobos = new Przeciwnik(goblin, statystyki1, new Pozycja(1,1));
        Przeciwnik gobos2 = new Przeciwnik(goblin, statystyki1, new Pozycja(2,2));
        gobos.podnies(tarcza);
        gobos.ubierz(tarcza,3);
        postac.podnies(sztylet);
        postac.podnies(sztylet);

        postac.ubierz(sztylet,2);
        postac.ubierz(sztylet,3);

        postac.zdejmij(2);//testowanie ataku bez broni
        postac.zdejmij(3);




        System.out.println("Zadano: "+ postac.atakuj(gobos)+" obrazeń!");

        //demo planszy
        int[][] plansza___ = new int[5][5];

        Plansza plansza = new Plansza(plansza___,postac);
        plansza.dodajUczestnika(gobos);
        plansza.dodajUczestnika(gobos2);

        plansza.przemiesc(postac,0,0);
        plansza.przemiesc(gobos,4,4);
        plansza.przemiesc(postac,2,3);
        plansza.przemiesc(gobos,3,1);

        Walka walka = new Walka(plansza);
        */

    }

}

