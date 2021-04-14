package fr.insa.empire.graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainGraphique extends BorderPane {

    private MyTB tbNoeud;
    private MyTB mtbBarre;
    private MyTB mtbTerrain;
    private MyTB mtbSelection;
    private MyTB test;
    private MyB mbLancerCalculs;
    private MyB mbReglages;
    private HBox hbConstruction;
    //private VBox vbConstruction;
    private HBox hbIcones;
    //private VBox vbIcones;

    public MainGraphique() throws IOException {
        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        this.mtbSelection = new MyTB("Selection");
        this.tbNoeud = new MyTB("Noeud");
        this.mtbBarre = new MyTB("Barre");
        this.mtbTerrain = new MyTB("Terrain");
        this.mbLancerCalculs = new MyB("Lancer les calculs");
        this.mbReglages = new MyB("Réglages");
        this.hbConstruction = new HBox(this.tbNoeud, this.mtbBarre);
        //this.vbConstruction = new VBox(this.tbNoeud, this.mtbBarre);
        this.hbIcones = new HBox(this.hbConstruction, separator, this.mtbTerrain, separator1, this.mtbSelection, separator2, this.mbLancerCalculs);
        //this.vbIcones = new VBox(this.vbConstruction, this.mtbTerrain, this.mtbSelection, this.mbLancerCalculs);
        this.hbIcones.setSpacing(10);
        //this.vbIcones.setSpacing(20);
        this.setTop(this.hbIcones);

        this.tbNoeud.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tbNoeud.isSelected()) {
                    tbNoeud.setText("Prout");
                } else {
                    tbNoeud.setText("Noeud");
                }
            }
        });

        //création noeud avec bouton Noeud Simple


    }
}
