package fr.insa.empire.utils;

import fr.insa.empire.graphique.MainGraphique;
import fr.insa.empire.graphique.MyCanvas;
import fr.insa.empire.treillis.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.text.html.ImageView;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class Controller {

    private MainGraphique vue;
    private int etat;
    private int etatPrecedent;
    Point p1;
    Point p2;
    Point p3;
    Noeuds noeudDebut;
    Noeuds noeudFin;

    public Controller(MainGraphique vue)
    {
        this.vue = vue;
        this.etat = 0;

        this.p1 = new Point();
        this.p2 = new Point();
        this.p3 = new Point();
    }

    public void changeEtat(int newState)
    {
        this.etatPrecedent = this.etat;
        this.etat = newState;

        switch (newState)
        {
            case 0: //remise à zero
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 110 : //noeud appui simple
                this.vue.getTbNoeud().setText("Noeud Appui simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 120 : //noeud simple
                this.vue.getTbNoeud().setText("Noeud Simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 130 : //noeud appui double
                this.vue.getTbNoeud().setText("Noeud Appui double");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 140 : //noeud appui encastré
                this.vue.getTbNoeud().setText("Noeud Appui encasté");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 20 : //barre
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
            case 30 : //terrain
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 40 : //selection
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 50 : //gomme
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                EraseAll();
                break;
            case 60 : //barre
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 70 : //calculs
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
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;

            case 80 : //on donne la position manuellement
                choixPositionManuelle();
                changeEtat(etatPrecedent);
                break;
        }

    }


    public void canvasClicked(MouseEvent E)
    {
        double px = E.getX();
        double py = E.getY();
        System.out.println("Canvas cliqué en " + px + " " + py);

        if(E.getButton() == MouseButton.PRIMARY)
        {
            switch (this.etat)
            {
                case 110 :
                    Segment_terrain segment_terrain = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                    System.out.println("Segment terrain le plus proche : " + segment_terrain);
                    if(segment_terrain == null)
                    {
                        //demander si on passe en mode de création segment terrain
                    }
                    else
                    {
                        //remplacer coord par le pt le plus proche sur le segment
                        //creer le point sur le segment
                        Point temp = segment_terrain.getPointSegmTerrPlusProche(px, py);
                        Appui_simple appuiSimple = new Appui_simple(temp.getPx(), temp.getPy(), segment_terrain);
                        appuiSimple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(appuiSimple));
                    }
                    break;

                case 120 :
                    System.out.println("Noeud simple le plus proche : " + this.vue.getCanvas().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur));
                    Noeud_simple noeud_simple = new Noeud_simple(px, py);
                    noeud_simple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(noeud_simple));
                    System.out.println(noeud_simple.getID());
                    this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.RED);
                    this.vue.getCanvas().getGraphicsContext2D().strokeOval(px - 5, py - 5, 10, 10);
                    break;

                case 130 :
                    Segment_terrain segment_terrain1 = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                    System.out.println("Segment terrain le plus proche : " + segment_terrain1);
                    if(segment_terrain1 == null)
                    {
                        //demander si on passe en mode de création segment terrain
                    }
                    else
                    {
                        //remplacer coord par le pt le plus proche sur le segment
                        //creer le point sur le segment
                        Point temp = segment_terrain1.getPointSegmTerrPlusProche(px, py);
                        Appui_double appuiDouble = new Appui_double(temp.getPx(), temp.getPy(), segment_terrain1);
                        appuiDouble.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(appuiDouble));
                    }
                    break;
                case 20 :
                    noeudDebut = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if(noeudDebut == null)
                    {
                        this.changeEtat(120);
                        this.canvasClicked(E);
                        this.changeEtat(20);
                        this.canvasClicked(E);
                    }
                    this.changeEtat(21);
                    break;

                case 21 :
                    noeudFin = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                    if(noeudFin == null)
                    {
                        this.changeEtat(120);
                        this.canvasClicked(E);
                        this.changeEtat(21);
                        this.canvasClicked(E);
                    }
                    else
                    {
                        Barre barre = creationBarre(this.noeudDebut, this.noeudFin);
                    }
                    this.changeEtat(20);
                    break;

                case 30 :
                    this.p1.setPx(px);
                    this.p1.setPy(py);
                    this.p1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p1));
                    changeEtat(31);
                    System.out.println("point 1");
                    break;

                case 31 :
                    this.p2.setPx(px);
                    this.p2.setPy(py);
                    this.p2.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p2));
                    changeEtat(32);
                    System.out.println("point 2");
                    break;

                case 32 :
                    this.p3.setPx(px);
                    this.p3.setPy(py);
                    this.p3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p3));
                    System.out.println("point 3");
                    Triangle_terrain triangle_terrain = creationTriangleTerrain(this.p1, this.p2, this.p3);

                    System.out.println(this.p1 + "\n" +
                            this.p2 + "\n" +
                            this.p3);

                    changeEtat(30);
                    break;
            }
        }
    }

    public void canvasOver(MouseEvent E) {
        double px = E.getX();
        double py = E.getY();
        this.vue.getbPosition().setText(px + " ; " + py);
    }

    private Triangle_terrain creationTriangleTerrain(Point p1, Point p2, Point p3) {
        Segment_terrain seg1 = new Segment_terrain(p1, p2);
        Segment_terrain seg2 = new Segment_terrain(p2, p3);
        Segment_terrain seg3 = new Segment_terrain(p3, p1);
        Triangle_terrain triangle_terrain = new Triangle_terrain(seg1, seg2, seg3);
        triangle_terrain.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(triangle_terrain));

        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.BLACK);
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p1.getPx(), p1.getPy(), p2.getPx(), p2.getPy());
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p2.getPx(), p2.getPy(), p3.getPx(), p3.getPy());
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p3.getPx(), p3.getPy(), p1.getPx(), p1.getPy());

        System.out.println("Triangle n°" + triangle_terrain.getIdentifiant() + " a été créé.");
        return triangle_terrain;
    }

    private Barre creationBarre(Noeuds noeudDebut, Noeuds noeudFin) {

        Barre barre = new Barre(noeudDebut, noeudFin);
        barre.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(barre));

        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.BLUE);
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(noeudDebut.getPx(), noeudDebut.getPy(), noeudFin.getPx(), noeudFin.getPy());

        System.out.println("barre n°" + barre.getIdentifiant() + " a été créé." );
        return barre;
    }



    private void EraseAll() {
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
        tfPX.setPromptText("0");
        TextField tfPY = new TextField();
        tfPY.setPromptText("0");

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
