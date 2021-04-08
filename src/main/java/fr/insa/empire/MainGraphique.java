package fr.insa.empire;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainGraphique extends BorderPane
{
    private ToggleButton tbNoeud;
    private VBox vbIcones;

    public MainGraphique()
    {
        this.tbNoeud = new ToggleButton("Noeud");
        this.vbIcones = new VBox(this.tbNoeud);
        this.setLeft(this.vbIcones);
    }
}
