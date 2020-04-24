package Tests;

import Model.Direction;
import Model.Orientation;
import Model.Route;
import Model.Voiture;

public class mainTest {

    private int SIZE_X = 10;
    private int SIZE_Y = 10;

    private Voiture[] voitures;

    int nbTests = 1;
    int nbTestDejaFait = 0;
    int nbAssert = 0;

    public void mainTest() {
    }

    private void testVoiture() {
        System.out.println("Début des tests de la classe voiture");

        //Déclaration des voitures
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS),
                new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SN),
                new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OE),
                new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EO)
        };

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


        //Vérification de la fonction réaliser action quand elle peut avancer
        voitures[0].setOrientation(Orientation.Sud);
        voitures[0].realiserAction();
        assert (voitures[0].getCoordonneX()==2) : "getCoordonneX() != 2";
        assert (voitures[0].getCoordonneY()==5) : "getCoordonneY() != 5";

        voitures[1].realiserAction();
        assert (voitures[1].getCoordonneX()==4) : "getCoordonneX() != 4";
        assert (voitures[1].getCoordonneY()==7) : "getCoordonneY() != 7";

        voitures[2].realiserAction();
        assert (voitures[2].getCoordonneX()==0) : "getCoordonneX() != 0";
        assert (voitures[2].getCoordonneY()==4) : "getCoordonneY() != 4";

        voitures[3].realiserAction();
        assert (voitures[3].getCoordonneX()==7) : "getCoordonneX() != 7";
        assert (voitures[3].getCoordonneY()==3) : "getCoordonneY() != 3";


        //Vérification de la fonction estEnVie()
        assert (voitures[0].estEnVie()) : "La voiture0 est pas en vie";
        voitures[0].setCoordonneY(-2);
        assert (!voitures[0].estEnVie()) : "La voiture0 est en vie";

        assert (voitures[1].estEnVie()) : "La voiture1 est pas en vie";
        voitures[1].setCoordonneY(11);
        assert (!voitures[0].estEnVie()) : "La voiture1 est en vie";

        assert (voitures[2].estEnVie()) : "La voiture2 est pas en vie";
        voitures[2].setCoordonneX(-3);
        assert (!voitures[2].estEnVie()) : "La voiture2 est en vie";

        assert (voitures[3].estEnVie()) : "La voiture3 est pas en vie";
        voitures[3].setCoordonneX(11);
        assert (!voitures[3].estEnVie()) : "La voiture3 est en vie";



        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 36;
        System.out.println("Fin des tests de la classe voiture.");
    }

    public void runTest() {
        System.out.println("");
        System.out.println("Debut des tests.");
        System.out.println("");

        //Déclaration de la route
        Route route = new Route();

        // Test de la classe Voiture
        testVoiture();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");


        System.out.println();
        System.out.println("Le nombres d'asserts réalisé est de: " + nbAssert);
        if (nbTestDejaFait > 1) {
            System.out.println("Nous avons réalisé " + nbTestDejaFait + " tests.");
        }
        else {
            System.out.println("Nous avons réalisé " + nbTestDejaFait + " test.");
        }
        System.out.println("");
        System.out.println("Fin des tests.");
    }
}
