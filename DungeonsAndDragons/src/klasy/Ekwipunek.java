package klasy;

import java.util.ArrayList;

public class Ekwipunek {
    private Pancerz pancerz;
    private Bron prawaReka;
    private Przedmiot lewaReka;
    private double maxUdzwig;//jeszcze nie wykorzystane zapewne będzie miało znaczenie gdy zrobi sie liste przedmiotów nie ubranych :V
    private double aktualnyUdzwig=0;
    private ArrayList<Przedmiot> plecak = new ArrayList<>();
    private ArrayList<Eliksir> pasek = new ArrayList<>();
    private int mieszek=0;

    public Ekwipunek(int str) {
        this.maxUdzwig = str*15;

    }
    public void zalozPancerz(Pancerz pancerz){
        if(this.pancerz!=null){
            plecak.add(this.pancerz);
        }
        this.pancerz = pancerz;
        plecak.remove(pancerz);
    }

    public void zalozPrawaReka (Bron bron){
        if( bron.isDwureczny()){
            if(prawaReka!=null){
                plecak.add(prawaReka);
                if(prawaReka.isDwureczny()){
                    lewaReka=null;
                }
            }

             if(lewaReka!=null){
                 plecak.add(lewaReka);
             }lewaReka = null;


            prawaReka = bron;
            lewaReka = bron;
            plecak.remove(bron);
        }
        else{
            if(prawaReka!=null){
                plecak.add(prawaReka);
                if(prawaReka.isDwureczny()){
                    lewaReka = null;
                }
            }
            prawaReka = bron;
            plecak.remove(bron);
        }

    }

    public void zalozLewaReka (Przedmiot przedmiot){
        if( przedmiot instanceof Bron || przedmiot instanceof Tarcza){
            if(lewaReka instanceof Bron){
                if(((Bron)lewaReka).isDwureczny()){
                    prawaReka = null;
                }
            }
            if(lewaReka!=null) {
                plecak.add(lewaReka);
            }
            lewaReka = przedmiot;
            if(prawaReka!=null&&prawaReka.isDwureczny()){
                prawaReka=null;
            }
            plecak.remove(przedmiot);
            if(lewaReka instanceof Bron && ((Bron) lewaReka).isDwureczny()){
                if(prawaReka!=null){
                    plecak.add(prawaReka);
                }
                prawaReka = (Bron) lewaReka;
            }
        }
    }

    public void zdejmijLewaReka (){
        if(lewaReka!=null) {
            plecak.add(lewaReka);
            if (prawaReka!=null&&prawaReka.isDwureczny()) {
                prawaReka = null;
                lewaReka = null;
            } else {
                lewaReka = null;
            }

        }
    }

    public void zdejmijPancerz(){
        if(pancerz!=null) {
            plecak.add(pancerz);
            pancerz = null;
        }

    }

    public void zdejmijPrawaReka (){
        if(prawaReka!=null) {
            plecak.add(prawaReka);
            if (prawaReka.isDwureczny()) {

                prawaReka = null;
                lewaReka = null;
            } else {
                prawaReka = null;
            }
        }

    }




    public void dodajDoPlecaka(Przedmiot przedmiot){
        if(przedmiot instanceof Eliksir){
            pasek.add((Eliksir) przedmiot);
        }else {
            plecak.add(przedmiot);
            aktualnyUdzwig = aktualnyUdzwig + przedmiot.getWaga();
        }
    }
    public void usunZPlecaka(Przedmiot przedmiot){
        if(przedmiot instanceof Eliksir){
            pasek.remove(przedmiot);
        }else {
            plecak.remove(przedmiot);
            aktualnyUdzwig = aktualnyUdzwig - przedmiot.getWaga();
        }
    }

    public void dodajZłoto(int zloto){//odejmowanie złota to można zrobić dodając ujemne złoto
        mieszek += zloto;
    }


    public Pancerz getPancerz() {
        return pancerz;
    }

    public Bron getPrawaReka() {
        return prawaReka;
    }

    public Przedmiot getLewaReka() {
        return lewaReka;
    }

    public double getMaxUdzwig() {
        return maxUdzwig;
    }

    public double getAktualnyUdzwig() {
        return aktualnyUdzwig;
    }

    public ArrayList<Przedmiot> getPlecak() {
        return plecak;
    }

    public ArrayList<Eliksir> getPasek() {
        return pasek;
    }

    public int getMieszek() {
        return mieszek;
    }

    public void zaaktualizujUdzwig(int sila){
        maxUdzwig=sila*15;
    }



    /*
    public void dodajPrzedmiot(klasy.Przedmiot przedmiot){
        if(przedmiot instanceof klasy.Bron){
            if(((klasy.Bron) przedmiot).isDwureczny()){
                prawaReka = (klasy.Bron) przedmiot;
                lewaReka = (klasy.Bron) przedmiot;
            }
            else{
                //jak zaimplementować do ktorej ręki?
            }
        }
        if (przedmiot instanceof klasy.Pancerz){
            pancerz = (klasy.Pancerz) przedmiot;

        }
    }

*/

}
