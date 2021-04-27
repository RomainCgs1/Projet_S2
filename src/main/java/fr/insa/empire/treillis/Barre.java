package fr.insa.empire.treillis;

//import fr.insa.empire.utils.Identificateur;
import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;

public class Barre {

    //Attributs
    private int identifiant;
    private Force tensionBarre;
    private Noeuds noeudDebut;
    private Noeuds noeudFin;
    private Type_de_barre type;
    private double longueur;

    //Constructeur
    public Barre(Noeuds noeudDebut, Noeuds noeudFin, Type_de_barre type) {
        this.noeudDebut = noeudDebut;
        this.noeudFin = noeudFin;
        this.type = type;
        
        //On ajoute la barre dans la SET(la liste) des noeuds à ses extrémités
        if (this.noeudDebut.getClass()==Noeud_simple.class){
            ((Noeud_simple)this.noeudDebut).addBarreSet(this);
        }
        if (this.noeudDebut.getClass()==Noeud_appui.class){
            ((Noeud_simple)this.noeudDebut).addBarreSet(this);
        }
        if (this.noeudFin.getClass()==Noeud_simple.class){
            ((Noeud_simple)this.noeudFin).addBarreSet(this);
        }
        if (this.noeudFin.getClass()==Noeud_appui.class){
            ((Noeud_simple)this.noeudFin).addBarreSet(this);
        }
        
        //On ajoute la barre à la SET du type auquel elle appartient
        this.type.addBarreSet(this);
    }

    //Constructeur pour test des forces 
    public Barre (Noeuds debut, Noeuds fin){
        this.noeudDebut = debut;
        this.noeudFin = fin;
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
        return this.type.getCoutAuMetre();
    }

    public int getIdentifiant() {
        return identifiant;
    }
    
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public Force getTensionBarre() {
        return tensionBarre;
    }
    
    
    
    //to String 
    public String toString() {
        String s = "";
        s = s + "Barre selectionnee : " + this.identifiant + "\n";
        s = s + "Type de la barre : " + this.type + "\n";
        s = s + "Noeud du debut " + this.noeudDebut + "\n";
        s = s + "Noeud de la fin " + this.noeudFin + "\n";

        return s;
    }

    //Calcul de la longueur
    public double calculLongueur() {
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
            pxDeb = ((Noeud_appui) this.noeudDebut).getPx();
            pyDeb = ((Noeud_appui) this.noeudDebut).getPy();
        }
        if (this.noeudFin.getClass() == Noeud_simple.class) {
            pxFin = ((Noeud_simple) this.noeudDebut).getPxNoeudSimple();
            pyFin = ((Noeud_simple) this.noeudDebut).getPyNoeudSimple();
        }
        if (this.noeudFin.getClass() == Noeud_appui.class) {
            pxFin = ((Noeud_appui) this.noeudDebut).getPx();
            pyFin = ((Noeud_appui) this.noeudDebut).getPy();
        }

        //On calcul les coordonnées du vecteur donné par les deux noeuds
        double bX = pxFin - pxDeb;
        double bY = pyFin - pyDeb;

        //On calcul la norme de ce vecteur
        double longeur = Math.sqrt(bX * bX + bY * bY);

        return longeur;
    }

    //Calcul du prix barre
    public double calculPrixBarre() {
        return this.type.getCoutAuMetre() * this.longueur;
    }

    //Sauvegarde
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : BARRE/id/type/idpdébut/idpfin
        bW.append("DEBUT_Barre/");
        bW.append(this.identifiant + "/");
        Type_de_barre type = this.getType();
        bW.append(type + "/");
        if (!idNum.objetPresent(this.noeudDebut)) {
            this.noeudDebut.save(bW, idNum);
        } else {
            bW.append(this.noeudDebut + "/");
        }
        if (!idNum.objetPresent(this.noeudFin)) {
            this.noeudFin.save(bW, idNum);
        } else {
            bW.append(this.noeudFin + "/");
        }
        bW.append("FIN_Barre/\n");
    }
}
