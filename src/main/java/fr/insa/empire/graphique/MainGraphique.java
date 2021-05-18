package fr.insa.empire.graphique;

import com.sun.javafx.scene.control.skin.FXVK;
import fr.insa.empire.treillis.*;
import fr.insa.empire.utils.Controller;
import fr.insa.empire.utils.Identificateur;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
    private final MenuButton mbGomme;
    private final MenuItem miGommeNoeud;
    private final MenuItem miGommeBarre;
    private final MenuItem miGommeTriangleTerrain;
    private final MenuItem miGommeAll;
    private final MenuButton mbTypeDeBarre;
    private final MenuItem miTypeBarreSolo;
    private final MenuItem miTypeBarreTodos;
    private final MenuItem miTypeBarreCurrent;
    private final MyB mbLancerCalculs;
    private final MyB mbReglages;
    private final HBox hbConstruction;
    private final VBox vbUp;
    private final HBox hbIcones;
    private final HBox hbBottom;
    private final HBox hbZoneText;
    private final MyCanvas canvas;
    private Text text;

    private ScrollBar scrollBar;

    private String theme;

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

    public MenuButton getMbGomme() {
        return mbGomme;
    }

    public MyB getMbLancerCalculs() {
        return mbLancerCalculs;
    }

    public MyB getMbReglages() {
        return mbReglages;
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

    public MyMenuBar getMenuBar() {
        return menuBar;
    }

    public Identificateur getIdentificateur() {
        return this.treillis.identificateur;
    }

    public Treillis getTreillis() {
        return treillis;
    }

    public HBox getHbPosition() {
        return hbBottom;
    }

    public MenuButton getMbTypeDeBarre() {
        return mbTypeDeBarre;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    
    public MainGraphique() throws IOException {

        //controlleur :
        this.controller = new Controller(this);

        this.theme = "clair";

        //point pour triangle terrain
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();

        this.treillis = new Treillis();

        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);


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
        this.scrollBar = new ScrollBar();
        this.scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(0);
        scrollBar.setMax(this.canvas.getHeight());
        //tests ... ça donne pas grand chose...
        this.setRight(this.scrollBar);

        //Set up du splitMenuButton
        this.mbNoeud = new MenuButton();
        this.mbNoeud.setText("Noeud");
        this.choix1 = new MenuItem("Noeud simple");
        this.choix2 = new MenuItem("Noeud Appui simple");
        this.choix3 = new MenuItem("Noeud Appui double");
        this.choix4 = new MenuItem("Noeud Appui encastré");
        this.mbNoeud.getItems().addAll(choix1, choix2,choix3, choix4);

        //menuBar
        this.menuBar = new MyMenuBar(this, controller);

        this.mtbBarre = new MyTB("Barre");
        this.mtbTerrain = new MyTB("Terrain");
        this.mbLancerCalculs = new MyB("Lancer les calculs");
        this.mbTypeDeBarre = new MenuButton("Type de Barre");
        this.mbReglages = new MyB("Réglages");
        this.mbGomme = new MenuButton("Gomme");
        this.hbConstruction = new HBox(this.mbNoeud, this.mtbBarre);
        this.hbIcones = new HBox(this.hbConstruction, separator, this.mtbTerrain, separator1,this.mbGomme, separator2,this.mbTypeDeBarre, this.mbLancerCalculs);
        this.hbIcones.setSpacing(10);
        this.vbUp = new VBox(this.menuBar, this.hbIcones);
        this.setTop(this.vbUp);
        this.vbUp.setSpacing(5);
        this.hbConstruction.setSpacing(5);


        

        this.text = new Text();
        this.text.setFill(Color.BLACK);
        this.hbZoneText = new HBox(text);
        this.hbZoneText.setAlignment(Pos.CENTER_LEFT);
        
        this.hbBottom = new HBox(hbZoneText);
        this.hbBottom.setAlignment(Pos.CENTER_LEFT);
        this.setBottom(hbBottom);


        controller.changeEtat(-10);

        //action de barre
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

        //action de Type de Barre
        //action de Gomme
        this.miTypeBarreCurrent = new MenuItem("Type actif");
        this.miTypeBarreSolo = new MenuItem("Type : barre seule");
        this.miTypeBarreTodos = new MenuItem("Type : Tout modifier");
        this.mbTypeDeBarre.getItems().addAll(miTypeBarreCurrent,miTypeBarreSolo,miTypeBarreTodos);
        this.miTypeBarreCurrent.setOnAction(
                action -> {
                    controller.changeEtat(600);
                }
        );
        this.miTypeBarreSolo.setOnAction(
                action -> {
                    controller.changeEtat(601);
                }
        );
        this.miTypeBarreTodos.setOnAction(
                action -> {
                    controller.changeEtat(602);
                }
        );

        //action de Lancer les calculs
        this.mbLancerCalculs.setOnAction(
                action -> {
                    controller.changeEtat(70);
                }
        );

    }

    public void recontruction()
    {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();

        graphicsContext.clearRect(0,0, this.canvas.getWidth(), this.canvas.getHeight());

        for (Map.Entry mapentry : this.treillis.identificateur.getKetToObject().entrySet())
        {
            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            if(val.getClass() == Appui_double.class)
            {
                ((Appui_double) val).draw(graphicsContext);
                graphicsContext.setStroke(Color.SILVER);
                graphicsContext.strokeOval(((Appui_double) val).getPx() - 5, ((Appui_double) val).getPy() - 5, 10, 10);
            }
            else if(val.getClass() == Appui_simple.class)
            {
                ((Appui_simple) val).draw(graphicsContext);
            }
            else if(val.getClass() == Noeud_appui.class)
            {
                //normalement on sera passé par appui double ou simple
            }
            else if(val.getClass() == Noeud_simple.class)
            {
                ((Noeud_simple) val).draw(graphicsContext);
            }
            else if(val.getClass() == Barre.class)
            {
                ((Barre) val).draw(graphicsContext);
            }
            else if(val.getClass() == Triangle_terrain.class)
            {
                ((Triangle_terrain) val).draw(graphicsContext);
            }
            else if(val.getClass() == Zone_constructible.class)
            {
                ((Zone_constructible) val).draw(graphicsContext);
            }
        }

        graphicsContext.setStroke(Color.GRAY);
        graphicsContext.strokeRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

    }

    public String getTheme()
    {
        //à coder
        return this.theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }
}
