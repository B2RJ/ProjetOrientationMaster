package Test;

import Model.*;

import java.util.ArrayList;

/**
 * Because I didn't use maven, I wrote this test.
 * This class must disappear if the project goes under maven.
 *
 * @author B2RJ
 */
public class mainTest {

    private final int SIZE_X = 10;
    private final int SIZE_Y = 10;

    ArrayList<Vehicle> fileAttente = new ArrayList<>();

    int nbTests = 6;
    float nbTestDejaFait = 0;
    int nbAssert = 0;
    Road road = new Road();

    /**
     * This function test the car class
     */
    private void testVoiture() {
        System.out.println("Début des tests de la classe voiture");

        //Déclaration des voitures
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car(SIZE_X, SIZE_Y, 3, -1, Orientation.South, Direction.NS, null, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 4, 8, Orientation.North, Direction.SN, null, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, -1, 4, Orientation.East, Direction.WE, null, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 8, 3, Orientation.West, Direction.EW, null, fileAttente, road));
//        voitures = new Voiture[] {
//                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, null, fileAttente, route),
//                new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SN, null, fileAttente, route),
//                new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OE, null, fileAttente, route),
//                new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EO, null, fileAttente, route)
//        };

        for (Car v : cars) {
            v.setCars(cars);
        }

        //Vérification des GET sur le tableau
        assert (cars.get(0).getCoordinateX()==3) : "getCoordinateX() != 3";
        assert (cars.get(0).getCoordinateY()==-1) : "getCoordinateY() != -1";
        assert (cars.get(0).getOrientation() == Orientation.South) : "getOrientation() != Sud";
        assert (cars.get(0).getDirection() == Direction.NS) : "getDirection() != NS";

        assert (cars.get(1).getCoordinateX()==4) : "getCoordinateX() != 4";
        assert (cars.get(1).getCoordinateY()==8) : "getCoordinateY() != 8";
        assert (cars.get(1).getOrientation() == Orientation.North) : "getOrientation() != Nord";
        assert (cars.get(1).getDirection() == Direction.SN) : "getDirection() != SN";

        assert (cars.get(2).getCoordinateX()==-1) : "getCoordinateX() != -1";
        assert (cars.get(2).getCoordinateY()==4) : "getCoordinateY() != 4";
        assert (cars.get(2).getOrientation() == Orientation.East) : "getOrientation() != Est";
        assert (cars.get(2).getDirection() == Direction.WE) : "getDirection() != OE";

        assert (cars.get(3).getCoordinateX()==8) : "getCoordinateX() != 8";
        assert (cars.get(3).getCoordinateY()==3) : "getCoordinateY() != 3";
        assert (cars.get(3).getOrientation() == Orientation.West) : "getOrientation() != Ouest";
        assert (cars.get(3).getDirection() == Direction.EW) : "getDirection() != EO";

        //Vérification des SET sur le tableau
        cars.get(0).setCoordonneX(1);
        assert (cars.get(0).getCoordinateX()==1) : "getCoordinateX() != 1";

        cars.get(0).setCoordonneY(5);
        assert (cars.get(0).getCoordinateY()==5) : "getCoordinateY() != 5";

        cars.get(0).setOrientation(Orientation.East);
        assert (cars.get(0).getOrientation() == Orientation.East) : "getOrientation() != Est";

        cars.get(0).setDirection(Direction.WE);
        assert (cars.get(2).getDirection() == Direction.WE) : "getDirection() != OE";

        //Vérification de la fonction isInLife()
        assert (cars.get(0).isInLife()) : "La voiture0 est pas en vie";
        cars.get(0).setTuable(true);
        cars.get(0).setCoordonneY(-2);
        assert (!cars.get(0).isInLife()) : "La voiture0 est en vie";

        assert (cars.get(1).isInLife()) : "La voiture1 est pas en vie";
        cars.get(1).setTuable(true);
        cars.get(1).setCoordonneY(11);
        assert (!cars.get(1).isInLife()) : "La voiture1 est en vie";

        assert (cars.get(2).isInLife()) : "La voiture2 est pas en vie";
        cars.get(2).setTuable(true);
        cars.get(2).setCoordonneX(-3);
        assert (!cars.get(2).isInLife()) : "La voiture2 est en vie";

        assert (cars.get(3).isInLife()) : "La voiture3 est pas en vie";
        cars.get(3).setTuable(true);
        cars.get(3).setCoordonneX(11);
        assert (!cars.get(3).isInLife()) : "La voiture3 est en vie";


        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 28;
        System.out.println("Fin des tests de la classe voiture.");
    }

    /**
     * This function test how a car forward alone
     */
    private void avancerUneSeuleVoiture() {
        System.out.println("Début des tests pour tread une seule voiture");

        //Creation de la voiture
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car(SIZE_X, SIZE_Y, 3, -1, Orientation.South, Direction.NS, null, fileAttente, road));


        for (Car v : cars) {
            v.setCars(cars);
        }

        //Tests Avancer

        //Direction Sud
        cars.get(0).tread();
        assert (cars.get(0).getCoordinateX() == 3) : "La voiture 0 a avancé en X";
        assert (cars.get(0).getCoordinateY() == 0) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Nord
        //Setup
        cars.get(0).setOrientation(Orientation.North);
        cars.get(0).setDirection(Direction.SN);
        cars.get(0).setCoordonneY(8);
        //Test
        cars.get(0).tread();
        assert (cars.get(0).getCoordinateX() == 3) : "La voiture 0 a avancé en X";
        assert (cars.get(0).getCoordinateY() == 7) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Est
        //Setup
        cars.get(0).setOrientation(Orientation.East);
        cars.get(0).setDirection(Direction.WE);
        cars.get(0).setCoordonneY(4);
        cars.get(0).setCoordonneX(-1);
        //Test
        cars.get(0).tread();
        assert (cars.get(0).getCoordinateX() == 0) : "La voiture 0 n'a pas avancé correctement en X";
        assert (cars.get(0).getCoordinateY() == 4) : "La voiture 0 a avancé en Y";

        //Direction Ouest
        //Setup
        cars.get(0).setOrientation(Orientation.West);
        cars.get(0).setDirection(Direction.EW);
        cars.get(0).setCoordonneY(3);
        cars.get(0).setCoordonneX(8);
        //Test
        cars.get(0).tread();
        assert (cars.get(0).getCoordinateX() == 7) : "La voiture 0 n'a pas avancé correctement en X";
        assert (cars.get(0).getCoordinateY() == 3) : "La voiture 0 a avancé en Y";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Fin des tests pour tread une seule voiture");
    }

    /**
     * This function test how a car forward carefully alone
     */
    private void avancerPrudementUneSeuleVoiture() {
        System.out.println("Début des tests pour tread prudement une seule voiture");

        //Creation de la voiture
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car(SIZE_X, SIZE_Y, 3, -1, Orientation.South, Direction.NS, cars, fileAttente, road));


        for (Car v : cars) {
            v.setCars(cars);
        }

        //Tests Avancer

        //Direction Sud
        cars.get(0).treadCarefully();
        assert (cars.get(0).getCoordinateX() == 3) : "La voiture 0 a avancé en X";
        assert (cars.get(0).getCoordinateY() == 0) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Nord
        //Setup
        cars.get(0).setOrientation(Orientation.North);
        cars.get(0).setDirection(Direction.SN);
        cars.get(0).setCoordonneY(8);
        //Test
        cars.get(0).treadCarefully();
        assert (cars.get(0).getCoordinateX() == 3) : "La voiture 0 a avancé en X";
        assert (cars.get(0).getCoordinateY() == 7) : "La voiture 0 n'a pas avancé correctement en Y";

        //Direction Est
        //Setup
        cars.get(0).setOrientation(Orientation.East);
        cars.get(0).setDirection(Direction.WE);
        cars.get(0).setCoordonneY(4);
        cars.get(0).setCoordonneX(-1);
        //Test
        cars.get(0).treadCarefully();
        assert (cars.get(0).getCoordinateX() == 0) : "La voiture 0 n'a pas avancé correctement en X";
        assert (cars.get(0).getCoordinateY() == 4) : "La voiture 0 a avancé en Y";

        //Direction Ouest
        //Setup
        cars.get(0).setOrientation(Orientation.West);
        cars.get(0).setDirection(Direction.EW);
        cars.get(0).setCoordonneY(3);
        cars.get(0).setCoordonneX(8);
        //Test
        cars.get(0).treadCarefully();
        assert (cars.get(0).getCoordinateX() == 7) : "La voiture 0 n'a pas avancé correctement en X";
        assert (cars.get(0).getCoordinateY() == 3) : "La voiture 0 a avancé en Y";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Fin des tests pour tread prudement à une seule voiture");
    }

    /**
     * This function test if the car are on position to change its orientation
     */
    private void doisJeTournerUneSeuleVoiture() {
        System.out.println("Début des tests pour tourner avec une seule voiture");

        //Creation des voitures
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car(SIZE_X, SIZE_Y, 3, 3, Orientation.South, Direction.NS, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 3, 3, Orientation.South, Direction.NW, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 3, 3, Orientation.South, Direction.NE, cars, fileAttente, road));

        cars.add(new Car(SIZE_X, SIZE_Y, 4, 4, Orientation.North, Direction.SE, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 4, 4, Orientation.North, Direction.SW, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 4, 4, Orientation.North, Direction.SN, cars, fileAttente, road));

        cars.add(new Car(SIZE_X, SIZE_Y, 3, 4, Orientation.East, Direction.WE, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 3, 4, Orientation.East, Direction.WN, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 3, 4, Orientation.East, Direction.WS, cars, fileAttente, road));

        cars.add(new Car(SIZE_X, SIZE_Y, 4, 3, Orientation.West, Direction.EW, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 4, 3, Orientation.West, Direction.EN, cars, fileAttente, road));
        cars.add(new Car(SIZE_X, SIZE_Y, 4, 3, Orientation.West, Direction.ES, cars, fileAttente, road));


        for (Car v : cars) {
            v.setCars(cars);
        }

        //On test toutes les voitures une première fois
        assert (!cars.get(0).ShouldTurn()) : "La voiture 0 ne doit pas tourner";
        assert (cars.get(1).ShouldTurn()) : "La voiture 1 doit tourner";
        assert (!cars.get(2).ShouldTurn()) : "La voiture 2 ne doit pas tourner";

        assert (cars.get(3).ShouldTurn()) : "La voiture 3 doit tourner";
        assert (!cars.get(4).ShouldTurn()) : "La voiture 4 ne doit pas tourner";
        assert (!cars.get(5).ShouldTurn()) : "La voiture 5 ne doit pas tourner";

        assert (!cars.get(6).ShouldTurn()) : "La voiture 6 ne doit pas tourner";
        assert (!cars.get(7).ShouldTurn()) : "La voiture 7 ne doit pas tourner";
        assert (cars.get(8).ShouldTurn()) : "La voiture 8  doit  tourner";

        assert (!cars.get(9).ShouldTurn()) : "La voiture 6 ne doit pas tourner";
        assert (cars.get(10).ShouldTurn()) : "La voiture 10  doit  tourner";
        assert (!cars.get(11).ShouldTurn()) : "La voiture 10 ne doit pas tourner";

        //On les fait tread
        for (Car v : cars) {
            v.tread();
        }

        //On vérifie si c'est juste sur la case conflit suivante
        // Les voitures commentés sont celles qui ont déjà tournée avant
        assert (!cars.get(0).ShouldTurn()) : "La voiture 0 ne doit pas tourner";
        //assert (voitures[1].ShouldTurn() == false) : "La voiture 1 ne doit pas tourner";
        assert (cars.get(2).ShouldTurn()) : "La voiture 2 doit tourner";

        //assert (voitures[3].ShouldTurn() == false) : "La voiture 3 ne doit pas tourner";
        assert (cars.get(4).ShouldTurn()) : "La voiture 4 doit tourner";
        assert (!cars.get(5).ShouldTurn()) : "La voiture 5 ne doit pas tourner";

        assert (!cars.get(6).ShouldTurn()) : "La voiture 6 ne doit pas tourner";
        assert (cars.get(7).ShouldTurn()) : "La voiture 7 doit tourner";
        //assert (voitures[8].ShouldTurn() == false) : "La voiture 8  ne doit pas  tourner";

        assert (!cars.get(9).ShouldTurn()) : "La voiture 6 ne doit pas tourner";
        //assert (voitures[10].ShouldTurn() == false) : "La voiture 10  ne doit pas  tourner";
        assert (cars.get(11).ShouldTurn()) : "La voiture 10 doit tourner";


        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 20;
        System.out.println("Début des tests pour tourner avec une seule voiture");
    }

    /**
     * This function test if the next case is free
     */
    private void caseSuivanteLibre() {
        System.out.println("Début des tests pour tourner avec une seule voiture");

        //Creation des voitures
        ArrayList<Car> v = new ArrayList<>();
        v.add(new Car(SIZE_X, SIZE_Y, 3, 3, Orientation.South, Direction.NS, null, fileAttente, road));
        v.add(new Car(SIZE_X, SIZE_Y, 4, 4, Orientation.North, Direction.SN, null, fileAttente, road));
        v.add(new Car(SIZE_X, SIZE_Y, 3, 4, Orientation.East, Direction.WE, null, fileAttente, road));
        v.add(new Car(SIZE_X, SIZE_Y, 4, 3, Orientation.West, Direction.EW, null, fileAttente, road));

        for (Car v1 : v) {
            v1.setCars(v);
        }

        //On test toutes les voitures devant elle
        //System.out.println(voitures[0].NextCaseFree());
        assert (!v.get(0).NextCaseFree()) : "La voie était libre";
        assert (!v.get(1).NextCaseFree()) : "La voie était libre";
        assert (!v.get(2).NextCaseFree()) : "La voie était libre";
        assert (!v.get(3).NextCaseFree()) : "La voie était libre";

        //Pour changer la configuration
        for (Car v1 : v) {
            v1.tread();
        }

        assert (v.get(0).NextCaseFree()) : "La voie n'était pas libre";
        assert (v.get(1).NextCaseFree()) : "La voie n'était pas libre";
        assert (v.get(2).NextCaseFree()) : "La voie n'était pas libre";
        assert (v.get(3).NextCaseFree()) : "La voie n'était pas libre";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 8;
        System.out.println("Début des tests pour tourner avec une seule voiture");
    }

    /**
     * This function test the getter of the waiting list
     */
    private void getDirectionFileAttente() {
        System.out.println("Début des tests pour obtenir les directions de la file d'attente");

        //Creation des voitures
        ArrayList<Car> v = new ArrayList<>();
        v.add(new Car(SIZE_X, SIZE_Y, 3, 0, Orientation.South, Direction.NS, null, fileAttente, road));
        v.add(new Car(SIZE_X, SIZE_Y, 4, 7, Orientation.North, Direction.SN, null, fileAttente, road));
        v.add(new Car(SIZE_X, SIZE_Y, 0, 4, Orientation.East, Direction.WE, null, fileAttente, road));
        v.add(new Car(SIZE_X, SIZE_Y, 7, 3, Orientation.West, Direction.EW, null, fileAttente, road));

        for (Car v1 : v) {
            v1.setCars(v);
        }

        v.get(0).doActions();
        v.get(1).doActions();
        v.get(2).doActions();
        v.get(3).doActions();

        //On refait tread les voitures pour qu'elles soient sur liste d'attente
        for (Car v1 : v) {
            v1.doActions();
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

        Direction[] eux = v.get(0).getDirectionWaitingList();
        assert (eux[0] == comparatif[0]) : "Indice 0 : pas ok";
        assert (eux[1] == comparatif[1]) : "Indice 1 : pas ok";
        assert (eux[2] == comparatif[2]) : "Indice 2 : pas ok";
        assert (eux[3] == comparatif[3]) : "Indice 3 : pas ok";

        nbTestDejaFait = nbTestDejaFait + 1;
        nbAssert = nbAssert + 4;
        System.out.println("Fin des tests pour obtenir les directions de la file d'attente");
    }

    /**
     * This function manage all tests
     */
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

        //Tests pour tread à une seule voiture
        avancerUneSeuleVoiture();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Tests pour tread prudement une seule voiture
        avancerPrudementUneSeuleVoiture();
        System.out.println();
        System.out.println("Nous avons réalisé " + (nbTestDejaFait/nbTests)*100 + "% des tests.");

        //Tests de la fonction ShouldTurn pour une seule voiture
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
