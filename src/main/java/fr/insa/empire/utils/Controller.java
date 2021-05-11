package fr.insa.empire.utils;

import fr.insa.empire.graphique.MainGraphique;
import fr.insa.empire.treillis.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Optional;

public class Controller {

    private MainGraphique vue;
    private int etat;
    private int etatPrecedent;
    Point p1;
    Point p2;
    Point p3;
    Point p4;
    Noeuds noeudDebut;
    Noeuds noeudFin;

    public Controller(MainGraphique vue) {
        this.vue = vue;
        this.etat = -10;

        this.p1 = new Point();
        this.p2 = new Point();
        this.p3 = new Point();
    }

    public void changeEtat(int newState) {
        this.etatPrecedent = this.etat;
        this.etat = newState;

        switch (newState) {
            case -10: // initialisation zone_constructible
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 0: //remise à zero
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 110: //noeud appui simple
                this.vue.getMbNoeud().setText("Noeud Appui simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 120: //noeud simple
                this.vue.getMbNoeud().setText("Noeud Simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 130: //noeud appui double
                this.vue.getMbNoeud().setText("Noeud Appui double");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 140: //noeud appui encastré
                this.vue.getMbNoeud().setText("Noeud Appui encasté");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 20: //barre
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
            case 30: //terrain
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 40: //selection
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 50: //gomme noeuds
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbGomme().setText("Gomme : noeuds");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 51: //gomme terrain
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbGomme().setText("Gomme : terrain");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 52: //gomme barre
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbGomme().setText("Gomme : barre");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 53: //gomme : tout effacer
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                eraseAll();
                break;
            case 54:
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                recommencer();
                changeEtat(-10);
                break;
            case 70:
                //reset
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbSelection().setSelected(false);
                break;

            case 80: //on donne la position manuellement
                choixPositionManuelle();
                changeEtat(etatPrecedent);
                break;
        }

    }

    public void canvasClicked(MouseEvent E) {
        double px = E.getX();
        double py = E.getY();
        System.out.println("Canvas cliqué en " + px + " " + py);

        if (E.getButton() == MouseButton.PRIMARY) {
            switch (this.etat) {
                case -10:
                    p4 = new Point(px, py);
                    this.changeEtat(-11);
                    break;
                case -11:

                    double Xmax;
                    double Xmin;
                    double Ymax;
                    double Ymin;
                    if (px > p4.getPx()) {
                        Xmax = px;
                        Xmin = p4.getPx();
                    } else {
                        Xmax = p4.getPx();
                        Xmin = px;
                    }
                    if (py > p4.getPy()) {
                        Ymax = py;
                        Ymin = p4.getPy();
                    } else {
                        Ymax = p4.getPy();
                        Ymin = py;
                    }
                    Zone_constructible ZoneConstructible = new Zone_constructible(Xmin, Xmax, Ymin, Ymax);
                    this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.GREEN);
                    this.vue.getCanvas().getGraphicsContext2D().strokeLine(Xmax, Ymin, Xmax, Ymax);
                    this.vue.getCanvas().getGraphicsContext2D().strokeLine(Xmin, Ymin, Xmin, Ymax);
                    this.vue.getCanvas().getGraphicsContext2D().strokeLine(Xmax, Ymax, Xmin, Ymax);
                    this.vue.getCanvas().getGraphicsContext2D().strokeLine(Xmax, Ymin, Xmin, Ymin);

                    ZoneConstructible.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(ZoneConstructible));
                    this.changeEtat(0);
                    break;

                case 110:
                    Segment_terrain segment_terrain = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                    System.out.println("Segment terrain le plus proche : " + segment_terrain);
                    if (segment_terrain == null) {
                        //demander si on passe en mode de création segment terrain
                    } else {
                        //remplacer coord par le pt le plus proche sur le segment
                        //creer le point sur le segment
                        Point temp = segment_terrain.getPointSegmTerrPlusProche(px, py);
                        Appui_simple appuiSimple = new Appui_simple(temp.getPx(), temp.getPy(), segment_terrain);
                        appuiSimple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(appuiSimple));
                        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.ORANGE);
                        this.vue.getCanvas().getGraphicsContext2D().strokeOval(appuiSimple.getPx() - 5, appuiSimple.getPy() - 5, 10, 10);
                        System.out.println("Noeud appui simple créé !");
                    }
                    break;

                case 120:
                    Noeud_simple noeud_simple = this.vue.getCanvas().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur);
                    System.out.println("Noeud simple le plus proche : " + noeud_simple);
                    if (noeud_simple == null) {
                        noeud_simple = new Noeud_simple(px, py);
                        noeud_simple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(noeud_simple));
                        System.out.println(noeud_simple.getID());
                        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.RED);
                        this.vue.getCanvas().getGraphicsContext2D().strokeOval(px - 5, py - 5, 10, 10);
                    } else {
                        System.out.println("noeud déjà créé : " + noeud_simple);
                    }
                    break;

                case 130:
                    Noeuds noeuds = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if (noeuds == null) {
                        Segment_terrain segment_terrain1 = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                        System.out.println("Segment terrain le plus proche : " + segment_terrain1);
                        if (segment_terrain1 == null) {
                            //demander si on passe en mode de création segment terrain
                        } else {
                            //remplacer coord par le pt le plus proche sur le segment
                            //creer le point sur le segment
                            Point temp = segment_terrain1.getPointSegmTerrPlusProche(px, py);
                            System.out.println("Point temporaire : " + temp);
                            Appui_double appuiDouble = new Appui_double(temp.getPx(), temp.getPy(), segment_terrain1);
                            appuiDouble.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(appuiDouble));
                            System.out.println("test : " + appuiDouble);
                            this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.SILVER);
                            this.vue.getCanvas().getGraphicsContext2D().strokeOval(appuiDouble.getPx() - 5, appuiDouble.getPy() - 5, 10, 10);
                            System.out.println("Noeud appui double créé !");
                        }
                    } else {
                        System.out.println("Il y a déjà un noeud ici");
                    }
                    break;
                case 20:
                    noeudDebut = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if (noeudDebut == null) {
                        this.changeEtat(120);
                        this.canvasClicked(E);
                        this.changeEtat(20);
                        this.canvasClicked(E);
                    }
                    this.changeEtat(21);
                    break;

                case 21:
                    noeudFin = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if (noeudFin == null) {
                        this.changeEtat(120);
                        this.canvasClicked(E);
                        this.changeEtat(21);
                        this.canvasClicked(E);
                    } else {
                        creationBarre(this.noeudDebut, this.noeudFin);
                    }
                    this.changeEtat(20);
                    break;

                case 30:
                    p1 = new Point(px, py);
                    //this.p1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p1));
                    changeEtat(31);
                    System.out.println("point 1");
                    break;

                case 31:
                    p2 = new Point(px, py);
                    //this.p2.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p2));
                    changeEtat(32);
                    System.out.println("point 2");
                    break;

                case 32:
                    p3 = new Point(px, py);
                    //this.p3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p3));
                    System.out.println("point 3");
                    creationTriangleTerrain(this.p1, this.p2, this.p3);

                    System.out.println(this.p1 + "\n"
                            + this.p2 + "\n"
                            + this.p3);

                    changeEtat(30);
                    break;

                case 50:
                    Noeuds noeud = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if (noeud != null) {
                        int key = this.vue.getTreillis().identificateur.getObjectToKey().get(noeud);
                        this.vue.getTreillis().identificateur.getObjectToKey().remove(noeud);
                        this.vue.getTreillis().identificateur.getKetToObject().remove(key);
                        this.vue.recontruction();
                    }

                    break;
                case 51:
                    Segment_terrain segmentTerrain = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if (segmentTerrain != null) {
                        clearTriangleTerrain(segmentTerrain);
                        this.vue.recontruction();
                    }
                    break;

                case 70:
                     //On sélectionne le noeud souhaité
                    Noeud_simple noeudSimpleChoisit = this.vue.getCanvas().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur);
                    while (noeudSimpleChoisit == null){
                        System.out.println("Recommencez");
                        noeudSimpleChoisit = this.vue.getCanvas().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur);
                    }
                    System.out.println("Noeud simple le plus proche : " + noeudSimpleChoisit);
                    
                    //On demande la valeur de la composante sur X de la force
                    TextInputDialog boiteDiagPX = new TextInputDialog();
                    boiteDiagPX.setTitle("Ajout de la force");
                    boiteDiagPX.setHeaderText("Entrez la composante sur X de la force");
                    boiteDiagPX.setContentText("Composante sur X :");
                    Optional<String> textPX = boiteDiagPX.showAndWait();
                    double pxForce = Double.parseDouble(textPX.get());
                    
                    //On demande la valeur de la composante sur Y de la force
                    TextInputDialog boiteDiagPY = new TextInputDialog();
                    boiteDiagPY.setTitle("Ajout de la force");
                    boiteDiagPY.setHeaderText("Entrez la composante sur Y de la force");
                    boiteDiagPY.setContentText("Composante sur Y :");
                    Optional<String> textPY = boiteDiagPY.showAndWait();
                    double pyForce = Double.parseDouble(textPY.get());
                    
                    //On crée la force ajoutée
                    Force forceAjoutee = new Force (noeudSimpleChoisit, pxForce, pyForce);
                    
                    //Calcul
                    System.out.println("Lancement des calculs");
                    System.out.println("Calculs en cours...");
                    
            }
        }
    }

    public void canvasOver(MouseEvent E) {
        double px = E.getX();
        double py = E.getY();
        this.vue.getbPosition().setText(px + " ; " + py);
    }

    private void creationTriangleTerrain(Point p1, Point p2, Point p3) {
        Segment_terrain seg1 = new Segment_terrain(p1, p2);
        seg1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(seg1));
        Segment_terrain seg2 = new Segment_terrain(p2, p3);
        seg2.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(seg2));
        Segment_terrain seg3 = new Segment_terrain(p3, p1);
        seg3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(seg3));

        Triangle_terrain triangle_terrain = new Triangle_terrain(seg1, seg2, seg3);
        triangle_terrain.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(triangle_terrain));
        seg1.setAppartient(triangle_terrain);
        seg2.setAppartient(triangle_terrain);
        seg3.setAppartient(triangle_terrain);

        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.BLACK);
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p1.getPx(), p1.getPy(), p2.getPx(), p2.getPy());
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p2.getPx(), p2.getPy(), p3.getPx(), p3.getPy());
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p3.getPx(), p3.getPy(), p1.getPx(), p1.getPy());

        System.out.println("Triangle n°" + triangle_terrain.getIdentifiant() + " a été créé.");
    }

    private void clearTriangleTerrain(Segment_terrain segmentTerrain) {
        int key = this.vue.getTreillis().identificateur.getObjectToKey().get(segmentTerrain);
        this.vue.getTreillis().identificateur.getObjectToKey().remove(segmentTerrain);
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);

        Triangle_terrain triangle_terrain = segmentTerrain.getAppartient();
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain.getSegment1());
        this.vue.getTreillis().identificateur.getKetToObject().remove(triangle_terrain.getSegment1().getIdentifiant());

        key = this.vue.getTreillis().identificateur.getObjectToKey().get(triangle_terrain.getSegment2());
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain.getSegment2());
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);

        key = this.vue.getTreillis().identificateur.getObjectToKey().get(triangle_terrain.getSegment3());
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain.getSegment3());
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);

        key = this.vue.getTreillis().identificateur.getObjectToKey().get(triangle_terrain);
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain);
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);
    }

    private void creationBarre(Noeuds noeudDebut, Noeuds noeudFin) {

        Barre barre = new Barre(noeudDebut, noeudFin);
        barre.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(barre));

        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.BLUE);
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(noeudDebut.getPx(), noeudDebut.getPy(), noeudFin.getPx(), noeudFin.getPy());

        System.out.println("barre n°" + barre.getIdentifiant() + " a été créé.");
    }

    private void eraseAll() {
        Zone_constructible zone_constructible = (Zone_constructible) this.vue.getIdentificateur().getKetToObject().get(0);
        this.vue.getIdentificateur().clear();
        this.vue.getCanvas().getGraphicsContext2D().clearRect(0, 0, this.vue.getCanvas().getWidth(), this.vue.getCanvas().getHeight());
        this.vue.getIdentificateur().getOrSetKey(zone_constructible);
    }

    private void recommencer() {
        this.vue.getIdentificateur().clear();
        this.vue.getCanvas().getGraphicsContext2D().clearRect(0, 0, this.vue.getCanvas().getWidth(), this.vue.getCanvas().getHeight());
    }

    private Point choixPositionManuelle() {
        showPopupChoixPos();
        return null;
    }

    private Point showPopupChoixPos() {
        /*TextInputDialog inDialog = new TextInputDialog("Choix manuel de position");
        inDialog.setTitle("Veuillez saisir la position de votre point");
        inDialog.setHeaderText(null);
        inDialog.setContentText("px :");
        Optional<String> textIn = inDialog.showAndWait();
        inDialog.setContentText("py :");
        Optional<String> textIn2 = inDialog.showAndWait();
        //Renvoie un boolean true si OK et ferme si false
        if (textIn.isPresent()) {
            String fichier =   "/" + textIn.get() + ".txt";
        }*/

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choix manuel de position");
        dialog.setHeaderText("Veuillez saisir la position de votre point");

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
        // Set the button types.
        ButtonType validationButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validationButtonType, ButtonType.CANCEL);

        // Create the tfPYX and tfPY labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tfPX = new TextField();
        String[] positon = this.vue.getbPosition().getText().split(" ");
        tfPX.setPromptText(positon[0]);
        TextField tfPY = new TextField();
        tfPY.setPromptText(positon[2]);

        grid.add(new Label("Px:"), 0, 0);
        grid.add(tfPX, 1, 0);
        grid.add(new Label("Py:"), 0, 1);
        grid.add(tfPY, 1, 1);

        // Enable/Disable login button depending on whether a tfPYX was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(validationButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        tfPX.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the tfPYX field by default.
        Platform.runLater(() -> tfPX.requestFocus());

        // Convert the result to a tfPYX-tfPY-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validationButtonType) {
                return new Pair<>(tfPX.getText(), tfPY.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(tfPYXtfPY -> {
            System.out.println("px = " + tfPYXtfPY.getKey() + ", py = " + tfPYXtfPY.getValue());
            //return new Point((double) tfPYXtfPY.getKey(), (double) tfPYXtfPY.getValue());
        });
        return null;
    }
}
