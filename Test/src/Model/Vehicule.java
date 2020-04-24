package Model;

import java.util.Observable;

public abstract class Vehicule extends Observable implements Runnable {
    private int coordonneX;
    private int coordonneY;

    private int SIZE_X;
    private int SIZE_Y;

    private Orientation orientation;
    private Direction direction;

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

    public Vehicule(int sizeX, int sizeY) {
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
    }

    public Vehicule(int sizeX, int sizeY, int coordonneX, int coordonneY, Orientation orientation, Direction direction) {
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.coordonneX = coordonneX;
        this.coordonneY = coordonneY;
        this.orientation = orientation;
        this.direction = direction;
    }

    abstract public void run();

    public boolean estEnVie() {
        return (coordonneX <= SIZE_X && coordonneY <= SIZE_Y && coordonneX >= -1 && coordonneY >= -1);
    }

}
