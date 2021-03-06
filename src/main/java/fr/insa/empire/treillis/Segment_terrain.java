package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Segment_terrain {

    private final ArrayList<Noeud_appui> contientNoeudAppui;
    private final ArrayList<Point> contientPoint;
    private Triangle_terrain appartient;   //EN A-T-ON BESOIN ?
    private final Point pointDebut;
    private final Point pointFin;
    private int identifiant;

    //Constructeur
    public Segment_terrain(Point pointDebut, Point pointFin) {
        this.pointDebut = pointDebut;
        this.pointFin = pointFin;
        this.contientNoeudAppui = new ArrayList<Noeud_appui>();
        this.contientPoint = new ArrayList<Point>();
        this.contientPoint.add(this.pointDebut);
        this.contientPoint.add(this.pointFin);
    }

    //get
    public Triangle_terrain getAppartient() {
        return appartient;
    }

    public Point getPointDebut() {
        return this.pointDebut;
    }

    public Point getPointFin() {
        return this.pointFin;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setAppartient(Triangle_terrain appartient) {
        this.appartient = appartient;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public void addNASet(Noeud_appui na) {
        this.contientNoeudAppui.add(na);
    }

    public void removeNASet(Noeud_appui na) {
        this.contientNoeudAppui.remove(na);
    }

    public void removeAllNASet() {
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
    
    public double calculAngleBeta() {
       
        Point p1 = new Point(this.getPointDebut().getPx(), this.getPointDebut().getPy()); //Noeud début
        Point p2 = new Point(this.getPointFin().getPx(), this.getPointFin().getPy()); //Noeud fin

        //Seg1 correspond à la barre
        double seg1X = p2.getPx() - p1.getPx();
        double seg1Y = p2.getPy() - p1.getPy();
        double beta = Math.atan2(seg1Y, seg1X)+ (Math.PI/2);
        
        System.out.println("Beta : "+beta);
        
        return beta;
    }

    public double getDistanceAuClic(double Px, double Py) {
        double X1 = this.pointDebut.getPx();
        double Y1 = this.pointDebut.getPy();
        double X2 = this.pointFin.getPx();
        double Y2 = this.pointFin.getPy();

        double D;

        // test si le point est entre les droite normales au segment Terrain passantes aux extrémitées du segment Terrain.
        if (((X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X1 - (Y2 - Y1) * Y1 > 0 && (X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X2 - (Y2 - Y1) * Y2 < 0) || ((X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X1 - (Y2 - Y1) * Y1 < 0 && (X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X2 - (Y2 - Y1) * Y2 > 0)) {


            D = (Math.abs((Y1 - Y2) * Px + (X2 - X1) * Py + (Y2 - Y1) * X1 + (X1 - X2) * Y1) / (Math.sqrt((Y1 - Y2) * (Y1 - Y2) + (X2 - X1) * (X2 - X1))));
            return D;
        } else if (this.pointDebut.getDistanceAuClick(Px, Py) < this.pointFin.getDistanceAuClick(Px, Py)) {
            D = this.pointDebut.getDistanceAuClick(Px, Py);
            return D;
        } else {
            D = this.pointFin.getDistanceAuClick(Px, Py);
            return D;
        }
    }

    public Point getPointSegmTerrPlusProche(double Px, double Py) {
        double X1 = this.pointDebut.getPx();
        double Y1 = this.pointDebut.getPy();
        double X2 = this.pointFin.getPx();
        double Y2 = this.pointFin.getPy();
        double X;
        double Y;

        /*X = (-(Y1 - Y2) / (X1 - X2) * X1 - Y1 + ((X2 - X1) / (Y2 - Y1) * Px) + Py) / ((X1 - X2) / (Y2 - Y1) - (Y2 - Y1) / (X2 - X1));
        Y = (Y2 - Y1) / (X2 - X1) * X + (Y1 - Y2) / (X2 - X1) * X1 + Y1;
        Point P = new Point(X, Y);
        System.out.println(P); */

        //alternative :
        double ux = Px - X1;
        double uy = Py - Y1;

        double vx = X2 - X1;
        double vy = Y2 - Y1;

        double longueurU = Math.sqrt(Math.pow(Px - X1, 2) + Math.pow(Py - Y1, 2));
        double longueurV = Math.sqrt(Math.pow(X2 - X1, 2) + Math.pow(Y2 - Y1, 2));

        double cosAlpha = ((Px - X1)*(X2 - X1) + (Py - Y1)*(Y2 - Y1)) / (longueurU * longueurV);

        double x0 = cosAlpha * (Px - X1);
        double y0 = cosAlpha * (Py - Y1);

        X = x0 + X1;
        Y = y0 + Y1;

        Point P1 = new Point(X, Y);
        System.out.println(P1);

        return P1;
    }

    //Save
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : SEGMENT_TERRAIN/id/idpdébut/idpfin
        this.pointDebut.save(bW, idNum);
        this.pointFin.save(bW, idNum);
        bW.append("DEBUT_Segment_terrain/");
        bW.append(this.identifiant + "/");
        bW.append(idNum.getOrSetKey(this.pointDebut) + "/");
        bW.append(idNum.getOrSetKey(this.pointFin) + "/");
        bW.append("FIN_Segment_terrain/");
    }
}
