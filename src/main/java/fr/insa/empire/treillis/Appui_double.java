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

    public void setReactionAppuiDouble(Force reactionAppuiDouble) {
        this.reactionAppuiDouble = reactionAppuiDouble;
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