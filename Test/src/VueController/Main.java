package VueController;

import Model.*;
import Test.mainTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observer;

/**
 * This is the main class
 */
public class Main extends Application {

    /**
     * The size of the graphical representation
     */
    public final int SIZE_X = 8;
    public final int SIZE_Y = 8;

    //private Voiture[] voitures;
    /**
     * The arrayList of the waitingList
     */
    ArrayList<Vehicle> waitingList = new ArrayList<>();

    /**
     * The function who manage the simulation
     * @param stage : The javafx Scene
     */
    @Override
    public void start(Stage stage) {
        System.out.println("Begin");
        GridPane grid = new GridPane();

        //Load the image
        Image gris = new Image("img/grey.png");
        Image wait = new Image("img/wait.png");
        Image conflict = new Image("img/conflict.png");
        Image decision = new Image("img/decision.png");
        Image green = new Image("img/green.png");

        Image imgCar_EW = new Image ("img/car_EW.PNG");
        Image imgCar_EW_D = new Image ("img/car_EW_R.PNG");
        Image imCar_EW_G = new Image ("img/car_EW_L.PNG");
        Image imgCar_NS = new Image ("img/car_NS.PNG");
        Image imgCar_NS_D = new Image ("img/car_NS_R.PNG");
        Image imgCar_NS_G= new Image ("img/car_NS_L.PNG");
        Image imgCar_WE = new Image ("img/car_WE.PNG");
        Image imgCar_WE_D = new Image ("img/car_WE_R.PNG");
        Image imgCar_WE_G = new Image ("img/car_WE_L.PNG");
        Image imgCar_SN = new Image ("img/car_SN.PNG");
        Image imgCar_SN_D = new Image ("img/car_SN_R.PNG");
        Image imgCar_SN_G = new Image ("img/car_SN_L.PNG");

        //Array who give the graphical case for the refresh
        ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y];

        //Grid initialisation
        for (int i = 0 ; i < SIZE_X ; i++) {
            for (int j = 0 ; j < SIZE_Y  ; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        //The road
        Road road = new Road();

        //The car
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < 25 ; i++) {
             cars.add(randomCar(road));
        }

        for (Car v : cars) {
            v.setCars(cars);
        }

        //The scene and the Title
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("SMART INTERSECTION");
        stage.setScene(scene);
        stage.show();

        Observer o = new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                //Graphical update
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0 ; j < SIZE_Y  ; j++) {
                        tab[j][i].setImage(green);
                        if (road.tabCase[i][j] instanceof Tar) {
                            if(road.tabCase[i][j] instanceof Wait) {
                                tab[j][i].setImage(wait);
                            } else {
                                if(road.tabCase[i][j] instanceof Conflict) {
                                    tab[j][i].setImage(conflict);
                                } else {
                                    if (road.tabCase[i][j] instanceof Decision) {
                                        tab[j][i].setImage(decision);
                                    } else {
                                        //tar display
                                        tab[j][i].setImage(gris);
                                    }
                                }
                            }
                        } else {
                            tab[j][i].setImage(green);
                        }
                    }
                }
                //vehicle display
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0; j < SIZE_Y; j++) {
                        for (Car car : cars) {
                            if (car.getCoordinateX() == i && car.getCoordinateY() == j) {
                                switch (car.getOrientation()) {
                                    case East:
                                        if ((car.getCoordinateX() == 1
                                                || car.getCoordinateX() == 3)
                                                && car.getCoordinateY() == 4) {
                                            if (car.getDirection() == Direction.WS) {
                                                tab[i][j].setImage(imgCar_WE_D);
                                            } else if (car.getDirection() == Direction.WN) {
                                                tab[i][j].setImage(imgCar_WE_G);
                                            }
                                            else { tab[i][j].setImage(imgCar_WE); }
                                        }
                                        else { tab[i][j].setImage(imgCar_WE); }
                                        break;

                                    case West:
                                        if ((car.getCoordinateX() == 6
                                                || car.getCoordinateX() == 4)
                                                && car.getCoordinateY() == 3) {
                                            if (car.getDirection() == Direction.EN) {
                                                tab[i][j].setImage(imgCar_EW_D);
                                            } else if (car.getDirection() == Direction.ES) {
                                                tab[i][j].setImage(imCar_EW_G);
                                            }
                                            else { tab[i][j].setImage(imgCar_EW); }
                                        }
                                        else { tab[i][j].setImage(imgCar_EW); }
                                        break;

                                    case South:
                                        if ((car.getCoordinateY() == 1
                                                || car.getCoordinateY() == 3)
                                                && car.getCoordinateX() == 3) {
                                            if (car.getDirection() == Direction.NE) {
                                                tab[i][j].setImage(imgCar_NS_G);
                                            } else if (car.getDirection() == Direction.NW) {
                                                tab[i][j].setImage(imgCar_NS_D);
                                            }
                                            else { tab[i][j].setImage(imgCar_NS); }
                                        }
                                        else { tab[i][j].setImage(imgCar_NS); }
                                        break;

                                    case North:
                                        if ((car.getCoordinateY() == 6
                                                || car.getCoordinateY() == 4)
                                                && car.getCoordinateX() == 4) {
                                            if (car.getDirection() == Direction.SE) {
                                                tab[i][j].setImage(imgCar_SN_D);
                                            } else if (car.getDirection() == Direction.SW) {
                                                tab[i][j].setImage(imgCar_SN_G);
                                            }
                                            else { tab[i][j].setImage(imgCar_SN); }
                                        }
                                        else { tab[i][j].setImage(imgCar_SN); }
                                        break;
                                }
                            }
                        }
                    }
                }
                if(cars.size() <= 0) {
                    System.out.println("Fin");
                }
            }
        };

        for (Car car : cars) {
            car.addObserver(o);
            car.start();
        }
        grid.requestFocus();
    }

    /**
     * To create random car
     * @param road : The road
     * @return a random car
     */
    public Car randomCar(Road road) {
        // We choose an orientation
        int orientation = (int) (Math.random() * ( 5 - 1 ));
        int direction, coordX, coordY;
        Orientation o;
        Direction d;
        switch(orientation) {
            case 1 :
                o = Orientation.North;
                // We choose a direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                coordX = 4;
                coordY = (int) (Math.random() * ( 55 - 8 ));
                coordY = 8 + coordY;
                switch (direction) {
                    case 1:
                        d = Direction.SW;
                        break;
                    case 2:
                        d = Direction.SN;
                        break;
                    default:
                        d = Direction.SE;
                }
                break;
            case 2 :
                o = Orientation.South;
                coordX = 3;
                coordY = (int) (Math.random() * ( -58 - (-1) ));
                coordY = coordY - 8;
                // We choose a direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                switch (direction) {
                    case 1:
                        d = Direction.NW;
                        break;
                    case 2:
                        d = Direction.NS;
                        break;
                    default:
                        d = Direction.NE;
                }
                break;
            case 3 :
                o = Orientation.East;
                coordX = (int) (Math.random() * ( -58 - (-1) ));
                coordX = coordX - 8;
                coordY = 4;
                // We choose a direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                switch (direction) {
                    case 1:
                        d = Direction.WS;
                        break;
                    case 2:
                        d = Direction.WE;
                        break;
                    default:
                        d = Direction.WN;
                }
                break;
            default:
                o = Orientation.West;
                coordX = (int) (Math.random() * ( 55 - 8 ));
                coordX = 8 + coordX;
                coordY = 3;
                // We choose a direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                switch (direction) {
                    case 1:
                        d = Direction.EN;
                        break;
                    case 2:
                        d = Direction.ES;
                        break;
                    default:
                        d = Direction.EW;
                }
                break;
        }
        return new Car(SIZE_X, SIZE_Y, coordX, coordY, o, d, null, waitingList, road);
    }

    /**
     * To launch the tests
     */
    public static void startTest() {
        mainTest lesTests = new mainTest();
        lesTests.runTest();
    }

    /**
     * The main function
     * @param args to choose the mode
     * demo : without tests
     * dev : graphical + tests
     * test : without graphical
     */
    public static void main(String[] args) {
        if (args[0].equals("demo")) {
            System.out.println("Demostration mode");
            launch(args);
        }
        if (args[0].equals("dev")) {
            System.out.println("Developpement mode");
            startTest();
            launch(args);
        }
        if (args[0].equals("test")) {
            System.out.println("Test mode");
            startTest();
            System.exit(1);
        }
    }
}
