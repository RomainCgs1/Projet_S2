package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Segment_terrain {

    private final Set<Noeud_appui> contientNoeudAppui;
    private final Set<Point> contientPoint;
    // private Triangle_terrain appartient;   EN A-T-ON BESOIN ?
    private final Point pointDebut;
    private final Point pointFin;
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
        Point p1 = this.pointDebut;
        Point p2 = this.pointFin;
        Point p3 = new Point(p2.getPx(), p1.getPy());

        double beta = Math.PI / 2;

//        System.out.println("DANS CALCUL BETA");
//        System.out.println("p1 : " + p1.toString());
//        System.out.println("p2 : " + p2.toString());
//        System.out.println("p3 : " + p3.toString());

        double seg12X = p2.getPx() - p1.getPx();
        double seg12Y = p2.getPy() - p1.getPy();

        double seg13X = p3.getPx() - p1.getPx();
        double seg13Y = p3.getPy() - p1.getPy();

        double norme12 = Barre.calculNorme(seg12X, seg12Y);
        double norme13 = Barre.calculNorme(seg13X, seg13Y);

        double scalaire = seg12X * seg13X + seg12Y * seg13Y;

        if (norme12 * norme13 == 0) {
            System.out.println("Beta vaut : " + beta);
            return beta;
        } else {
            beta = (Math.PI / 2) + Math.acos(scalaire / (norme12 * norme13));
            System.out.println("Beta vaut : " + beta);
            return beta;
        }
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

        double X = (-(Y1 - Y2) / (X1 - X2) * X1 - Y1 + ((X2 - X1) / (Y2 - Y1) * Px) + Py) / ((X1 - X2) / (Y2 - Y1) - (Y2 - Y1) / (X2 - X1));
        double Y = (Y2 - Y1) / (X2 - X1) * X + (Y1 - Y2) / (X2 - X1) * X1 + Y1;
        Point P = new Point(X, Y);
        return P;
    }

    //Save
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : SEGMENT_TERRAIN/id/idpdébut/idpfin
        bW.append("DEBUT_Segment_terrain/");
        bW.append(this.identifiant + "/\n");
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
}
