package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Noeud_simple;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainGraphique extends BorderPane {

    private MenuItem choix1;
    private MenuItem choix2;
    private final SplitMenuButton tbNoeud;
    private final MyTB mtbBarre;
    private final MyTB mtbTerrain;
    private final MyTB mtbSelection;
    private MyTB test;
    private final MyB mbLancerCalculs;
    private final MyB mbReglages;
    private final HBox hbConstruction;
    //private VBox vbConstruction;
    private final HBox hbIcones;
    //private VBox vbIcones;
    private final Canvas canvas;
    private int etatNoeud;
    private double PX;
    private double PY;

    public MainGraphique() throws IOException {
        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        this.mtbSelection = new MyTB("Selection");

        //set du Canvas
        this.canvas = new Canvas();
        canvas.setHeight(512);
        canvas.setWidth(512);
        canvas.setOnMouseClicked(
                canvasMouseEvent -> {
                    if (canvasMouseEvent.getButton() == MouseButton.PRIMARY) {
                        PX = canvasMouseEvent.getX();
                        PY = canvasMouseEvent.getY();
                        System.out.println("Canvas cliqué en " + PX + " " + PY);
                        if (etatNoeud == 1) {
                            Noeud_simple noeud_simple = new Noeud_simple(PX, PY);
                            canvas.getGraphicsContext2D().setStroke(Color.RED);
                            canvas.getGraphicsContext2D().strokeOval(PX - 5, PY - 5, 10, 10);
                        }
                    }

                }
        );

        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        graphicsContext2D.setStroke(Color.GRAY);
        graphicsContext2D.strokeRect(0, 0, 512, 512);

        this.setCenter(canvas);

        //Set up du splitMenuButton
        this.tbNoeud = new SplitMenuButton();
        this.tbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui");
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
                //Noeud_simple.testCreationPt();
                etatNoeud = 1;
            }
        });

        choix2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tbNoeud.setText("Noeud Appui");
                etatNoeud = 2;
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

        this.mbLancerCalculs.setOnAction(
                event -> {
                    System.out.println("test");
                }
        );

    }
}
