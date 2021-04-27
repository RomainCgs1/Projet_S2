package fr.insa.empire.treillis;

public class Appui_simple extends Noeud_appui {

    private double px;
    private double py;
    private int identificant = super.getID();
    private Force reactionAppuiSimple;

    public Appui_simple(double px, double py, Segment_terrain segTerrain) {
        super(px, py,segTerrain);
    }

    public double getPx() {
        return px;
    }

    public double getPy() {
        return py;
    }

    public Force getReactionAppuiSimple() {
        return reactionAppuiSimple;
    }

    public String toString(){
        String s="";
        s=s+"id : "+this.identifiant+"\n";
        s=s+"px : "+super.getPx()+"\n";
        s=s+"py : "+super.getPy()+"\n";
        s=s+"pos alpha : "+super.getPosition_alpha()+"\n";
        s=s+"id segTerrain : "+super.getSegment_appui().getIdentifiant()+"\n";
        return s;
    }
    
}