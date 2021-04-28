package fr.insa.empire.treillis;

import fr.insa.empire.syslin.Matrice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
    public static void creationMatrice(Treillis treilli) {

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

    public static void remplissageMatrice(int idNoeudOuForceAjoutee, Force forceAjoutee, Matrice systeme, Treillis treilli) {

        ArrayList<Integer> idNoeudVerifies = new ArrayList<Integer>();
        ArrayList<Integer> idForceLinkMatrice = new ArrayList<Integer>();

        for (Map.Entry mapentry : treilli.getIdentificateur().getKetToObject().entrySet()) {

            int tailleArrayIni = idForceLinkMatrice.size();

            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            
            //Pour les noeuds simples
            if (val.getClass() == Noeud_simple.class) {
                if (!idNoeudVerifies.contains(key)) {
                    ArrayList<Force> noeudSimpleforces = new ArrayList<Force>();
                    //Création des tensions
                    for (int i = 0; i < ((Noeud_simple) val).getAppartientABarre().size(); i++) {
                        Force tension = new Force(((Noeud_simple) val).getAppartientABarre().get(i), ((Noeud_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        tension.setIdentifiant(treilli.getIdentificateurForce().getOrSetKey(tension));
                        noeudSimpleforces.add(tension);
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                    }
                    //Aujout de la force ajoutée si noeud conserné
                    if (((Noeud_simple) val).getID() == idNoeudOuForceAjoutee) {
                        noeudSimpleforces.add(forceAjoutee);
                    }

                    //On ajoute dans la matrice
                    System.out.println("Ajout des forces à la matrice");
                    System.out.println("Liste de référence : \n" + idForceLinkMatrice.toString());
                    for (int i = 0; i < noeudSimpleforces.size(); i++) {
                        if (idForceLinkMatrice.contains(noeudSimpleforces.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != noeudSimpleforces.get(i).getIdentifiant()) {
                                j++;
                            }
                            System.out.println("Les forces à l'id " + noeudSimpleforces.get(i).getIdentifiant() + "sont rangées dans la colonne " + j);
                            systeme.getCoeffs()[i][j] = noeudSimpleforces.get(i).getFx();
                            systeme.getCoeffs()[i + 1][j] = noeudSimpleforces.get(i).getFy();
                            i++;
                        } else {
                            System.out.println("Cette force ne possède pas de colonne");
                            System.out.println("Ajout de la force en colonne "+tailleArrayIni);
                            systeme.getCoeffs()[i][tailleArrayIni] = noeudSimpleforces.get(i).getFx();
                            systeme.getCoeffs()[i + 1][tailleArrayIni] = noeudSimpleforces.get(i).getFy();
                            tailleArrayIni++;
                            i++;
                        }
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
