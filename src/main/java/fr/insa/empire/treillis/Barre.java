package fr.insa.empire.treillis;

//import fr.insa.empire.utils.Identificateur;
import fr.insa.empire.utils.Identificateur;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    private double angleAlpha;

    //Constructeur
    public Barre(Noeuds noeudDebut, Noeuds noeudFin) {
        this.noeudDebut = noeudDebut;
        this.noeudFin = noeudFin;
        this.longueur = this.calculLongueur();

        //On ajoute la barre dans l'arraylist des noeuds
        if (this.noeudDebut.getClass() == Noeud_simple.class) {
            ((Noeud_simple) this.noeudDebut).getAppartientABarre().add(this);
        }
        if (this.noeudDebut.getClass() == Appui_simple.class) {
            ((Appui_simple) this.noeudDebut).getAppartientABarre().add(this);
        }
        if (this.noeudDebut.getClass() == Appui_double.class) {
            ((Appui_double) this.noeudDebut).getAppartientABarre().add(this);
        }
        
        
        if (this.noeudFin.getClass() == Noeud_simple.class) {
            ((Noeud_simple) this.noeudFin).getAppartientABarre().add(this);
        }
        if (this.noeudFin.getClass() == Appui_simple.class) {
            ((Appui_simple) this.noeudFin).getAppartientABarre().add(this);
        }
        if (this.noeudFin.getClass() == Appui_double.class) {
            ((Appui_double) this.noeudFin).getAppartientABarre().add(this);
        }
        
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

    public void setTensionBarre(Force tensionBarre) {
        this.tensionBarre = tensionBarre;
    }

    public double getAngleAlpha() {
        return angleAlpha;
    }

    public void setAngleAlpha(double angleAlpha) {
        this.angleAlpha = angleAlpha;
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
            pxDeb = ((Noeud_simple) this.noeudDebut).getPx();
            pyDeb = ((Noeud_simple) this.noeudDebut).getPy();
        }
        if (this.noeudDebut.getClass() == Appui_simple.class) {
            pxDeb = ((Appui_simple) this.noeudDebut).getPx();
            pyDeb = ((Appui_simple) this.noeudDebut).getPy();
        }
        if (this.noeudFin.getClass() == Noeud_simple.class) {
            pxFin = ((Noeud_simple) this.noeudFin).getPx();
            pyFin = ((Noeud_simple) this.noeudFin).getPy();
        }
        if ((this.noeudFin.getClass() == Appui_simple.class) || (this.noeudFin.getClass() == Appui_double.class)) {
            pxFin = ((Noeud_appui) this.noeudFin).getPx();
            pyFin = ((Noeud_appui) this.noeudFin).getPy();
        }
        if (this.noeudDebut.getClass() == Appui_double.class) {
            pxDeb = ((Appui_double) this.noeudDebut).getPx();
            pyDeb = ((Appui_double) this.noeudDebut).getPy();
        }
        if (this.noeudFin.getClass() == Appui_double.class) {
            pxFin = ((Appui_double) this.noeudFin).getPx();
            pyFin = ((Appui_double) this.noeudFin).getPy();
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

    //Calcul angle alpha
    public double calculAngleAlphaTension() {

        Point p1 = new Point(this.getNoeudDebut().getPx(), this.getNoeudDebut().getPy()); //Noeud début
        Point p2 = new Point(this.getNoeudFin().getPx(), this.getNoeudFin().getPy()); //Noeud fin

//        System.out.println("DANS CALCUL ALPHA");
//        System.out.println("p1 : " + p1.toString());
//        System.out.println("p2 : " + p2.toString());
        //Seg1 correspond à la barre
        double seg1X = p2.getPx() - p1.getPx();
        double seg1Y = p2.getPy() - p1.getPy();
        double alpha = Math.atan2(seg1Y, seg1X);
        
        System.out.println("Alpha : "+alpha);
        
        return alpha;

    }

//    public void composantesTension(Noeuds noeudCheck) {
//        
//        Point p1 = new Point(this.noeudDebut.getPx(), this.noeudDebut.getPy());
//        Point p2 = new Point(this.noeudFin.getPx(), this.noeudFin.getPy());
//        
//        if (noeudCheck != this.getNoeudDebut()) {
//            System.out.println("On échange les points p1" + p1.toString() + "et" + p2.toString());
//            Point pTampon = new Point(p2.getPx(), p2.getPy());
//            p1 = p2;
//            p1 = pTampon;
//            
//            System.out.println(p1.toString());
//            System.out.println(p2.toString());
//        }
//        
//        double segX = p2.getPx() - p1.getPx();
//        double segY = p2.getPy() - p1.getPy();
//        
//        if (segX < 0) {
//            System.out.println("La force est négative sur X");
//            this.getTensionBarre().setFx(-this.getTensionBarre().getFx());
//        }
//        
//        if (segY < 0) {
//            System.out.println("La force est négative sur Y");
//            this.getTensionBarre().setFy(-this.getTensionBarre().getFy());
//        }
//    }
    public String afficheSeg(double segX, double segY, double norme) {
        String s = "";
        s = s + "seg X " + segX + "\n";
        s = s + "seg Y " + segY + "\n";
        s = s + "norme " + norme + "";
        return s;
    }

   

    public static double calculLongueurSegmentP1P2(Point p1, Point p2) {
        double segX = p2.getPx() - p1.getPx();
        double segY = p2.getPy() - p1.getPy();

        return Math.sqrt(segX * segX + segY * segY);
    }

    public double getDistanceAuClic(double Px, double Py) {
        double X1 = this.noeudDebut.getPx();
        double Y1 = this.noeudDebut.getPy();
        double X2 = this.noeudFin.getPx();
        double Y2 = this.noeudFin.getPy();
        double D;
        // test si le point est entre les droite normales à la barre passantes aux extrémitées de la barre
        if (((X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X1 - (Y2 - Y1) * Y1 > 0 && (X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X2 - (Y2 - Y1) * Y2 < 0) || ((X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X1 - (Y2 - Y1) * Y1 < 0 && (X2 - X1) * Px + (Y2 - Y1) * Py - (X2 - X1) * X2 - (Y2 - Y1) * Y2 > 0)) {

            D = (Math.abs((Y1 - Y2) * Px + (X2 - X1) * Py + (Y2 - Y1) * X1 + (X1 - X2) * Y1) / (Math.sqrt((Y1 - Y2) * (Y1 - Y2) + (X2 - X1) * (X2 - X1))));
            return D;
        } else if (this.noeudDebut.getDistanceAuClick(Px, Py) < this.noeudFin.getDistanceAuClick(Px, Py)) {
            D = this.noeudDebut.getDistanceAuClick(Px, Py);
            return D;
        } else {
            D = this.noeudFin.getDistanceAuClick(Px, Py);
            return D;
        }

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

    public void draw(GraphicsContext graphicsContext)
    {
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.strokeLine(this.getNoeudDebut().getPx(), this.getNoeudDebut().getPy(), this.getNoeudFin().getPx(), this.getNoeudFin().getPy());
    }
}
