package fr.insa.empire.treillis;

public class Noeud_simple extends Noeuds {

    private double px;
    private double py;

    public Noeud_simple(double px, double py) {  //constructeur
        this.px = px;
        this.py = py;
    }
    
    public double getPxNoeudSimple(Noeud_simple N){  //Get Px
        return this.px;
    }
    
    public double getPyNoeudSimple (Noeud_simple N){ //Get Py
        return this.py;
    }
    
    public String toString(Noeud_simple N){ //Méthode ToString
        return this.identificateur + "( " + this.px +" ; " + this.py + " ) ";
    }

}
