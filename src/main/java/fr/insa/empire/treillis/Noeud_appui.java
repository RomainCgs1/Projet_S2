package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;

public class Noeud_appui extends Noeuds {

    //Attributs
    private double position_alpha;
    private Segment_terrain segment_appui;

    //Constructeurs
    public Noeud_appui()
    {

    }

    public Noeud_appui(double px, double py, Segment_terrain segment_appui)
    {
        //utiliser les poins de départ et d'arrivée du segement appui lorsqu'on aura défini les get
        double px1 = 1;
        double py1 = 1;
        double px2 = 1;
        double py2 = 1;

        this.segment_appui = segment_appui;
        this.position_alpha = Math.sqrt(Math.pow(px-px1, 2) + Math.pow(py-py1, 2)) / Math.sqrt(Math.pow(px2-px1, 2) + Math.pow(py2-py1, 2));
    }

    public Noeud_appui(double position_alpha, Segment_terrain segment_appui) {
        this.position_alpha = position_alpha;
        this.segment_appui = segment_appui;
    }

    //Encpasulation
    public double getPosition_alpha() {
        return position_alpha;
    }

    private void setPosition_alpha(double position_alpha) {
        this.position_alpha = position_alpha;
    }

    public Segment_terrain getSegment_appui() {
        return segment_appui;
    }

    private void setSegment_appui(Segment_terrain segment_appui) {
        this.segment_appui = segment_appui;
    }
    
    public String toString(){
        String s="";
        s=s+"Position du noeud appui : "+this.position_alpha+"\n";
        s=s+"Le noeud appuis se trouve sur le segment : "+this.segment_appui+"\n";
        
        return s;
    }
    
    public void save (BufferedWriter bW, Identificateur idNum){
        //TODO
    }

    @Override
    public double getDistanceAuClick(double px, double py) {
        return 0;
    }

}