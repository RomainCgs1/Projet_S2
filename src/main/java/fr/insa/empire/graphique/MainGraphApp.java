package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Noeud_simple;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class MainGraphApp extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("MeshApp : L'application parfaite pour vos beaux treillis");
        Scene scene = new Scene(new MainGraphique());
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(600);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
