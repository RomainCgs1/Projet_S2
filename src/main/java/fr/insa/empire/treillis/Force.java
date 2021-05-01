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
        this.fx = fx;
        this.fy = fy;
        this.norme = this.calculNormeForce();
        this.identifiant = this.treilli.getIdentificateurForce().getOrSetKey(this);
        System.out.println("Force ajoutée au noeud "+noeudSimple.getID()+" créee");
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
        System.out.println("Nombre d'inconnues : " + nbInconnues);
        int nbLig = nbInconnues;
        int nbCol = nbBarres + nbAppuiDouble + nbAppuiSimple + 2;

        Matrice systeme = new Matrice(nbLig, nbCol);
//        System.out.println("Nouvelle matrice créee");
//        System.out.println("Nombre de lignes : " + nbLig);
//        System.out.println("Nombre de colonnes : " + nbCol);

        return systeme;
    }

    public static void remplissageMatrice(int idNoeudOuForceAjoutee, Force forceAjoutee, Matrice systeme, Matrice vecteur, Treillis treilli) {

        ArrayList<Integer> idNoeudVerifies = new ArrayList<Integer>();
        ArrayList<Integer> idForceLinkMatrice = new ArrayList<Integer>();
        int l = 0;
        int nbTension =0;
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
                        Force tension = new Force(((Noeud_simple) val).getAppartientABarre().get(i), ((Noeud_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        //System.out.println("Alpha vaut \n"+((Noeud_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        noeudsimpleTension.add(tension);
                        System.out.println("Tension "+(i+1)+"créee \n"+"fx"+tension.getFx()+"\n"+"fy"+tension.getFy());
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
                        System.out.println("Force MAIN ajoutée : \n"+forceAjoutee.toString());
                        System.out.println("Force " + forceAjoutee.getIdentifiant() + "ajouté en [" + l + "," + 0+ "], la composante " + forceAjoutee.getFx());
                        vecteur.getCoeffs()[l][0] = -forceAjoutee.getFx();
                        System.out.println("Force " + forceAjoutee.getIdentifiant() + "ajouté en [" + (l + 1) + "," + 0 + "], la composante " + forceAjoutee.getFy());
                        vecteur.getCoeffs()[l + 1][0] = -forceAjoutee.getFy();
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
                        Force tension = new Force(((Appui_simple) val).getAppartientABarre().get(i), ((Appui_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        System.out.println("Alpha vaut \n"+((Appui_simple) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        appuiSimpleTension.add(tension);
                        System.out.println("Tension "+(i+1)+"créee \n"+"fx"+tension.getFx()+"\n"+"fy"+tension.getFy());
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
                    nbReaction++;
                    idForceLinkMatrice.add(reactionAS.getIdentifiant());
                    idForceLinkMatrice.add(reactionAS.getIdentifiant());
                    System.out.println("La dernière colonne remplie est "+tailleArrayIni);
                    System.out.println("Ajout de la réaction du noeud");
                    System.out.println("Force " + reactionAS.getIdentifiant() + "ajouté en [" + l + "," + (tailleArrayIni) + "], la composante " + reactionAS.getFx());
                    systeme.getCoeffs()[l][tailleArrayIni] = reactionAS.getFx();
                    System.out.println("Force " + reactionAS.getIdentifiant() + "ajouté en [" + (l+1) + "," + (tailleArrayIni+1) + "], la composante " + reactionAS.getFy());
                    systeme.getCoeffs()[l + 1][tailleArrayIni+1] = reactionAS.getFy();
                    tailleArrayIni = idForceLinkMatrice.size();
                    appuiSimpleTension.removeAll(appuiSimpleTension);
                    l = l + 2;
                }
            }
            if (val.getClass() == Appui_double.class) {
                if (!idNoeudVerifies.contains(key)) {
                    System.out.println("Nouvel Appui_double trouvé !");
                    idNoeudVerifies.add(((Appui_double) val).getID());
                    ArrayList<Force> appuiDoubleForce = new ArrayList<Force>();

                    //Création des tensions
                    for (int i = 0; i < ((Appui_double) val).getAppartientABarre().size(); i++) {
                        Force tension = new Force(((Appui_double) val).getAppartientABarre().get(i), ((Appui_double) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        System.out.println("Alpha vaut \n"+((Appui_double) val).getAppartientABarre().get(i).calculAngleAlphaTension());
                        appuiDoubleForce.add(tension);
                        System.out.println("Tension "+(i+1)+"créee \n"+"fx"+tension.getFx()+"\n"+"fy"+tension.getFy());
                        if (!idForceLinkMatrice.contains(tension.getIdentifiant())) {
                            idForceLinkMatrice.add(tension.getIdentifiant());
                        }
                        nbTension++;
                    }

                    //On ajoute dans la matrice
                    System.out.println("Ajout des forces à la matrice");
                    System.out.println("Liste de référence : \n" + idForceLinkMatrice.toString());
                    for (int i = 0; i < appuiDoubleForce.size(); i++) {
                        if (idForceLinkMatrice.contains(appuiDoubleForce.get(i).getIdentifiant())) {
                            int j = 0;
                            while (idForceLinkMatrice.get(j) != appuiDoubleForce.get(i).getIdentifiant()) {
                                j++;
                            }
                            System.out.println("L'id " + appuiDoubleForce.get(i).getIdentifiant() + " a été retrouvé en position " + j + "dans la liste de référence");
                            System.out.println("Les forces à l'id " + appuiDoubleForce.get(i).getIdentifiant() + "sont rangées dans la colonne " + j);
                            System.out.println("Force " + appuiDoubleForce.get(i).getIdentifiant() + "ajouté en [" + l + "," + j + "], la composante " + appuiDoubleForce.get(i).getFx());
                            systeme.getCoeffs()[l][j] = appuiDoubleForce.get(i).getFx();
                            System.out.println("Force " + appuiDoubleForce.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + j + "], la composante " + appuiDoubleForce.get(i).getFy());
                            systeme.getCoeffs()[l + 1][j] = appuiDoubleForce.get(i).getFy();
                        } else {
                            System.out.println("Cette force ne possède pas de colonne");
                            System.out.println("Ajout de la force en colonne " + tailleArrayIni);
                            System.out.println("Force " + appuiDoubleForce.get(i).getIdentifiant() + "ajouté en [" + l + "," + tailleArrayIni + "], la composante " + appuiDoubleForce.get(i).getFx());
                            systeme.getCoeffs()[l][tailleArrayIni] = appuiDoubleForce.get(i).getFx();
                            System.out.println("Force " + appuiDoubleForce.get(i).getIdentifiant() + "ajouté en [" + (l + 1) + "," + tailleArrayIni + "], la composante " + appuiDoubleForce.get(i).getFy());
                            systeme.getCoeffs()[l + 1][tailleArrayIni] = appuiDoubleForce.get(i).getFy();
                            tailleArrayIni++;
                        }
                    }
                    tailleArrayIni = idForceLinkMatrice.size();
                    //Création de la force de réaction de l'appui-simple
                    Force reactionAD = new Force(((Appui_double) val));
                    nbReaction++;
                    idForceLinkMatrice.add(reactionAD.getIdentifiant());
                    idForceLinkMatrice.add(reactionAD.getIdentifiant());
                    System.out.println("La dernière colonne remplie est "+tailleArrayIni);
                    System.out.println("Ajout de la réaction du noeud");
                    System.out.println("Force " + reactionAD.getIdentifiant() + "ajouté en [" + l + "," + (tailleArrayIni) + "], la composante " + reactionAD.getFx());
                    systeme.getCoeffs()[l][tailleArrayIni] = -reactionAD.getFx();
                    System.out.println("Force " + reactionAD.getIdentifiant() + "ajouté en [" + (l+1) + "," + (tailleArrayIni+1) + "], la composante " + reactionAD.getFy());
                    systeme.getCoeffs()[l + 1][tailleArrayIni+1] = -reactionAD.getFy();
                    tailleArrayIni = idForceLinkMatrice.size();
                    appuiDoubleForce.removeAll(appuiDoubleForce);
                    l = l + 2;
                }
            }
        }
        System.out.println("Nombre de noeuds vérifiés : " + idNoeudVerifies.size());
        idNoeudVerifies.removeAll(idNoeudVerifies);
        System.out.println("Nb tension : "+nbTension);
        System.out.println("Nb reaction : "+nbReaction);
        System.out.println("Liste de référence : "+idForceLinkMatrice.toString());
    }
    
    public static void resSysteme(Matrice systeme, Matrice vecteur){
        
        Matrice resolution = systeme.concatCol(vecteur);
        System.out.println("Matrice systeme : \n"+systeme.toString());
        System.out.println("Matrice vecteur : \n"+vecteur.toString());
        System.out.println("Matrice résolution : \n"+resolution.toString());
        resolution.descenteGauss();
        System.out.println("Resolution  après descente : \n"+resolution.toString());
        resolution.monterGauss();
        System.out.println("Resolution  après montée : \n"+resolution.toString());
        
    }
}
