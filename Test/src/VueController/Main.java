package VueController;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;


public class Main extends Application {

    public final int SIZE_X = 8;
    public final int SIZE_Y = 8;

    @Override
    public void start(Stage stage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        StackPane root = new StackPane();
        Route route = new Route();
        GridPane grid = new GridPane();
        ImageView[][] tabCase = new ImageView[SIZE_X][SIZE_Y];

        Image vert = new Image(new FileInputStream("src/img/vert.png"));
        ImageView imageVert = new ImageView(vert);

        Image gris = new Image(new FileInputStream("src/img/gris.png"));
        ImageView imageGris = new ImageView(gris);

        imageGris.setX(46);
        imageGris.setY(45);

        for(int i = 0; i < SIZE_X; i++) {
            for(int j = 0; j < SIZE_Y; j++) {

              /*  // AVEC les images (pas encore au point)
                if(route.tabCase[i][j] instanceof Goudron) {
                    tabCase[i][j].setImage(imageVert);
                } else {
                    image = imageGris;
                }
                grid.add(image, i, j); */


                // SANS les images
                Rectangle rectangle = new Rectangle();
                rectangle.setX(46);
                rectangle.setY(45);
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                if(route.tabCase[i][j] instanceof Goudron) {
                    rectangle.setFill(Color.GRAY);
                } else {
                    rectangle.setFill(Color.GREEN);
                }
                grid.add(rectangle, i, j);
            }
        }

        // initialisation de la grille sans les images
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                ImageView img = new ImageView();

                grid.add(img, i, j);
            }
        }

        // définit la largeur et la hauteur de la fenêtre
        // en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
        stage.setWidth(800);
        stage.setHeight(600);
        // met un titre dans la fenêtre
        stage.setTitle("POPOPO POM");

        // la racine du sceneGraph est le root
      //  Group root2 = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //imageSoleil.setX(60);
        //imageSoleil.setY(50);

        // création du sol
        Rectangle ground = new Rectangle(0, 400, 800, 200);
        ground.setFill(Color.GREEN);

        // création d'un élément plus complexe, le panneau

        // le repère utilisé est celui du panneau
        Rectangle panel = new Rectangle( 0, -50, 500, 110);
        panel.setFill(Color.DARKBLUE);


        @Deprecated
        Observer à = new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        };

        // ajout de tous les éléments de la scène
        //root2.getChildren().add(imageSoleil);
        //root2.getChildren().add(ground);
        //root.getChildren().add(sign);
        root.requestFocus();
        root.getChildren().add(grid);
        // ajout de la scène sur l'estrade
        stage.setScene(scene);
        // ouvrir le rideau
        stage.show();
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
