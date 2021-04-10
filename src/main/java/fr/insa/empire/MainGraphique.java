package fr.insa.empire;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainGraphique extends BorderPane
{
    private ToggleButton tbNoeud;
    private VBox vbIcones;

    public MainGraphique()
    {
        String font = "Helvetica Neue";
        this.tbNoeud = new ToggleButton("Noeud");
        this.tbNoeud.setFont(Font.font(font));
        this.vbIcones = new VBox(this.tbNoeud);
        this.setLeft(this.vbIcones);
    }
}
