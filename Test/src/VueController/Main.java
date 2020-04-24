package VueController;

import Model.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Observer;


public class Main extends Application {

    public final int SIZE_X = 8;
    public final int SIZE_Y = 8;

    private Voiture[] voitures;

    @Override
    public void start(Stage stage) throws Exception{
        GridPane grid = new GridPane();

        //Chargement des images
        Image gris = new Image("img/gris.png");
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
                new Voiture()
        };

        //Déclaration de la scène + titre
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("POPOPO POM");
        stage.setScene(scene);
        stage.show();


        Observer o = new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                //Raffraichissement graphique
                for (int i = 0 ; i < SIZE_X ; i++) {
                    for (int j = 0 ; j < SIZE_Y  ; j++) {
                        tab[i][j].setImage(vert);

                        //Affichage de la route
                        if (route.tabCase[i][j] instanceof Goudron) {
                            //Affichage du goudron
                            tab[i][j].setImage(gris);
                        } else {
                            tab[i][j].setImage(vert);
                        }

                        //Affichage des voitures
                        for (Voiture voiture : voitures) {
                            if (voiture.getCoordonneX() == i && voiture.getCoordonneY() == j) {
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


    public static void main(String[] args) {
        if (args[0].equals("test")) {
            System.out.println("Mode test activé");
        }
        if (args[0].equals("demo")) {
            System.out.println("Mode démo activé");
        }
        launch(args);
    }
}
