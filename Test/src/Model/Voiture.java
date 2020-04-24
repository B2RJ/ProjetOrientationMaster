package Model;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Voiture extends Vehicule {
    private int coordonneX;
    private int coordonneY;

    private Orientation orientation;
    private Direction direction;

    public Voiture(int sizeX, int sizeY) {
        super(sizeX, sizeY);
        setCoordonneX(2);
        setCoordonneY(2);
        setOrientation(Orientation.Nord);
        setDirection(Direction.NS);
    }

    public Voiture(int sizeX, int sizeY, int coordonneX, int coordonneY, Orientation orientation, Direction direction) {
        super( sizeX,  sizeY,  coordonneX,  coordonneY,  orientation,  direction);
    }

    public void realiserAction() {
        if(voieEstLibre()) {
            switch (getDirection())
            {
                case NS:
                    setCoordonneY(getCoordonneY()+1);
                    break;

                case SN:
                    setCoordonneY(getCoordonneY()-1);
                    break;

                case EO:
                    setCoordonneX(getCoordonneX()-1);
                    break;

                case OE:
                    setCoordonneX(getCoordonneX()+1);
                    break;

                default:
                    System.out.println("Ce trajet n'est pas encore implémenté");
            }
        }
    }

    public boolean voieEstLibre() {
        return true;
    }

    public void run() {
        while(estEnVie()) {
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
