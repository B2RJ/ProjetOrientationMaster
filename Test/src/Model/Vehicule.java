package Model;


import javafx.scene.shape.Circle;

import java.util.Observable;

public abstract class Vehicule extends Observable implements Runnable {
    private int coordonneX;
    private int coordonneY;

    private Orientation orientation;
    private Direction direction;

    private Couleur couleur;

    private Circle  maForme = new Circle();

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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }
    public Couleur getCouleur() {
        return couleur;
    }

    public void setMaForme(Circle maForme) {
        this.maForme = maForme;
    }
    public Circle getMaForme() {
        return maForme;
    }

    public Vehicule() {
    }

    abstract public void run();



}
