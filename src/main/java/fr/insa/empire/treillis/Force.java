package fr.insa.empire.treillis;

import fr.insa.empire.syslin.Matrice;
import fr.insa.empire.utils.Identificateur;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Force {

    private double fx;
    private double fy;
    private double norme;
    private Treillis treilli = Treillis.treillis;
    private int identifiant;

    
    //Constructeur Tension
    public Force(Barre barre, double angle_alpha) {
        this.fx = Math.cos(angle_alpha);
        this.fy = Math.sin(angle_alpha);
        if (this.treilli.getIdBarreTensionsCreees().contains(barre.getIdentifiant())) {
            System.out.println("La tension a déjà été rentrée");
            System.out.println("Identifiant récupéré : " + barre.getTensionBarre().getIdentifiant());
            this.identifiant = barre.getTensionBarre().getIdentifiant();
            System.out.println("Id réel :" + this.identifiant);
        } else {
            System.out.println("Nouvelle tension ");
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            System.out.println("Id réel :" + this.identifiant);
            this.treilli.getIdBarreTensionsCreees().add(barre.getIdentifiant());
            System.out.println("Ajout de la force à la barre");
            barre.setTensionBarre(this);
        }
    }

    //Constructeur Réaction appui-simple
    public Force(Appui_simple appuiSimple, double angle_beta) {
        this.fx = Math.cos(angle_beta);
        this.fy = Math.sin(angle_beta);
        if (this.treilli.getIdAppuiSimpleCrees().contains(appuiSimple.getID())) {
            System.out.println("Réaction de l'appui simple existe déjà");
            System.out.println("Id récupéré : " + appuiSimple.getReactionAppuiSimple().getIdentifiant());
            this.identifiant = appuiSimple.getReactionAppuiSimple().getIdentifiant();
            System.out.println("Id réel : " + this.identifiant);
        } else {
            System.out.println("Nouvelle réaction");
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            System.out.println("Id attribué : " + this.identifiant);
            this.treilli.getIdAppuiSimpleCrees().add(appuiSimple.getID());
            System.out.println("Ajout de la force à l'appui simple");
            appuiSimple.setReactionAppuiSimple(this);
        }
    }

    //Constructeur Réaction appui-double
    public Force(Appui_double appuiDouble) {
        if (this.treilli.getIdAppuiDoubleCrees().contains(appuiDouble.getID())) {
            System.out.println("Réaction de l'appui double existe déjà");
            System.out.println("Id récupéré : " + appuiDouble.getReactionAppuiDouble().getIdentifiant());
            this.identifiant = appuiDouble.getReactionAppuiDouble().getIdentifiant();
            System.out.println("Id réel : " + this.identifiant);
        } else {
            System.out.println("Nouvelle réaction appui double");
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            System.out.println("Id réel : " + this.identifiant);
            this.treilli.getIdAppuiDoubleCrees().add(appuiDouble.getID());
            System.out.println("Ajout de la force à l'appui double");
            appuiDouble.setReactionAppuiDouble(this);
        }
    }

    //Constructeur Force appliquée en Noeud Simple
    public Force(Noeud_simple noeudSimple, double fx, double fy) {
        this.fx = fx;
        this.fy = fy;
        this.norme = this.calculNormeForce();
        this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
        System.out.println("Force ajoutée créee");
    }

    //Encapsulation
    public double getFx() {
        return this.fx;
    }

    public double getFy() {
        return this.fx;
    }

    private void setFx(double fx) {
        this.fx = fx;
    }

    private void setFy(double fy) {
        this.fy = fy;
    }

    public double getnorme() {
        return norme;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public double calculNormeForce() {
        return this.norme = Math.sqrt(fx * fx + fy * fy);
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public void setTreilli(Treillis treilli) {
        this.treilli = treilli;
    }

    //To String
    public String toString() {
        String s = "";
        s = s + "id : " + this.identifiant + "\n";
        s = s + "fx : " + this.fx + "\n";
        s = s + "fy : " + this.fy + "\n";
        s = s + "norme : " + this.norme + "\n";
        return s;
    }

    //Matrices et calculs
    /**
     * On va calculer le nb d'inconnues pour créer la matrice de la bonne taille
     * Demande une force à l'utilisateur Auprès de chaque noeud, on va récupérer
     * les infos qui nous intéressent On crée une liste d'identifiant en
     * parallèle de la matrice pour pouvoir se repérer
     */
    public void creationMatrice() {

        int nbAppuiSimple = 0;
        int nbAppuiDouble = 0;
        int nbBarres = 0;

        for (Map.Entry mapentry : treilli.getIdentificateur().getKetToObject().entrySet()) {
            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            if (val.getClass() == Appui_simple.class) {
                nbAppuiSimple = nbAppuiSimple + 1;
            }
            if (val.getClass() == Appui_double.class) {
                nbAppuiDouble = nbAppuiDouble + 1;
            }
            if (val.getClass() == Barre.class) {
                nbBarres = nbBarres + 1;
            }
        }

        int nbInconnues = nbBarres + nbAppuiSimple + 2 * nbAppuiDouble;
        int nbLig = nbInconnues + 1;
        int nbCol = nbInconnues + 1;

        Matrice systeme = new Matrice(nbLig, nbCol);
    }

    public void remplissageMatrice() {

        Set<Integer> idNoeudVerifies = new HashSet<Integer>();

        for (Map.Entry mapentry : treilli.getIdentificateur().getKetToObject().entrySet()) {
            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            if (val.getClass() == Noeud_simple.class) {
                if (!idNoeudVerifies.contains(key)) {
                    Iterator it = ((Noeud_simple) val).getAppartientABarre().iterator();
                    while (it.hasNext()) {
                        //TODO
                        //Creer les forces, recupérer la barre correspondante
                        //voir get pour SET ou passer en arraylist
                    }
                }
            }
            if (val.getClass() == Appui_simple.class) {

            }
            if (val.getClass() == Appui_double.class) {

            }
        }
    }
}
