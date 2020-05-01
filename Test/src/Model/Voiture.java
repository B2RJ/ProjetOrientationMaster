package Model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Voiture extends Vehicule {

    public Voiture(int sizeX, int sizeY,
                   int coordonneX, int coordonneY,
                   Orientation orientation, Direction direction,
                   Voiture[] voitures, ArrayList<Vehicule> fileAttente,
                   Route route) {
        super( sizeX,  sizeY,  coordonneX,  coordonneY,  orientation,  direction, voitures, fileAttente, route);
    }

    public void realiserAction() {
        Case[][] maRoute = getRoute().getTabCase();
        if (getCoordonneY() > -1 && getCoordonneX() > -1 && getCoordonneX() < 8 && getCoordonneY() < 8) {
            if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Decision) {
                addFileAttente(this);
                avancerPrudament();
            } else {
                if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Attente) {
                    if(!getFileAttente().isEmpty()) {
                        if (getFileAttente().get(0) == this) {
                            avancerPrudament();
                        }
                    }
                } else {
                    if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Conflit) {
                        if ( !getDejaTourner() && doisJeTourner()) {
                            //fonction qui tourne maintenant
                            Direction d = getDirection();
                            if(d == Direction.EN || d == Direction.ON) {
                                setOrientation(Orientation.Nord);
                            } else {
                                if(d == Direction.ES || d == Direction.OS) {
                                    setOrientation(Orientation.Sud);
                                } else {
                                    if(d == Direction.NE || d == Direction.SE) {
                                        setOrientation(Orientation.Est);
                                    } else {
                                        if(d == Direction.SO || d == Direction.NO) {
                                            setOrientation(Orientation.Ouest);
                                        }
                                    }
                                }
                            }
                            avancerPrudament();
                            setDejaTourne(true);
                        }
                        else {
                            avancerPrudament();
                            if (!(maRoute[getCoordonneY()][getCoordonneX()] instanceof Conflit)) {
                                deleteFileAttente(this);
                            }
                        }
                    } else if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Goudron) {
                        avancerPrudament();
                    }
                }
            }
        } else {
            avancerEnDehorsDeLaMap();
        }
    }

    public void avancer() {
        switch (getOrientation())
        {
            case Nord:
                setCoordonneY(getCoordonneY()-1);
                break;
            case Sud:
                setCoordonneY(getCoordonneY()+1);
                break;
            case Est:
                setCoordonneX(getCoordonneX()+1);
                break;

            case Ouest:
                setCoordonneX(getCoordonneX()-1);
                break;
        }
    }

    public void avancerPrudament() {
        if(CaseSuivanteLibre()) {
            avancer();
        }
    }

    public void avancerEnDehorsDeLaMap() {
        avancer();
        if (getCoordonneY() > -1 && getCoordonneX() > -1 && getCoordonneX() < 8 && getCoordonneY() < 8) {
            this.setTuable(true);
        }
    }

    public boolean doisJeTourner() {
        Direction maDirection = getDirection();
        ArrayList<Direction> tourne1 = new ArrayList<>();
        tourne1.add(Direction.NO);
        tourne1.add(Direction.OS);
        tourne1.add(Direction.EN);
        tourne1.add(Direction.SE);
        if (tourne1.contains(maDirection)) {
            return true;
        }
        switch (maDirection) {
            case NE:
                return getCoordonneX() == 3 && getCoordonneY() == 4;
            case SO:
                return getCoordonneX() == 4 && getCoordonneY() == 3;
            case ES:
                return getCoordonneX() == 3 && getCoordonneY() == 3;
            case ON:
                return getCoordonneX() == 4 && getCoordonneY() == 4;
            default:
                return false;
        }
    }

    public boolean CaseSuivanteLibre() {
        switch (getOrientation())
        {
            case Nord:
                for (Voiture voiture : getVoitures()) {
                    if(voiture.getCoordonneX() == this.getCoordonneX() && voiture.getCoordonneY() == this.getCoordonneY()-1) {
                        return false;
                    }
                }
                break;
            case Sud:
                for (Voiture voiture : getVoitures()) {
                    if(voiture.getCoordonneX() == this.getCoordonneX() && voiture.getCoordonneY() == this.getCoordonneY()+1) {
                        return false;
                    }
                }
                break;
            case Est:
                for (Voiture voiture : getVoitures()) {
                    if(voiture.getCoordonneX() == this.getCoordonneX()+1 && voiture.getCoordonneY() == this.getCoordonneY()) {
                        return false;
                    }
                }
                break;

            case Ouest:
                for (Voiture voiture : getVoitures()) {
                    if(voiture.getCoordonneX() == this.getCoordonneX()-1 && voiture.getCoordonneY() == this.getCoordonneY()) {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    public void run() {
        while(estEnVie()) {
            this.realiserAction();
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Vehicule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }
}
