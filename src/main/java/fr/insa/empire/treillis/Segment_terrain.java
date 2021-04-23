package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Segment_terrain {

    private Set<Noeud_appui> contientNoeudAppui;
    private Set<Point> contientPoint;
   // private Triangle_terrain appartient;   EN A-T-ON BESOIN ?
    private Point pointDebut;
    private Point pointFin;
    private int identifiant;

    //Constructeur
    public Segment_terrain(Point pointDebut, Point pointFin) {
        this.pointDebut = pointDebut;
        this.pointFin = pointFin;
        this.contientNoeudAppui = new HashSet<Noeud_appui>();
        this.contientPoint = new HashSet<Point>();
        this.contientPoint.add(this.pointDebut);
        this.contientPoint.add(this.pointFin);
    }

    //get
    public Point getPointDebut() {
        return this.pointDebut;
    }

    public Point getPointFin() {
        return this.pointFin;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    
    public void addNASet(Noeud_appui na){
        this.contientNoeudAppui.add(na);
    }
    
     public void removeNASet(Noeud_appui na){
        this.contientNoeudAppui.remove(na);
    }
     
      public void removeAllNASet(){
        this.contientNoeudAppui.removeAll(contientNoeudAppui);
    }
    
    //Calcul de la longueur
    public double calculLongueurSegmentT(double pxNoeudAppui, double pyNoeudAppui) {

        double pxDeb = this.pointDebut.getPx();
        double pyDeb = this.pointDebut.getPy();
        double pxFin = this.pointFin.getPx();
        double pyFin = this.pointFin.getPy();

        //On calcul les coordonnées du vecteur donné par les deux noeuds
        double segpX = pxFin - pxDeb;
        double segPy = pyFin - pyDeb;

        //On calcul la norme de ce vecteur
        double longeur = Math.sqrt(segpX * segpX + segPy * segPy);

        return longeur;
    }

    //Save
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : SEGMENT_TERRAIN/id/idpdébut/idpfin
        bW.append("DEBUT_Segment_terrain/");
        bW.append(this.identifiant + "/");
        if (!idNum.objetPresent(this.pointDebut)) {
            this.pointDebut.save(bW, idNum);
        } else {
            bW.append(idNum.getOrSetKey(this.pointDebut) + "/");
        }

        if (!idNum.objetPresent(this.pointFin)) {
            this.pointFin.save(bW, idNum);
        } else {
            bW.append(idNum.getOrSetKey(this.pointFin) + "/");
        }
        bW.append("FIN_Segment_terrain/\n");
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
}
