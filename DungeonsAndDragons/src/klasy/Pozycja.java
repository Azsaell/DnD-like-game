package klasy;

public class Pozycja {
    private int x;
    private int y;

    public Pozycja(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int ruchY(Pozycja pozycja){
        return y + (int) Math.round((pozycja.getY()-y)/Math.sqrt(Math.pow(pozycja.getY()-y,2)+Math.pow(pozycja.getX()-x,2)));

    }
    public int ruchX(Pozycja pozycja){
        return x + (int) Math.round((pozycja.getX()-x)/Math.sqrt(Math.pow(pozycja.getY()-y,2)+Math.pow(pozycja.getX()-x,2)));

    }



    public int getMaxOdl(Pozycja pozycja){
        int odlY = Math.abs(y- pozycja.getY());
        int odlX = Math.abs(x- pozycja.getX());
        if(odlY>=odlX){
            return odlY;
        }
        return odlX;
    }
}
