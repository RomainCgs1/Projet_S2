package fr.insa.empire.graphique;

import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;

public class MyTB extends ToggleButton {

    public MyTB(String name)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
    }

}
