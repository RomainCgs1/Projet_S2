package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Point {

    private int identifiant;
    private double px;
    private double py;
    private Set<Segment_terrain> appartient;

    public Point() {
        this.appartient = new HashSet<Segment_terrain>();
    }
    
    //Constructeur testForce 
    public Point (double px, double py){
        this.px = px;
        this.py = py;
    }
    
    public double getPx()
    {
        return this.px;
    }
    
    public double getPy()
    {
        return this.py;
    }
    
    public void setPx(double px)
    {
        this.px = px;
    }

    public void setPy(double py)
    {
        this.py = py;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public Set<Segment_terrain> getAppartient() {
        return appartient;
    }
    

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
    
    public void addSegTerrainSet(Segment_terrain segTerrain){
        this.appartient.add(segTerrain);
    }
    
    public void removeSegTerrainSet(Segment_terrain segTerrain){
        this.appartient.remove(segTerrain);
    }
    
    public void removeAllBarre(){
        this.appartient.removeAll(appartient);
    }
    
    //Save
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : POINT /id/px/py
        bW.append("Point/");
        bW.append(this.identifiant +"/");
        bW.append(this.px+"/");
        bW.append(this.py+"/\n");
    }

    public String toString()
    {
        return "( " + this.px + " ; " + this.py + ")";
    }
}