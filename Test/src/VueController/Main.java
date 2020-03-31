package VueController;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;


public class Main extends Application {

    public final int SIZE_X = 8;
    public final int SIZE_Y = 8;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // définit la largeur et la hauteur de la fenêtre
        // en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
        stage.setWidth(800);
        stage.setHeight(600);
        // met un titre dans la fenêtre
        stage.setTitle("POPOPO POM");

        // la racine du sceneGraph est le root
        Group root2 = new Group();
        Scene scene = new Scene(root2);
        scene.setFill(Color.SKYBLUE);

        Route route = new Route();

        GridPane grid = new GridPane();

        Image vert = new Image(new FileInputStream("src/img/vert.png"));
        ImageView imageVert = new ImageView(vert);

        // création du soleil

        //Circle sun = new Circle(60, Color.web("yellow", 0.8));
        //sun.setCenterX(600);
        //sun.setCenterY(100);

        Image soleil = new Image(new FileInputStream("src/img/soleil.png"));
        ImageView imageSoleil = new ImageView(soleil);

        imageSoleil.setX(60);
        imageSoleil.setY(50);

        // création du sol
        Rectangle ground = new Rectangle(0, 400, 800, 200);
        ground.setFill(Color.GREEN);

        // création d'un élément plus complexe, le panneau

        // le repère utilisé est celui du panneau
        Rectangle panel = new Rectangle( 0, -50, 500, 110);
        panel.setFill(Color.DARKBLUE);
        // composer l'élément plus complexe


        // ajout de tous les éléments de la scène
        root2.getChildren().add(imageSoleil);
        root2.getChildren().add(ground);
        //root.getChildren().add(sign);

        // ajout de la scène sur l'estrade
        stage.setScene(scene);
        // ouvrir le rideau
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
