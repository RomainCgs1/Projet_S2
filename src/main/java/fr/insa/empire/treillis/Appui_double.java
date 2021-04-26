package fr.insa.empire.treillis;

public class Appui_double extends Noeud_appui {

    private int identifiant = super.getID();
    private Force reactionAppuiDouble;
    
    public Appui_double(double px, double py, Segment_terrain segTerrain) {
        super(px, py, segTerrain);
    }

    public Force getReactionAppuiDouble() {
        return reactionAppuiDouble;
    }

    

    
}