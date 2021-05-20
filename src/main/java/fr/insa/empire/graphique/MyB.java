package fr.insa.empire.graphique;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MyB extends Button {

    public MyB(String name)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
    }

    public MyB(String name, Node node)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
        this.setGraphic(node);
    }

}
