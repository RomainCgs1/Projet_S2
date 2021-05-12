package fr.insa.empire.treillis;

import fr.insa.empire.syslin.Matrice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Force {

    private double fx;
    private double fy;
    private double norme;
    private String typeDeForce;
    private Barre barreAssociee;
    private Appui_simple asAssocie;
    private Appui_double adAssocie;
    private Treillis treilli = Treillis.treillis;
    private int identifiant;

    //Constructeur Tension
    public Force(Barre barre, double angle_alpha) {
        this.typeDeForce = "Tension";
        this.barreAssociee = barre;
        this.fx = Math.cos(angle_alpha);
        this.fy = Math.sin(angle_alpha);
        if (this.treilli.getIdBarreTensionsCreees().contains(barre.getIdentifiant())) {
            System.out.println("La tension de la barre " + barre.getIdentifiant() + " a déjà été rentrée");
            System.out.println("Identifiant récupéré : " + barre.getTensionBarre().getIdentifiant());
            this.identifiant = barre.getTensionBarre().getIdentifiant();
            System.out.println("Id réel :" + this.identifiant);
        } else {
            System.out.println("Nouvelle tension ");
            System.out.println("Barre " + barre.getIdentifiant());
            this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
            System.out.println("Id réel :" + this.identifiant);
            this.treilli.getIdBarreTensionsCreees().add(barre.getIdentifiant());
            System.out.println("Ajout de la force à la barre");
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
        this.typeDeForce = "Reaction Double";
        this.adAssocie = appuiDouble;
        this.fx = 1;
        this.fy = 1;
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
        this.typeDeForce = "Ajout";
        this.fx = fx;
        this.fy = fy;
        this.norme = this.calculNormeForce();
        this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
        System.out.println("Force ajoutée au noeud " + noeudSimple.getID() + " créee");
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
    /**
     * On va calculer le nb d'inconnues pour créer la matrice de la bonne taille
     * Demande une force à l'utilisateur Auprès de chaque noeud, on va récupérer
     * les infos qui nous intéressent On crée une liste d'identifiant en
     * parallèle de la matrice pour pouvoir se repérer
     */
    public static Matrice creationMatrice(Treillis treilli) {

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

//        System.out.println("Nombre de barre : " + nbBarres);
//        System.out.println("Nombre d'Appui_simple : " + nbAppuiSimple);
//        System.out.println("Nombre d'Appui_double : " + nbAppuiDouble);
        int nbInconnues = nbBarres + nbAppuiSimple + 2 * nbAppuiDouble;
//        System.out.println("Nombre d'inconnues : " + nbInconnues);
        int nbLig = nbInconnues;
        int nbCol = nbBarres + nbAppuiDouble + nbAppuiSimple + 2;

        Matrice systeme = new Matrice(nbLig, nbCol);
//        System.out.println("Nouvelle matrice créee");
//        System.out.println("Nombre de lignes : " + nbLig);
//        System.out.println("Nombre de colonnes : " + nbCol);

        return systeme;
    }

    public static void remplissageMatrice(int idNoeudOuForceAjoutee, Force forceAjoutee, Matrice systeme, Matrice vecteur, Treillis treilli, ArrayList<Integer> idForceLinkMatrice) {

        ArrayList<Integer> idNoeudVerifies = new ArrayList<Integer>();

        int l = 0;
        int nbTension = 0;
        int nbReaction = 0;

        for (Map.Entry mapentry : treilli.getIdentificateur().getKetToObject().entrySet()) {

            int tailleArrayIni = idForceLinkMatrice.size();

            Object key = mapentry.getKey();
            Object val = mapentry.getValue();

            //Pour les noeuds simples
            if (val.getClass() == Noeud_simple.class) {
                if (!idNoeudVerifies.contains(key)) {
                    System.out.println("Nouveau Noeud_simple trouvé !");
                    idNoeudVerifies.add(((Noeud_simple) val).getID());
                    ArrayList<Force> noeudsimpleTension = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Noeud_simple) val).getAppartientABarre().size(); i++) {
                        System.out.println("Barre : " + ((Noeud_simple) val).getAppartientABarre().get(i).toString());
                        Force tension = new Force(((Noeud_simple) val).getAppartientABarre().get(i), ((Noeud_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        System.out.println("Type de la tension : " + tension.getTypeDeForce());
                        noeudsimpleTension.add(tension);
                        System.out.println("Tension " + (i + 1) + "créee \n" + "fx :" + tension.getFx() + "\n" + "fy : " + tension.getFy() + "\n");
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                        nbTension++;
                    }

                    //On ajoute dans la matrice
                    System.out.println("Ajout des forces à la matrice");
                    System.out.println("Liste de référence : \n" + idForceLinkMatrice.toString());
                    for (int i = 0; i < noeudsimpleTension.size(); i++) {
                        if (idForceLinkMatrice.contains(noeudsimpleTension.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != noeudsimpleTension.get(i).getIdentifiant()) {
                                j++;
                            }
//                            System.out.println("L'id " + noeudSimpleforces.get(i).getIdentifiant() + " a été retrouvé en position " + j + "dans la liste de référence");
//                            System.out.println("Les forces à l'id " + noeudSimpleforces.get(i).getIdentifiant() + "sont rangées dans la colonne " + j);
                            System.out.println("Force " + noeudsimpleTension.get(i).getIdentifiant() + "ajouté en [" + l + "," + j + "], la composante " + noeudsimpleTension.get(i).getFx());
                            systeme.getCoeffs()[l][j] = noeudsimpleTension.get(i).getFx();
                            System.out.println("Force " + noeudsimpleTension.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + j + "], la composante " + noeudsimpleTension.get(i).getFy());
                            systeme.getCoeffs()[l + 1][j] = noeudsimpleTension.get(i).getFy();
                        } else {
                            System.out.println("Cette force ne possède pas de colonne");
                            System.out.println("Ajout de la force en colonne " + tailleArrayIni);
                            System.out.println("Force " + noeudsimpleTension.get(i).getIdentifiant() + "ajouté en [" + l + "," + tailleArrayIni + "], la composante " + noeudsimpleTension.get(i).getFx());
                            systeme.getCoeffs()[l][tailleArrayIni] = noeudsimpleTension.get(i).getFx();
                            System.out.println("Force " + noeudsimpleTension.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + tailleArrayIni + "], la composante " + noeudsimpleTension.get(i).getFy());
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = noeudsimpleTension.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }

                    //Aujout de la force ajoutée si noeud conserné
                    if (((Noeud_simple) val).getID() == idNoeudOuForceAjoutee) {
                        noeudsimpleTension.add(forceAjoutee);
                        System.out.println("Type de la tension : " + forceAjoutee.getTypeDeForce());
                        System.out.println("Force MAIN ajoutée : \n" + forceAjoutee.toString());
                        System.out.println("Force " + forceAjoutee.getIdentifiant() + "ajouté en [" + l + "," + 0 + "], la composante " + forceAjoutee.getFx());
                        vecteur.getCoeffs()[l][0] = forceAjoutee.getFx();
                        System.out.println("Force " + forceAjoutee.getIdentifiant() + "ajouté en [" + (l + 1) + "," + 0 + "], la composante " + forceAjoutee.getFy());
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
                    System.out.println("Nouvel Appui_simple trouvé !");
                    idNoeudVerifies.add(((Appui_simple) val).getID());
                    ArrayList<Force> appuiSimpleTension = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Appui_simple) val).getAppartientABarre().size(); i++) {
                        System.out.println("Barre : " + ((Appui_simple) val).getAppartientABarre().get(i).toString());
                        Force tension = new Force(((Appui_simple) val).getAppartientABarre().get(i), ((Appui_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        appuiSimpleTension.add(tension);
                        System.out.println("Tension " + (i + 1) + "créee \n" + "fx : " + tension.getFx() + "\n" + "fy : " + tension.getFy() + "\n");
                        System.out.println("Type de la tension : " + tension.getTypeDeForce());
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                        nbTension++;
                    }

                    //On ajoute dans la matrice
                    System.out.println("Ajout des forces à la matrice");
                    System.out.println("Liste de référence : \n" + idForceLinkMatrice.toString());
                    for (int i = 0; i < appuiSimpleTension.size(); i++) {
                        if (idForceLinkMatrice.contains(appuiSimpleTension.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != appuiSimpleTension.get(i).getIdentifiant()) {
                                j++;
                            }
                            System.out.println("L'id " + appuiSimpleTension.get(i).getIdentifiant() + " a été retrouvé en position " + j + "dans la liste de référence");
                            System.out.println("Les forces à l'id " + appuiSimpleTension.get(i).getIdentifiant() + "sont rangées dans la colonne " + j);
                            System.out.println("Force " + appuiSimpleTension.get(i).getIdentifiant() + "ajouté en [" + l + "," + j + "], la composante " + appuiSimpleTension.get(i).getFx());
                            systeme.getCoeffs()[l][j] = appuiSimpleTension.get(i).getFx();
                            System.out.println("Force " + appuiSimpleTension.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + j + "], la composante " + appuiSimpleTension.get(i).getFy());
                            systeme.getCoeffs()[l + 1][j] = appuiSimpleTension.get(i).getFy();
                        } else {
                            System.out.println("Cette force ne possède pas de colonne");
                            System.out.println("Ajout de la force en colonne " + tailleArrayIni);
                            System.out.println("Force " + appuiSimpleTension.get(i).getIdentifiant() + "ajouté en [" + l + "," + tailleArrayIni + "], la composante " + appuiSimpleTension.get(i).getFx());
                            systeme.getCoeffs()[l][tailleArrayIni] = appuiSimpleTension.get(i).getFx();
                            System.out.println("Force " + appuiSimpleTension.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + tailleArrayIni + "], la composante " + appuiSimpleTension.get(i).getFy());
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = appuiSimpleTension.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }
                    tailleArrayIni = idForceLinkMatrice.size();
                    //Création de la force de réaction de l'appui-simple
                    Force reactionAS = new Force(((Appui_simple) val), ((Appui_simple) val).getSegment_appui().calculAngleBeta());
                    System.out.println("Type de la reac AS : " + reactionAS.getTypeDeForce());
                    nbReaction++;
                    idForceLinkMatrice.add(reactionAS.getIdentifiant()); //Composante sur X
                    idForceLinkMatrice.add(-reactionAS.getIdentifiant()); //Composante sur Y 
                    System.out.println("La dernière colonne remplie est " + tailleArrayIni);
                    System.out.println("Ajout de la réaction du noeud");
                    System.out.println("Force " + reactionAS.getIdentifiant() + "ajouté en [" + l + "," + (tailleArrayIni) + "], la composante " + reactionAS.getFx());
                    systeme.getCoeffs()[l][tailleArrayIni] = reactionAS.getFx();
                    System.out.println("Force " + reactionAS.getIdentifiant() + "ajouté en [" + (l + 1) + "," + (tailleArrayIni + 1) + "], la composante " + reactionAS.getFy());
                    systeme.getCoeffs()[l + 1][tailleArrayIni + 1] = reactionAS.getFy();
                    tailleArrayIni = idForceLinkMatrice.size();
                    appuiSimpleTension.removeAll(appuiSimpleTension);
                    l = l + 2;
                }
            }
            if (val.getClass() == Appui_double.class) {
                if (!idNoeudVerifies.contains(key)) {
                    System.out.println("Nouvel Appui_double trouvé !");
                    idNoeudVerifies.add(((Appui_double) val).getID());
                    ArrayList<Force> appuiDoubleTension = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Appui_double) val).getAppartientABarre().size(); i++) {
                        Force tension = new Force(((Appui_double) val).getAppartientABarre().get(i), ((Appui_double) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        appuiDoubleTension.add(tension);
                        System.out.println("Barre : " + ((Appui_double) val).getAppartientABarre().get(i).toString());
                        System.out.println("Tension " + (i + 1) + "créee \n" + "fx : " + tension.getFx() + "\n" + "fy : " + tension.getFy() + "\n");
                        System.out.println("Type de la tension : " + tension.getTypeDeForce());
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                        nbTension++;
                    }

                    //On ajoute dans la matrice
                    System.out.println("Ajout des forces à la matrice");
                    System.out.println("Liste de référence : \n" + idForceLinkMatrice.toString());
                    for (int i = 0; i < appuiDoubleTension.size(); i++) {
                        if (idForceLinkMatrice.contains(appuiDoubleTension.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != appuiDoubleTension.get(i).getIdentifiant()) {
                                j++;
                            }
                            System.out.println("L'id " + appuiDoubleTension.get(i).getIdentifiant() + " a été retrouvé en position " + j + "dans la liste de référence");
                            System.out.println("Les forces à l'id " + appuiDoubleTension.get(i).getIdentifiant() + "sont rangées dans la colonne " + j);
                            System.out.println("Force " + appuiDoubleTension.get(i).getIdentifiant() + "ajouté en [" + l + "," + j + "], la composante " + appuiDoubleTension.get(i).getFx());
                            systeme.getCoeffs()[l][j] = appuiDoubleTension.get(i).getFx();
                            System.out.println("Force " + appuiDoubleTension.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + j + "], la composante " + appuiDoubleTension.get(i).getFy());
                            systeme.getCoeffs()[l + 1][j] = appuiDoubleTension.get(i).getFy();
                        } else {
                            System.out.println("Cette force ne possède pas de colonne");
                            System.out.println("Ajout de la force en colonne " + tailleArrayIni);
                            System.out.println("Force " + appuiDoubleTension.get(i).getIdentifiant() + "ajouté en [" + l + "," + tailleArrayIni + "], la composante " + appuiDoubleTension.get(i).getFx());
                            systeme.getCoeffs()[l][tailleArrayIni] = appuiDoubleTension.get(i).getFx();
                            System.out.println("Force " + appuiDoubleTension.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + tailleArrayIni + "], la composante " + appuiDoubleTension.get(i).getFy());
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = appuiDoubleTension.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }
                    tailleArrayIni = idForceLinkMatrice.size();
                    //Création de la force de réaction de l'appui-simple
                    Force reactionAD = new Force(((Appui_double) val));
                    System.out.println("Type de la reac AD : " + reactionAD.getTypeDeForce());
                    nbReaction++;
                    idForceLinkMatrice.add(reactionAD.getIdentifiant()); //On reconnaitra les composantes sur X
                    idForceLinkMatrice.add(-reactionAD.getIdentifiant()); //On reconnaitra les composantes sur Y
                    System.out.println("La dernière colonne remplie est " + tailleArrayIni);
                    System.out.println("Ajout de la réaction du noeud");
                    System.out.println("Force " + reactionAD.getIdentifiant() + "ajouté en [" + l + "," + (tailleArrayIni) + "], la composante " + reactionAD.getFx());
                    systeme.getCoeffs()[l][tailleArrayIni] = reactionAD.getFx();
                    System.out.println("Force " + reactionAD.getIdentifiant() + "ajouté en [" + (l + 1) + "," + (tailleArrayIni + 1) + "], la composante " + reactionAD.getFy());
                    systeme.getCoeffs()[l + 1][tailleArrayIni + 1] = reactionAD.getFy();
                    tailleArrayIni = idForceLinkMatrice.size();
                    appuiDoubleTension.removeAll(appuiDoubleTension);
                    l = l + 2;
                }
            }
        }
        System.out.println("Nombre de noeuds vérifiés : " + idNoeudVerifies.size());
        idNoeudVerifies.removeAll(idNoeudVerifies);
        System.out.println("Nb tension : " + nbTension);
        System.out.println("Nb reaction : " + nbReaction);
        System.out.println("Liste de référence : " + idForceLinkMatrice.toString());
    }

    public static Matrice resSysteme(Matrice systeme, Matrice vecteur) {

        Matrice resolution = systeme.concatCol(vecteur);
        System.out.println("Matrice systeme : \n" + systeme.toString());
        System.out.println("Matrice vecteur : \n" + vecteur.toString());
        System.out.println("Matrice résolution : \n" + resolution.toString());
        resolution.descenteGauss();
        System.out.println("Resolution  après descente : \n" + resolution.toString());
        resolution = resolution.monterGauss();

        return resolution;
    }

    public static String[][] recupSolution(Matrice resolution, ArrayList<Integer> listeRef, Treillis treilli) {

        //Création du tableau d'affichage des résultats
        String[][] resultat = new String[listeRef.size()][3];
        System.out.println("Taille liste "+ listeRef.size());

        //Création du vecteur solution
        System.out.println("Matrice résolution : \n" + resolution.toString());
        Matrice solution = resolution.recupSolution();
        System.out.println("Matrice solution : \n" + solution.toString());

        //La force dans la colonne 0 sera dans la ligne 0 du vecteur résultat 
        for (int i = 0; i < listeRef.size(); i++) {
            int idForce = Math.abs(listeRef.get(i));
            System.out.println("Id force " + idForce);
            Force forceConcernee = (Force) treilli.getIdentificateurForce().getKetToObject().get(idForce);

            //On regarde si c'est une tension et on agi en conséquence
            if (forceConcernee.getTypeDeForce().contains("Tension")) {
                System.out.println("La force de la colonne " + i + "est la tension de la barre " + forceConcernee.getBarreAssociee().getIdentifiant());
                forceConcernee.setNorme(Math.abs(solution.getCoeffs()[i][0]));
                System.out.println("Sa norme vaut : " + forceConcernee.getnorme());

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

                if (!listeRef.contains(idForce)) {
                    System.out.println("La liste de contient pas " + idForce);
                    forceConcernee.setFx(0);
                }

                if (!listeRef.contains(-idForce)) {
                    System.out.println("La liste de contient pas " + (-idForce));
                    forceConcernee.setFy(0);
                }

                System.out.println("La force est la réaction de l'appui simple " + forceConcernee.getAsAssocie().getID());
                if (listeRef.get(i) > 0) {
                    System.out.println("Positif donc X");
                    forceConcernee.setFx(solution.getCoeffs()[i][0]);

                    resultat[i][0] = "Reaction de l'Appui Simple " + forceConcernee.getAsAssocie().getID();
                    resultat[i][1] = forceConcernee.getFx() + " N";
                    resultat[i][2] = "-";

                } else {
                    System.out.println(listeRef.get(i) + " est négatif donc Y");
                    forceConcernee.setFy(solution.getCoeffs()[i][0]);

                    resultat[i][0] = "Reaction de l'Appui Simple " + forceConcernee.getAsAssocie().getID();
                    resultat[i][1] = forceConcernee.getFy() + " N";
                    resultat[i][2] = "-";
                }

            }

            //On regarde si c'est une réaction d'appui double
            if (forceConcernee.getTypeDeForce().contains("Double")) {

                if (!listeRef.contains(idForce)) {
                    System.out.println("La liste de contient pas " + idForce);
                    forceConcernee.setFx(0);
                }

                if (!listeRef.contains(-idForce)) {
                    System.out.println("La liste de contient pas " + (-idForce));
                    forceConcernee.setFy(0);
                }

                System.out.println("La force est la réaction de l'appui double " + forceConcernee.getAdAssocie().getID());
                if (listeRef.get(i) > 0) {
                    System.out.println("Positif donc X");
                    forceConcernee.setFx(solution.getCoeffs()[i][0]);

                    resultat[i][0] = "Reaction de l'Appui Double " + forceConcernee.getAdAssocie().getID();
                    resultat[i][1] = forceConcernee.getFx() + " N";
                    resultat[i][2] = "-";

                } else {
                    System.out.println(listeRef.get(i) + " est négatif donc Y");
                    System.out.println("i="+i);
                    forceConcernee.setFy(solution.getCoeffs()[i][0]);

                    resultat[i][0] = "Reaction de l'Appui Double " + forceConcernee.getAdAssocie().getID();
                    resultat[i][1] = forceConcernee.getFy() + " N";
                    resultat[i][2] = "-";
                }

            }
        }
        System.out.println("Tableau résultat : \n");
        for (int i = 0; i < resolution.getNbrLig(); i++) {
            System.out.println(resultat[i][0] + " " + resultat[i][1]+" "+resultat[i][2]);
        }
        
        return resultat;
    }

    public void verificationSiForceSupportee(String [][] resultat, int ligne) {

        //On verifie bien qu'il s'agit d'une tension
        if (this.typeDeForce.contains("Tension")) {

            double valeurMaxSupportee = 0;

            //On regarde si c'est une traction ou une compression
            if (this.typeDeForce.contains("Compression")) {
                System.out.println("La force est une compression");
                valeurMaxSupportee = this.getBarreAssociee().getType().getResMaxComp();
            } else {
                System.out.println("La force est une traction");
                valeurMaxSupportee = this.getBarreAssociee().getType().getResMaxTens();
            }

            // On va comparer la norme de la tension avec la valeur max
            if (this.getnorme() > valeurMaxSupportee) {
                System.out.println("La force est trop importante ");
                resultat[ligne][2] = "NON SUPPORTEE";
            }
            
            if (this.getnorme() == valeurMaxSupportee) {
                System.out.println("La force est supportée, mais attention la situation est limite ");
                resultat[ligne][2] = "ETAT LIMITE";
            }
            
            if (this.getnorme() < valeurMaxSupportee) {
                System.out.println("La force est supportée ");
                resultat[ligne][2] = "SUPPORTEE";
            }
            
        } else {
            throw new Error("La force entrée n'est ni une compression ni une traction");
        }
    }
}
