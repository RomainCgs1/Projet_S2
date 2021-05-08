package fr.insa.empire.graphique;

import fr.insa.empire.treillis.*;
import fr.insa.empire.utils.Controller;
import fr.insa.empire.utils.Identificateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Map;

public class MainGraphique extends BorderPane {

    private final Controller controller;
    private final Treillis treillis;
    private final MenuItem choix1;
    private final MenuItem choix2;
    private final MenuItem choix3;
    private final MenuItem choix4;
    private final MenuButton mbNoeud;
    private final MyTB mtbBarre;
    private final MyTB mtbTerrain;
    private final MyTB mtbSelection;
    private final MenuButton mbGomme;
    private final MenuItem miGommeNoeud;
    private final MenuItem miGommeBarre;
    private final MenuItem miGommeTriangleTerrain;
    private final MenuItem miGommeAll;
    private final MyB mbLancerCalculs;
    private final MyB mbReglages;
    private final MyB mbTypeBarre;
    private final MyB bPosition;
    private final HBox hbConstruction;
    private final VBox vbUp;
    private final HBox hbIcones;
    private final HBox hbPosition;
    private final MyCanvas canvas;
    private int etatNoeud;
    private double px;
    private double py;
    private int nbDeClick;

    private final MyMenuBar menuBar;

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

    public MenuButton getMbNoeud() {
        return mbNoeud;
    }

    public MyCanvas getCanvas() {
        return canvas;
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

    public MenuButton getMbGomme() {
        return mbGomme;
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

    public MyB getbPosition() {
        return bPosition;
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
        this.canvas = new MyCanvas(this);
        canvas.setOnMouseClicked(
                canvasMouseEvent -> {

                    Controller controller = this.controller;
                    controller.canvasClicked(canvasMouseEvent);
                }
        );

        this.canvas.setOnMouseMoved(
                action -> {
                    controller.canvasOver(action);
                }
        );

        this.setCenter(canvas);

        //Set up du splitMenuButton
        this.mbNoeud = new MenuButton();
        this.mbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui simple");
        this.choix3 = new MenuItem("Noeud Appui double");
        this.choix4 = new MenuItem("Noeud Appui encastré");
        this.mbNoeud.getItems().addAll(choix1, choix2,choix3, choix4);

        //menuBar
        this.menuBar = new MyMenuBar(this);

        this.mtbBarre = new MyTB("Barre");
        this.mtbTerrain = new MyTB("Terrain");
        this.mbLancerCalculs = new MyB("Lancer les calculs");
        this.mbTypeBarre = new MyB ("Type de Barre");
        this.mbReglages = new MyB("Réglages");
        mbGomme = new MenuButton("Gomme");
        this.hbConstruction = new HBox(this.mbNoeud, this.mtbBarre);
        this.hbIcones = new HBox(this.hbConstruction, separator, this.mtbTerrain, separator1,this.mbGomme, this.mtbSelection, separator2,this.mbTypeBarre, this.mbLancerCalculs);
        this.hbIcones.setSpacing(10);
        this.vbUp = new VBox(this.menuBar, this.hbIcones);
        this.setTop(this.vbUp);
        vbUp.setSpacing(5);
        this.hbConstruction.setSpacing(5);

        bPosition = new MyB("0 ; 0");
        this.hbPosition = new HBox(bPosition);
        this.hbPosition.setAlignment(Pos.CENTER_RIGHT);
        this.setBottom(hbPosition);
        this.bPosition.setOnAction(
                action -> {
                    controller.changeEtat(80);
                }
        );

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
                controller.changeEtat(120);
            }
        });

        //
        choix2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.changeEtat(110);
            }
        });

        choix3.setOnAction(
                action -> {
                    controller.changeEtat(130);
                }
        );

        choix4.setDisable(true);
        choix4.setOnAction(
                action -> {
                    controller.changeEtat(140);
                }
        );

        //action de Terrain
        this.mtbTerrain.setOnAction(
                action -> {
                    if(mtbTerrain.isSelected())
                    {
                        controller.changeEtat(30);
                    }
                    else
                    {
                        controller.changeEtat(0);
                    }
                }
        );

        //action de Gomme
        miGommeNoeud = new MenuItem("Gomme : noeuds");
        miGommeBarre = new MenuItem("Gomme : Barre");
        miGommeTriangleTerrain = new MenuItem("Gomme : terrain");
        miGommeAll = new MenuItem("Tout effacer");
        mbGomme.getItems().addAll(miGommeNoeud, miGommeBarre, miGommeTriangleTerrain, miGommeAll);

        miGommeNoeud.setOnAction(
                action -> {
                    controller.changeEtat(50);
                }
        );

        this.miGommeTriangleTerrain.setOnAction(
                action -> {
                    controller.changeEtat(51);
                }
        );

        this.miGommeBarre.setOnAction(
                action -> {
                    controller.changeEtat(52);
                }
        );

        this.miGommeAll.setOnAction(
                action -> {
                    controller.changeEtat(53);
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
                    for (int i = 0; i < this.treillis.getTreilliTypeBarre().size(); i++) {
                        System.out.println("Type \n"+this.treillis.getTreilliTypeBarre().get(i).toString());
                    }
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

    public void recontruction()
    {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        for (Map.Entry mapentry : this.treillis.identificateur.getKetToObject().entrySet())
        {
            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            if(val.getClass() == Appui_double.class)
            {
                graphicsContext.setStroke(Color.SILVER);
                graphicsContext.strokeOval(((Appui_double) val).getPx() - 5, ((Appui_double) val).getPy() - 5, 10, 10);
            }
            else if(val.getClass() == Appui_simple.class)
            {
                graphicsContext.setStroke(Color.ORANGE);
                graphicsContext.strokeOval(((Appui_simple) val).getPx() - 5, ((Appui_simple) val).getPy() - 5, 10, 10);
            }
            else if(val.getClass() == Noeud_appui.class)
            {
                //normalement on sera passé par appui double ou simple
            }
            else if(val.getClass() == Noeud_simple.class)
            {
                graphicsContext.setStroke(Color.RED);
                graphicsContext.strokeOval(((Noeud_simple) val).getPx() - 5, ((Noeud_simple) val).getPy() - 5, 10, 10);
            }
            else if(val.getClass() == Barre.class)
            {
                graphicsContext.setStroke(Color.BLUE);
                graphicsContext.strokeLine(((Barre) val).getNoeudDebut().getPx(), ((Barre) val).getNoeudDebut().getPy(), ((Barre) val).getNoeudFin().getPx(), ((Barre) val).getNoeudFin().getPy());
            }
            else if(val.getClass() == Triangle_terrain.class)
            {
                graphicsContext.setStroke(Color.BLACK);
                graphicsContext.strokeLine(((Triangle_terrain) val).getSegment1().getPointDebut().getPx(), ((Triangle_terrain) val).getSegment1().getPointDebut().getPy(), ((Triangle_terrain) val).getSegment1().getPointFin().getPx(), ((Triangle_terrain) val).getSegment1().getPointFin().getPy());
                graphicsContext.strokeLine(((Triangle_terrain) val).getSegment2().getPointDebut().getPx(), ((Triangle_terrain) val).getSegment2().getPointDebut().getPy(), ((Triangle_terrain) val).getSegment2().getPointFin().getPx(), ((Triangle_terrain) val).getSegment2().getPointFin().getPy());
                graphicsContext.strokeLine(((Triangle_terrain) val).getSegment3().getPointDebut().getPx(), ((Triangle_terrain) val).getSegment3().getPointDebut().getPy(), ((Triangle_terrain) val).getSegment3().getPointFin().getPx(), ((Triangle_terrain) val).getSegment3().getPointFin().getPy());
            }
            else if(val.getClass() == Zone_constructible.class)
            {
                graphicsContext.setStroke(Color.GREEN);
                graphicsContext.strokeLine(((Zone_constructible) val).getXmax(), ((Zone_constructible) val).getYmin(), ((Zone_constructible) val).getXmax(), ((Zone_constructible) val).getYmax());
                graphicsContext.strokeLine(((Zone_constructible) val).getXmin(), ((Zone_constructible) val).getYmin(), ((Zone_constructible) val).getXmin(), ((Zone_constructible) val).getYmax());
                graphicsContext.strokeLine(((Zone_constructible) val).getXmax(), ((Zone_constructible) val).getYmax(), ((Zone_constructible) val).getXmin(), ((Zone_constructible) val).getYmax());
                graphicsContext.strokeLine(((Zone_constructible) val).getXmax(), ((Zone_constructible) val).getYmin(), ((Zone_constructible) val).getXmin(), ((Zone_constructible) val).getYmin());
            }
        }
    }
}
