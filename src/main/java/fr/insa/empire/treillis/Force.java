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
    private Treillis treilli;
    private Identificateur identificateurForce = new Identificateur();
    private int identifiant;
    private Set<Integer> idBarreTensionsCreees = new HashSet<Integer>();
    private Set<Integer> idAppuiSimpleCrees = new HashSet<Integer>();
    private Set<Integer> idAppuiDoubleCrees = new HashSet<Integer>();

    //Constructeurs
    //Constructeur Tension
    public Force(Barre barre, double angle_alpha) {
        this.fx = Math.cos(angle_alpha);
        this.fy = Math.sin(angle_alpha);
        if (this.idBarreTensionsCreees.contains(barre.getIdentifiant())) {
            this.identifiant = barre.getTensionBarre().getIdentifiant();
        } else {
            this.identifiant = this.identificateurForce.getOrSetKey(this);
            this.idBarreTensionsCreees.add(barre.getIdentifiant());
        }
    }

    //Constructeur Réaction appui-simple
    public Force(Appui_simple appuiSimple, double angle_beta) {
        this.fx = Math.cos(angle_beta);
        this.fy = Math.sin(angle_beta);
        if (this.idAppuiSimpleCrees.contains(appuiSimple.getID())) {
            this.identifiant = appuiSimple.getReactionAppuiSimple().getIdentifiant();
        } else {
            this.identifiant = this.identificateurForce.getOrSetKey(this);
            this.idAppuiSimpleCrees.add(appuiSimple.getID());
        }
    }

    //Constructeur Réaction appui-double
    public Force(Appui_double appuiDouble) {
        if (this.idAppuiDoubleCrees.contains(appuiDouble.getID())) {
            this.identifiant = appuiDouble.getReactionAppuiDouble().getIdentifiant();
        } else {
            this.identifiant = this.identificateurForce.getOrSetKey(this);
            this.idAppuiSimpleCrees.add(appuiDouble.getID());
        }
    }

    //Constructeur Force appliquée en Noeud Simple
    public Force(Noeud_simple noeudSimple, double fx, double fy) {
        this.fx = fx;
        this.fy = fy;
        this.norme = this.calculNormeForce();
        this.identifiant = this.identificateurForce.getOrSetKey(this);
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

    public Identificateur getIdentificateurForce() {
        return identificateurForce;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public double calculNormeForce() {
        return this.norme = Math.sqrt(fx * fx + fy * fy);
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
                if (!idNoeudVerifies.contains(key)){
                Iterator it = ((Noeud_simple)val).getAppartientABarre().iterator();
                while(it.hasNext()){
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
