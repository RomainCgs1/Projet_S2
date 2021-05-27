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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainGraphique extends BorderPane {

    private final Controller controller;
    public final Treillis treillis;
    private final MenuItem miNoeudSimple;
    private final MenuItem miNoeudAppuiSimple;
    private final MenuItem miNoeudAppuiDouble;
    private final MenuItem miNoeudAppuiEncastre;
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
    private final MenuItem miTypeBarreInfos;
    private final MyB mbLancerCalculs;
    private final MyB mbPrix;
    private final MyB mbReglages;
    private final HBox hbConstruction;
    private final VBox vbUp;
    private final HBox hbIcones;
    private final HBox hbBottom;
    private final HBox hbZoneText;
    private final MyCanvas canvas;
    private Text text;

    //private ScrollBar scrollBar;
    private String theme;

    private final MyMenuBar menuBar;

    public Controller getController() {
        return controller;
    }

    public MenuItem getChoix1() {
        return miNoeudSimple;
    }

    public MenuItem getChoix2() {
        return miNoeudAppuiSimple;
    }

    public MenuItem getChoix3() {
        return miNoeudAppuiDouble;
    }

    public MenuItem getChoix4() {
        return miNoeudAppuiEncastre;
    }

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

    public MyB getMbPrix() {
        return mbPrix;
    }

    public MenuItem getMiTypeBarreSolo() {
        return miTypeBarreSolo;
    }

    public MenuItem getMiTypeBarreTodos() {
        return miTypeBarreTodos;
    }

    public MenuItem getMiTypeBarreCurrent() {
        return miTypeBarreCurrent;
    }

    public MenuItem getMiTypeBarreInfos() {
        return miTypeBarreInfos;
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
        Separator separator3 = new Separator(Orientation.VERTICAL);

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
        //this.scrollBar = new ScrollBar();
        //this.scrollBar.setOrientation(Orientation.VERTICAL);
        //scrollBar.setMin(0);
        //scrollBar.setMax(this.canvas.getHeight());
        //tests ... ça donne pas grand chose...
        //this.setRight(this.scrollBar);

        //Set up du splitMenuButton
        ImageView logoNoeudView = createImageView("autres/noeud.png");

        logoNoeudView.setFitWidth(40);
        logoNoeudView.setFitHeight(40);

        this.mbNoeud = new MenuButton("Noeud", logoNoeudView);
        this.mbNoeud.setText("Noeud");
        this.miNoeudSimple = new MenuItem("Noeud simple");
        this.miNoeudAppuiSimple = new MenuItem("Noeud Appui simple");
        this.miNoeudAppuiDouble = new MenuItem("Noeud Appui double");
        this.miNoeudAppuiEncastre = new MenuItem("Noeud Appui encastré");
        this.mbNoeud.getItems().addAll(miNoeudSimple, miNoeudAppuiSimple, miNoeudAppuiDouble, miNoeudAppuiEncastre);

        //menuBar
        this.menuBar = new MyMenuBar(this, controller);
        ImageView logoBarreView = createImageView("autres/barre.png");

        logoBarreView.setFitHeight(40);
        logoBarreView.setFitWidth(40);

        this.mtbBarre = new MyTB("Barre", logoBarreView);

        ImageView logoTerrainView = createImageView("autres/triangleTerrain.png");
        logoTerrainView.setFitWidth(40);
        logoTerrainView.setFitHeight(40);
        this.mtbTerrain = new MyTB("Segment terrain", logoTerrainView);

        ImageView logoCalcView = createImageView("autres/calculatriceSansFond.png");

        logoCalcView.setFitWidth(30);
        logoCalcView.setFitHeight(40);

        this.mbLancerCalculs = new MyB("Lancer les calculs", logoCalcView);
        ImageView logoTypeDeBarreView = createImageView("autres/typeDeBarre.png");
        logoTypeDeBarreView.setFitHeight(40);
        logoTypeDeBarreView.setFitWidth(40);
        this.mbTypeDeBarre = new MenuButton("Type de Barre", logoTypeDeBarreView);
        this.mbReglages = new MyB("Réglages");

        ImageView logoPrixView = createImageView("autres/prix.png");

        this.mbPrix = new MyB("Prix", logoPrixView);

        logoPrixView.setFitWidth(40);
        logoPrixView.setFitHeight(40);

        ImageView logoGommeView = createImageView("autres/Gomme.png");

        logoGommeView.setFitWidth(40);
        logoGommeView.setFitHeight(40);

        this.mbGomme = new MenuButton("Gomme", logoGommeView);

        this.hbConstruction = new HBox(this.mbNoeud, this.mtbBarre);
        this.hbIcones = new HBox(this.hbConstruction, separator, this.mtbTerrain, separator1, this.mbGomme, separator2, this.mbTypeDeBarre, this.mbLancerCalculs, separator3, this.mbPrix);
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
        miNoeudSimple.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.changeEtat(120);
            }
        });

        //
        miNoeudAppuiSimple.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.changeEtat(110);
            }
        });

        miNoeudAppuiDouble.setOnAction(
                action -> {
                    controller.changeEtat(130);
                }
        );

        miNoeudAppuiEncastre.setDisable(true);
        miNoeudAppuiEncastre.setOnAction(
                action -> {
                    controller.changeEtat(140);
                }
        );

        //action de Terrain
        this.mtbTerrain.setOnAction(
                action -> {
                    if (mtbTerrain.isSelected()) {
                        controller.changeEtat(30);
                    } else {
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
        this.miTypeBarreInfos = new MenuItem("Informations Types");
        this.mbTypeDeBarre.getItems().addAll(miTypeBarreCurrent, miTypeBarreSolo, miTypeBarreTodos, miTypeBarreInfos);
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
        this.miTypeBarreInfos.setOnAction(
                action -> {
                    controller.changeEtat(603);
                }
        );

        //action de Lancer les calculs
        this.mbLancerCalculs.setOnAction(
                action -> {
                    controller.changeEtat(70);
                }
        );

        //action de prix
        this.mbPrix.setOnAction(
                Action -> {
                    controller.changeEtat(80);
                }
        );

    }

    public void recontruction() {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();

        graphicsContext.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        for (Map.Entry mapentry : this.treillis.identificateur.getKetToObject().entrySet()) {
            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            if(val.getClass() == Appui_double.class)
            {
                ((Appui_double) val).draw(graphicsContext);
                graphicsContext.setStroke(Color.BLUE);
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

    public String getTheme() {
        //à coder
        return this.theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    private ImageView createImageView(String chemin) {
        InputStream inputLogo = this.getClass().getResourceAsStream(chemin);
        Image logo = new Image(inputLogo);
        return new ImageView(logo);
    }
}
