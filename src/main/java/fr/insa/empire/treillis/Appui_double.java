package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    public void addBarreArray(Barre barre) {
        if (!super.getAppartientABarre().contains(barre)) {
            super.getAppartientABarre().add(barre);
        } else {
            System.out.println("Cette barre appartient déjà au noeud");
        }
    }

    public void removeBarreArray(Barre barre) {
        if (super.getAppartientABarre().contains(barre)) {
            super.getAppartientABarre().remove(barre);
        } else {
            System.out.println("Cette barre n'appartient pas au noeud");
        }
    }

    public void removeAllBarreArray(Barre barre) {
        super.getAppartientABarre().removeAll(super.getAppartientABarre());
    }

    public String toString() {
        String s = "";
        s = s + "id : " + this.identifiant + "\n";
        s = s + "px : " + super.getPx() + "\n";
        s = s + "py : " + super.getPy() + "\n";
        s = s + "pos alpha : " + super.getPosition_alpha() + "\n";
        s = s + "id segTerrain : " + super.getSegment_appui().getIdentifiant() + "\n";
        return s;
    }

    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : Appui_double/id/px/py/posAlpha
        bW.append("Appui_Double/");
        bW.append(this.identifiant + "/");
        bW.append(this.getPx() + "/");
        bW.append(this.getPy() + "/");
        bW.append(this.getPosition_alpha() + "/");
        bW.append(this.getSegment_appui().getIdentifiant() + "/\n");
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.SILVER);
        graphicsContext.strokeOval(this.getPx() - 5, this.getPy() - 5, 10, 10);
    }
}
