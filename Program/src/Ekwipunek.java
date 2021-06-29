public class Ekwipunek {
    private Pancerz pancerz;
    private Bron prawaReka;
    private Przedmiot lewaReka;
    private double maxUdzwig;//jeszcze nie wykorzystane zapewne będzie miało znaczenie gdy zrobi sie liste przedmiotów nie ubranych :V
    private double aktualnyUdzwig;

    public Ekwipunek(int strMod) {
        this.maxUdzwig = strMod*15;

    }
    public void zalozPancerz(Pancerz pancerz){
        this.pancerz = pancerz;
    }

    public void zalozPrawaReka (Bron bron){
        if( bron.isDwureczny()){
            prawaReka = bron;
            lewaReka = bron;
        }
        else{
           prawaReka = bron;
        }

    }

    public void zalozLewaReka (Przedmiot przedmiot){
        if( !(przedmiot instanceof Pancerz)){
           lewaReka = przedmiot;
        }
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
/*
    public void dodajPrzedmiot(Przedmiot przedmiot){
        if(przedmiot instanceof Bron){
            if(((Bron) przedmiot).isDwureczny()){
                prawaReka = (Bron) przedmiot;
                lewaReka = (Bron) przedmiot;
            }
            else{
                //jak zaimplementować do ktorej ręki?
            }
        }
        if (przedmiot instanceof Pancerz){
            pancerz = (Pancerz) przedmiot;

        }
    }

*/

}
