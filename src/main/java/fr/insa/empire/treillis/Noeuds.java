package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public abstract class Noeuds {

    protected int identifiant;

    public Noeuds() 
    {
        
    }

    public int getID()
    {
        return this.identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public abstract void save (BufferedWriter bW, Identificateur idNum)throws IOException;

    public abstract double getDistanceAuClick(double px, double py);
}
