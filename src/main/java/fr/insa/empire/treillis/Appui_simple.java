/*
Cette classe comporte les fonctions liées aux appui-simples
*/

package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Appui_simple extends Noeud_appui {

    //Attributs
    private int identificant = super.getID();
    private Force reactionAppuiSimple;

    //Constructeurs
    public Appui_simple(double px, double py, Segment_terrain segTerrain) {
        super(px, py,segTerrain);
    }
    
    public Appui_simple(double PositionA, Segment_terrain segmentT){
        super(PositionA,segmentT);
    }

    //Encapsulation
    public Force getReactionAppuiSimple() {
        return reactionAppuiSimple;
    }

    public void setReactionAppuiSimple(Force reactionAppuiSimple) {
        this.reactionAppuiSimple = reactionAppuiSimple;
    }
    
    //Gestions array
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

    //Méthode toString
    public String toString(){
        String s="";
        s=s+"id : "+this.identifiant+"\n";
        s=s+"px : "+super.getPx()+"\n";
        s=s+"py : "+super.getPy()+"\n";
        s=s+"pos alpha : "+super.getPosition_alpha()+"\n";
        s=s+"id segTerrain : "+super.getSegment_appui().getIdentifiant()+"\n";
        return s;
    }

    //Sauvegarde
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        bW.append("Appui_Simple/");
        super.save(bW, idNum);
    }
    
    //Méthode pour dessiner un appui-simple
    public void draw(GraphicsContext graphicsContext)
    {
        graphicsContext.setStroke(Color.ORANGE);
        graphicsContext.strokeOval(this.getPx() - 5, this.getPy() - 5, 10, 10);
    }
}