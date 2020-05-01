package Model;

import java.util.ArrayList;
import java.util.Observable;

public abstract class Vehicule extends Observable implements Runnable {
    private int coordonneX;
    private int coordonneY;

    private final int SIZE_X;
    private final int SIZE_Y;

    private Orientation orientation;
    private Direction direction;

    // Ce booleen nous permet de gagner du temps sur la fonction dejaTourne
    private boolean dejaTourne;

    //Le but de ce booleen est de faire partir les voitures depuis l'extérieur de la carte.
    // Ainsi, il y aura plus de réalité au cours de la simulation
    private boolean tuable;

    private Voiture[] voitures;
    private ArrayList<Vehicule> fileAttente;

    private final Route route;

    public void setCoordonneX(int coordonneX) {
        this.coordonneX = coordonneX;
    }
    public int getCoordonneX() {
        return coordonneX;
    }

    public void setCoordonneY(int coordonneY) {
        this.coordonneY = coordonneY;
    }
    public int getCoordonneY() {
        return coordonneY;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
    public Orientation getOrientation() {
        return orientation;
    }

    public boolean getDejaTourner() { return this.dejaTourne;}
    public void setDejaTourne(boolean b) { this.dejaTourne = b; }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }

    public Voiture[] getVoitures() { return voitures;}
    public void setVoiture(Voiture[] newVoiture) { this.voitures = newVoiture; }

    public boolean getTuable() { return tuable;}
    public void setTuable(boolean b) { this.tuable = b;}

    public ArrayList<Vehicule> getFileAttente() {return fileAttente;}
    public void addFileAttente(Vehicule v) {
        //On utilise le if au cas où nous somme à l'arret sur une case Décision
        if(!fileAttente.contains(v)) {
            fileAttente.add(v);
        }
    }

    public void deleteFileAttente(Vehicule v) {
        // On utilise le while au cas où il y a deux accès en même temps sur la file
        while(fileAttente.contains(this)) {
            fileAttente.remove(v);
        }
    }

    public Route getRoute() {return route;}

    public Vehicule(int sizeX, int sizeY,
                    int coordonneX, int coordonneY,
                    Orientation orientation, Direction direction,
                    Voiture[] voitures, ArrayList<Vehicule> fileAttente,
                    Route route) {
        this.dejaTourne = false;
        this.tuable = false;
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.coordonneX = coordonneX;
        this.coordonneY = coordonneY;
        this.orientation = orientation;
        this.direction = direction;
        this.voitures = voitures;
        this.fileAttente = fileAttente;
        this.route = route;
    }

    abstract public void run();


    public boolean estEnVie() {
        if(getTuable()) {
            return (coordonneX <= SIZE_X && coordonneY <= SIZE_Y && coordonneX >= -1 && coordonneY >= -1);
        }
        return (true);
    }

}
