package fr.insa.empire.treillis;

import java.util.Set;

public class Barre {

    //Attributs
    private int identificateur;
    private Noeuds noeudDebut;
    private Noeuds noeudFin;
    private Type_de_barre type;
    
    
    
    //Constructeur
    public Barre(int id, Noeuds noeudDebut, Noeuds noeudFin, Type_de_barre type) {
        this.identificateur = id;
        this.noeudDebut = noeudDebut;
        this.noeudFin = noeudFin;
        this.type = type;
    }
    
    //Encapsulation
    public Noeuds getNoeudDebut (){
        return this.noeudDebut;
    }
    
    public Noeuds getNoeudFin (){
        return this.noeudFin;
    }
    
    public Type_de_barre getType (){
        return this.type;
    }
    
    public void setNoeudDebut (Noeuds noeudDebut){
        this.noeudDebut = noeudDebut;
    }
    
    public void setNoeudFin (Noeuds noeudFin){
        this.noeudFin = noeudFin;
    }
    
    public void setType (Type_de_barre type){
        this.type = type;
    }
    
    //to String 
    public String toString (){
        String s="";
        s= s+"Barre selectionnee : "+this.identificateur +"\n";
        s= s+"Type de la barre : "+ this.type+"\n";
        s= s+"Noeud du debut "+this.noeudDebut+"\n";
        s= s+"Noeud du fin "+this.noeudFin+"\n";
        
        return s;
    }
  
    public Set<Noeuds> extrémités;





}
