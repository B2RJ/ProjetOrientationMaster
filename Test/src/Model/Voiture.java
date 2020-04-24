package Model;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Voiture extends Vehicule {
    private int coordonneX;
    private int coordonneY;

    private Orientation orientation;
    private Direction direction;

    public Voiture() {
        setCoordonneX(2);
        setCoordonneY(2);
        setOrientation(Orientation.Nord);
        setDirection(Direction.NS);
    }

    public void realiserAction() {
        setCoordonneY(getCoordonneY() + 1);
    }

    public void run() {
        while(true) {
            this.realiserAction();
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Vehicule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }
}
