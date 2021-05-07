package fr.insa.empire.treillis;

import static fr.insa.empire.treillis.Barre.calculNorme;
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
    
    public double calculAngleBeta(Noeuds noeudCheck) {
       
        Point p1 = this.getPointDebut();
        Point p2 = this.getPointFin();
        
        //Création du segment de référence
        double segRefX = 1;
        double segRefY = 0;
        double normeSegRef = 1;
               
        double beta = Math.PI/2;
        
        System.out.println("DANS CALCUL BETA");
        System.out.println("p1 : " + p1.toString());
        System.out.println("p2 : " + p2.toString());

        //Seg1 correspond à la barre
        double seg1X = p2.getPx() - p1.getPx();
        double seg1Y = p2.getPy() - p1.getPy();
        double normeSeg1 = calculNorme(seg1X, seg1Y);
        System.out.println("Norme : "+normeSeg1+"\n");

        
        double scalaire = seg1X * segRefX + seg1Y * segRefY;
        System.out.println("SCALAIRE = " + scalaire);
        
        if (normeSeg1 * normeSegRef == 0) {
            System.out.println("BETA vaut : " + beta+"\n");
            return beta;
        } else {
//            System.out.println("On va fait arccos de " + (scalaire / (normeSeg1 * normeSegRef)));
            beta = Math.acos(scalaire / (normeSeg1 * normeSegRef));
            System.out.println("BETA SANS PI/2 : "+beta+"\n");
            beta = beta + Math.PI/2;
            System.out.println("BETA vaut : " + beta+"\n"); 
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
