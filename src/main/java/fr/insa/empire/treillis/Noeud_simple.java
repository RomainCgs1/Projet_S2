package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import fr.insa.empire.utils.Lire;
import java.io.BufferedWriter;
import java.io.IOException;

public class Noeud_simple extends Noeuds {

    private double px;
    private double py;

    public Noeud_simple(double px, double py) {  //constructeur
        this.px = px;
        this.py = py;

        System.out.println("Votre point à été créé : " + this);
    }
    
    private void setPx(Noeud_simple N, double Px){
        this.px = Px;
    }
    private void SetPy(Noeud_simple N, double Py){
        this.py = Py;
    }
    
    
    public double getPxNoeudSimple(Noeud_simple N){  //Get Px
        return this.px;
    }
    
    public double getPyNoeudSimple (Noeud_simple N){ //Get Py
        return this.py;
    }

    public String toString(){ //Méthode ToString
        return super.identifiant + " ( " + this.px + " ; " + this.py + " )";
    }


    //fonctions de test
    public static void testCreationPt()
    {
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
        int id = idNum.getOrSetKey(this);
        bW.append(id +"/");
        bW.append(this.px+"/");
        bW.append(this.py+"/");
    }
    
    public static void main(String[] args)
    {
        testCreationPt();
    }

}
