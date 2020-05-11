package Test;

import Model.*;

import java.util.ArrayList;

public class mainTest {

    private final int SIZE_X = 10;
    private final int SIZE_Y = 10;

    ArrayList<Vehicule> fileAttente = new ArrayList<>();

    int nbTests = 6;
    float nbTestDejaFait = 0;
    int nbAssert = 0;
    Route route = new Route();

    private void testVoiture() {
        System.out.println("Début des tests de la classe voiture");

        //Déclaration des voitures
        ArrayList<Voiture> voitures = new ArrayList<>();
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, null, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SN, null, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OE, null, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EO, null, fileAttente, route));
//        voitures = new Voiture[] {
//                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, null, fileAttente, route),
//                new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SN, null, fileAttente, route),
//                new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OE, null, fileAttente, route),
//                new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EO, null, fileAttente, route)
//        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Vérification des GET sur le tableau
        assert (voitures.get(0).getCoordonneX()==3) : "getCoordonneX() != 3";
        assert (voitures.get(0).getCoordonneY()==-1) : "getCoordonneY() != -1";
        assert (voitures.get(0).getOrientation() == Orientation.Sud) : "getOrientation() != Sud";
        assert (voitures.get(0).getDirection() == Direction.NS) : "getDirection() != NS";

        assert (voitures.get(1).getCoordonneX()==4) : "getCoordonneX() != 4";
        assert (voitures.get(1).getCoordonneY()==8) : "getCoordonneY() != 8";
        assert (voitures.get(1).getOrientation() == Orientation.Nord) : "getOrientation() != Nord";
        assert (voitures.get(1).getDirection() == Direction.SN) : "getDirection() != SN";

        assert (voitures.get(2).getCoordonneX()==-1) : "getCoordonneX() != -1";
        assert (voitures.get(2).getCoordonneY()==4) : "getCoordonneY() != 4";
        assert (voitures.get(2).getOrientation() == Orientation.Est) : "getOrientation() != Est";
        assert (voitures.get(2).getDirection() == Direction.OE) : "getDirection() != OE";

        assert (voitures.get(3).getCoordonneX()==8) : "getCoordonneX() != 8";
        assert (voitures.get(3).getCoordonneY()==3) : "getCoordonneY() != 3";
        assert (voitures.get(3).getOrientation() == Orientation.Ouest) : "getOrientation() != Ouest";
        assert (voitures.get(3).getDirection() == Direction.EO) : "getDirection() != EO";

        //Vérification des SET sur le tableau
        voitures.get(0).setCoordonneX(1);
        assert (voitures.get(0).getCoordonneX()==1) : "getCoordonneX() != 1";

        voitures.get(0).setCoordonneY(5);
        assert (voitures.get(0).getCoordonneY()==5) : "getCoordonneY() != 5";

        voitures.get(0).setOrientation(Orientation.Est);
        assert (voitures.get(0).getOrientation() == Orientation.Est) : "getOrientation() != Est";

        voitures.get(0).setDirection(Direction.OE);
        assert (voitures.get(2).getDirection() == Direction.OE) : "getDirection() != OE";

        //Vérification de la fonction estEnVie()
        assert (voitures.get(0).estEnVie()) : "La voiture0 est pas en vie";
        voitures.get(0).setTuable(true);
        voitures.get(0).setCoordonneY(-2);
        assert (!voitures.get(0).estEnVie()) : "La voiture0 est en vie";

        assert (voitures.get(1).estEnVie()) : "La voiture1 est pas en vie";
        voitures.get(1).setTuable(true);
        voitures.get(1).setCoordonneY(11);
        assert (!voitures.get(1).estEnVie()) : "La voiture1 est en vie";

        assert (voitures.get(2).estEnVie()) : "La voiture2 est pas en vie";
        voitures.get(2).setTuable(true);
        voitures.get(2).setCoordonneX(-3);
        assert (!voitures.get(2).estEnVie()) : "La voiture2 est en vie";

        assert (voitures.get(3).estEnVie()) : "La voiture3 est pas en vie";
        voitures.get(3).setTuable(true);
        voitures.get(3).setCoordonneX(11);
        assert (!voitures.get(3).estEnVie()) : "La voiture3 est en vie";


        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 28;
        System.out.println("Fin des tests de la classe voiture.");
    }

    private void avancerUneSeuleVoiture() {
        System.out.println("Début des tests pour avancer une seule voiture");

        //Creation de la voiture
        ArrayList<Voiture> voitures = new ArrayList<>();
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, null, fileAttente, route));


        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Tests Avancer

        //Direction Sud
        voitures.get(0).avancer();
        assert (voitures.get(0).getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures.get(0).getCoordonneY() == 0) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Nord
        //Setup
        voitures.get(0).setOrientation(Orientation.Nord);
        voitures.get(0).setDirection(Direction.SN);
        voitures.get(0).setCoordonneY(8);
        //Test
        voitures.get(0).avancer();
        assert (voitures.get(0).getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures.get(0).getCoordonneY() == 7) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Est
        //Setup
        voitures.get(0).setOrientation(Orientation.Est);
        voitures.get(0).setDirection(Direction.OE);
        voitures.get(0).setCoordonneY(4);
        voitures.get(0).setCoordonneX(-1);
        //Test
        voitures.get(0).avancer();
        assert (voitures.get(0).getCoordonneX() == 0) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures.get(0).getCoordonneY() == 4) : "La voiture 0 a avancé en Y";

        //Direction Ouest
        //Setup
        voitures.get(0).setOrientation(Orientation.Ouest);
        voitures.get(0).setDirection(Direction.EO);
        voitures.get(0).setCoordonneY(3);
        voitures.get(0).setCoordonneX(8);
        //Test
        voitures.get(0).avancer();
        assert (voitures.get(0).getCoordonneX() == 7) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures.get(0).getCoordonneY() == 3) : "La voiture 0 a avancé en Y";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Fin des tests pour avancer une seule voiture");
    }

    private void avancerPrudementUneSeuleVoiture() {
        System.out.println("Début des tests pour avancer prudement une seule voiture");

        //Creation de la voiture
        ArrayList<Voiture> voitures = new ArrayList<>();
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, voitures, fileAttente, route));


        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Tests Avancer

        //Direction Sud
        voitures.get(0).avancerPrudament();
        assert (voitures.get(0).getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures.get(0).getCoordonneY() == 0) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Nord
        //Setup
        voitures.get(0).setOrientation(Orientation.Nord);
        voitures.get(0).setDirection(Direction.SN);
        voitures.get(0).setCoordonneY(8);
        //Test
        voitures.get(0).avancerPrudament();
        assert (voitures.get(0).getCoordonneX() == 3) : "La voiture 0 a avancé en X";
        assert (voitures.get(0).getCoordonneY() == 7) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Est
        //Setup
        voitures.get(0).setOrientation(Orientation.Est);
        voitures.get(0).setDirection(Direction.OE);
        voitures.get(0).setCoordonneY(4);
        voitures.get(0).setCoordonneX(-1);
        //Test
        voitures.get(0).avancerPrudament();
        assert (voitures.get(0).getCoordonneX() == 0) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures.get(0).getCoordonneY() == 4) : "La voiture 0 a avancé en Y";

        //Direction Ouest
        //Setup
        voitures.get(0).setOrientation(Orientation.Ouest);
        voitures.get(0).setDirection(Direction.EO);
        voitures.get(0).setCoordonneY(3);
        voitures.get(0).setCoordonneX(8);
        //Test
        voitures.get(0).avancerPrudament();
        assert (voitures.get(0).getCoordonneX() == 7) : "La voiture 0 n'a pas avancé correctement en X";
        assert (voitures.get(0).getCoordonneY() == 3) : "La voiture 0 a avancé en Y";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Fin des tests pour avancer prudement à une seule voiture");
    }

    private void doisJeTournerUneSeuleVoiture() {
        System.out.println("Début des tests pour tourner avec une seule voiture");

        //Creation des voitures
        ArrayList<Voiture> voitures = new ArrayList<>();
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NS, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NO, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NE, voitures, fileAttente, route));

        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SE, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SO, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SN, voitures, fileAttente, route));

        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.OE, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.ON, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.OS, voitures, fileAttente, route));

        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.EO, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.EN, voitures, fileAttente, route));
        voitures.add(new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.ES, voitures, fileAttente, route));


        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //On test toutes les voitures une première fois
        assert (!voitures.get(0).doisJeTourner()) : "La voiture 0 ne doit pas tourner";
        assert (voitures.get(1).doisJeTourner()) : "La voiture 1 doit tourner";
        assert (!voitures.get(2).doisJeTourner()) : "La voiture 2 ne doit pas tourner";

        assert (voitures.get(3).doisJeTourner()) : "La voiture 3 doit tourner";
        assert (!voitures.get(4).doisJeTourner()) : "La voiture 4 ne doit pas tourner";
        assert (!voitures.get(5).doisJeTourner()) : "La voiture 5 ne doit pas tourner";

        assert (!voitures.get(6).doisJeTourner()) : "La voiture 6 ne doit pas tourner";
        assert (!voitures.get(7).doisJeTourner()) : "La voiture 7 ne doit pas tourner";
        assert (voitures.get(8).doisJeTourner()) : "La voiture 8  doit  tourner";

        assert (!voitures.get(9).doisJeTourner()) : "La voiture 6 ne doit pas tourner";
        assert (voitures.get(10).doisJeTourner()) : "La voiture 10  doit  tourner";
        assert (!voitures.get(11).doisJeTourner()) : "La voiture 10 ne doit pas tourner";

        //On les fait avancer
        for (Voiture v : voitures) {
            v.avancer();
        }

        //On vérifie si c'est juste sur la case conflit suivante
        // Les voitures commentés sont celles qui ont déjà tournée avant
        assert (!voitures.get(0).doisJeTourner()) : "La voiture 0 ne doit pas tourner";
        //assert (voitures[1].doisJeTourner() == false) : "La voiture 1 ne doit pas tourner";
        assert (voitures.get(2).doisJeTourner()) : "La voiture 2 doit tourner";

        //assert (voitures[3].doisJeTourner() == false) : "La voiture 3 ne doit pas tourner";
        assert (voitures.get(4).doisJeTourner()) : "La voiture 4 doit tourner";
        assert (!voitures.get(5).doisJeTourner()) : "La voiture 5 ne doit pas tourner";

        assert (!voitures.get(6).doisJeTourner()) : "La voiture 6 ne doit pas tourner";
        assert (voitures.get(7).doisJeTourner()) : "La voiture 7 doit tourner";
        //assert (voitures[8].doisJeTourner() == false) : "La voiture 8  ne doit pas  tourner";

        assert (!voitures.get(9).doisJeTourner()) : "La voiture 6 ne doit pas tourner";
        //assert (voitures[10].doisJeTourner() == false) : "La voiture 10  ne doit pas  tourner";
        assert (voitures.get(11).doisJeTourner()) : "La voiture 10 doit tourner";


        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 20;
        System.out.println("Début des tests pour tourner avec une seule voiture");
    }

    private void caseSuivanteLibre() {
        System.out.println("Début des tests pour tourner avec une seule voiture");

        //Creation des voitures
        ArrayList<Voiture> v = new ArrayList<>();
        v.add(new Voiture(SIZE_X, SIZE_Y, 3, 3, Orientation.Sud, Direction.NS, null, fileAttente, route));
        v.add(new Voiture(SIZE_X, SIZE_Y, 4, 4, Orientation.Nord, Direction.SN, null, fileAttente, route));
        v.add(new Voiture(SIZE_X, SIZE_Y, 3, 4, Orientation.Est, Direction.OE, null, fileAttente, route));
        v.add(new Voiture(SIZE_X, SIZE_Y, 4, 3, Orientation.Ouest, Direction.EO, null, fileAttente, route));

        for (Voiture v1 : v) {
            v1.setVoiture(v);
        }

        //On test toutes les voitures devant elle
        //System.out.println(voitures[0].CaseSuivanteLibre());
        assert (!v.get(0).CaseSuivanteLibre()) : "La voie était libre";
        assert (!v.get(1).CaseSuivanteLibre()) : "La voie était libre";
        assert (!v.get(2).CaseSuivanteLibre()) : "La voie était libre";
        assert (!v.get(3).CaseSuivanteLibre()) : "La voie était libre";

        //Pour changer la configuration
        for (Voiture v1 : v) {
            v1.avancer();
        }

        assert (v.get(0).CaseSuivanteLibre()) : "La voie n'était pas libre";
        assert (v.get(1).CaseSuivanteLibre()) : "La voie n'était pas libre";
        assert (v.get(2).CaseSuivanteLibre()) : "La voie n'était pas libre";
        assert (v.get(3).CaseSuivanteLibre()) : "La voie n'était pas libre";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Début des tests pour tourner avec une seule voiture");
    }

    private void getDirectionFileAttente() {
        System.out.println("Début des tests pour obtenir les directions de la file d'attente");

        //Creation des voitures
        ArrayList<Voiture> v = new ArrayList<>();
        v.add(new Voiture(SIZE_X, SIZE_Y, 3, 0, Orientation.Sud, Direction.NS, null, fileAttente, route));
        v.add(new Voiture(SIZE_X, SIZE_Y, 4, 7, Orientation.Nord, Direction.SN, null, fileAttente, route));
        v.add(new Voiture(SIZE_X, SIZE_Y, 0, 4, Orientation.Est, Direction.OE, null, fileAttente, route));
        v.add(new Voiture(SIZE_X, SIZE_Y, 7, 3, Orientation.Ouest, Direction.EO, null, fileAttente, route));

        for (Voiture v1 : v) {
            v1.setVoiture(v);
        }

        v.get(0).realiserAction();
        v.get(1).realiserAction();
        v.get(2).realiserAction();
        v.get(3).realiserAction();

        //On refait avancer les voitures pour qu'elles soient sur liste d'attente
        for (Voiture v1 : v) {
            v1.realiserAction();
        }

        //Maintenant, on vérifie qu'obtient bien le bon tableau
        Direction[] comparatif = {
                v.get(0).getDirection(),
                v.get(1).getDirection(),
                v.get(2).getDirection(),
                v.get(3).getDirection()
        };
        v.get(1).setCoordonneX(5);
        v.get(1).setCoordonneY(4);
        v.get(1).setCoordonneX(5);
        v.get(1).setCoordonneY(4);
        v.get(2).setCoordonneX(4);
        v.get(2).setCoordonneY(2);
        v.get(3).setCoordonneX(3);
        v.get(3).setCoordonneY(5);

        Direction[] eux = v.get(0).getDirectionFileAttente();
        assert (eux[0] == comparatif[0]) : "Indice 0 : pas ok";
        assert (eux[1] == comparatif[1]) : "Indice 1 : pas ok";
        assert (eux[2] == comparatif[2]) : "Indice 2 : pas ok";
        assert (eux[3] == comparatif[3]) : "Indice 3 : pas ok";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 4;
        System.out.println("Fin des tests pour obtenir les directions de la file d'attente");
    }

    public void runTest() {
        System.out.println();
        System.out.println("Debut des tests.");
        System.out.println();

        //Déclaration de la route
        //Route route = new Route();

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

        //Tests file d'attente
        getDirectionFileAttente();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Bilan
        System.out.println("Le nombres d'asserts réalisé est de: " + nbAssert);
        System.out.println("Nous avons réalisé " + nbTestDejaFait + " tests.");

        System.out.println();
        System.out.println("Fin des tests.");
    }
}
