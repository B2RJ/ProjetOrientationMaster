package Model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This is the mother class of each vehicle
 *
 * @author B2RJ
 */


public abstract class Vehicle extends Observable implements Runnable {
    /**
     * This is the position in the representation of the road
     *
     * @see Road
     */
    private int coordinateX;
    private int coordinateY;

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
    private boolean alreadyTurned;

    /**
     * This boolean is use to start vehicule outside the graphical representation.
     * It's use to had more vehicle during the simulation
     */
    private boolean killable;

    /**
     * This is the arrayList of all vehicles in the intersection
     */
    private ArrayList<Car> cars;

    /**
     * This is the arrayList of the waiting list
     */
    private ArrayList<Vehicle> waitingList;

    /**
     * This is the road
     */
    private final Road road;

    /**
     * Setter to change the position in X
     * @param coordinateX
     */
    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Getter to have the position in X
     * @return coordonneX
     */
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Setter to change the position in Y
     * @param coordinateY
     */
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    /**
     * Getter to have the position in Y
     * @return coordonneY
     */
    public int getCoordinateY() {
        return coordinateY;
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
     * @return getAlreadyTurned
     */
    public boolean getAlreadyTurned() { return this.alreadyTurned;}

    /**
     * Setter to change the orientation
     * @param b
     */
    public void setAlreadyTurned(boolean b) { this.alreadyTurned = b; }

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
    public ArrayList<Car> getCars() { return cars;}

    /**
     * Setter to change the arrayList of the vehicle
     * @param newCar
     */
    public void setCars(ArrayList<Car> newCar) { this.cars = newCar; }

    /**
     * Getter to have the boolean killable
     * @return killable
     */
    public boolean getKillable() { return killable;}

    /**
     * Setter to change the boolean killable
     * @param b
     */
    public void setKillable(boolean b) { this.killable = b;}

    /**
     * Getter to have the waiting list
     * @return fileAttente
     */
    public ArrayList<Vehicle> getWaitingList() {return waitingList;}

    /**
     * This add the vehicle in the waiting list
     * @param v : The vehicule to add
     */
    public void addWaintingList(Vehicle v) {
        // This is in case we are stop on a decision case
        if(!waitingList.contains(v)) {
            waitingList.add(v);
        }
    }

    /**
     * This is to remove a vehicle from the waiting list
     * @param v : The vehicule to remove
     */
    public void deleteFileAttente(Vehicle v) {
        // We use the while in cas there is two acces on the list
        while(waitingList.contains(this)) {
            waitingList.remove(v);
        }
    }

    /**
     * Getter to have the road
     * @return route
     */
    public Road getRoad() {return road;}

    /**
     * This function is to forward in all case
     */
    public void tread() {
        switch (getOrientation())
        {
            case North:
                setCoordinateY(getCoordinateY()-1);
                break;
            case South:
                setCoordinateY(getCoordinateY()+1);
                break;
            case East:
                setCoordinateX(getCoordinateX()+1);
                break;

            case West:
                setCoordinateX(getCoordinateX()-1);
                break;
        }
    }

    /**
     * This function is to forward when there is nothing in the next case
     */
    public void treadCarefully() {
        if(NextCaseFree()) {
            tread();
        }
    }

    /**
     * This function is to forward outside the graphical representation
     */
    public void treadOutsideMap() {
        tread();
        if (getCoordinateY() > -1 && getCoordinateX() > -1 && getCoordinateX() < 8 && getCoordinateY() < 8) {
            this.setKillable(true);
        }
    }

    /**
     * This function is to know if the next case are free
     * @return true if it is, false otherwise
     */
    public boolean NextCaseFree() {
        switch (getOrientation())
        {
            case North:
                for (Car car : getCars()) {
                    if(car.getCoordinateX() == this.getCoordinateX() && car.getCoordinateY() == this.getCoordinateY()-1) {
                        return false;
                    }
                }
                break;
            case South:
                for (Car car : getCars()) {
                    if(car.getCoordinateX() == this.getCoordinateX() && car.getCoordinateY() == this.getCoordinateY()+1) {
                        return false;
                    }
                }
                break;
            case East:
                for (Car car : getCars()) {
                    if(car.getCoordinateX() == this.getCoordinateX()+1 && car.getCoordinateY() == this.getCoordinateY()) {
                        return false;
                    }
                }
                break;

            case West:
                for (Car car : getCars()) {
                    if(car.getCoordinateX() == this.getCoordinateX()-1 && car.getCoordinateY() == this.getCoordinateY()) {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    /**
     * This function is to know if I have to change the orientation
     * @return true if I have to, false otherwise
     */
    public boolean ShouldTurn() {
        Direction maDirection = getDirection();
        ArrayList<Direction> turn = new ArrayList<>();
        turn.add(Direction.NW);
        turn.add(Direction.WS);
        turn.add(Direction.EN);
        turn.add(Direction.SE);
        if (turn.contains(maDirection)) {
            return true;
        }
        switch (maDirection) {
            case NE:
                return getCoordinateX() == 3 && getCoordinateY() == 4;
            case SW:
                return getCoordinateX() == 4 && getCoordinateY() == 3;
            case ES:
                return getCoordinateX() == 3 && getCoordinateY() == 3;
            case WN:
                return getCoordinateX() == 4 && getCoordinateY() == 4;
            default:
                return false;
        }
    }

    /**
     * This function is to get the first four direction in the waiting list
     * @return an array with the four direction
     * [0] is the direction of the first vehicle
     * [1] is the direction of the second vehicle
     * [2] is the direction of the third vehicle
     * [3] is the direction of the fouth vehicle
     */
    public Direction[] getDirectionWaitingList() {
        Direction[] toReturn = new Direction[4];
        toReturn[0] = getWaitingList().get(0).getDirection();
        toReturn[1] = null;
        toReturn[2] = null;
        toReturn[3] = null;
        if(getWaitingList().size() > 1) {
            if(getWaitingList().get(1).amIwaiting()) {
                toReturn[1] = getWaitingList().get(1).getDirection();
            }
        }
        if(getWaitingList().size() > 2) {
            if(getWaitingList().get(2).amIwaiting()) {
                toReturn[2] = getWaitingList().get(2).getDirection();
            }
        }
        if(getWaitingList().size() > 3) {
            if(getWaitingList().get(3).amIwaiting()) {
                toReturn[3] = getWaitingList().get(3).getDirection();
            }
        }
        return toReturn;
    }

    /**
     * This function count how many vehicle can pass on the intersection
     * @return the number of vehicle
     */
    public int HowManyVehicleCanPass() {

        Direction[] MyTab = getDirectionWaitingList();
        Direction D0 = MyTab[0];
        Direction D1 = MyTab[1];
        Direction D2 = MyTab[2];
        Direction D3 = MyTab[3];

        final boolean ENSE = (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                && (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE);
        final boolean WSNW = (D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW)
                && (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS);
        final boolean SEWS = (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)
                && (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE);
        final boolean NWEN = (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                && (D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW);
        final boolean WSEW = (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)
                && (D1 == Direction.EW || D2 == Direction.EW || D3 == Direction.EW);
        final boolean NSEN = (D1 == Direction.NS || D2 == Direction.NS || D3 == Direction.NS)
                && (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN);
        final boolean SNWS = (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)
                && (D1 == Direction.SN || D2 == Direction.SN || D3 == Direction.SN);
        final boolean SENS = (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)
                && (D1 == Direction.NS || D2 == Direction.NS || D3 == Direction.NS);
        final boolean NWSN = (D1 == Direction.SN || D2 == Direction.SN || D3 == Direction.SN)
                && (D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW);

        switch (D0) {
            case NS :
                if (ENSE) {
                    return 3;
                } else {
                    if ((D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                            ||  (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)
                            || (D1 == Direction.SN || D2 == Direction.SN || D3 == Direction.SN)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            case NE :
                if (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN) {
                    return 2;
                } else {
                    return 1;
                }
            case NW:
                if (ENSE && (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)) {
                    return 4;
                } else if (ENSE || SEWS || SNWS) {
                    return 3;
                } else
                {
                    return 2;
                }
            case SN :
                if (WSNW) {
                    return 3;
                } else {
                    if ((D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW)
                            ||  (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)
                            || (D1 == Direction.NS || D2 == Direction.NS || D3 == Direction.NS)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            case SE :
                if (NWEN && (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)) {
                    return 4;
                } else if (WSEW || NSEN || NWEN) {
                    return 3;
                } else
                {
                    return 2;
                }
            case SW:
                if (D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS) {
                    return 2;
                } else {
                    return 1;
                }
            case EN :
                if (WSNW && (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)) {
                    return 4;
                } else if (WSNW || SEWS || SENS ) {
                    return 3;
                } else
                {
                    return 2;
                }
            case ES :
                if (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE) {
                    return 2;
                } else {
                    return 1;
                }
            case EW:
                if (SEWS) {
                    return 3;
                } else {
                    if ((D1 == Direction.WS || D2 == Direction.WS || D3 == Direction.WS)
                            ||  (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)
                            || (D1 == Direction.WE || D2 == Direction.WE || D3 == Direction.WE)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            case WN:
                if (D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW) {
                    return 2;
                } else {
                    return 1;
                }
            case WS:
                if (ENSE && (D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW)) {
                    return 4;
                } else if (ENSE || NWEN || NWSN ) {
                    return 3;
                } else
                {
                    return 2;
                }
            case WE:
                if (NWEN) {
                    return 3;
                } else {
                    if ((D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                            ||  (D1 == Direction.NW || D2 == Direction.NW || D3 == Direction.NW)
                            || (D1 == Direction.EW || D2 == Direction.EW || D3 == Direction.EW)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
        }
        return 1;
    }

    /**
     * This is to know if we are waiting
     * @return boolean : true if we are waiting, false otherwise
     */
    public boolean amIwaiting() {
        return (road.tabCase[getCoordinateX()][getCoordinateY()] instanceof Wait);
    }

    /**
     * This is to know if I there is someone on the intersection
     * @return
     */
    public boolean IntersectionFree() {
        for (Car v : cars) {
            if (v.getCoordinateX() < 8 && v.getCoordinateX() > -1
            && v.getCoordinateY() < 8 && v.getCoordinateY() > -1) {
                if (v.getRoad().tabCase[v.getCoordinateX()][v.getCoordinateY()] instanceof Conflict && v != this) {
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
     * @param coordinateX : The position of the vehicle
     * @param coordinateY : The position of the vehicle
     * @param orientation : The orientation of the vehicle
     * @param direction : The direction of the vehicle
     * @param cars : The arrayList with all vehicles
     * @param waitingList : The arrayList with the waitingList
     * @param road: The road
     */
    public Vehicle(int sizeX, int sizeY,
                   int coordinateX, int coordinateY,
                   Orientation orientation, Direction direction,
                   ArrayList<Car> cars, ArrayList<Vehicle> waitingList,
                   Road road) {
        this.alreadyTurned = false;
        this.killable = false;
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.orientation = orientation;
        this.direction = direction;
        this.cars = cars;
        this.waitingList = waitingList;
        this.road = road;
    }

    /**
     * Use for the multi-threading
     */
    abstract public void run();

    /**
     * To know if the vehicle is out of the intersection
     * @return
     */
    public boolean isInLife() {
        if(getKillable()) {
            return (coordinateX <= SIZE_X && coordinateY <= SIZE_Y && coordinateX >= -1 && coordinateY >= -1);
        }
        return (true);
    }

}
