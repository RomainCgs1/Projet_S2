package fr.insa.empire.graphique;

import fr.insa.empire.treillis.*;
import fr.insa.empire.utils.Controller;
import fr.insa.empire.utils.Identificateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainGraphique extends BorderPane {

    private Controller controller;
    private Treillis treillis;
    private MenuItem choix1;
    private MenuItem choix2;
    private MenuItem choix3;
    private MenuItem choix4;
    private SplitMenuButton tbNoeud;
    private MyTB mtbBarre;
    private MyTB mtbTerrain;
    private MyTB mtbSelection;
    private MyTB mtbGomme;
    private MyB mbLancerCalculs;
    private MyB mbReglages;
    private final MyB mbTypeBarre;
    private Label lPosition;
    private HBox hbConstruction;
    private VBox vbUp;
    private HBox hbIcones;
    private HBox hbPosition;
    private Zone_constructible zone_constructible;
    private int etatNoeud;
    private double px;
    private double py;
    private int nbDeClick;

    private MyMenuBar menuBar;

    public Controller getController() {
        return controller;
    }

    public MenuItem getChoix1() {
        return choix1;
    }

    public MenuItem getChoix2() {
        return choix2;
    }

    public MenuItem getChoix3() { return choix3; }

    public MenuItem getChoix4() { return choix4; }

    public SplitMenuButton getTbNoeud() {
        return tbNoeud;
    }

    public MyTB getMtbBarre() {
        return mtbBarre;
    }

    public MyTB getMtbTerrain() {
        return mtbTerrain;
    }

    public MyTB getMtbSelection() {
        return mtbSelection;
    }

    public MyTB getMtbGomme() {
        return mtbGomme;
    }

    public MyB getMbLancerCalculs() {
        return mbLancerCalculs;
    }

    public MyB getMbReglages() {
        return mbReglages;
    }

    public MyB getMbTypeBarre() {
        return mbTypeBarre;
    }

    public HBox getHbConstruction() {
        return hbConstruction;
    }

    public VBox getVbUp() {
        return vbUp;
    }

    public HBox getHbIcones() {
        return hbIcones;
    }

    public Zone_constructible getZone_constructible() {
        return zone_constructible;
    }

    public int getEtatNoeud() {
        return etatNoeud;
    }

    public double getPx() {
        return px;
    }

    public double getPy() {
        return py;
    }

    public int getNbDeClick() {
        return nbDeClick;
    }

    public MyMenuBar getMenuBar() {
        return menuBar;
    }

    public Identificateur getIdentificateur() {
        return this.treillis.identificateur;
    }

    public Treillis getTreillis() {
        return treillis;
    }

    public Label getlPosition() {
        return lPosition;
    }

    public HBox getHbPosition() {
        return hbPosition;
    }

    public MainGraphique() throws IOException {

        //controlleur :
        this.controller = new Controller(this);

        //point pour triangle terrain
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();

        this.treillis = new Treillis();

        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        this.mtbSelection = new MyTB("Selection");

        //set du Canvas
        this.zone_constructible = new Zone_constructible();
        zone_constructible.setOnMouseClicked(
                canvasMouseEvent -> {

                    Controller controller = this.controller;
                    controller.canvasClicked(canvasMouseEvent);
                }
        );

        this.zone_constructible.setOnMouseMoved(
                action -> {
                    controller.canvasOver(action);
                }
        );

        this.setCenter(zone_constructible);

        //Set up du splitMenuButton
        this.tbNoeud = new SplitMenuButton();
        this.tbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui simple");
        this.choix3 = new MenuItem("Noeud Appui double");
        this.choix4 = new MenuItem("Noeud Appui encastré");
        this.tbNoeud.getItems().addAll(choix1, choix2,choix3, choix4);

        //menuBar
        this.menuBar = new MyMenuBar(this);

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

        lPosition = new Label("0 ; 0");
        this.hbPosition = new HBox(lPosition);
        this.hbPosition.setAlignment(Pos.CENTER_RIGHT);
        this.setBottom(hbPosition);

        //actione de barre
        this.mtbBarre.setOnAction(
                Action -> {
                    controller.changeEtat(20);
                }
        );


        //
        //Set up des actions du splitMenuButton
        choix1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.changeEtat(12);
            }
        });

        //
        choix2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.changeEtat(11);
            }
        });

        choix3.setOnAction(
                action -> {
                    controller.changeEtat(13);
                }
        );

        choix4.setDisable(true);
        choix4.setOnAction(
                action -> {
                    controller.changeEtat(14);
                }
        );

        //action de Terrain
        this.mtbTerrain.setOnAction(
                action -> {
                    controller.changeEtat(30);
                }
        );

        //action de Gomme
        this.mtbGomme.setOnAction(
                action -> {
                    if(mtbGomme.isSelected())
                    {
                        controller.changeEtat(50);
                    }
                }
        );

        //action de Selection
        this.mtbSelection.setOnAction(
                action -> {
                    controller.changeEtat(40);
                }
        );

        //action de Type de Barre
        this.mbTypeBarre.setOnAction(
                action -> {
                    controller.changeEtat(60);
                }
        );

        //action de Lancer les calculs
        this.mbLancerCalculs.setOnAction(
                action -> {
                    controller.changeEtat(70);
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
        
        //test pour force par bouton sélection
        this.mtbSelection.setOnAction(
                action -> {
                    this.treillis.testForce();
                }
        );
    }
}
