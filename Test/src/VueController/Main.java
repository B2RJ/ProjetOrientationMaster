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

public class Main extends Application {

    public final int SIZE_X = 8;
    public final int SIZE_Y = 8;

    //private Voiture[] voitures;
    ArrayList<Vehicule> fileAttente = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        System.out.println("Debut");
        GridPane grid = new GridPane();

        //Chargement des images
        Image gris = new Image("img/gris.png");
        Image attente = new Image("img/attente.png");
        Image conflit = new Image("img/conflit.png");
        Image decision = new Image("img/decision.png");
        Image vert = new Image("img/vert.png");

        Image imgVoiture_EO = new Image ("img/Voiture_EO.PNG");
        Image imgVoiture_EO_D = new Image ("img/Voiture_EO_D.PNG");
        Image imgVoiture_EO_G = new Image ("img/Voiture_EO_G.PNG");
        Image imgVoiture_NS = new Image ("img/Voiture_NS.PNG");
        Image imgVoiture_NS_D = new Image ("img/Voiture_NS_D.PNG");
        Image imgVoiture_NS_G= new Image ("img/Voiture_NS_G.PNG");
        Image imgVoiture_OE = new Image ("img/Voiture_OE.PNG");
        Image imgVoiture_OE_D = new Image ("img/Voiture_OE_D.PNG");
        Image imgVoiture_OE_G = new Image ("img/Voiture_OE_G.PNG");
        Image imgVoiture_SN = new Image ("img/Voiture_SN.PNG");
        Image imgVoiture_SN_D = new Image ("img/Voiture_SN_D.PNG");
        Image imgVoiture_SN_G = new Image ("img/Voiture_SN_G.PNG");

        //Tableau permettant de récuperer les cases graphiques lors du raffraichissement
        ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y];

        //Initialisation de la grille
        for (int i = 0 ; i < SIZE_X ; i++) {
            for (int j = 0 ; j < SIZE_Y  ; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        //Déclaration de la route
        Route route = new Route();

        //Déclaration des voiture
        ArrayList<Voiture> voitures = new ArrayList<>();
        for (int i = 0; i < 25 ; i++) {
             voitures.add(voitureAleatoire(route));
        }

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Déclaration de la scène + titre
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("CARREFOUR INTELLIGENT");
        stage.setScene(scene);
        stage.show();

        Observer o = new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                //Raffraichissement graphique
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0 ; j < SIZE_Y  ; j++) {
                        tab[j][i].setImage(vert);
                        if (route.tabCase[i][j] instanceof Goudron) {
                            if(route.tabCase[i][j] instanceof Attente) {
                                tab[j][i].setImage(attente);
                            } else {
                                if(route.tabCase[i][j] instanceof Conflit) {
                                    tab[j][i].setImage(conflit);
                                } else {
                                    if (route.tabCase[i][j] instanceof Decision) {
                                        tab[j][i].setImage(decision);
                                    } else {
                                        //Affichage du goudron
                                        tab[j][i].setImage(gris);
                                    }
                                }
                            }
                        } else {
                            tab[j][i].setImage(vert);
                        }
                    }
                }
                //Affichage des voitures
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0; j < SIZE_Y; j++) {
                        for (Voiture voiture : voitures) {
                            if (voiture.getCoordonneX() == i && voiture.getCoordonneY() == j) {
                                switch (voiture.getOrientation()) {
                                    case Est:
                                        if ((voiture.getCoordonneX() == 1
                                                || voiture.getCoordonneX() == 3)
                                                && voiture.getCoordonneY() == 4) {
                                            if (voiture.getDirection() == Direction.OS) {
                                                tab[i][j].setImage(imgVoiture_OE_D);
                                            } else if (voiture.getDirection() == Direction.ON) {
                                                tab[i][j].setImage(imgVoiture_OE_G);
                                            }
                                            else { tab[i][j].setImage(imgVoiture_OE); }
                                        }
                                        else { tab[i][j].setImage(imgVoiture_OE); }
                                        break;

                                    case Ouest:
                                        if ((voiture.getCoordonneX() == 6
                                                || voiture.getCoordonneX() == 4)
                                                && voiture.getCoordonneY() == 3) {
                                            if (voiture.getDirection() == Direction.EN) {
                                                tab[i][j].setImage(imgVoiture_EO_D);
                                            } else if (voiture.getDirection() == Direction.ES) {
                                                tab[i][j].setImage(imgVoiture_EO_G);
                                            }
                                            else { tab[i][j].setImage(imgVoiture_EO); }
                                        }
                                        else { tab[i][j].setImage(imgVoiture_EO); }
                                        break;

                                    case Sud:
                                        if ((voiture.getCoordonneY() == 1
                                                || voiture.getCoordonneY() == 3)
                                                && voiture.getCoordonneX() == 3) {
                                            if (voiture.getDirection() == Direction.NE) {
                                                tab[i][j].setImage(imgVoiture_NS_G);
                                            } else if (voiture.getDirection() == Direction.NO) {
                                                tab[i][j].setImage(imgVoiture_NS_D);
                                            }
                                            else { tab[i][j].setImage(imgVoiture_NS); }
                                        }
                                        else { tab[i][j].setImage(imgVoiture_NS); }
                                        break;

                                    case Nord:
                                        if ((voiture.getCoordonneY() == 6
                                                || voiture.getCoordonneY() == 4)
                                                && voiture.getCoordonneX() == 4) {
                                            if (voiture.getDirection() == Direction.SE) {
                                                tab[i][j].setImage(imgVoiture_SN_D);
                                            } else if (voiture.getDirection() == Direction.SO) {
                                                tab[i][j].setImage(imgVoiture_SN_G);
                                            }
                                            else { tab[i][j].setImage(imgVoiture_SN); }
                                        }
                                        else { tab[i][j].setImage(imgVoiture_SN); }
                                        break;
                                }
                            }
                        }
                    }
                }
                if(voitures.size() <= 0) {
                    System.out.println("Fin");
                }
            }
        };

        for (Voiture voiture : voitures) {
            voiture.addObserver(o);
            voiture.start();
        }
        grid.requestFocus();
    }

    //public ArrayList<Vehicule> getFileAttente() { return fileAttente;}
    //public void setFileAttente(ArrayList<Vehicule> newFileAttente) { this.fileAttente = newFileAttente;}

    public Voiture voitureAleatoire(Route route) {
        // On fixe une Orientation
        int orientation = (int) (Math.random() * ( 5 - 1 ));
        int direction, coordX, coordY;
        Orientation o;
        Direction d;
        switch(orientation) {
            case 1 :
                o = Orientation.Nord;
                // On fixe une Direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                coordX = 4;
                coordY = (int) (Math.random() * ( 55 - 8 ));
                coordY = 8 + coordY;
                switch (direction) {
                    case 1:
                        d = Direction.SO;
                        break;
                    case 2:
                        d = Direction.SN;
                        break;
                    default:
                        d = Direction.SE;
                }
                break;
            case 2 :
                o = Orientation.Sud;
                coordX = 3;
                coordY = (int) (Math.random() * ( -58 - (-1) ));
                coordY = coordY - 8;
                // On fixe une Direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                switch (direction) {
                    case 1:
                        d = Direction.NO;
                        break;
                    case 2:
                        d = Direction.NS;
                        break;
                    default:
                        d = Direction.NE;
                }
                break;
            case 3 :
                o = Orientation.Est;
                coordX = (int) (Math.random() * ( -58 - (-1) ));
                coordX = coordX - 8;
                coordY = 4;
                // On fixe une Direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                switch (direction) {
                    case 1:
                        d = Direction.OS;
                        break;
                    case 2:
                        d = Direction.OE;
                        break;
                    default:
                        d = Direction.ON;
                }
                break;
            default:
                o = Orientation.Ouest;
                coordX = (int) (Math.random() * ( 55 - 8 ));
                coordX = 8 + coordX;
                coordY = 3;
                // On fixe une Direction
                direction  = (int) (Math.random() * ( 4 - 1 ));
                switch (direction) {
                    case 1:
                        d = Direction.EN;
                        break;
                    case 2:
                        d = Direction.ES;
                        break;
                    default:
                        d = Direction.EO;
                }
                break;
        }
        return new Voiture(SIZE_X, SIZE_Y, coordX, coordY, o, d, null, fileAttente, route);
    }

    public static void startTest() {
        mainTest lesTests = new mainTest();
        lesTests.runTest();
    }

    public static void main(String[] args) {
        if (args[0].equals("demo")) {
            System.out.println("Mode démo activé");
            launch(args);
        }
        if (args[0].equals("dev")) {
            System.out.println("Mode dev activé");
            startTest();
            launch(args);
        }
        if (args[0].equals("test")) {
            System.out.println("Mode test activé");
            startTest();
            System.exit(1);
        }
    }
}
