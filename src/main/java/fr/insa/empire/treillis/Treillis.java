package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Treillis {
    
    public static Treillis treillis;

    private Zone_constructible appartient;
    public Identificateur identificateur;
    private Identificateur identificateurForce;
    private Set<Noeud_simple> treilliContientNoeudSimple;
    private Set<Noeud_appui> treilliContientNoeudAppui;
    private Set<Barre> treilliContientBarre;
    private Set<Integer> idBarreTensionsCreees = new HashSet<Integer>();
    private Set<Integer> idAppuiSimpleCrees = new HashSet<Integer>();
    private Set<Integer> idAppuiDoubleCrees = new HashSet<Integer>();

    public Treillis() {
        this.identificateur = new Identificateur();
        this.identificateurForce = new Identificateur();
        this.treilliContientNoeudSimple = new HashSet<Noeud_simple>();
        this.treilliContientNoeudAppui = new HashSet<Noeud_appui>();
        this.treilliContientBarre = new HashSet<Barre>();
        this.idBarreTensionsCreees = new HashSet<Integer>();
        this.idAppuiSimpleCrees = new HashSet<Integer>();
        this.idAppuiDoubleCrees = new HashSet<Integer>();
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

    public Set<Noeud_simple> getTreilliContientNoeudSimple() {
        return treilliContientNoeudSimple;
    }

    public Set<Noeud_appui> getTreilliContientNoeudAppui() {
        return treilliContientNoeudAppui;
    }

    public Set<Barre> getTreilliContientBarre() {
        return treilliContientBarre;
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

    public Set<Integer> getIdBarreTensionsCreees() {
        return idBarreTensionsCreees;
    }

    public Set<Integer> getIdAppuiSimpleCrees() {
        return idAppuiSimpleCrees;
    }

    public Set<Integer> getIdAppuiDoubleCrees() {
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
    
    public void testForce(){
        Identificateur identificateurTest = new Identificateur();
       
        Noeud_simple ns = new Noeud_simple (2,0);
        
        Point p1 = new Point (-2,0);
        p1.setIdentifiant(identificateurTest.getOrSetKey(p1));
        System.out.println("Point "+p1.getIdentifiant()+" crée en ("+p1.getPx()+","+p1.getPy()+")");
       
        Point p2 = new Point (0,2);
        p2.setIdentifiant(identificateurTest.getOrSetKey(p2));
        System.out.println("Point "+p2.getIdentifiant()+" crée en ("+p2.getPx()+","+p2.getPy()+")");
       
        Point p3 = new Point (0,-2);
        p3.setIdentifiant(identificateurTest.getOrSetKey(p3));
        System.out.println("Point "+p3.getIdentifiant()+" crée en ("+p3.getPx()+","+p3.getPy()+")");
        
        Segment_terrain seg1 = new Segment_terrain(p1, p2);
        seg1.setIdentifiant(identificateurTest.getOrSetKey(seg1));
        System.out.println("Seg1 : \n"+seg1.toString());
       
        Segment_terrain seg2 = new Segment_terrain(p2, p3);
        seg2.setIdentifiant(identificateurTest.getOrSetKey(seg2));
        System.out.println("Seg2 : \n"+seg2.toString());
       
        Segment_terrain seg3 = new Segment_terrain(p3, p1);
        seg3.setIdentifiant(identificateurTest.getOrSetKey(seg3));
        System.out.println("Seg3 : \n"+seg3.toString());
       
        Triangle_terrain triangle_terrain = new Triangle_terrain(seg1, seg2, seg3);
        triangle_terrain.setIdentifiant(identificateurTest.getOrSetKey(triangle_terrain));
        
        Appui_simple as = new Appui_simple (p2.getPx(), p2.getPy(), seg2);
        as.setIdentifiant(identificateurTest.getOrSetKey(as));
        System.out.println("Appui_Simple : \n"+as.toString());
        
        Appui_double ad = new Appui_double (p3.getPx(), p3.getPy(),seg2);
        ad.setIdentifiant(identificateurTest.getOrSetKey(ad));
        System.out.println("Appui_double : \n"+ad.toString());
        
        Barre b1 = new Barre (as,ns);
        b1.setIdentifiant(identificateurTest.getOrSetKey(b1));
        System.out.println("Barre 1 : \n"+b1.toString());
        
        Barre b2 = new Barre (ns,ad);
        b2.setIdentifiant(identificateurTest.getOrSetKey(b2));
        System.out.println("Barre 2 : \n"+b2.toString());
        
        Barre b3 = new Barre (ad,as);
        b3.setIdentifiant(identificateurTest.getOrSetKey(b3));
        System.out.println("Barre 3 : \n"+b3.toString());
        System.out.println("  ");
        
        //Test de création de force 
        System.out.println("TEST FORCE");
        
        Force forceAjoutee = new Force (ns,0,-10000);
        System.out.println("Force ajoutée : \n"+forceAjoutee.toString());
        
        Force tensionb1 = new Force (b1,b1.calculAngleAlphaTension());
        System.out.println("Angle alpha tension 1 : "+b1.calculAngleAlphaTension());
        System.out.println("Tension barre 1 : \n"+tensionb1.toString());
        
        Force tensionb2 = new Force (b2, b2.calculAngleAlphaTension());
        System.out.println("Angle alpha tension 2 : "+b2.calculAngleAlphaTension());
        System.out.println("Tension barre 2 : \n"+tensionb2.toString());
        
        Force tensionb3 = new Force (b3, b3.calculAngleAlphaTension());
        System.out.println("Angle alpha tension 3 : "+b3.calculAngleAlphaTension());
        System.out.println("Tension barre 3 : \n"+tensionb3.toString());
        
        Force reacAS = new Force (as, 40);
        System.out.println("Reaction AS : \n"+reacAS.toString());
        
        Force reacAD = new Force (ad);
        System.out.println("Reaction AD : \n"+reacAD.toString());
        
        System.out.println("Nombre de tensions créées : "+this.idBarreTensionsCreees.size());
        System.out.println("Nombre de réactions simples créees : "+this.idAppuiSimpleCrees.size());
        System.out.println("Nombre de réactions doubles créees : "+this.idAppuiDoubleCrees.size());
        
    }
}
