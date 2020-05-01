package Model;


import VueController.Main;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Voiture extends Vehicule {
    private int coordonneX;
    private int coordonneY;

    private Orientation orientation;
    private Direction direction;

    public Voiture(int sizeX, int sizeY,
                   int coordonneX, int coordonneY,
                   Orientation orientation, Direction direction,
                   Voiture[] voitures, ArrayList fileAttente,
                   Route route) {
        super( sizeX,  sizeY,  coordonneX,  coordonneY,  orientation,  direction, voitures, fileAttente, route);
    }

    public void realiserAction() {
        Case[][] maRoute = getRoute().getTabCase();
        if (getCoordonneY() > -1 && getCoordonneX() > -1 && getCoordonneX() < 8 && getCoordonneY() < 8) {
            //System.out.println("Attente: "  + (maRoute[getCoordonneY()][getCoordonneX()] instanceof Attente));
            //System.out.println("Decision: " +(maRoute[getCoordonneY()][getCoordonneX()] instanceof Decision));
            if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Decision) {
                //System.out.println("Je suis sur une décision");
                addFileAttente(this);
                //System.out.println("vide après ajout? " + (getFileAttente().isEmpty()));
                avancerPrudament();
            } else {
                if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Attente) {
                    //System.out.println("Je suis sur une attente");
                    //System.out.println("vide ? " + (getFileAttente().isEmpty()));
                    if(!getFileAttente().isEmpty()) {
                        if (getFileAttente().get(0) == this) {
                            avancerPrudament();
                        }
                    }
                } else {
                    if (maRoute[getCoordonneY()][getCoordonneX()] instanceof Conflit) {
                        if ( !getDejaTourner() && doisJeTourner()) {
                            System.out.println("Oui, je dois tourner");
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
                            System.out.println("Non, je ne dois pas tourner");
                            avancerPrudament();
                            if (!(maRoute[getCoordonneY()][getCoordonneX()] instanceof Conflit)) {
                                deleteFileAttente(this);
                                //System.out.println("vide après ma suppresion ? " + (getFileAttente().isEmpty()));
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
        //System.out.println("X: " + getCoordonneX() + " Y: " + getCoordonneY());
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
        ArrayList tourne1 = new ArrayList();
        tourne1.add(Direction.NO);
        tourne1.add(Direction.OS);
        tourne1.add(Direction.EN);
        tourne1.add(Direction.SE);
        if (tourne1.contains(maDirection)) {
            return true;
        }
        switch (maDirection) {
            case NE:
                if(getCoordonneX() == 3 && getCoordonneY() == 4 ) {
                    return true;
                }
                else {
                    return false;
                }
            case SO:
                if(getCoordonneX() == 4 && getCoordonneY() == 3) {
                    return true;
                }
                else {
                    return false;
                }
            case ES:
                if(getCoordonneX() == 3 && getCoordonneY() == 3 ) {
                    return true;
                }
                else {
                    return false;
                }
            case ON:
                if(getCoordonneX() == 4 && getCoordonneY() == 4 ) {
                    return true;
                }
                else {
                    return false;
                }
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
