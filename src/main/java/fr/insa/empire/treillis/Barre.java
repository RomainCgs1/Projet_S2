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
        if (this.noeudDebut.getClass() == Noeud_simple.class) {
            ((Noeud_simple) this.noeudDebut).addBarreSet(this);
        }
        if (this.noeudDebut.getClass() == Noeud_appui.class) {
            ((Noeud_simple) this.noeudDebut).addBarreSet(this);
        }
        if (this.noeudFin.getClass() == Noeud_simple.class) {
            ((Noeud_simple) this.noeudFin).addBarreSet(this);
        }
        if (this.noeudFin.getClass() == Noeud_appui.class) {
            ((Noeud_simple) this.noeudFin).addBarreSet(this);
        }

        //On ajoute la barre à la SET du type auquel elle appartient
        this.type.addBarreSet(this);
    }

    //Constructeur pour test des forces 
    public Barre(Noeuds debut, Noeuds fin) {
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

    public void setTensionBarre(Force tensionBarre) {
        this.tensionBarre = tensionBarre;
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
        if (this.noeudDebut.getClass() == Noeud_appui.class) {
            pxDeb = ((Noeud_appui) this.noeudDebut).getPx();
            pyDeb = ((Noeud_appui) this.noeudDebut).getPy();
        }
        if (this.noeudFin.getClass() == Noeud_simple.class) {
            pxFin = ((Noeud_simple) this.noeudDebut).getPx();
            pyFin = ((Noeud_simple) this.noeudDebut).getPy();
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

    //Calcul angle alpha
    public double calculAngleAlphaTension() {
        Point p1 = new Point();
        Point p2 = new Point();

        double alpha = 0;

        if (this.getNoeudDebut().getClass() == Noeud_simple.class) {
            p1.setPx(((Noeud_simple) this.getNoeudDebut()).getPx());
            p1.setPy(((Noeud_simple) this.getNoeudDebut()).getPy());
        }
        if (this.getNoeudFin().getClass() == Noeud_simple.class) {
            p2.setPx(((Noeud_simple) this.getNoeudFin()).getPx());
            p2.setPy(((Noeud_simple) this.getNoeudFin()).getPy());
        }
        if ((this.getNoeudDebut().getClass() == Appui_simple.class) || (this.getNoeudDebut().getClass() == Appui_double.class)) {
            p1.setPx(((Noeud_appui) this.getNoeudDebut()).getPx());
            p1.setPy(((Noeud_appui) this.getNoeudDebut()).getPy());
        }
        if ((this.getNoeudFin().getClass() == Appui_simple.class) || (this.getNoeudFin().getClass() == Appui_double.class)) {
            p2.setPx(((Noeud_appui) this.getNoeudFin()).getPx());
            p2.setPy(((Noeud_appui) this.getNoeudFin()).getPy());
        }

        Point p3 = new Point(p1.getPx(), p2.getPy());

//        System.out.println("DANS CALCUL ALPHA");
//        System.out.println("p1 : " + p1.toString());
//        System.out.println("p2 : " + p2.toString());
//        System.out.println("p3 : " + p3.toString());

        double longeurSegP2P3 = calculLongueurSegmentP1P2(p2, p3);
        double longueurSegP1P3 = calculLongueurSegmentP1P2(p1, p3);

        if (longeurSegP2P3 == 0) {
            System.out.println("Impossible d'effectuer la division par 0");
            System.out.println("La force ne possède qu'une composante sur Y");
            if (p3.getPy() > 0) {
                alpha = Math.PI / 2;
                System.out.println("Alpha vaut : " + Math.toDegrees(alpha));
                return alpha;
            } else {
                alpha = -Math.PI / 2;
                System.out.println("Alpha vaut : " + Math.toDegrees(alpha));
                return alpha;
            }
        } else {
            alpha = Math.atan(longueurSegP1P3 / longeurSegP2P3);
            System.out.println("Alpha vaut : " + Math.toDegrees(alpha));
            return alpha;
        }
    }

    public static double calculLongueurSegmentP1P2(Point p1, Point p2) {
        double segX = p2.getPx() - p1.getPx();
        double segY = p2.getPy() - p1.getPy();

        return Math.sqrt(segX * segX + segY * segY);
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
