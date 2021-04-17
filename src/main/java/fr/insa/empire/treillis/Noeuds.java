package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public abstract class Noeuds {

    protected int identificateur;
    private Set<Barre> extremites;

    public Noeuds() 
    {
        
    }

    public int getID()
    {
        return this.identificateur;
    }

    public abstract void save (BufferedWriter bW, Identificateur idNum)throws IOException;
}
