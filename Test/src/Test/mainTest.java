package Test;

import Model.Direction;
import Model.Orientation;
import Model.Route;
import Model.Voiture;
import VueController.Main;

import java.util.ArrayList;

public class mainTest {

    private final int SIZE_X = 10;
    private final int SIZE_Y = 10;

    private Voiture[] voitures;
    ArrayList fileAttente = new ArrayList();

    int nbTests = 5;
    float nbTestDejaFait = 0;
    int nbAssert = 0;
    Route route = new Route();

    public void mainTest() {    }

    private void testVoiture() {
        System.out.println("Début des tests de la classe voiture");

        //Déclaration des voitures
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SN, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OE, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EO, voitures, fileAttente, route)
        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Vérification des GET sur le tableau
        assert (voitures[0].getCoordonneX()==3) : "getCoordonneX() != 3";
        assert (voitures[0].getCoordonneY()==-1) : "getCoordonneY() != -1";
        assert (voitures[0].getOrientation() == Orientation.Sud) : "getOrientation() != Sud";
        assert (voitures[0].getDirection() == Direction.NS) : "getDirection() != NS";

        assert (voitures[1].getCoordonneX()==4) : "getCoordonneX() != 4";
        assert (voitures[1].getCoordonneY()==8) : "getCoordonneY() != 8";
        assert (voitures[1].getOrientation() == Orientation.Nord) : "getOrientation() != Nord";
        assert (voitures[1].getDirection() == Direction.SN) : "getDirection() != SN";

        assert (voitures[2].getCoordonneX()==-1) : "getCoordonneX() != -1";
        assert (voitures[2].getCoordonneY()==4) : "getCoordonneY() != 4";
        assert (voitures[2].getOrientation() == Orientation.Est) : "getOrientation() != Est";
        assert (voitures[2].getDirection() == Direction.OE) : "getDirection() != OE";

        assert (voitures[3].getCoordonneX()==8) : "getCoordonneX() != 8";
        assert (voitures[3].getCoordonneY()==3) : "getCoordonneY() != 3";
        assert (voitures[3].getOrientation() == Orientation.Ouest) : "getOrientation() != Ouest";
        assert (voitures[3].getDirection() == Direction.EO) : "getDirection() != EO";

        //Vérification des SET sur le tableau
        voitures[0].setCoordonneX(1);
        assert (voitures[0].getCoordonneX()==1) : "getCoordonneX() != 1";

        voitures[0].setCoordonneY(5);
        assert (voitures[0].getCoordonneY()==5) : "getCoordonneY() != 5";

        voitures[0].setOrientation(Orientation.Est);
        assert (voitures[0].getOrientation() == Orientation.Est) : "getOrientation() != Est";

        voitures[0].setDirection(Direction.OE);
        assert (voitures[2].getDirection() == Direction.OE) : "getDirection() != OE";

        //Vérification de la fonction estEnVie()
        assert (voitures[0].estEnVie()) : "La voiture0 est pas en vie";
        voitures[0].setTuable(true);
        voitures[0].setCoordonneY(-2);
        assert (!voitures[0].estEnVie()) : "La voiture0 est en vie";

        assert (voitures[1].estEnVie()) : "La voiture1 est pas en vie";
        voitures[1].setTuable(true);
        voitures[1].setCoordonneY(11);
        assert (!voitures[1].estEnVie()) : "La voiture1 est en vie";

        assert (voitures[2].estEnVie()) : "La voiture2 est pas en vie";
        voitures[2].setTuable(true);
        voitures[2].setCoordonneX(-3);
        assert (!voitures[2].estEnVie()) : "La voiture2 est en vie";

        assert (voitures[3].estEnVie()) : "La voiture3 est pas en vie";
        voitures[3].setTuable(true);
        voitures[3].setCoordonneX(11);
        assert (!voitures[3].estEnVie()) : "La voiture3 est en vie";


        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 28;
        System.out.println("Fin des tests de la classe voiture.");
    }

    private void avancerUneSeuleVoiture() {
        System.out.println("Début des tests pour avancer une seule voiture");

        //Creation de la voiture
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, voitures, fileAttente, route),
        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Tests Avancer

        //Direction Sud
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures[0].getCoordonneY() == 0) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Nord
        //Setup
        voitures[0].setOrientation(Orientation.Nord);
        voitures[0].setDirection(Direction.SN);
        voitures[0].setCoordonneY(8);
        //Test
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures[0].getCoordonneY() == 7) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Est
        //Setup
        voitures[0].setOrientation(Orientation.Est);
        voitures[0].setDirection(Direction.OE);
        voitures[0].setCoordonneY(4);
        voitures[0].setCoordonneX(-1);
        //Test
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 0) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures[0].getCoordonneY() == 4) : "La voiture 0 a avancé en Y";

        //Direction Ouest
        //Setup
        voitures[0].setOrientation(Orientation.Ouest);
        voitures[0].setDirection(Direction.EO);
        voitures[0].setCoordonneY(3);
        voitures[0].setCoordonneX(8);
        //Test
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 7) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures[0].getCoordonneY() == 3) : "La voiture 0 a avancé en Y";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Fin des tests pour avancer une seule voiture");
    }

    private void avancerPrudementUneSeuleVoiture() {
        System.out.println("Début des tests pour avancer prudement une seule voiture");

        //Creation de la voiture
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, voitures, fileAttente, route),
        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Tests Avancer

        //Direction Sud
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures[0].getCoordonneY() == 0) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Nord
        //Setup
        voitures[0].setOrientation(Orientation.Nord);
        voitures[0].setDirection(Direction.SN);
        voitures[0].setCoordonneY(8);
        //Test
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures[0].getCoordonneY() == 7) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Est
        //Setup
        voitures[0].setOrientation(Orientation.Est);
        voitures[0].setDirection(Direction.OE);
        voitures[0].setCoordonneY(4);
        voitures[0].setCoordonneX(-1);
        //Test
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 0) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures[0].getCoordonneY() == 4) : "La voiture 0 a avancé en Y";

        //Direction Ouest
        //Setup
        voitures[0].setOrientation(Orientation.Ouest);
        voitures[0].setDirection(Direction.EO);
        voitures[0].setCoordonneY(3);
        voitures[0].setCoordonneX(8);
        //Test
        voitures[0].avancer();
        assert (voitures[0].getCoordonneX() == 7) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures[0].getCoordonneY() == 3) : "La voiture 0 a avancé en Y";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Fin des tests pour avancer prudement à une seule voiture");
    }

    private void doisJeTournerUneSeuleVoiture() {
        System.out.println("Début des tests pour tourner avec une seule voiture");

        //Creation des voitures
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NS, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NO, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NE, voitures, fileAttente, route),

                new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SE, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SO, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SN, voitures, fileAttente, route),

                new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.OE, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.ON, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.OS, voitures, fileAttente, route),

                new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.EO, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.EN, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.ES, voitures, fileAttente, route),
        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //On test toutes les voitures une première fois
        assert (voitures[0].doisJeTourner() == false) : "La voiture 0 ne doit pas tourner";
        assert (voitures[1].doisJeTourner() == true) : "La voiture 1 doit tourner";
        assert (voitures[2].doisJeTourner() == false) : "La voiture 2 ne doit pas tourner";

        assert (voitures[3].doisJeTourner() == true) : "La voiture 3 doit tourner";
        assert (voitures[4].doisJeTourner() == false) : "La voiture 4 ne doit pas tourner";
        assert (voitures[5].doisJeTourner() == false) : "La voiture 5 ne doit pas tourner";

        assert (voitures[6].doisJeTourner() == false) : "La voiture 6 ne doit pas tourner";
        assert (voitures[7].doisJeTourner() == false) : "La voiture 7 ne doit pas tourner";
        assert (voitures[8].doisJeTourner() == true) : "La voiture 8  doit  tourner";

        assert (voitures[9].doisJeTourner() == false) : "La voiture 6 ne doit pas tourner";
        assert (voitures[10].doisJeTourner() == true) : "La voiture 10  doit  tourner";
        assert (voitures[11].doisJeTourner() == false) : "La voiture 10 ne doit pas tourner";

        //On les fait avancer
        for (Voiture v : voitures) {
            v.avancer();
        }

        //On vérifie si c'est juste sur la case conflit suivante
        // Les voitures commentés sont celles qui ont déjà tournée avant
        assert (voitures[0].doisJeTourner() == false) : "La voiture 0 ne doit pas tourner";
        //assert (voitures[1].doisJeTourner() == false) : "La voiture 1 ne doit pas tourner";
        assert (voitures[2].doisJeTourner() == true) : "La voiture 2 doit tourner";

        //assert (voitures[3].doisJeTourner() == false) : "La voiture 3 ne doit pas tourner";
        assert (voitures[4].doisJeTourner() == true) : "La voiture 4 doit tourner";
        assert (voitures[5].doisJeTourner() == false) : "La voiture 5 ne doit pas tourner";

        assert (voitures[6].doisJeTourner() == false) : "La voiture 6 ne doit pas tourner";
        assert (voitures[7].doisJeTourner() == true) : "La voiture 7 doit tourner";
        //assert (voitures[8].doisJeTourner() == false) : "La voiture 8  ne doit pas  tourner";

        assert (voitures[9].doisJeTourner() == false) : "La voiture 6 ne doit pas tourner";
        //assert (voitures[10].doisJeTourner() == false) : "La voiture 10  ne doit pas  tourner";
        assert (voitures[11].doisJeTourner() == true) : "La voiture 10 doit tourner";


        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 20;
        System.out.println("Début des tests pour tourner avec une seule voiture");
    }

    private void caseSuivanteLibre() {
        System.out.println("Début des tests pour tourner avec une seule voiture");

        //Creation des voitures
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NS, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SN, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.OE, voitures, fileAttente, route),
                new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.EO, voitures, fileAttente, route),
        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //On test toutes les voitures devant elle
        System.out.println("Voiture0: ("+ voitures[0].getCoordonneX() + "," + voitures[0].getCoordonneY() + ")");
        System.out.println("Voiture2: ("+ voitures[2].getCoordonneX() + "," + voitures[2].getCoordonneY() + ")");
        //System.out.println(voitures[0].CaseSuivanteLibre());
        assert (voitures[0].CaseSuivanteLibre() == false) : "La voie était libre";
        assert (voitures[1].CaseSuivanteLibre() == false) : "La voie était libre";
        assert (voitures[2].CaseSuivanteLibre() == false) : "La voie était libre";
        assert (voitures[3].CaseSuivanteLibre() == false) : "La voie était libre";

        //Pour changer la configuration
        for (Voiture v : voitures) {
            v.avancer();
        }

        assert (voitures[0].CaseSuivanteLibre() == true) : "La voie n'était pas libre";
        assert (voitures[1].CaseSuivanteLibre() == true) : "La voie n'était pas libre";
        assert (voitures[2].CaseSuivanteLibre() == true) : "La voie n'était pas libre";
        assert (voitures[3].CaseSuivanteLibre() == true) : "La voie n'était pas libre";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Début des tests pour tourner avec une seule voiture");
    }

    public void runTest() {
        System.out.println("");
        System.out.println("Debut des tests.");
        System.out.println("");

        //Déclaration de la route
        Route route = new Route();

        //Tests de la classe Voiture
        testVoiture();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Tests pour avancer à une seule voiture
        avancerUneSeuleVoiture();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Tests pour avancer prudement une seule voiture
        avancerPrudementUneSeuleVoiture();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Tests de la fonction doisJeTourner pour une seule voiture
        doisJeTournerUneSeuleVoiture();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Tests si la case suivante est libre
        caseSuivanteLibre();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Bilan
        System.out.println("Le nombres d'asserts réalisé est de: " + nbAssert);
        System.out.println("Nous avons réalisé " + nbTestDejaFait + " test.");

        System.out.println("");
        System.out.println("Fin des tests.");
    }
}
