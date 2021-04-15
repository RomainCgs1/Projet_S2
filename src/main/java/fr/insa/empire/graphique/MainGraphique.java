package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Noeud_simple;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainGraphique extends BorderPane {

    private MenuItem choix1;
    private MenuItem choix2;
    private SplitMenuButton tbNoeud;
    private MyTB mtbBarre;
    private MyTB mtbTerrain;
    private MyTB mtbSelection;
    private MyTB mtbGomme;
    private MyTB test;
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

    private MenuBar menuBar;
    private Menu mFichier;
    private Menu mParametre;
    private Menu mAide;

    public MainGraphique() throws IOException {
        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        this.mtbSelection = new MyTB("Selection");

        //set du Canvas
        this.canvas = new ResizableCanvas();
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


        this.setCenter(canvas);

        //Set up du splitMenuButton
        this.tbNoeud = new SplitMenuButton();
        this.tbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui");
        this.tbNoeud.getItems().addAll(choix1, choix2);

        //menuBar
        this.menuBar = new MenuBar();
        this.mFichier = new Menu("Fichier");
        this.mParametre = new Menu("Paramètres");
        this.mAide = new Menu("Aide");

        menuBar.getMenus().addAll(this.mFichier, this.mParametre, this.mAide);

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
