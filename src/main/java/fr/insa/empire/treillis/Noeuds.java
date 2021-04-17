package fr.insa.empire.treillis;

import java.util.Set;

public class Noeuds {

    protected int identificateur;
    private Set<Barre> extremites;

    public Noeuds() 
    {
        
    }

    public int getID()
    {
        return this.identificateur;
    }
    public void setID(int ID) {
        this.identificateur = ID;
    }

}