package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Noeud_simple;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class MainGraphique extends BorderPane {

    private SplitMenuButton tbNoeud;
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

        //set du Canvas
        Canvas canvas = new Canvas();
        canvas.setHeight(512);
        canvas.setWidth(512);

        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        graphicsContext2D.setFill(Color.BLUE);
        graphicsContext2D.fillRect(100, 100, 200, 200);

        graphicsContext2D.setStroke(Color.ORANGE);
        graphicsContext2D.strokeRect(100,100,200,200);

        this.setCenter(canvas);

        //Set up du splitMenuButton
        this.tbNoeud = new SplitMenuButton();
        this.tbNoeud.setText("Noeud");
        MenuItem choix1 = new MenuItem("Noeud simple");
        MenuItem choix2 = new MenuItem("Noeud Appui");
        this.tbNoeud.getItems().addAll(choix1, choix2);
        
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

        //Set up des actions du splitMenuButton
        choix1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               tbNoeud.setText("Noeud Simple");
                Noeud_simple.testCreationPt();
            }
        });
        
        choix2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               tbNoeud.setText("Noeud Appui");
            }
        });

        //tests action souris
        this.mtbSelection.setOnMouseEntered(
                mouseEvent -> {
                    mtbSelection.setTextFill(Color.RED);
                }
        );

        //tests action souris
        this.mtbSelection.setOnMouseExited(
                mouseEvent -> {
                    mtbSelection.setTextFill(Color.BLACK);
                }
        );


    }
}
