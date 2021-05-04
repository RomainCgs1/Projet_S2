package fr.insa.empire.utils;

import fr.insa.empire.graphique.MainGraphique;
import fr.insa.empire.graphique.MyCanvas;
import fr.insa.empire.treillis.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Optional;

public class Controller {

    private MainGraphique vue;
    private int etat;
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
        this.vue.getlPosition().setText(px + " ; " + py);
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
}
