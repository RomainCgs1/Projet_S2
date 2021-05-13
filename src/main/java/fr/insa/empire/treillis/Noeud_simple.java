package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import fr.insa.empire.utils.Lire;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Noeud_simple extends Noeuds {

    private double px;
    private double py;
    private ArrayList <Barre> appartientABarre;


    public Noeud_simple(double px, double py) {  //constructeur
        this.px = px;
        this.py = py;
        this.appartientABarre = new ArrayList<Barre>();
        
        System.out.println("Votre noeud simple a été créé : " + this);
    }
    

    private void setPx(double Px) {
        this.px = Px;
    }

    private void SetPy(double Py) {
        this.py = Py;
    }

    public void addBarreSet(Barre barre) {
        if (!this.appartientABarre.contains(barre)){
            this.appartientABarre.add(barre);  
        }else{
            System.out.println("Cette barre appartient déjà au noeud");
        }
    }

    public void removeBarreSet(Barre barre) {
        if (this.appartientABarre.contains(barre)){
            this.appartientABarre.remove(barre);  
        }else{
            System.out.println("Cette barre n'appartient pas au noeud !");
        }
    }

    public void removeAllBarre() {
        this.appartientABarre.removeAll(appartientABarre);
    }

    @Override
    public double getPx() {  //Get Px
        return this.px;
    }

    @Override
    public double getPy() { //Get Py
        return this.py;
    }

    public String toString() { //Méthode ToString
        return " ( " + this.px + " ; " + this.py + " )";
    }

    public ArrayList<Barre> getAppartientABarre() {
        return appartientABarre;
    }
    
    

    //fonctions de test
     public static void testCreationPt() {
        System.out.println("Veuiller indiquer les coordonées de votre noeud :");
        System.out.println("px = ");
        double px = Lire.d();
        System.out.println("py = ");
        double py = Lire.d();

        Noeud_simple monNoeud = new Noeud_simple(px, py);
    }

    //Sauvegarde
    @Override
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : NOEUD_SIMPLE/id/px/py
        bW.append("Noeud_Simple/");
        bW.append(this.identifiant + "/");
        bW.append(this.px + "/");
        bW.append(this.py + "\n");
    }

    @Override
    public double getDistanceAuClick(double X, double Y) {
        double distance = Math.sqrt(Math.pow(this.px - X, 2) + Math.pow(this.py - Y, 2));
        return distance;
    }

    public static void main(String[] args) {
        testCreationPt();
    }

    public void draw(GraphicsContext graphicsContext)
    {
        graphicsContext.setStroke(Color.RED);
        graphicsContext.strokeOval(this.px - 5, this.py - 5, 10, 10);
    }
}
