package fr.insa.empire.treillis;

import java.util.ArrayList;

public class Catalogue {

    public ArrayList <Type_de_barre> contient;
    //public Arraytlist catalogue;

    public Catalogue() {
        
        this.contient = new ArrayList<Type_de_barre>();
        
    }

    public ArrayList<Type_de_barre> getContient() {
        return contient;
    }
    
    

}