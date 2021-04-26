package fr.insa.empire.utils;

import fr.insa.empire.graphique.MainGraphique;
import fr.insa.empire.treillis.Noeud_simple;
import fr.insa.empire.treillis.Zone_constructible;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {

    private MainGraphique vue; //à remplacer par le canvas
    private int etat;

    public Controller(MainGraphique vue)
    {
        this.vue = vue;
        this.etat = 0;
        this.vue.getMtbGomme().setDisable(true);
        this.vue.getMtbSelection().setDisable(true);
        this.vue.getMbTypeBarre().setDisable(true);
        this.vue.getMbLancerCalculs().setDisable(true);
    }

    public void changeEtat(int newState)
    {
        this.etat = newState;

        switch (newState)
        {
            case 11 :
                this.vue.getTbNoeud().setText("Noeud Appui");

                //reset
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 12 :
                this.vue.getTbNoeud().setText("Noeud Simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 20 :
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
            case 30 :
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 40 :
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 50 :
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
                break;
            case 60 :
                this.vue.getTbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbGomme().setSelected(false);
                this.vue.getMtbSelection().setSelected(false);
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
                case 12 :
                    System.out.println("Noeud le plus proche : " + this.vue.getZone_constructible().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur));
                    Noeud_simple noeud_simple = new Noeud_simple(px, py);
                    noeud_simple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(noeud_simple));
                    System.out.println(noeud_simple.getID());
                    this.vue.getZone_constructible().getGraphicsContext2D().setStroke(Color.RED);
                    this.vue.getZone_constructible().getGraphicsContext2D().strokeOval(px - 5, py - 5, 10, 10);
                    break;
            }
        }
    }
}
