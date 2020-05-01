package Model;

import VueController.Main;

import java.util.ArrayList;
import java.util.Observable;

public abstract class Vehicule extends Observable implements Runnable {
    private int coordonneX;
    private int coordonneY;

    private int SIZE_X;
    private int SIZE_Y;

    private Orientation orientation;
    private Direction direction;

    private boolean dejaTourne;

    private Voiture[] voitures;
    private ArrayList fileAttente;

    private Route route;

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

    public ArrayList getFileAttente() {return fileAttente;}
    public void addFileAttente(Vehicule v) { fileAttente.add(v);}
    public void deleteFileAttente(Vehicule v) { fileAttente.remove(v);}

    public Route getRoute() {return route;}

    public Vehicule(int sizeX, int sizeY,
                    int coordonneX, int coordonneY,
                    Orientation orientation, Direction direction,
                    Voiture[] voitures, ArrayList fileAttente,
                    Route route) {
        this.dejaTourne = false;
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
        return (coordonneX <= SIZE_X && coordonneY <= SIZE_Y && coordonneX >= -1 && coordonneY >= -1);
    }

}
