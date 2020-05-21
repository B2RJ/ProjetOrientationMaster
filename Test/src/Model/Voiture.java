package Model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Voiture extends Vehicule {

    public Voiture(int sizeX, int sizeY,
                   int coordonneX, int coordonneY,
                   Orientation orientation, Direction direction,
                   ArrayList<Voiture> voitures, ArrayList<Vehicule> fileAttente,
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
                            // Permet de vérifier qu'il y a personne sur le carrefour
                            if(canIgo()) {
                                avancerPrudament();
                            }
                        } else {
                            if ((getFileAttente().indexOf(this) >= 1) && (getFileAttente().indexOf(this) <= 3)){
                                int cmbvoiture = CombienDeVehiculesPeuventPasser();
                                if(cmbvoiture == 4) {
                                    avancerPrudament();
                                } else {
                                    if (cmbvoiture > 1 && puisJePasser(cmbvoiture)) {
                                        avancerPrudament();
                                    }
                                }
                            }
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
                        }
                        if (!(maRoute[getCoordonneY()][getCoordonneX()] instanceof Conflit)) {
                            deleteFileAttente(this);
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

    public Direction[] getDirectionFileAttente() {
        Direction[] toReturn = new Direction[4];
        toReturn[0] = getFileAttente().get(0).getDirection();
        toReturn[1] = null;
        toReturn[2] = null;
        toReturn[3] = null;
        if(getFileAttente().size() > 1) {
            if(getFileAttente().get(1).amIwaiting()) {
                toReturn[1] = getFileAttente().get(1).getDirection();
            }
        }
        if(getFileAttente().size() > 2) {
            if(getFileAttente().get(2).amIwaiting()) {
                toReturn[2] = getFileAttente().get(2).getDirection();
            }
        }
        if(getFileAttente().size() > 3) {
            if(getFileAttente().get(3).amIwaiting()) {
                toReturn[3] = getFileAttente().get(3).getDirection();
            }
        }
        return toReturn;
    }

    public int CombienDeVehiculesPeuventPasser() {

        Direction[] MyTab = getDirectionFileAttente();
        Direction D0 = MyTab[0];
        Direction D1 = MyTab[1];
        Direction D2 = MyTab[2];
        Direction D3 = MyTab[3];

        final boolean ENSE = (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                && (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE);
        final boolean OSNO = (D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO)
                && (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS);
        final boolean SEOS = (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)
                && (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE);
        final boolean NOEN = (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                && (D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO);
        final boolean OSEO = (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)
                && (D1 == Direction.EO || D2 == Direction.EO || D3 == Direction.EO);
        final boolean NSEN = (D1 == Direction.NS || D2 == Direction.NS || D3 == Direction.NS)
                && (D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN);
        final boolean SNOS = (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)
                && (D1 == Direction.SN || D2 == Direction.SN || D3 == Direction.SN);
        final boolean SENS = (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)
                && (D1 == Direction.NS || D2 == Direction.NS || D3 == Direction.NS);
        final boolean NOSN = (D1 == Direction.SN || D2 == Direction.SN || D3 == Direction.SN)
                && (D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO);

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
            case NO :
                if (ENSE && (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)) {
                    return 4;
                } else if (ENSE || SEOS || SNOS) {
                    return 3;
                } else
                {
                    return 2;
                }
            case SN :
                if (OSNO) {
                    return 3;
                } else {
                    if ((D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO)
                            ||  (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)
                            || (D1 == Direction.NS || D2 == Direction.NS || D3 == Direction.NS)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            case SE :
                if (NOEN && (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)) {
                    return 4;
                } else if (OSEO || NSEN || NOEN) {
                    return 3;
                } else
                {
                    return 2;
                }
            case SO :
                if (D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS) {
                    return 2;
                } else {
                    return 1;
                }
            case EN :
                if (OSNO && (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)) {
                    return 4;
                } else if (OSNO || SEOS || SENS ) {
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
            case EO :
                if (SEOS) {
                    return 3;
                } else {
                    if ((D1 == Direction.OS || D2 == Direction.OS || D3 == Direction.OS)
                            ||  (D1 == Direction.SE || D2 == Direction.SE || D3 == Direction.SE)
                            || (D1 == Direction.OE || D2 == Direction.OE || D3 == Direction.OE)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            case ON :
                if (D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO) {
                    return 2;
                } else {
                    return 1;
                }
            case OS :
                if (ENSE && (D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO)) {
                    return 4;
                } else if (ENSE || NOEN || NOSN ) {
                    return 3;
                } else
                {
                    return 2;
                }
            case OE :
                if (NOEN) {
                    return 3;
                } else {
                    if ((D1 == Direction.EN || D2 == Direction.EN || D3 == Direction.EN)
                            ||  (D1 == Direction.NO || D2 == Direction.NO || D3 == Direction.NO)
                            || (D1 == Direction.EO || D2 == Direction.EO || D3 == Direction.EO)) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
        }
        return 1;
    }

    public boolean puisJePasser(int cmbVoiture){

        Direction[] MyTab = getDirectionFileAttente();
        Direction D0 = MyTab[0];
        Direction D1 = MyTab[1];
        Direction D2 = MyTab[2];
        Direction D3 = MyTab[3];

        switch (D0)
        {
            case NS :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces deux directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.SE || getDirection() == Direction.EN);
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            // Regarder si ma direction est compatible avec le premier
                            switch(getDirection())
                            {
                                case NS:
                                    return(getFileAttente().get(0).getDirection() == Direction.SN
                                            || getFileAttente().get(0).getDirection() == Direction.SE
                                            || getFileAttente().get(0).getDirection() == Direction.EN);
                                case NE:
                                    return(getFileAttente().get(0).getDirection() == Direction.EN);
                                case NO:
                                    return(getFileAttente().get(0).getDirection() == Direction.OS
                                    || getFileAttente().get(0).getDirection() == Direction.SE
                                    || getFileAttente().get(0).getDirection() == Direction.EN
                                    || getFileAttente().get(0).getDirection() == Direction.OE
                                    || getFileAttente().get(0).getDirection() == Direction.SN);
                                case SN:
                                    return(getFileAttente().get(0).getDirection() == Direction.NO
                                    || getFileAttente().get(0).getDirection() == Direction.OS);
                                case SE:
                                    return(getFileAttente().get(0).getDirection() == Direction.NO
                                    || getFileAttente().get(0).getDirection() == Direction.OS
                                    || getFileAttente().get(0).getDirection() == Direction.SE
                                    || getFileAttente().get(0).getDirection() == Direction.NS
                                    || getFileAttente().get(0).getDirection() == Direction.EO);
                                case SO:
                                    return(getFileAttente().get(0).getDirection() == Direction.OS);
                                case EN:
                                    return(getFileAttente().get(0).getDirection() == Direction.NO
                                    ||getFileAttente().get(0).getDirection() == Direction.OS
                                    || getFileAttente().get(0).getDirection() == Direction.SE
                                    || getFileAttente().get(0).getDirection() == Direction.NS
                                    || getFileAttente().get(0).getDirection() == Direction.OE);
                                case ES:
                                    return(getFileAttente().get(0).getDirection() == Direction.SE);
                                case EO:
                                    return(getFileAttente().get(0).getDirection() == Direction.OE
                                    || getFileAttente().get(0).getDirection() == Direction.OS
                                    || getFileAttente().get(0).getDirection() == Direction.SE);
                                case ON:
                                    return(getFileAttente().get(0).getDirection() == Direction.NO);
                                case OS:
                                    return(getFileAttente().get(0).getDirection() == Direction.SE
                                    || getFileAttente().get(0).getDirection() == Direction.EN
                                    || getFileAttente().get(0).getDirection() == Direction.NO
                                    || getFileAttente().get(0).getDirection() == Direction.SN
                                    || getFileAttente().get(0).getDirection() == Direction.EO);
                                case OE:
                                    return(getFileAttente().get(0).getDirection() == Direction.EO
                                    || getFileAttente().get(0).getDirection() == Direction.EN
                                    || getFileAttente().get(0).getDirection() == Direction.NO);
                                default:
                                    return false;
                            }
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return getFileAttente().get(1).getDirection() != Direction.SE
                                    && getFileAttente().get(1).getDirection() != Direction.EN
                                    && getFileAttente().get(1).getDirection() != Direction.SN;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return getFileAttente().get(1).getDirection() != Direction.SE
                                    && getFileAttente().get(1).getDirection() != Direction.EN
                                    && getFileAttente().get(1).getDirection() != Direction.SN
                                    && getFileAttente().get(2).getDirection() != Direction.SE
                                    && getFileAttente().get(2).getDirection() != Direction.EN
                                    && getFileAttente().get(2).getDirection() != Direction.SN;
                    }
                }
            case NE :
                return (getDirection() == Direction.EN);
            case NO :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces directions
                if(cmbVoiture == 3) {
                    // Il faut regarder si les gens qui peuvent passer sont en OE - EN ou en OS - SN
                    // Je regarde déjà si je suis dans l'une des configurations
                    if(getDirection() != Direction.OE
                        && getDirection() != Direction.EN
                        && getDirection() != Direction.OS
                        && getDirection() != Direction.SN) { return false; }
                    if(D1 == Direction.OE || D1 == Direction.EN) {
                        if ((D2 == Direction.OE || D2 == Direction.EN) && D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if ((D3 == Direction.OE || D3 == Direction.EN) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if(D1 == Direction.OS || D1 == Direction.SN) {
                        if ((D2 == Direction.OS || D2 == Direction.SN) && D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if ((D3 == Direction.OS || D3 == Direction.SN) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }

                    if((D2 == Direction.OE || D3 == Direction.OE
                     && D2 == Direction.EN || D3 == Direction.EN)
                     ||
                       (D2 == Direction.OS || D3 == Direction.OS
                     && D2 == Direction.SN || D3 == Direction.SN)) {
                        return getFileAttente().get(2) == this || getFileAttente().get(3) == this;
                    }
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    boolean b = getFileAttente().get(1).getDirection() != Direction.SE
                            && getFileAttente().get(1).getDirection() != Direction.EN
                            && getFileAttente().get(1).getDirection() != Direction.SN
                            && getFileAttente().get(1).getDirection() != Direction.OE
                            && getFileAttente().get(1).getDirection() != Direction.OS
                            && getFileAttente().get(1).getDirection() != Direction.ON;
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return b;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return  b
                                    && getFileAttente().get(2).getDirection() != Direction.SE
                                    && getFileAttente().get(2).getDirection() != Direction.EN
                                    && getFileAttente().get(2).getDirection() != Direction.SN
                                    && getFileAttente().get(2).getDirection() != Direction.OE
                                    && getFileAttente().get(2).getDirection() != Direction.OS
                                    && getFileAttente().get(2).getDirection() != Direction.ON;
                    }
                }
            case SN :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces deux directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.NO || getDirection() == Direction.OS);
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return getFileAttente().get(1).getDirection() != Direction.NS
                                    && getFileAttente().get(1).getDirection() != Direction.NO
                                    && getFileAttente().get(1).getDirection() != Direction.OS;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return getFileAttente().get(1).getDirection() != Direction.NS
                                    && getFileAttente().get(1).getDirection() != Direction.NO
                                    && getFileAttente().get(1).getDirection() != Direction.OS
                                    && getFileAttente().get(2).getDirection() != Direction.NS
                                    && getFileAttente().get(2).getDirection() != Direction.NO
                                    && getFileAttente().get(2).getDirection() != Direction.OS;
                    }
                }
            case SE :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces directions
                if(cmbVoiture == 3) {
                    // Il faut regarder si les gens qui peuvent passer sont en OE - EN ou en OS - SN
                    // Je regarde déjà si je suis dans l'une des configurations
                    if(getDirection() != Direction.EO
                            && getDirection() != Direction.OS
                            && getDirection() != Direction.EN
                            && getDirection() != Direction.NS) { return false; }
                    if (D1 == Direction.EO || D1 == Direction.OS){
                        if ((D2 == Direction.EO || D2 == Direction.OS)&& D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if ((D3 == Direction.EO || D3 == Direction.OS) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if (D1 == Direction.EN || D1 == Direction.NS){
                        if ((D2 == Direction.EN || D2 == Direction.NS)&& D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if ((D3 == Direction.EN || D3 == Direction.NS) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if((D2 == Direction.EO || D3 == Direction.EO
                     && D2 == Direction.OS || D3 == Direction.OS)
                     ||
                       (D2 == Direction.EN || D3 == Direction.EN
                     && D2 == Direction.NS || D3 == Direction.NS)) {
                        return getFileAttente().get(2) == this || getFileAttente().get(3) == this;
                    }
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    boolean b = getFileAttente().get(1).getDirection() != Direction.EN
                            && getFileAttente().get(1).getDirection() != Direction.EO
                            && getFileAttente().get(1).getDirection() != Direction.ES
                            && getFileAttente().get(1).getDirection() != Direction.NO
                            && getFileAttente().get(1).getDirection() != Direction.NS
                            && getFileAttente().get(1).getDirection() != Direction.OS;
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return b;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return b
                                   && getFileAttente().get(2).getDirection() != Direction.EN
                                   && getFileAttente().get(2).getDirection() != Direction.EO
                                   && getFileAttente().get(2).getDirection() != Direction.ES
                                   && getFileAttente().get(2).getDirection() != Direction.NO
                                   && getFileAttente().get(2).getDirection() != Direction.NS
                                   && getFileAttente().get(2).getDirection() != Direction.OS;
                    }
                }
            case SO :
                return (getDirection() == Direction.OS);
            case EN :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces directions
                if(cmbVoiture == 3) {
                    // Je regarde déjà si je suis dans l'une des configurations
                    if(getDirection() != Direction.NS
                            && getDirection() != Direction.SE
                            && getDirection() != Direction.OE
                            && getDirection() != Direction.NO) { return false; }
                    if (D1 == Direction.NS || D1 == Direction.SE){
                        if ((D2 == Direction.NS || D2 == Direction.SE) && D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if ((D3 == Direction.NS || D3 == Direction.SE) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if (D1 == Direction.OE || D1 == Direction.NO){
                        if ((D2 == Direction.OE || D2 == Direction.NO) && D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if ((D3 == Direction.OE || D3 == Direction.NO) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if((D2 == Direction.NS || D3 == Direction.NS
                    && D2 == Direction.SE || D3 == Direction.SE)
                    ||
                    (D2 == Direction.OE || D3 == Direction.OE
                  && D2 == Direction.NO || D3 == Direction.NO)) {
                        return getFileAttente().get(2) == this || getFileAttente().get(3) == this;
                    }
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    boolean b = getFileAttente().get(1).getDirection() != Direction.NO
                            && getFileAttente().get(1).getDirection() != Direction.NS
                            && getFileAttente().get(1).getDirection() != Direction.NE
                            && getFileAttente().get(1).getDirection() != Direction.OS
                            && getFileAttente().get(1).getDirection() != Direction.OE
                            && getFileAttente().get(1).getDirection() != Direction.SE;
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return b;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return b
                                    && getFileAttente().get(2).getDirection() != Direction.NO
                                    && getFileAttente().get(2).getDirection() != Direction.NS
                                    && getFileAttente().get(2).getDirection() != Direction.NE
                                    && getFileAttente().get(2).getDirection() != Direction.OS
                                    && getFileAttente().get(2).getDirection() != Direction.OE
                                    && getFileAttente().get(2).getDirection() != Direction.SE;
                    }
                }
            case ES :
                return (getDirection() == Direction.SE);
            case EO :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces deux directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.OS || getDirection() == Direction.SE);
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return getFileAttente().get(1).getDirection() != Direction.OS
                                    && getFileAttente().get(1).getDirection() != Direction.OE
                                    && getFileAttente().get(1).getDirection() != Direction.SE;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return getFileAttente().get(1).getDirection() != Direction.OS
                                    && getFileAttente().get(1).getDirection() != Direction.OE
                                    && getFileAttente().get(1).getDirection() != Direction.SE
                                    && getFileAttente().get(2).getDirection() != Direction.OS
                                    && getFileAttente().get(2).getDirection() != Direction.OE
                                    && getFileAttente().get(2).getDirection() != Direction.SE;
                    }
                }
            case ON :
                return (getDirection() == Direction.NO);
            case OS :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces directions
                if(cmbVoiture == 3) {
                    // Je regarde déjà si je suis dans l'une des configurations
                    if(getDirection() != Direction.SN
                            && getDirection() != Direction.NO
                            && getDirection() != Direction.SE
                            && getDirection() != Direction.EO) { return false; }
                    if(D1 == Direction.SN || D1 == Direction.NO) {
                        if ((D2 == Direction.SN || D2 == Direction.NO) && D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if((D3 == Direction.SN || D3 == Direction.NO) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if(D1 == Direction.SE || D1 == Direction.EO) {
                        if ((D2 == Direction.SE || D2 == Direction.EO) && D1 != D2) {
                            return getFileAttente().get(1) == this || getFileAttente().get(2) == this;
                        }
                        if((D3 == Direction.SE|| D3 == Direction.EO) && D1 != D3) {
                            return getFileAttente().get(1) == this || getFileAttente().get(3) == this;
                        }
                    }
                    if((D2 == Direction.SN || D3 == Direction.SN
                    && D2 == Direction.NO || D3 == Direction.NO)
                       ||
                      (D2 == Direction.SE || D3 == Direction.SE
                    && D2 == Direction.EO || D3 == Direction.EO)) {
                        return getFileAttente().get(2) == this || getFileAttente().get(3) == this;
                    }
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    boolean b = getFileAttente().get(1).getDirection() != Direction.SE
                            && getFileAttente().get(1).getDirection() != Direction.SN
                            && getFileAttente().get(1).getDirection() != Direction.SO
                            && getFileAttente().get(1).getDirection() != Direction.EN
                            && getFileAttente().get(1).getDirection() != Direction.EO
                            && getFileAttente().get(1).getDirection() != Direction.NO;
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return b;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return b
                                   && getFileAttente().get(2).getDirection() != Direction.SE
                                   && getFileAttente().get(2).getDirection() != Direction.SN
                                   && getFileAttente().get(2).getDirection() != Direction.EN
                                   && getFileAttente().get(2).getDirection() != Direction.EO
                                   && getFileAttente().get(2).getDirection() != Direction.NO;
                    }
                }
            case OE :
                // Si trois voitures peuvent passer, je peux passer si je suis dans l'une de ces deux directions
                if(cmbVoiture == 3) {
                    return (getDirection() == Direction.EN || getDirection() == Direction.NO);
                } else {
                    // Sinon, juste 2 voitures peuvent passer
                    switch(getFileAttente().indexOf(this))
                    {
                        // Si je suis deuxième de la file, c'est donc à moi de passer
                        case 1:
                            return true;
                        case 2:
                            //Si je suis troisième de la file, je regarde que le deuxième ne puisse pas passer
                            return getFileAttente().get(1).getDirection() != Direction.EN
                                    && getFileAttente().get(1).getDirection() != Direction.EO
                                    && getFileAttente().get(1).getDirection() != Direction.NO;
                        case 3:
                            //Si je suis le quatrième de la file, je regarde que le deuxieme
                            //et le troisième ne puissent pas passer
                            return getFileAttente().get(1).getDirection() != Direction.EN
                                    && getFileAttente().get(1).getDirection() != Direction.EO
                                    && getFileAttente().get(1).getDirection() != Direction.NO
                                    && getFileAttente().get(2).getDirection() != Direction.EN
                                    && getFileAttente().get(2).getDirection() != Direction.EO
                                    && getFileAttente().get(2).getDirection() != Direction.NO;
                    }
                }
        }
        return false;
    }

    public void run() {
        while(estEnVie()) {
            this.realiserAction();
            setChanged();
            notifyObservers();
            int i = (int) (Math.random() * ( 401 - 1 ));
            try {
                Thread.sleep(750 + i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Vehicule.class.getName()).log(Level.SEVERE, null, ex);
            }
//            this.realiserAction();
//            setChanged();
//            notifyObservers();
        }
        getVoitures().remove(this);
        setChanged();
        notifyObservers();
        this.deleteObservers();
    }

    public void start() {
        new Thread(this).start();
    }
}
