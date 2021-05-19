package fr.insa.empire.graphique;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

public class MyB extends Button {

    public MyB(String name)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
    }
}
