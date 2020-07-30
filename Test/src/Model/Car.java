package Model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the daughter class of vehicle
 *
 * @see Vehicle
 *
 * @author B2RJ
 */
public class Car extends Vehicle {

    /**
     * The constructor
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
    public Car(int sizeX, int sizeY,
               int coordinateX, int coordinateY,
               Orientation orientation, Direction direction,
               ArrayList<Car> cars, ArrayList<Vehicle> waitingList,
               Road road) {
        super( sizeX,  sizeY,  coordinateX,  coordinateY,  orientation,  direction, cars, waitingList, road);
    }

    /**
     * This function manage the action do by the vehicle
     */
    public void doActions() {
        Case[][] myRoad = getRoad().getTabCase();
        if (getCoordinateY() > -1 && getCoordinateX() > -1 && getCoordinateX() < 8 && getCoordinateY() < 8) {
            if (myRoad[getCoordinateY()][getCoordinateX()] instanceof Decision) {
                addWaintingList(this);
                treadCarefully();
            } else {
                if (myRoad[getCoordinateY()][getCoordinateX()] instanceof Wait) {
                    if(!getWaitingList().isEmpty()) {
                        if (getWaitingList().get(0) == this) {
                            // To check there is nothing on the intersection
                            if(IntersectionFree()) {
                                treadCarefully();
                            }
                        } else {
                            if ((getWaitingList().indexOf(this) >= 1) && (getWaitingList().indexOf(this) <= 3)){
                                int nbVehicle = HowManyVehicleCanPass();
                                if(nbVehicle == 4) {
                                    treadCarefully();
                                } else {
                                    if (nbVehicle > 1 && canIgo(nbVehicle)) {
                                        treadCarefully();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (myRoad[getCoordinateY()][getCoordinateX()] instanceof Conflict) {
                        if ( !getAlreadyTurned() && ShouldTurn()) {
                            //Function to make the rotation
                            Direction d = getDirection();
                            if(d == Direction.EN || d == Direction.WN) {
                                setOrientation(Orientation.North);
                            } else {
                                if(d == Direction.ES || d == Direction.WS) {
                                    setOrientation(Orientation.South);
                                } else {
                                    if(d == Direction.NE || d == Direction.SE) {
                                        setOrientation(Orientation.East);
                                    } else {
                                        if(d == Direction.SW || d == Direction.NW) {
                                            setOrientation(Orientation.West);
                                        }
                                    }
                                }
                            }
                            treadCarefully();
                            setAlreadyTurned(true);
                        }
                        else {
                            treadCarefully();
                        }
                        if (!(myRoad[getCoordinateY()][getCoordinateX()] instanceof Conflict)) {
                            deleteFileAttente(this);
                        }
                    } else if (myRoad[getCoordinateY()][getCoordinateX()] instanceof Tar) {
                        treadCarefully();
                    }
                }
            }
        } else {
            treadOutsideMap();
        }
    }

    /**
     * This function look if I can pass on the intersection
     * I keep this function in the car class because with a truck or a motorcycle it could be different
     * @param cmbVoiture : The number of vehicule that can pass
     * @return true if I can, false otherwise
     */
    public boolean canIgo(int cmbVoiture){

        Direction[] MyTab = getDirectionWaitingList();
        Direction D0 = MyTab[0];
        Direction D1 = MyTab[1];
        Direction D2 = MyTab[2];
        Direction D3 = MyTab[3];

        switch (D0)
        {
            case NS :
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.SE || getDirection() == Direction.EN);
                } else {
                    // Otherwise, only 2 vehicle could pass
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            // I look if my direction is compatible with the first
                            switch(getDirection())
                            {
                                case SN:
                                case SE:
                                case EN:
                                    return true;
                                default:
                                    return false;
                            }
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.SE
                                    && getWaitingList().get(1).getDirection() != Direction.EN
                                    && getWaitingList().get(1).getDirection() != Direction.SN;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.SE
                                    && getWaitingList().get(1).getDirection() != Direction.EN
                                    && getWaitingList().get(1).getDirection() != Direction.SN
                                    && getWaitingList().get(2).getDirection() != Direction.SE
                                    && getWaitingList().get(2).getDirection() != Direction.EN
                                    && getWaitingList().get(2).getDirection() != Direction.SN;
                    }
                }
            case NE :
                return (getDirection() == Direction.EN);
            case NW:
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    // It's important to look if vehicle could pass are in WE - EN or WS - SN
                    // I'm looking if I'm in this configuration
                    if(getDirection() != Direction.WE
                        && getDirection() != Direction.EN
                        && getDirection() != Direction.WS
                        && getDirection() != Direction.SN) { return false; }
                    if(D1 == Direction.WE || D1 == Direction.EN) {
                        if ((D2 == Direction.WE || D2 == Direction.EN) && D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if ((D3 == Direction.WE || D3 == Direction.EN) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if(D1 == Direction.WS || D1 == Direction.SN) {
                        if ((D2 == Direction.WS || D2 == Direction.SN) && D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if ((D3 == Direction.WS || D3 == Direction.SN) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }

                    if((D2 == Direction.WE || D3 == Direction.WE
                     && D2 == Direction.EN || D3 == Direction.EN)
                     ||
                       (D2 == Direction.WS || D3 == Direction.WS
                     && D2 == Direction.SN || D3 == Direction.SN)) {
                        return getWaitingList().get(2) == this || getWaitingList().get(3) == this;
                    }
                } else {
                    // Otherwise, only 2 vehicle could pass
                    boolean b = getWaitingList().get(1).getDirection() != Direction.SE
                            && getWaitingList().get(1).getDirection() != Direction.EN
                            && getWaitingList().get(1).getDirection() != Direction.SN
                            && getWaitingList().get(1).getDirection() != Direction.WE
                            && getWaitingList().get(1).getDirection() != Direction.WS
                            && getWaitingList().get(1).getDirection() != Direction.WN;
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            return (getDirection()==Direction.WS
                            || getDirection()==Direction.SE
                            || getDirection()==Direction.EN
                            || getDirection()==Direction.WE
                            || getDirection()==Direction.SN
                            || getDirection()==Direction.WN);
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return b;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return  b
                                    && getWaitingList().get(2).getDirection() != Direction.SE
                                    && getWaitingList().get(2).getDirection() != Direction.EN
                                    && getWaitingList().get(2).getDirection() != Direction.SN
                                    && getWaitingList().get(2).getDirection() != Direction.WE
                                    && getWaitingList().get(2).getDirection() != Direction.WS
                                    && getWaitingList().get(2).getDirection() != Direction.WN;
                    }
                }
            case SN :
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.NW || getDirection() == Direction.WS);
                } else {
                    // Otherwise, only 2 vehicle could pass
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            switch(getDirection())
                            {
                                case NS:
                                case NW:
                                case WS:
                                    return true;
                                default:
                                    return false;
                            }
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.NS
                                    && getWaitingList().get(1).getDirection() != Direction.NW
                                    && getWaitingList().get(1).getDirection() != Direction.WS;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.NS
                                    && getWaitingList().get(1).getDirection() != Direction.NW
                                    && getWaitingList().get(1).getDirection() != Direction.WS
                                    && getWaitingList().get(2).getDirection() != Direction.NS
                                    && getWaitingList().get(2).getDirection() != Direction.NW
                                    && getWaitingList().get(2).getDirection() != Direction.WS;
                    }
                }
            case SE :
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    // If three vehicles could pass, I can pass if I've this two directions
                    if(getDirection() != Direction.EW
                            && getDirection() != Direction.WS
                            && getDirection() != Direction.EN
                            && getDirection() != Direction.NS) { return false; }
                    if (D1 == Direction.EW || D1 == Direction.WS){
                        if ((D2 == Direction.EW || D2 == Direction.WS)&& D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if ((D3 == Direction.EW || D3 == Direction.WS) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if (D1 == Direction.EN || D1 == Direction.NS){
                        if ((D2 == Direction.EN || D2 == Direction.NS)&& D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if ((D3 == Direction.EN || D3 == Direction.NS) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if((D2 == Direction.EW || D3 == Direction.EW
                     && D2 == Direction.WS || D3 == Direction.WS)
                     ||
                       (D2 == Direction.EN || D3 == Direction.EN
                     && D2 == Direction.NS || D3 == Direction.NS)) {
                        return getWaitingList().get(2) == this || getWaitingList().get(3) == this;
                    }
                } else {
                    // Otherwise, only 2 vehicle could pass
                    boolean b = getWaitingList().get(1).getDirection() != Direction.EN
                            && getWaitingList().get(1).getDirection() != Direction.EW
                            && getWaitingList().get(1).getDirection() != Direction.ES
                            && getWaitingList().get(1).getDirection() != Direction.NW
                            && getWaitingList().get(1).getDirection() != Direction.NS
                            && getWaitingList().get(1).getDirection() != Direction.WS;
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            return(getDirection()==Direction.NW
                            || getDirection()==Direction.WS
                            || getDirection()==Direction.EN
                            || getDirection()==Direction.EW
                            || getDirection()==Direction.NS
                            || getDirection()==Direction.ES);
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return b;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return b
                                   && getWaitingList().get(2).getDirection() != Direction.EN
                                   && getWaitingList().get(2).getDirection() != Direction.EW
                                   && getWaitingList().get(2).getDirection() != Direction.ES
                                   && getWaitingList().get(2).getDirection() != Direction.NW
                                   && getWaitingList().get(2).getDirection() != Direction.NS
                                   && getWaitingList().get(2).getDirection() != Direction.WS;
                    }
                }
            case SW:
                return (getDirection() == Direction.WS);
            case EN :
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    // If three vehicles could pass, I can pass if I've this two directions
                    if(getDirection() != Direction.NS
                            && getDirection() != Direction.SE
                            && getDirection() != Direction.WE
                            && getDirection() != Direction.NW) { return false; }
                    if (D1 == Direction.NS || D1 == Direction.SE){
                        if ((D2 == Direction.NS || D2 == Direction.SE) && D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if ((D3 == Direction.NS || D3 == Direction.SE) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if (D1 == Direction.WE || D1 == Direction.NW){
                        if ((D2 == Direction.WE || D2 == Direction.NW) && D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if ((D3 == Direction.WE || D3 == Direction.NW) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if((D2 == Direction.NS || D3 == Direction.NS
                    && D2 == Direction.SE || D3 == Direction.SE)
                    ||
                    (D2 == Direction.WE || D3 == Direction.WE
                  && D2 == Direction.NW || D3 == Direction.NW)) {
                        return getWaitingList().get(2) == this || getWaitingList().get(3) == this;
                    }
                } else {
                    // Otherwise, only 2 vehicle could pass
                    boolean b = getWaitingList().get(1).getDirection() != Direction.NW
                            && getWaitingList().get(1).getDirection() != Direction.NS
                            && getWaitingList().get(1).getDirection() != Direction.NE
                            && getWaitingList().get(1).getDirection() != Direction.WS
                            && getWaitingList().get(1).getDirection() != Direction.WE
                            && getWaitingList().get(1).getDirection() != Direction.SE;
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            return(getDirection()==Direction.NW
                            || getDirection()==Direction.WS
                            || getDirection()==Direction.SE
                            || getDirection()==Direction.NS
                            || getDirection()==Direction.WE
                            || getDirection()==Direction.NE);
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return b;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return b
                                    && getWaitingList().get(2).getDirection() != Direction.NW
                                    && getWaitingList().get(2).getDirection() != Direction.NS
                                    && getWaitingList().get(2).getDirection() != Direction.NE
                                    && getWaitingList().get(2).getDirection() != Direction.WS
                                    && getWaitingList().get(2).getDirection() != Direction.WE
                                    && getWaitingList().get(2).getDirection() != Direction.SE;
                    }
                }
            case ES :
                return (getDirection() == Direction.SE);
            case EW:
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.WS || getDirection() == Direction.SE);
                } else {
                    // Otherwise, only 2 vehicle could pass
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            return (getDirection()==Direction.WE
                            || getDirection()==Direction.WS
                            || getDirection()==Direction.SE);
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.WS
                                    && getWaitingList().get(1).getDirection() != Direction.WE
                                    && getWaitingList().get(1).getDirection() != Direction.SE;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.WS
                                    && getWaitingList().get(1).getDirection() != Direction.WE
                                    && getWaitingList().get(1).getDirection() != Direction.SE
                                    && getWaitingList().get(2).getDirection() != Direction.WS
                                    && getWaitingList().get(2).getDirection() != Direction.WE
                                    && getWaitingList().get(2).getDirection() != Direction.SE;
                    }
                }
            case WN:
                return (getDirection() == Direction.NW);
            case WS:
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    // If three vehicles could pass, I can pass if I've this two directions
                    if(getDirection() != Direction.SN
                            && getDirection() != Direction.NW
                            && getDirection() != Direction.SE
                            && getDirection() != Direction.EW) { return false; }
                    if(D1 == Direction.SN || D1 == Direction.NW) {
                        if ((D2 == Direction.SN || D2 == Direction.NW) && D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if((D3 == Direction.SN || D3 == Direction.NW) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if(D1 == Direction.SE || D1 == Direction.EW) {
                        if ((D2 == Direction.SE || D2 == Direction.EW) && D1 != D2) {
                            return getWaitingList().get(1) == this || getWaitingList().get(2) == this;
                        }
                        if((D3 == Direction.SE|| D3 == Direction.EW) && D1 != D3) {
                            return getWaitingList().get(1) == this || getWaitingList().get(3) == this;
                        }
                    }
                    if((D2 == Direction.SN || D3 == Direction.SN
                    && D2 == Direction.NW || D3 == Direction.NW)
                       ||
                      (D2 == Direction.SE || D3 == Direction.SE
                    && D2 == Direction.EW || D3 == Direction.EW)) {
                        return getWaitingList().get(2) == this || getWaitingList().get(3) == this;
                    }
                } else {
                    // Otherwise, only 2 vehicle could pass
                    boolean b = getWaitingList().get(1).getDirection() != Direction.SE
                            && getWaitingList().get(1).getDirection() != Direction.SN
                            && getWaitingList().get(1).getDirection() != Direction.SW
                            && getWaitingList().get(1).getDirection() != Direction.EN
                            && getWaitingList().get(1).getDirection() != Direction.EW
                            && getWaitingList().get(1).getDirection() != Direction.NW;
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            return (getDirection()==Direction.SE
                            || getDirection()==Direction.EN
                            || getDirection()==Direction.NW
                            || getDirection()==Direction.SN
                            || getDirection()==Direction.EW
                            || getDirection()==Direction.SW);
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return b;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return b
                                   && getWaitingList().get(2).getDirection() != Direction.SE
                                   && getWaitingList().get(2).getDirection() != Direction.SN
                                   && getWaitingList().get(2).getDirection() != Direction.EN
                                   && getWaitingList().get(2).getDirection() != Direction.EW
                                   && getWaitingList().get(2).getDirection() != Direction.NW;
                    }
                }
            case WE:
                // If three vehicles could pass, I can pass if I've this two directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.EN || getDirection() == Direction.NW);
                } else {
                    // Otherwise, only 2 vehicle could pass
                    switch(getWaitingList().indexOf(this))
                    {
                        // If I'm second, it's my turn
                        case 1:
                            return (getDirection()==Direction.EW
                            || getDirection()==Direction.EN
                            || getDirection()==Direction.NW);
                        case 2:
                            // If I'm third, I look the second couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.EN
                                    && getWaitingList().get(1).getDirection() != Direction.EW
                                    && getWaitingList().get(1).getDirection() != Direction.NW;
                        case 3:
                            // If I'm fourth, I look the second and the third couldn't pass
                            return getWaitingList().get(1).getDirection() != Direction.EN
                                    && getWaitingList().get(1).getDirection() != Direction.EW
                                    && getWaitingList().get(1).getDirection() != Direction.NW
                                    && getWaitingList().get(2).getDirection() != Direction.EN
                                    && getWaitingList().get(2).getDirection() != Direction.EW
                                    && getWaitingList().get(2).getDirection() != Direction.NW;
                    }
                }
        }
        return false;
    }

    /**
     * This function mananage the car
     */
    public void run() {
        while(isInLife()) {
            // It's use to limit the concurrence access
            int i = (int) (Math.random() * ( 401 - 1 ));
            try {
                Thread.sleep(750 + i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.doActions();
            setChanged();
            notifyObservers();
        }
        getCars().remove(this);
        setChanged();
        notifyObservers();
        this.deleteObservers();
    }

    /**
     * This function is use for the thread
     */
    public void start() {
        new Thread(this).start();
    }
}
