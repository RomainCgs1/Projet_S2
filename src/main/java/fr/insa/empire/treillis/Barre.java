package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public class Barre {

    //Attributs
    private int identificateur;
    private Noeuds noeudDebut;
    private Noeuds noeudFin;
    private Type_de_barre type;
    // private Set<Noeuds> extrémités;

    //Constructeur
    public Barre(int id, Noeuds noeudDebut, Noeuds noeudFin, Type_de_barre type) {
        this.identificateur = id;
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

    //to String 
    public String toString() {
        String s = "";
        s = s + "Barre selectionnee : " + this.identificateur + "\n";
        s = s + "Type de la barre : " + this.type + "\n";
        s = s + "Noeud du debut " + this.noeudDebut + "\n";
        s = s + "Noeud du fin " + this.noeudFin + "\n";

        return s;
    }

    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : BARRE/id/type/idpdébut/idpfin
        /*if (!idNum.objetPresent(this.noeudDebut)) {
            this.noeudDebut.save(bW, idNum);
        }
        if (!idNum.objetPresent(this.noeudFin)) {
            this.noeudFin.save(bW, idNum);
        }
        bW.append("Barre/");
        int id = idNum.getOrSetKey(this);
        bW.append(id + "/");
        Type_de_barre type = this.getType();
        bW.append(type+"/");
        bW.append(this.noeudDebut + "/");
        bW.append(this.noeudFin + "/");*/
    }

}
