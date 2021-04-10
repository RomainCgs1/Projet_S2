package fr.insa.empire;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGraphApp extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("TestGraph");
        Scene scene = new Scene(new MainGraphique(), 600, 500);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
