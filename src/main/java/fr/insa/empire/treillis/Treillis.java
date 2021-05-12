package fr.insa.empire.treillis;

import fr.insa.empire.syslin.Matrice;
import fr.insa.empire.utils.Identificateur;
import java.awt.Color;

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

    public Treillis() {
        this.identificateur = new Identificateur();
        this.identificateurForce = new Identificateur();
        this.identificateurTypeBarre = new Identificateur();
        this.catalogue = new Catalogue();
        this.creationTypeBarre();
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

        for (Map.Entry mapentry : this.identificateur.getKetToObject().entrySet()) {
            Object objectClass = mapentry.getClass();

            if (objectClass == Barre.class) {
                prixTreilli = prixTreilli + ((Barre) objectClass).calculPrixBarre();
            }
        }
        return prixTreilli;
    }

    public void creationTypeBarre(){
        
        Color marron = new Color(163,65,0);
        Type_de_barre bois = new Type_de_barre("bois",5,20,1,10000,10000,marron);
        bois.setIdentifiant(this.getIdentificateurTypeBarre().getOrSetKey(bois));
        this.getCatalogue().getContient().add(bois);
        
        Color grisAcier = new Color(142,142,142);
        Type_de_barre acier = new Type_de_barre("acier",7,30,1,20000,20000, grisAcier);
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

    
    
    //Méthodes pour gérer les SET
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


    
    //Sauvegarde
    public void save(BufferedWriter bW) throws IOException {
        //Format : TREILLIS
        //<objets contenus dans le treillis, 1 barre par ligne>
        //Fin Treillis
        System.out.println("Je suis passé dans sauvegarde treilli");
        bW.append("DEBUT TREILLI \n");
        for (Map.Entry mapentry : this.identificateur.getKetToObject().entrySet()) {
            Object val = mapentry.getValue();
            System.out.println(val.getClass());
            if (val.getClass() == Noeud_simple.class) {
                ((Noeud_simple) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Noeud_appui.class) {
                ((Noeud_appui) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Barre.class) {
                ((Barre) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Segment_terrain.class) {
                ((Segment_terrain) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Triangle_terrain.class) {
                ((Triangle_terrain) val).save(bW, this.identificateur);
            }
        }
        bW.append("FIN TREILLI\n");
    }

    public void open(BufferedReader bR) throws IOException
    {
        System.out.println("Début de la lecture");
        String strCurrentLine;
        while ((strCurrentLine = bR.readLine()) != null) {
            System.out.println(strCurrentLine);
            String[] splitStrCurrLine = strCurrentLine.split("/");
            if(splitStrCurrLine[0].equals("DEBUT TREILLI"))
            {

            }
            else if(splitStrCurrLine[0].equals("FIN TREILLI"))
            {

            }
            else
            {
                if(splitStrCurrLine[0].equals("DEBUT_Triangle_terrain"))
                {
                    //a finir plus tard car on a besoin d'une autre version de sa sauvegarde
                    //on integrera la leture des segements qui lui appartiennent
                }
                else if(splitStrCurrLine[0].toLowerCase().equals("debut_barre"))
                {
                    for(int i=1; i<splitStrCurrLine.length; i++)
                    {
                        //a finir plus tard car on a besoin d'une autre version de sa sauvegarde
                    }
                }
                else if(splitStrCurrLine[0].toLowerCase().equals("noeud_simple"))
                {
                    Noeud_simple noeudSimple = new Noeud_simple(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]));
                    identificateur.getOrSetKey(noeudSimple);
                }
                else if(splitStrCurrLine[0].toLowerCase().equals("noeud_appui_simple"))
                {
                    Appui_simple appuiSimple = new Appui_simple(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]), (Segment_terrain) identificateur.getKetToObject().get(Integer.parseInt(splitStrCurrLine[4])));
                    identificateur.getOrSetKey(appuiSimple);
                }
                else if(splitStrCurrLine[0].toLowerCase().equals("noeud_appui_double"))
                {
                    Appui_double appuiDouble = new Appui_double(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]), (Segment_terrain) identificateur.getKetToObject().get(Integer.parseInt(splitStrCurrLine[4])));
                    identificateur.getOrSetKey(appuiDouble);
                }
                else if(splitStrCurrLine[0].toLowerCase().equals("noeud_appui_encastre"))
                {
                    Appui_encastre appuiEncastre = new Appui_encastre(Double.parseDouble(splitStrCurrLine[2]), Double.parseDouble(splitStrCurrLine[3]), (Segment_terrain) identificateur.getKetToObject().get(Integer.parseInt(splitStrCurrLine[4])));
                    identificateur.getOrSetKey(appuiEncastre);
                }
            }

        }
    }

    public String [][] lancerCalculGeneraux(Noeud_simple noeudSimple, Force fAjoutee){
        //Création des matrices
        Matrice systeme = Force.creationMatrice(this);
        Matrice vecteur = new Matrice(systeme.getNbrLig(),1);
       
        //Création de l'Arraylist de ref
        ArrayList<Integer> listeRef = new ArrayList<Integer>();
        
        //Remplissage des matrices
        Force.remplissageMatrice(noeudSimple.getID(), fAjoutee, systeme, vecteur, this, listeRef);
        System.out.println("Matrice obtenue :\n"+systeme.toString());
        
        //On supprime les colonnes de 0
        systeme = systeme.modifMatrice(listeRef);
        
        // On résout
         Matrice resolution = Force.resSysteme(systeme, vecteur);
        
        //On affiche les résultats et on les récupère par un tableau de String
        String [][] resultats = Force.recupSolution(resolution, listeRef, this);
        
        return resultats;
    }
    
     public String[][] lancerCalculTEST (Noeud_simple noeudSimple, Force fAjoutee){
        
        ArrayList<Integer> listeRef = new ArrayList<Integer>();
        Matrice systeme = Force.creationMatrice(this);
        Matrice vecteur = new Matrice(systeme.getNbrLig(),1);
        System.out.println("Force ajoutée FX : "+fAjoutee.getFx());
        System.out.println("Force ajoutée FY : "+fAjoutee.getFy());
        Force.remplissageMatrice(noeudSimple.getID(), fAjoutee, systeme, vecteur, this, listeRef);
        System.out.println(listeRef.toString());
        System.out.println("Matrice obtenue :\n"+systeme.toString());
        systeme = systeme.modifMatrice(listeRef);
        System.out.println("Début de la résolution...");
        Matrice resolution = Force.resSysteme(systeme, vecteur);
        String [][] resultats = Force.recupSolution(resolution, listeRef, this);
        
        return resultats;
    }
    
    public void testForce() {

        Noeud_simple ns = new Noeud_simple(2, 0);
        ns.setIdentifiant(this.identificateur.getOrSetKey(ns));

        Point p1 = new Point(-2, 0);
        p1.setIdentifiant(identificateur.getOrSetKey(p1));
        System.out.println("Point " + p1.getIdentifiant() + " crée en (" + p1.getPx() + "," + p1.getPy() + ")");

        Point p2 = new Point(0, 2);
        p2.setIdentifiant(identificateur.getOrSetKey(p2));
        System.out.println("Point " + p2.getIdentifiant() + " crée en (" + p2.getPx() + "," + p2.getPy() + ")");

        Point p3 = new Point(0, -2);
        p3.setIdentifiant(identificateur.getOrSetKey(p3));
        System.out.println("Point " + p3.getIdentifiant() + " crée en (" + p3.getPx() + "," + p3.getPy() + ")");

        Segment_terrain seg1 = new Segment_terrain(p1, p2);
        seg1.setIdentifiant(identificateur.getOrSetKey(seg1));
        System.out.println("Seg1 : \n" + seg1.toString());

        Segment_terrain seg2 = new Segment_terrain(p2, p3);
        seg2.setIdentifiant(identificateur.getOrSetKey(seg2));
        System.out.println("Seg2 : \n" + seg2.toString());

        Segment_terrain seg3 = new Segment_terrain(p3, p1);
        seg3.setIdentifiant(identificateur.getOrSetKey(seg3));
        System.out.println("Seg3 : \n" + seg3.toString());

        Triangle_terrain triangle_terrain = new Triangle_terrain(seg1, seg2, seg3);
        triangle_terrain.setIdentifiant(identificateur.getOrSetKey(triangle_terrain));

        Appui_simple as = new Appui_simple(p2.getPx(), p2.getPy(), seg2);
        as.setIdentifiant(identificateur.getOrSetKey(as));
        seg2.addNASet(as);
        System.out.println("Appui_Simple : \n" + as.toString());

        Appui_double ad = new Appui_double(p3.getPx(), p3.getPy(), seg2);
        ad.setIdentifiant(identificateur.getOrSetKey(ad));
        seg2.addNASet(ad);
        System.out.println("Appui_double : \n" + ad.toString());

        Barre b1 = new Barre(as, ns);
        b1.setIdentifiant(identificateur.getOrSetKey(b1));
        as.addBarreArray(b1);
        ns.addBarreSet(b1);
        System.out.println("Barre 1 : \n" + b1.toString());

        Barre b2 = new Barre(ns, ad);
        b2.setIdentifiant(identificateur.getOrSetKey(b2));
        ns.addBarreSet(b2);
        ad.addBarreArray(b2);
        System.out.println("Barre 2 : \n" + b2.toString());

        Barre b3 = new Barre(ad, as);
        b3.setIdentifiant(identificateur.getOrSetKey(b3));
        ad.addBarreArray(b3);
        as.addBarreArray(b3);
        System.out.println("Barre 3 : \n" + b3.toString());
        System.out.println("  ");

       /* //Test de création de force 
        System.out.println("TEST FORCE");

        Force forceAjoutee = new Force(ns, 0, -10000);
        System.out.println("Force ajoutée : \n" + forceAjoutee.toString());

        Force tensionb1 = new Force(b1, b1.calculAngleAlphaTension());
        System.out.println("Tension barre 1 : \n" + tensionb1.toString());

        Force tensionb2 = new Force(b2, b2.calculAngleAlphaTension());
        System.out.println("Tension barre 2 : \n" + tensionb2.toString());

        Force tensionb3 = new Force(b3, b3.calculAngleAlphaTension());
        System.out.println("Tension barre 3 : \n" + tensionb3.toString());

        Force reacAS = new Force(as, as.getSegment_appui().calculAngleBeta());
        System.out.println("Reaction AS : \n" + reacAS.toString());

        Force reacAD = new Force(ad);
        System.out.println("Reaction AD : \n" + reacAD.toString());

        System.out.println("Nombre de tensions créées : " + this.idBarreTensionsCreees.size());
        System.out.println("Nombre de réactions simples créees : " + this.idAppuiSimpleCrees.size());
        System.out.println("Nombre de réactions doubles créees : " + this.idAppuiDoubleCrees.size());

        System.out.println("Force de la barre 1 \n" + b1.getTensionBarre().toString());
        System.out.println("Id de la tension 1 : " + b1.getTensionBarre().getIdentifiant());
        System.out.println("Force de la barre 2 \n" + b2.getTensionBarre().toString());
        System.out.println("Id de la tension 2 : " + b2.getTensionBarre().getIdentifiant());
        System.out.println("Force de la barre 3 \n" + b3.getTensionBarre().toString());
        System.out.println("Id de la tension 3 : " + b3.getTensionBarre().getIdentifiant());

        System.out.println("--------------------------------------------------");*/
       
       
        
        
        
    }
}
