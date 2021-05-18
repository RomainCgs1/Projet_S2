package fr.insa.empire.treillis;

import fr.insa.empire.syslin.Matrice;
import fr.insa.empire.utils.Identificateur;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Treillis {

    public static Treillis treillis;

    private Zone_constructible appartient;
    public Identificateur identificateur;
    private Identificateur identificateurForce;
    private Identificateur identificateurTypeBarre;
    private Catalogue catalogue;
    private ArrayList<Noeud_simple> treilliContientNoeudSimple;
    private ArrayList<Noeud_appui> treilliContientNoeudAppui;
    private ArrayList<Barre> treilliContientBarre;
    private ArrayList<Integer> idBarreTensionsCreees = new ArrayList<Integer>();
    private ArrayList<Integer> idAppuiSimpleCrees = new ArrayList<Integer>();
    private ArrayList<Integer> idAppuiDoubleCrees = new ArrayList<Integer>();
    private Type_de_barre currentType;

    public Treillis() {
        this.identificateur = new Identificateur();
        this.identificateurForce = new Identificateur();
        this.identificateurTypeBarre = new Identificateur();
        this.catalogue = new Catalogue();
        this.creationTypeBarre();
        this.currentType = this.catalogue.getContient().get(0); //On set le type de référence
        this.treilliContientNoeudSimple = new ArrayList<Noeud_simple>();
        this.treilliContientNoeudAppui = new ArrayList<Noeud_appui>();
        this.treilliContientBarre = new ArrayList<Barre>();
        this.idBarreTensionsCreees = new ArrayList<Integer>();
        this.idAppuiSimpleCrees = new ArrayList<Integer>();
        this.idAppuiDoubleCrees = new ArrayList<Integer>();
        this.treillis = this;
    }

    //Calcul prix treilli
    public double calculPrixTreilli() {

        double prixTreilli = 0;

        for (int i = 0; i < this.getTreilliContientBarre().size(); i++) {
            prixTreilli = prixTreilli + this.getTreilliContientBarre().get(i).calculPrixBarre();
        }
        
        return prixTreilli;
    }

    public void creationTypeBarre() {
        Color marron = Color.rgb(181, 114, 0);
        Type_de_barre bois = new Type_de_barre("bois", 5, 20, 1, 10000, 10000, marron);
        bois.setIdentifiant(this.getIdentificateurTypeBarre().getOrSetKey(bois));
        this.getCatalogue().getContient().add(bois);

        Color grisAcier = Color.rgb(128, 128, 128);
        Type_de_barre acier = new Type_de_barre("acier", 7, 30, 1, 20000, 20000, grisAcier);
        acier.setIdentifiant(this.getIdentificateurTypeBarre().getOrSetKey(acier));
        this.getCatalogue().getContient().add(acier);

    }

    //Encapsulation 
    public Identificateur getIdentificateur() {
        return identificateur;
    }

    public Identificateur getIdentificateurForce() {
        return identificateurForce;
    }

    public Zone_constructible getAppartient() {
        return appartient;
    }

    public ArrayList<Noeud_simple> getTreilliContientNoeudSimple() {
        return treilliContientNoeudSimple;
    }

    public ArrayList<Noeud_appui> getTreilliContientNoeudAppui() {
        return treilliContientNoeudAppui;
    }

    public ArrayList<Barre> getTreilliContientBarre() {
        return treilliContientBarre;
    }

    public Identificateur getIdentificateurTypeBarre() {
        return identificateurTypeBarre;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

     public void setAppartient(Zone_constructible appartient) {
        this.appartient = appartient;
    }
     
    //Méthodes pour gérer les ArrayList
    public void addBarreSet(Barre barre) {
        this.treilliContientBarre.add(barre);
    }

    public void removeBarreSet(Barre barre) {
        this.treilliContientBarre.remove(barre);
    }

    public void removeAllBarre() {
        this.treilliContientBarre.removeAll(treilliContientBarre);
    }

    public void addNoeudSimpleSet(Noeud_simple ns) {
        this.treilliContientNoeudSimple.add(ns);
    }

    public void removeNoeudSimpleSet(Noeud_simple ns) {
        this.treilliContientNoeudSimple.remove(ns);
    }

    public void removeAllNoeudSimple() {
        this.treilliContientNoeudSimple.removeAll(treilliContientNoeudSimple);
    }

    public void addNoeudAppuiSet(Noeud_appui na) {
        this.treilliContientNoeudAppui.add(na);
    }

    public void removeNoeudAppuiSet(Noeud_appui na) {
        this.treilliContientNoeudAppui.remove(na);
    }

    public void removeAllNoeudAppui() {
        this.treilliContientNoeudAppui.removeAll(treilliContientNoeudAppui);
    }

    public ArrayList<Integer> getIdBarreTensionsCreees() {
        return idBarreTensionsCreees;
    }

    public ArrayList<Integer> getIdAppuiSimpleCrees() {
        return idAppuiSimpleCrees;
    }

    public ArrayList<Integer> getIdAppuiDoubleCrees() {
        return idAppuiDoubleCrees;
    }

    public Type_de_barre getCurrentType() {
        return currentType;
    }

    public void setCurrentType(Type_de_barre currentType) {
        this.currentType = currentType;
    }

    
    //Sauvegarde
    public void save(BufferedWriter bW) throws IOException {
        /*
        On enregistre l'intégralité du treilli
        -zone
        -l'ensemble des triangles FINTRIANGLES
        -catalogue FINCATALOGUE
        -noeuds FINNOEUDS
        -barres FINBARRES
         */

        //Créer des listes pour éviter de parcourir plusieurs fois l'indentificateur
        ArrayList<Noeud_simple> ns = new ArrayList<Noeud_simple>();
        ArrayList<Noeud_appui> na = new ArrayList<Noeud_appui>();
        ArrayList<Barre> barres = new ArrayList<Barre>();
        ArrayList<Triangle_terrain> triangles = new ArrayList<Triangle_terrain>();

        for (Map.Entry mapentry : this.identificateur.getKetToObject().entrySet()) {
            Object val = mapentry.getValue();

            if (val.getClass() == Noeud_simple.class) {
                ns.add(((Noeud_simple) val));
            }

            if (val.getClass() == Appui_simple.class) {
                na.add(((Appui_simple) val));
            }

            if (val.getClass() == Appui_double.class) {
                na.add(((Appui_double) val));
            }

            if (val.getClass() == Barre.class) {
                barres.add(((Barre) val));
            }

            if (val.getClass() == Triangle_terrain.class) {
                triangles.add(((Triangle_terrain) val));
            }
        }

        
        
        //SAVE DANS L'ORDRE IMPOSE
        
        //On save la zone constructible
        this.getAppartient().saveZone(bW, identificateur); 
        
        //On sauvegarde l'ensemble des triangles terrain
        for (int i = 0; i < triangles.size(); i++) { 
                triangles.get(i).save(bW, identificateur, triangles.get(i).getSegment1().getPointDebut(), triangles.get(i).getSegment2().getPointDebut(), triangles.get(i).getSegment3().getPointDebut());
        }
        bW.append("FINTRIANGLES\n");
        
        //On save le catalogue
        this.getCatalogue().saveCatalogue(bW, identificateur);
        bW.append("FINCATALOGUE\n");
        
        //On save les noeuds
        for (int i = 0; i < na.size(); i++) {
            na.get(i).save(bW, identificateur);
        }
        for (int i = 0; i < ns.size(); i++) {
            ns.get(i).save(bW, identificateur);
        }
        bW.append("FINNOEUDS\n");
        
        //On save les barres
        for (int i = 0; i < barres.size(); i++) {
                barres.get(i).save(bW, identificateur);
        }
        bW.append("FINBARRES\n");
    }

    public void open(BufferedReader bR) throws IOException {
        System.out.println("Début de la lecture");
        String strCurrentLine;
        while ((strCurrentLine = bR.readLine()) != null) {
            System.out.println(strCurrentLine);
            String[] splitStrCurrLine = strCurrentLine.split("/");
            if (splitStrCurrLine[0].equals("DEBUT TREILLI")) {

            } else if (splitStrCurrLine[0].equals("FIN TREILLI")) {

            } else {
                if (splitStrCurrLine[0].equals("DEBUT_Triangle_terrain")) {
                    //a finir plus tard car on a besoin d'une autre version de sa sauvegarde
                    //on integrera la leture des segements qui lui appartiennent
                } else if (splitStrCurrLine[0].toLowerCase().equals("debut_barre")) {
                    for (int i = 1; i < splitStrCurrLine.length; i++) {
                        //a finir plus tard car on a besoin d'une autre version de sa sauvegarde
                    }
                } else if (splitStrCurrLine[0].toLowerCase().equals("noeud_simple")) {
                    Noeud_simple noeudSimple = new Noeud_simple(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]));
                    identificateur.getOrSetKey(noeudSimple);
                } else if (splitStrCurrLine[0].toLowerCase().equals("noeud_appui_simple")) {
                    Appui_simple appuiSimple = new Appui_simple(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]), (Segment_terrain) identificateur.getKetToObject().get(Integer.parseInt(splitStrCurrLine[4])));
                    identificateur.getOrSetKey(appuiSimple);
                } else if (splitStrCurrLine[0].toLowerCase().equals("noeud_appui_double")) {
                    Appui_double appuiDouble = new Appui_double(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]), (Segment_terrain) identificateur.getKetToObject().get(Integer.parseInt(splitStrCurrLine[4])));
                    identificateur.getOrSetKey(appuiDouble);
                } else if (splitStrCurrLine[0].toLowerCase().equals("noeud_appui_encastre")) {
                    Appui_encastre appuiEncastre = new Appui_encastre(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]), (Segment_terrain) identificateur.getKetToObject().get(Integer.parseInt(splitStrCurrLine[4])));
                    identificateur.getOrSetKey(appuiEncastre);
                }
            }

        }
    }

    public String[][] lancerCalculGeneraux(Noeud_simple noeudSimple, Force fAjoutee) {
        //Création des matrices
        Matrice systeme = Force.creationMatrice(this);
        Matrice vecteur = new Matrice(systeme.getNbrLig(), 1);

        //Création de l'Arraylist de ref
        ArrayList<Integer> listeRef = new ArrayList<Integer>();

        //Remplissage des matrices
        Force.remplissageMatrice(noeudSimple.getID(), fAjoutee, systeme, vecteur, this, listeRef);
        System.out.println("Matrice obtenue :\n" + systeme.toString());

        //On supprime les colonnes de 0
        systeme = systeme.modifMatrice(listeRef);
        if (systeme.getNbrLig() != systeme.getNbrCol()) { //la matrice engendrée n'est pas carrée
            String[][] erreur = new String[1][1];
            erreur[0][0] = "erreur";
            return erreur;
        }

        // On résout
        Matrice resolution = Force.resSysteme(systeme, vecteur);
        
        if(resolution.getCoeffs()[0][0]==-404.02){ //cela veut dire que la matrice n'est pas inversible
            String[][] erreur = new String[1][1];
            erreur[0][0] = "erreur";
            return erreur;
        }

        //On affiche les résultats et on les récupère par un tableau de String
        String[][] resultats = Force.recupSolution(resolution, listeRef, this);
        
        //On efface les arraylist pour pouvoir recommencer des calculs
        this.getIdBarreTensionsCreees().clear();
        this.getIdAppuiDoubleCrees().clear();
        this.getIdAppuiSimpleCrees().clear();

        return resultats;
    }

    public String[][] lancerCalculTEST(Noeud_simple noeudSimple, Force fAjoutee) {

        ArrayList<Integer> listeRef = new ArrayList<Integer>();

        Matrice systeme = Force.creationMatrice(this);
        Matrice vecteur = new Matrice(systeme.getNbrLig(), 1);

        Force.remplissageMatrice(noeudSimple.getID(), fAjoutee, systeme, vecteur, this, listeRef);
        System.out.println(listeRef.toString());
        System.out.println("Matrice obtenue :\n" + systeme.toString());

        systeme = systeme.modifMatrice(listeRef);
        if (systeme.getNbrLig() != systeme.getNbrCol()) {
            String[][] erreur = new String[1][1];
            erreur[0][0] = "erreur";
            return erreur;
        }

        System.out.println("Début de la résolution...");
        Matrice resolution = Force.resSysteme(systeme, vecteur);

        String[][] resultats = Force.recupSolution(resolution, listeRef, this);

        return resultats;
    }

   
}
