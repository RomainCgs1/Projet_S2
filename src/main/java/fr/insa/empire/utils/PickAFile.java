package fr.insa.empire.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class PickAFile {

    public static String main()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le fichier à ouvir");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        System.out.println(selectedFile);
        return selectedFile.getAbsolutePath();
    }
}
