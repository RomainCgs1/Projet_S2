package fr.insa.empire.utils;

import fr.insa.empire.graphique.MainGraphique;
import fr.insa.empire.treillis.Noeud_simple;
import fr.insa.empire.treillis.Point;
import fr.insa.empire.treillis.Triangle_terrain;
import fr.insa.empire.treillis.Zone_constructible;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Optional;

public class Controller {

    private MainGraphique vue; //à remplacer par le canvas
    private int etat;
    Point p1;
    Point p2;
    Point p3;

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
        this.etat = newState;

        switch (newState)
        {
            case 11 : //noeud appui
                this.vue.getTbNoeud().setText("Noeud Appui");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 12 : //noeud simple
                this.vue.getTbNoeud().setText("Noeud Simple");
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
                case 12 :
                    System.out.println("Noeud le plus proche : " + this.vue.getZone_constructible().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur));
                    Noeud_simple noeud_simple = new Noeud_simple(px, py);
                    noeud_simple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(noeud_simple));
                    System.out.println(noeud_simple.getID());
                    this.vue.getZone_constructible().getGraphicsContext2D().setStroke(Color.RED);
                    this.vue.getZone_constructible().getGraphicsContext2D().strokeOval(px - 5, py - 5, 10, 10);
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
                    Triangle_terrain triangle_terrain = this.vue.creationTriangleTerrain(this.p1, this.p2, this.p3);

                    System.out.println(this.p1 + "\n" +
                            this.p2 + "\n" +
                            this.p3);

                    changeEtat(30);
                    break;
            }
        }
    }
}
