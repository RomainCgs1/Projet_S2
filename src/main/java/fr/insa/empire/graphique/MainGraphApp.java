package fr.insa.empire.graphique;

import javafx.application.Application;
import javafx.scene.Scene;
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
