package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
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

    //get
    public Point getPointDebut()
    {
        return this.pointDebut;
    }

    public Point getPointFin()
    {
        return this.pointFin;
    }

    //Save
     public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : SEGMENT_TERRAIN/id/idpdébut/idpfin
        if (!idNum.objetPresent(this.pointDebut)) {
            this.pointDebut.save(bW, idNum);
        }
        if (!idNum.objetPresent(this.pointFin)) {
            this.pointFin.save(bW, idNum);
        }
        bW.append("Segment_terrain/");
        bW.append(this.identifiant + "/");
        bW.append(idNum.getOrSetKey(this.pointDebut)+ "/");
        bW.append(idNum.getOrSetKey(this.pointFin) + "/");
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}