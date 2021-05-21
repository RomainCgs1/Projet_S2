/*
Dans cette classe est géré tout ce qui est création de force, remplissage de la matrice engendrée par le treillis
Les méthodes s'occupant de lancer les calculs et de récupérer les solutions se trouve aussi dans cette classe
*/

package fr.insa.empire.treillis;

import fr.insa.empire.syslin.Matrice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Force {

    //Attributs
    private double fx;
    private double fy;
    private double norme;
    private String typeDeForce;
    private Barre barreAssociee;
    private Appui_simple asAssocie;
    private Appui_double adAssocie;
    private Treillis treilli = Treillis.treillis;
    private int identifiant;

    
    /*
    On crée 3 constructeurs, chaque constructeur correspondant à un des types de force que le peut 
    trouver à savoir soit une tension, soit une réaction d'appui simple, soit une réaction d'appui double, soit une force ajoutée
    Les constructeurs de tension et de réaction vont vérifier si une tension de la barre observée a déjà été crée ou non
    pour lui accorder le même identifiant si une tension de la barre a été créee. Idem pour les réactions.
    Cela nous permet par la suite de ranger les forces dans la bonne colonne de la matrice
    */
    
    
    //Constructeur Tension
    public Force(Barre barre, double angle_alpha) {
        this.typeDeForce = "Tension";
        this.barreAssociee = barre;
        this.fx = Math.cos(angle_alpha);
        this.fy = Math.sin(angle_alpha);
        if (this.treilli.getIdBarreTensionsCreees().contains(barre.getIdentifiant())) {
            this.identifiant = barre.getTensionBarre().getIdentifiant();
        } else {
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            this.treilli.getIdBarreTensionsCreees().add(barre.getIdentifiant());
            barre.setTensionBarre(this);
        }
    }

    //Constructeur Réaction appui-simple
    public Force(Appui_simple appuiSimple, double angle_beta) {
        this.typeDeForce = "Reaction Simple";
        this.asAssocie = appuiSimple;
        this.fx = Math.cos(angle_beta);
        this.fy = Math.sin(angle_beta);
        if (this.treilli.getIdAppuiSimpleCrees().contains(appuiSimple.getID())) {
            this.identifiant = appuiSimple.getReactionAppuiSimple().getIdentifiant();
       } else {
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            this.treilli.getIdAppuiSimpleCrees().add(appuiSimple.getID());
            appuiSimple.setReactionAppuiSimple(this);
        }
    }

    //Constructeur Réaction appui-double
    public Force(Appui_double appuiDouble) {
        this.typeDeForce = "Reaction Double";
        this .adAssocie = appuiDouble;
        this.fx = 1;
        this.fy = 1;
        if (this.treilli.getIdAppuiDoubleCrees().contains(appuiDouble.getID())) {
           this.identifiant = appuiDouble.getReactionAppuiDouble().getIdentifiant();
        } else {
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            this.treilli.getIdAppuiDoubleCrees().add(appuiDouble.getID());
            appuiDouble.setReactionAppuiDouble(this);
        }
    }

    //Constructeur Force appliquée en Noeud Simple
    public Force(Noeud_simple noeudSimple, double fx, double fy) {
        this.typeDeForce = "Ajout";
        this.fx = fx;
        this.fy = fy;
        this.norme = this.calculNormeForce();
        this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
    }

    //Encapsulation
    public double getFx() {
        return this.fx;
    }

    public double getFy() {
        return this.fy;
    }

    public void setFx(double fx) {
        this.fx = fx;
    }

    public void setFy(double fy) {
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

    public String getTypeDeForce() {
        return typeDeForce;
    }

    public void setNorme(double norme) {
        this.norme = norme;
    }

    public Barre getBarreAssociee() {
        return barreAssociee;
    }

    public Appui_simple getAsAssocie() {
        return asAssocie;
    }

    public Appui_double getAdAssocie() {
        return adAssocie;
    }

    public void setTypeDeForce(String typeDeForce) {
        this.typeDeForce = typeDeForce;
    }

    //To String
    public String toString() {
        String s = "";
        s = s + "id : " + this.identifiant + "\n";
        s = s + "fx : " + this.fx + "\n";
        s = s + "fy : " + this.fy + "\n";
        s = s + "norme : " + this.norme + "\n";
        s = s + "type : " + this.typeDeForce + "\n";
        return s;
    }

    //Matrices et calculs
    /*
    On va crée une matrice de la bonne taille en calculant le nombre d'inconnu avec la formule donnée dans le sujet
    pour obtenir le nombre de colonne et le nombre de ligne avec 2*(nbAppuiDouble + nbAppuiSimple + nbNoeudSimple)
    
     */
    public static Matrice creationMatrice(Treillis treilli) {

        int nbNoeudSimple = 0;
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
            if (val.getClass() == Noeud_simple.class) {
                nbNoeudSimple = nbNoeudSimple + 1;
            }
        }
        int nbInconnues = nbBarres + nbAppuiSimple + 2 * nbAppuiDouble;
        int nbLig = 2 * (nbAppuiDouble + nbAppuiSimple + nbNoeudSimple);
        int nbCol = nbInconnues;

        Matrice systeme = new Matrice(nbLig, nbCol);

        return systeme;
    }

    /*
    Dans cette fonction on va remplir la matrice générée précédemment
    L'idée est que les contructeurs s'occupe de gérer correctement les identitifiants, donc l'objectif est de ranger les forces
    avec le même identifiant dans la même colonne pour toutes les forces à l'exeption de la force ajoutée et des réactions appuis-double
    
    De plus, une arraylist contenant les identifiants des forces "observées" est "liée" à la matrice nous permettant de savoir quelle force ou composante est rangée dans quelle colonne
    En effet, si par exemple on voit un -6 pour une force d'un appui double, on saura que la composante dans cette colonne est la composante sur Y de la force 6
    Et la valeur positive correpondra à la composante sur X
    
    De plus, une arraylist contenant les identifiants des noeuds déjà observés et mise en place pour éviter de regarder plusieurs fois le même noeud et donc pour éviter
    de créer deux fois la même force
    */
    public static void remplissageMatrice(int idNoeudOuForceAjoutee, Force forceAjoutee, Matrice systeme, Matrice vecteur, Treillis treilli, ArrayList<Integer> idForceLinkMatrice) {

        ArrayList<Integer> idNoeudVerifies = new ArrayList<Integer>();

        int l = 0;

        for (Map.Entry mapentry : treilli.getIdentificateur().getKetToObject().entrySet()) {

            int tailleArrayIni = idForceLinkMatrice.size();

            Object key = mapentry.getKey();
            Object val = mapentry.getValue();

            //Pour les noeuds simples
            if (val.getClass() == Noeud_simple.class) {
                if (!idNoeudVerifies.contains(key)) { //On vérifie que le noeud n'a pas déjà été vérifié
                    idNoeudVerifies.add(((Noeud_simple) val).getID());
                    ArrayList<Force> noeudsimpleTension = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Noeud_simple) val).getAppartientABarre().size(); i++) {
                        Force tension = new Force(((Noeud_simple) val).getAppartientABarre().get(i), ((Noeud_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        noeudsimpleTension.add(tension);
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                    }

                    //On ajoute dans la matrice
                    for (int i = 0; i < noeudsimpleTension.size(); i++) {
                        if (idForceLinkMatrice.contains(noeudsimpleTension.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != noeudsimpleTension.get(i).getIdentifiant()) {
                                j++;
                            }
                            systeme.getCoeffs()[l][j] = noeudsimpleTension.get(i).getFx();
                            systeme.getCoeffs()[l + 1][j] = noeudsimpleTension.get(i).getFy();
                        } else {
                            systeme.getCoeffs()[l][tailleArrayIni] = noeudsimpleTension.get(i).getFx();
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = noeudsimpleTension.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }

                    //Aujout de la force ajoutée si c'est le noeud conserné
                    if (((Noeud_simple) val).getID() == idNoeudOuForceAjoutee) {
                        noeudsimpleTension.add(forceAjoutee);
                        vecteur.getCoeffs()[l][0] = forceAjoutee.getFx();
                        vecteur.getCoeffs()[l + 1][0] = forceAjoutee.getFy();
                    }

                    tailleArrayIni = idForceLinkMatrice.size();
                    noeudsimpleTension.removeAll(noeudsimpleTension);
                    l = l + 2;
                }
            }

            //Pour les appuis-simples
            if (val.getClass() == Appui_simple.class) {
                if (!idNoeudVerifies.contains(key)) {
                    idNoeudVerifies.add(((Appui_simple) val).getID());
                    ArrayList<Force> appuiSimpleTension = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Appui_simple) val).getAppartientABarre().size(); i++) {
                        Force tension = new Force(((Appui_simple) val).getAppartientABarre().get(i), ((Appui_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        appuiSimpleTension.add(tension);
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                    }

                    //On ajoute dans la matrice
                    for (int i = 0; i < appuiSimpleTension.size(); i++) {
                        if (idForceLinkMatrice.contains(appuiSimpleTension.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != appuiSimpleTension.get(i).getIdentifiant()) {
                                j++;
                            }
                            systeme.getCoeffs()[l][j] = appuiSimpleTension.get(i).getFx();
                            systeme.getCoeffs()[l + 1][j] = appuiSimpleTension.get(i).getFy();
                        } else {
                            systeme.getCoeffs()[l][tailleArrayIni] = appuiSimpleTension.get(i).getFx();
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = appuiSimpleTension.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }
                    tailleArrayIni = idForceLinkMatrice.size();
                    
                    //Création de la force de réaction de l'appui-simple
                    Force reactionAS = new Force(((Appui_simple) val), ((Appui_simple) val).getSegment_appui().calculAngleBeta());
                    idForceLinkMatrice.add(reactionAS.getIdentifiant());
                    systeme.getCoeffs()[l][tailleArrayIni] = reactionAS.getFx();
                    systeme.getCoeffs()[l + 1][tailleArrayIni] = reactionAS.getFy();
                    tailleArrayIni = idForceLinkMatrice.size();
                    appuiSimpleTension.removeAll(appuiSimpleTension);
                    l = l + 2;
                }
            }
            
            //Pour l'appui double
            if (val.getClass() == Appui_double.class) {
                if (!idNoeudVerifies.contains(key)) {
                    idNoeudVerifies.add(((Appui_double) val).getID());
                    ArrayList<Force> appuiDoubleTension = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Appui_double) val).getAppartientABarre().size(); i++) {
                        Force tension = new Force(((Appui_double) val).getAppartientABarre().get(i), ((Appui_double) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        appuiDoubleTension.add(tension);
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                    }

                    //On ajoute dans la matrice
                    for (int i = 0; i < appuiDoubleTension.size(); i++) {
                        if (idForceLinkMatrice.contains(appuiDoubleTension.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != appuiDoubleTension.get(i).getIdentifiant()) {
                                j++;
                            }
                            systeme.getCoeffs()[l][j] = appuiDoubleTension.get(i).getFx();
                            systeme.getCoeffs()[l + 1][j] = appuiDoubleTension.get(i).getFy();
                        } else {
                            systeme.getCoeffs()[l][tailleArrayIni] = appuiDoubleTension.get(i).getFx();
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = appuiDoubleTension.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }
                    tailleArrayIni = idForceLinkMatrice.size();
                    
                    //Création de la force de réaction de l'appui-double
                    Force reactionAD = new Force(((Appui_double) val));
                    idForceLinkMatrice.add(reactionAD.getIdentifiant()); //On reconnaitra les composantes sur X
                    idForceLinkMatrice.add(-reactionAD.getIdentifiant()); //On reconnaitra les composantes sur Y
                    systeme.getCoeffs()[l][tailleArrayIni] = reactionAD.getFx();
                    systeme.getCoeffs()[l + 1][tailleArrayIni + 1] = reactionAD.getFy();
                    tailleArrayIni = idForceLinkMatrice.size();
                    appuiDoubleTension.removeAll(appuiDoubleTension);
                    l = l + 2;
                }
            }
        }
        idNoeudVerifies.removeAll(idNoeudVerifies);
    }

    public static Matrice resSysteme(Matrice systeme, Matrice vecteur) {

        Matrice resolution = systeme.concatCol(vecteur);
        resolution = resolution.descenteGauss();
        if(resolution.getCoeffs()[0][0]==-404.02){ //cela veut dire que la matrice n'est pas inversible
            return resolution;
        }
        resolution = resolution.monterGauss();

        return resolution;
    }

    public static String[][] recupSolution(Matrice resolution, ArrayList<Integer> listeRef, Treillis treilli) {

        //Création du tableau d'affichage des résultats
        String[][] resultat = new String[listeRef.size()][3];
        
        //Création du vecteur solution
        Matrice solution = resolution.recupSolution();
        
        //La force dans la colonne 0 sera dans la ligne 0 du vecteur résultat et ainsi de suite
        for (int i = 0; i < listeRef.size(); i++) {
            int idForce = Math.abs(listeRef.get(i));
           Force forceConcernee = (Force) treilli.getIdentificateurForce().getKetToObject().get(idForce);

            //On regarde si c'est une tension et on agi en conséquence
            if (forceConcernee.getTypeDeForce().contains("Tension")) {
                forceConcernee.setNorme(Math.abs(solution.getCoeffs()[i][0]));
                
                if (solution.getCoeffs()[i][0] > 0) {
                    forceConcernee.setTypeDeForce("Tension Traction");
                    resultat[i][0] = "Traction de la barre " + forceConcernee.getBarreAssociee().getIdentifiant();
                    resultat[i][1] = forceConcernee.getnorme() + " N";
                } else {
                    forceConcernee.setTypeDeForce("Tension Compression");
                    resultat[i][0] = "Compression de la barre " + forceConcernee.getBarreAssociee().getIdentifiant();
                    resultat[i][1] = forceConcernee.getnorme() + " N";
                }

                forceConcernee.verificationSiForceSupportee(resultat, i);
            }

            //On regarde si c'est une réaction d'appui simple
            if (forceConcernee.getTypeDeForce().contains("Simple")) {

                forceConcernee.setNorme(Math.abs(solution.getCoeffs()[i][0]));
                
                resultat[i][0] = "Reaction de l'Appui-simple " + forceConcernee.getAsAssocie().getID();
                resultat[i][1] = forceConcernee.getnorme() + " N";
                resultat[i][2] = "--";

            }

            //On regarde si c'est une réaction d'appui double
            if (forceConcernee.getTypeDeForce().contains("Double")) {

                if (!listeRef.contains(idForce)) {
                    forceConcernee.setFx(0);
                }

                if (!listeRef.contains(-idForce)) {
                    forceConcernee.setFy(0);
                }

                if (listeRef.get(i) > 0) {
                    forceConcernee.setFx(solution.getCoeffs()[i][0]);

                    resultat[i][0] = "Reaction de l'Appui Double " + forceConcernee.getAdAssocie().getID();
                    resultat[i][1] = forceConcernee.getFx() + " N";
                    resultat[i][2] = "-";

                } else {
                    forceConcernee.setFy(solution.getCoeffs()[i][0]);

                    resultat[i][0] = "Reaction de l'Appui Double " + forceConcernee.getAdAssocie().getID();
                    resultat[i][1] = forceConcernee.getFy() + " N";
                    resultat[i][2] = "-";
                }

            }
        }
        return resultat;
    }

    public void verificationSiForceSupportee(String[][] resultat, int ligne) {

        //On verifie bien qu'il s'agit d'une tension
        if (this.typeDeForce.contains("Tension")) {

            double valeurMaxSupportee = 0;

            //On regarde si c'est une traction ou une compression
            if (this.typeDeForce.contains("Compression")) {
                valeurMaxSupportee = this.getBarreAssociee().getType().getResMaxComp();
            } else {
                valeurMaxSupportee = this.getBarreAssociee().getType().getResMaxTens();
            }

            // On va comparer la norme de la tension avec la valeur max
            if (this.getnorme() > valeurMaxSupportee) {
                resultat[ligne][2] = "NON SUPPORTEE";
            }

            if (this.getnorme() == valeurMaxSupportee) {
                resultat[ligne][2] = "ETAT LIMITE";
            }

            if (this.getnorme() < valeurMaxSupportee) {
                resultat[ligne][2] = "SUPPORTEE";
            }

        } else {
            throw new Error("La force entrée n'est ni une compression ni une traction");
        }
    }
}
