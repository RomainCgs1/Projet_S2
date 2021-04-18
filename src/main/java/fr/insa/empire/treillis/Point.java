package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;

public class Point {

    private int identifiant;
    private double px;
    private double py;

    public Point() {
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

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }
    
    //Save
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : POINT /id/px/py
        bW.append("Point/");
        bW.append(this.identifiant +"/");
        bW.append(this.px+"/");
        bW.append(this.py+"/");
    }
}