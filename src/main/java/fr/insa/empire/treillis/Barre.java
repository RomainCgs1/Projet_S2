package fr.insa.empire.treillis;

//import fr.insa.empire.utils.Identificateur;
import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;

public class Barre {

    //Attributs
    private int identifiant;

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
    private Noeuds noeudDebut;
    private Noeuds noeudFin;
    private Type_de_barre type;
    private double longueur;
    private double prix = this.type.getCoutAuMetre();
    // private Set<Noeuds> extrémités;

    //Constructeur
    public Barre(Noeuds noeudDebut, Noeuds noeudFin, Type_de_barre type) {
        this.noeudDebut = noeudDebut;
        this.noeudFin = noeudFin;
        this.type = type;
    }

    //Encapsulation
    public Noeuds getNoeudDebut() {
        return this.noeudDebut;
    }

    public Noeuds getNoeudFin() {
        return this.noeudFin;
    }

    public Type_de_barre getType() {
        return this.type;
    }

    public void setNoeudDebut(Noeuds noeudDebut) {
        this.noeudDebut = noeudDebut;
    }

    public void setNoeudFin(Noeuds noeudFin) {
        this.noeudFin = noeudFin;
    }

    public void setType(Type_de_barre type) {
        this.type = type;
    }
    
    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }
    
    public double getLongueur() {
        return this.longueur;
    }

    public double getPrix() {
        return prix;
    }
    

    //to String 
    public String toString() {
        String s = "";
        s = s + "Barre selectionnee : " + this.identifiant + "\n";
        s = s + "Type de la barre : " + this.type + "\n";
        s = s + "Noeud du debut " + this.noeudDebut + "\n";
        s = s + "Noeud du fin " + this.noeudFin + "\n";

        return s;
    }

    //Calcul de la longueur
    public double calculLongueur(double pxNoeudAppui, double pyNoeudAppui) {
        double pxDeb = 0;
        double pyDeb = 0;
        double pxFin = 0;
        double pyFin = 0;

        //On set les valeurs des coordonnées en fonction du type de noeud
        //Car les infos ne sont pas disponibles de la même façon
        if (this.noeudDebut.getClass() == Noeud_simple.class) {
            pxDeb = ((Noeud_simple) this.noeudDebut).getPxNoeudSimple();
            pyDeb = ((Noeud_simple) this.noeudDebut).getPyNoeudSimple();
        }
        if (this.noeudDebut.getClass() == Noeud_appui.class) {
            pxDeb = pxNoeudAppui;
            pyDeb = pyNoeudAppui;
        }
        if (this.noeudFin.getClass() == Noeud_simple.class) {
            pxFin = ((Noeud_simple) this.noeudDebut).getPxNoeudSimple();
            pyFin = ((Noeud_simple) this.noeudDebut).getPyNoeudSimple();
        }
        if (this.noeudFin.getClass() == Noeud_appui.class) {
            pxFin = pxNoeudAppui;
            pyFin = pyNoeudAppui;
        }
        
        //On calcul les coordonnées du vecteur donné par les deux noeuds
        double bX = pxFin-pxDeb;
        double bY = pyFin-pyDeb;
        
        //On calcul la norme de ce vecteur
        double longeur = Math.sqrt(bX*bX+bY*bY);
        
        return longeur;
    }
    
    //Calcul du prix barre
    public double calculPrixBarre(double prixMetre, double longueur){
        return prixMetre*longueur;
    }

    //Sauvegarde
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : BARRE/id/type/idpdébut/idpfin
        if (!idNum.objetPresent(this.noeudDebut)) {
            this.noeudDebut.save(bW, idNum);
        }
        if (!idNum.objetPresent(this.noeudFin)) {
            this.noeudFin.save(bW, idNum);
        }
        bW.append("Barre/");
        bW.append(this.identifiant + "/");
        Type_de_barre type = this.getType();
        bW.append(type + "/");
        bW.append(this.noeudDebut + "/");
        bW.append(this.noeudFin + "/");
    }

}
