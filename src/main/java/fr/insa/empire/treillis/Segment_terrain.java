package fr.insa.empire.treillis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public class Segment_terrain {

    private Set<Noeud_appui> contient;
    private Triangle_terrain appartient;
    private Point pointDebut;
    private Point pointFin;
    private int identifiant;
    
    //Constructeur
    public Segment_terrain(Point pointDebut, Point pointFin) {
        this.pointDebut = pointDebut;
        this.pointFin = pointFin;
    }

    
/*
     public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : BARRE/id/type/idpdébut/idpfin
        if (!idNum.objetPresent(this.)) {
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
        bW.append(idNum.getOrSetKey(this.noeudDebut)+ "/");
        bW.append(idNum.getOrSetKey(this.noeudFin) + "/");
    } */

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}