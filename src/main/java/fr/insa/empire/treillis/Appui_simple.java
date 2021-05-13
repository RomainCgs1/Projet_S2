package fr.insa.empire.treillis;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Appui_simple extends Noeud_appui {

    private int identificant = super.getID();
    private Force reactionAppuiSimple;

    public Appui_simple(double px, double py, Segment_terrain segTerrain) {
        super(px, py,segTerrain);
    }


    public Force getReactionAppuiSimple() {
        return reactionAppuiSimple;
    }

    public void setReactionAppuiSimple(Force reactionAppuiSimple) {
        this.reactionAppuiSimple = reactionAppuiSimple;
    }
    
    public void addBarreArray(Barre barre){
        if (!super.getAppartientABarre().contains(barre)){
            super.getAppartientABarre().add(barre);  
        }else{
            System.out.println("Cette barre appartient déjà au noeud");
        }
    }
    
    public void removeBarreArray(Barre barre){
        if (super.getAppartientABarre().contains(barre)){
            super.getAppartientABarre().remove(barre);  
        }else{
            System.out.println("Cette barre n'appartient pas au noeud");
        }
    }
    
    public void removeAllBarreArray(Barre barre){
        super.getAppartientABarre().removeAll(super.getAppartientABarre());
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

    public void draw(GraphicsContext graphicsContext)
    {
        graphicsContext.setStroke(Color.ORANGE);
        graphicsContext.strokeOval(this.getPx() - 5, this.getPy() - 5, 10, 10);
    }
}