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
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {

    public final int SIZE_X = 8;
    public final int SIZE_Y = 8;

    private Voiture[] voitures;
    ArrayList fileAttente = new ArrayList();

    @Override
    public void start(Stage stage) throws Exception{
        GridPane grid = new GridPane();

        //Chargement des images
        Image gris = new Image("img/gris.png");
        Image attente = new Image("img/attente.png");
        Image conflit = new Image("img/conflit.png");
        Image decision = new Image("img/decision.png");
        Image vert = new Image("img/vert.png");
        Image imgVoiture = new Image ("img/voiture.png");

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
        voitures = new Voiture[] {
                new Voiture(SIZE_X, SIZE_Y, 3, -1, Orientation.Sud, Direction.NS, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 3, -4, Orientation.Sud, Direction.NO, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 3, -10, Orientation.Sud, Direction.NE, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SN, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SE, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 4, 8, Orientation.Nord, Direction.SO, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OE, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.ON, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, -1, 4, Orientation.Est, Direction.OS, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EO, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.EN, voitures, fileAttente, route),
                //new Voiture(SIZE_X, SIZE_Y, 8, 3, Orientation.Ouest, Direction.ES, voitures, fileAttente, route),

        };

        for (Voiture v : voitures) {
            v.setVoiture(voitures);
        }

        //Déclaration de la scène + titre
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("POPOPO POM");
        stage.setScene(scene);
        stage.show();


        Observer o = new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                //Raffraichissement graphique
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0 ; j < SIZE_Y  ; j++) {
                        tab[j][i].setImage(vert);

                        //Affichage de la route
//                        if (route.tabCase[i][j] instanceof Goudron) {
//                            //Affichage du goudron
//                            tab[i][j].setImage(gris);
//                        } else {
//                            tab[i][j].setImage(vert);
//                        }
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

                        //Affichage des voitures
//                        for (Voiture voiture : voitures) {
//                            if (voiture.getCoordonneX() == i && voiture.getCoordonneY() == j) {
//                                System.out.println("J'affiche en X: " + i + " et y en: " + j);
//                                tab[i][j].setImage(imgVoiture);
//                            }
                        }
                    }
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0; j < SIZE_Y; j++) {
                        for (Voiture voiture : voitures) {
                            if (voiture.getCoordonneX() == i && voiture.getCoordonneY() == j) {
                                //System.out.println("J'affiche en X: " + i + " et y en: " + j);
                                tab[i][j].setImage(imgVoiture);
                            }
                        }
                    }
                }
            }
        };

        for (Voiture voiture : voitures) {
            voiture.addObserver(o);
            voiture.start();
        }
        grid.requestFocus();
    }

    public ArrayList getFileAttente() { return fileAttente;}
    public void setFileAttente(ArrayList newFileAttente) { this.fileAttente = newFileAttente;}

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
