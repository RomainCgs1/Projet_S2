package fr.insa.empire.graphique;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;

public class MyTB extends ToggleButton {

    public MyTB(String name)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
    }

    public MyTB(String name, Node node)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
        this.setGraphic(node);
    }
}
