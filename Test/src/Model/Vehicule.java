package Model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This is the mother class of each vehicle
 *
 * @author B2RJ
 */


public abstract class Vehicule extends Observable implements Runnable {
    /**
     * This is the position in the representation of the road
     *
     * @see Route
     */
    private int coordonneX;
    private int coordonneY;

    /**
     * This is the size of the road
     */
    private final int SIZE_X;
    private final int SIZE_Y;

    /**
     * This is the variable to know the orientation
     *
     * @see Orientation
     */
    private Orientation orientation;

    /** This is the variable to know the direction
     *
     * @see Direction
     */
    private Direction direction;

    /**
     * This boolean is use to know if the vehicule has already turned on the intersection
     */
    private boolean dejaTourne;

    /**
     * This boolean is use to start vehicule outside the graphical representation.
     * It's use to had more vehicle during the simulation
     */
    private boolean tuable;

    /**
     * This is the arrayList of all vehicles in the intersection
     */
    private ArrayList<Voiture> voitures;

    /**
     * This is the arrayList of the waiting list
     */
    private ArrayList<Vehicule> fileAttente;

    /**
     * This is the road
     */
    private final Route route;

    /**
     * Setter to change the position in X
     * @param coordonneX
     */
    public void setCoordonneX(int coordonneX) {
        this.coordonneX = coordonneX;
    }

    /**
     * Getter to have the position in X
     * @return coordonneX
     */
    public int getCoordonneX() {
        return coordonneX;
    }

    /**
     * Setter to change the position in Y
     * @param coordonneY
     */
    public void setCoordonneY(int coordonneY) {
        this.coordonneY = coordonneY;
    }

    /**
     * Getter to have the position in Y
     * @return coordonneY
     */
    public int getCoordonneY() {
        return coordonneY;
    }

    /**
     * Setter to change the orientation
     * @param orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Getter to have the orientation
     * @return orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Getter to know if the vehicule has already turned
     * @return alreadyTurned
     */
    public boolean getDejaTourner() { return this.dejaTourne;}

    /**
     * Setter to change the orientation
     * @param b
     */
    public void setDejaTourne(boolean b) { this.dejaTourne = b; }

    /**
     * Setter to change the direction
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Getter to have the direction
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Getter to have the arrayList of the vehicle
     * @return voitures
     */
    public ArrayList<Voiture> getVoitures() { return voitures;}

    /**
     * Setter to change the arrayList of the vehicle
     * @param newVoiture
     */
    public void setVoiture(ArrayList<Voiture> newVoiture) { this.voitures = newVoiture; }

    /**
     * Getter to have the boolean killable
     * @return killable
     */
    public boolean getTuable() { return tuable;}

    /**
     * Setter to change the boolean killable
     * @param b
     */
    public void setTuable(boolean b) { this.tuable = b;}

    /**
     * Getter to have the waiting list
     * @return fileAttente
     */
    public ArrayList<Vehicule> getFileAttente() {return fileAttente;}

    /**
     * This add the vehicle in the waiting list
     * @param v : The vehicule to add
     */
    public void addFileAttente(Vehicule v) {
        // This is in case we are stop on a decision case
        if(!fileAttente.contains(v)) {
            fileAttente.add(v);
        }
    }

    /**
     * This is to remove a vehicle from the waiting list
     * @param v : The vehicule to remove
     */
    public void deleteFileAttente(Vehicule v) {
        // On utilise le while au cas où il y a deux accès en même temps sur la file
        while(fileAttente.contains(this)) {
            fileAttente.remove(v);
        }
    }

    /**
     * Getter to have the road
     * @return route
     */
    public Route getRoute() {return route;}

    /**
     * This is to know if we are waiting
     * @return boolean : true if we are waiting, false otherwise
     */
    public boolean amIwaiting() {
        return (route.tabCase[getCoordonneX()][getCoordonneY()] instanceof Attente);
    }

    /**
     * This is to know if I there is someone on the intersection
     * @return
     */
    public boolean canIgo() {
        for (Voiture v : voitures) {
            if (v.getCoordonneX() < 8 && v.getCoordonneX() > -1
            && v.getCoordonneY() < 8 && v.getCoordonneY() > -1) {
                if (v.getRoute().tabCase[v.getCoordonneX()][v.getCoordonneY()] instanceof Conflit && v != this) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The constructor of the vehicle class
     * @param sizeX : The size of the road
     * @param sizeY : The size of the road
     * @param coordonneX : The position of the vehicle
     * @param coordonneY : The position of the vehicle
     * @param orientation : The orientation of the vehicle
     * @param direction : The direction of the vehicle
     * @param voitures : The arrayList with all vehicles
     * @param fileAttente : The arrayList with the waitingList
     * @param route: The road
     */
    public Vehicule(int sizeX, int sizeY,
                    int coordonneX, int coordonneY,
                    Orientation orientation, Direction direction,
                    ArrayList<Voiture> voitures, ArrayList<Vehicule> fileAttente,
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

    /**
     * Use for the multi-threading
     */
    abstract public void run();

    /**
     * To know if the vehicle is out of the intersection
     * @return
     */
    public boolean estEnVie() {
        if(getTuable()) {
            return (coordonneX <= SIZE_X && coordonneY <= SIZE_Y && coordonneX >= -1 && coordonneY >= -1);
        }
        return (true);
    }

}
