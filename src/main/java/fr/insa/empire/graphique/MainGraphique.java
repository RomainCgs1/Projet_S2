package fr.insa.empire.graphique;

import fr.insa.empire.treillis.*;
import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
//import fr.insa.empire.utils.Identificateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainGraphique extends BorderPane {

    private Treillis treillis;
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
    private Zone_constructible zone_constructible;
    private int etatNoeud;
    private double px;
    private double py;
    private int nbDeClick;

    private MyMenuBar menuBar;

    public MainGraphique() throws IOException {

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
                    if (canvasMouseEvent.getButton() == MouseButton.PRIMARY) {
                        px = canvasMouseEvent.getX();
                        py = canvasMouseEvent.getY();
                        System.out.println("Canvas cliqué en " + px + " " + py);
                        if (etatNoeud == 1) {
                            System.out.println("Noeud le plus proche : " + zone_constructible.getNoeud_simplePlusProche(px, py, this.treillis.identificateur));
                            Noeud_simple noeud_simple = new Noeud_simple(px, py);
                            noeud_simple.setIdentifiant(this.treillis.identificateur.getOrSetKey(noeud_simple));
                            System.out.println(noeud_simple.getID());
                            zone_constructible.getGraphicsContext2D().setStroke(Color.RED);
                            zone_constructible.getGraphicsContext2D().strokeOval(px - 5, py - 5, 10, 10);
                        }
                        else if(mtbTerrain.isSelected())
                        {
                            if(nbDeClick == 0)
                            {
                                p1.setPx(px);
                                p1.setPy(py);
                                p1.setIdentifiant(this.treillis.identificateur.getOrSetKey(p1));
                                nbDeClick++;
                                System.out.println("point 1");
                            }
                            else if(nbDeClick == 1)
                            {
                                p2.setPx(px);
                                p2.setPy(py);
                                p2.setIdentifiant(this.treillis.identificateur.getOrSetKey(p2));
                                nbDeClick++;
                                System.out.println("point 2");
                            }
                            else if(nbDeClick == 2)
                            {
                                p3.setPx(px);
                                p3.setPy(py);
                                p3.setIdentifiant(this.treillis.identificateur.getOrSetKey(p3));
                                System.out.println("point 3");
                                Triangle_terrain triangle_terrain = creationTriangleTerrain(p1, p2, p3);
                                nbDeClick = 0;
                            }
                        }
                    }

                }
        );

        this.setCenter(zone_constructible);

        //Set up du splitMenuButton
        this.tbNoeud = new SplitMenuButton();
        this.tbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui");
        this.tbNoeud.getItems().addAll(choix1, choix2);

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
                    nbDeClick = 0;

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
                    Alert dBox = new Alert(Alert.AlertType.CONFIRMATION);
                    dBox.setTitle("A confirmation dialog-box with custom actions");
                    dBox.setHeaderText("Java-Pizza : The Very Best in Town !");
                    dBox.setContentText("Choose your pizza size :");
                    ButtonType btnSmall  = new ButtonType("Small");
                    ButtonType btnMedium = new ButtonType("Medium");
                    ButtonType btnBig    = new ButtonType("Big");
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dBox.getButtonTypes().setAll(btnSmall, btnMedium, btnBig, btnCancel);
                    Optional<ButtonType> choice = dBox.showAndWait();
                    if (choice.get() == btnSmall) {
                        System.out.println("User chose Small");
                    }
                    else if (choice.get() == btnMedium) {
                        System.out.println("User chose Medium");
                    }
                    else if (choice.get() == btnBig) {
                        System.out.println("User chose Big");
                    } else {
                        System.out.println("Cancel or Close"); }

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

    public Identificateur getIdentificateur() {
        return this.treillis.identificateur;
    }

    private Triangle_terrain creationTriangleTerrain(Point p1, Point p2, Point p3)
    {
        Segment_terrain seg1 = new Segment_terrain(p1, p2);
        Segment_terrain seg2 = new Segment_terrain(p2, p3);
        Segment_terrain seg3 = new Segment_terrain(p3, p1);
        Triangle_terrain triangle_terrain = new Triangle_terrain(seg1, seg2, seg3);
        triangle_terrain.setIdentifiant(this.treillis.identificateur.getOrSetKey(triangle_terrain));

        this.zone_constructible.getGraphicsContext2D().setStroke(Color.BLACK);
        this.zone_constructible.getGraphicsContext2D().strokeLine(p1.getPx(), p1.getPy(), p2.getPx(), p2.getPy());
        this.zone_constructible.getGraphicsContext2D().strokeLine(p2.getPx(), p2.getPy(), p3.getPx(), p3.getPy());
        this.zone_constructible.getGraphicsContext2D().strokeLine(p3.getPx(), p3.getPy(), p1.getPx(), p1.getPy());

        return triangle_terrain;
    }

    public Treillis getTreillis() {
        return treillis;
    }
}
