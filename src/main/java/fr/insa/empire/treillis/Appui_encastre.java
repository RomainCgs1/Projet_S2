package fr.insa.empire.treillis;

public class Appui_encastre extends Noeud_appui {

    private double px;
    private int nbDeBarres;
    private double force;

    public Appui_encastre(double px, double py, Segment_terrain segTerrain) {
        super(px, py, segTerrain);
    }

    
}