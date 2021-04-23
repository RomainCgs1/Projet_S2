package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import fr.insa.empire.utils.Lire;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Noeud_simple extends Noeuds {

    private double px;
    private double py;
    private Set<Barre> appartientABarre;


    public Noeud_simple(double px, double py) {  //constructeur
        this.px = px;
        this.py = py;
        this.appartientABarre = new HashSet<Barre>();
        
        System.out.println("Votre point à été créé : " + this);
    }

    private void setPx(double Px) {
        this.px = Px;
    }

    private void SetPy(double Py) {
        this.py = Py;
    }

    public void addBarreSet(Barre barre) {
        this.appartientABarre.add(barre);
    }

    public void removeBarreSet(Barre barre) {
        this.appartientABarre.remove(barre);
    }

    public void removeAllBarre() {
        this.appartientABarre.removeAll(appartientABarre);
    }

    public double getPxNoeudSimple() {  //Get Px
        return this.px;
    }

    public double getPyNoeudSimple() { //Get Py
        return this.py;
    }

    public String toString() { //Méthode ToString
        return super.identifiant + " ( " + this.px + " ; " + this.py + " )";
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

}
