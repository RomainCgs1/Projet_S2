package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;

public class Noeud_appui extends Noeuds {

    //Attributs
    private double position_alpha;
    private Segment_terrain segment_appui;

    //Constructeur
    public Noeud_appui()
    {

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
    
}