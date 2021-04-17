package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Noeud_simple;
//import fr.insa.empire.utils.Identificateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainGraphique extends BorderPane {

    //private Identificateur id;

    private MenuItem choix1;
    private MenuItem choix2;
    private SplitMenuButton tbNoeud;
    private MyTB mtbBarre;
    private MyTB mtbTerrain;
    private MyTB mtbSelection;
    private MyTB mtbGomme;
    private MyB mbLancerCalculs;
    private MyB mbReglages;
    private final MyB mbTypeBarre;
    private HBox hbConstruction;
    private VBox vbUp;
    private HBox hbIcones;
    private ResizableCanvas canvas;
    private int etatNoeud;
    private double PX;
    private double PY;

    private MyMenuBar menuBar;

    public MainGraphique() throws IOException {

        //this.id = new Identificateur();

        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        this.mtbSelection = new MyTB("Selection");

        //set du Canvas
        this.canvas = new ResizableCanvas();
        canvas.setOnMouseClicked(
                canvasMouseEvent -> {
                    if (canvasMouseEvent.getButton() == MouseButton.PRIMARY) {
                        PX = canvasMouseEvent.getX();
                        PY = canvasMouseEvent.getY();
                        System.out.println("Canvas cliqué en " + PX + " " + PY);
                        if (etatNoeud == 1) {
                            Noeud_simple noeud_simple = new Noeud_simple(PX, PY);
                            //noeud_simple.setID(id.getOrSetKey(noeud_simple));
                            System.out.println(noeud_simple.getID());
                            canvas.getGraphicsContext2D().setStroke(Color.RED);
                            canvas.getGraphicsContext2D().strokeOval(PX - 5, PY - 5, 10, 10);
                        }
                    }

                }
        );

        this.setCenter(canvas);

        //Set up du splitMenuButton
        this.tbNoeud = new SplitMenuButton();
        this.tbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui");
        this.tbNoeud.getItems().addAll(choix1, choix2);

        //menuBar
        this.menuBar = new MyMenuBar();

        this.mtbBarre = new MyTB("Barre");
        this.mtbTerrain = new MyTB("Terrain");
        this.mtbGomme = new MyTB("Gomme");
        this.mbLancerCalculs = new MyB("Lancer les calculs");
        this.mbTypeBarre = new MyB ("Type de Barre");
        this.mbReglages = new MyB("Réglages");
        this.hbConstruction = new HBox(this.tbNoeud, this.mtbBarre);
        this.hbIcones = new HBox(this.hbConstruction, separator, this.mtbTerrain, separator1,this.mtbGomme, this.mtbSelection, separator2,this.mbTypeBarre, this.mbLancerCalculs);
        this.hbIcones.setSpacing(10);
        this.vbUp = new VBox(this.menuBar, this.hbIcones);
        this.setTop(this.vbUp);
        vbUp.setSpacing(5);
        this.hbConstruction.setSpacing(5);

        //actione de barre
        this.mtbBarre.setOnAction(
                Action -> {

                    //reset
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                    mtbTerrain.setSelected(false);
                    mtbGomme.setSelected(false);
                    mtbSelection.setSelected(false);
                }
        );


        //actione de barre
        this.mtbBarre.setOnAction(
                Action -> {
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                }
        );


        //Set up des actions du splitMenuButton
        choix1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tbNoeud.setText("Noeud Simple");
                etatNoeud = 1;

                //reset
                mtbTerrain.setSelected(false);
                mtbBarre.setSelected(false);
                mtbGomme.setSelected(false);
                mtbSelection.setSelected(false);
            }
        });

        choix2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tbNoeud.setText("Noeud Appui");
                etatNoeud = 2;

                //reset
                mtbTerrain.setSelected(false);
                mtbBarre.setSelected(false);
                mtbGomme.setSelected(false);
                mtbSelection.setSelected(false);
            }
        });

        //action de Terrain
        this.mtbTerrain.setOnAction(
                action -> {

                    //reset
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                    mtbBarre.setSelected(false);
                    mtbGomme.setSelected(false);
                    mtbSelection.setSelected(false);
                }
        );

        //action de Gomme
        this.mtbGomme.setOnAction(
                action -> {

                    //reset
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                    mtbTerrain.setSelected(false);
                    mtbBarre.setSelected(false);
                    mtbSelection.setSelected(false);
                }
        );

        //action de Selection
        this.mtbSelection.setOnAction(
                action -> {

                    //reset
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                    mtbTerrain.setSelected(false);
                    mtbGomme.setSelected(false);
                    mtbBarre.setSelected(false);
                }
        );

        //action de Type de Barre
        this.mbTypeBarre.setOnAction(
                action -> {

                    //reset
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                    mtbTerrain.setSelected(false);
                    mtbGomme.setSelected(false);
                    mtbSelection.setSelected(false);
                }
        );

        //action de Lancer les calculs
        this.mbLancerCalculs.setOnAction(
                action -> {

                    //reset
                    etatNoeud = 0;
                    tbNoeud.setText("Noeud");
                    mtbBarre.setSelected(false);
                    mtbTerrain.setSelected(false);
                    mtbGomme.setSelected(false);
                    mtbSelection.setSelected(false);
                }
        );

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
