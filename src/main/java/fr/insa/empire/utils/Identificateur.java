/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * but : permets d'attribuer une clef a un objet et inversement
 *
 * @author romai
 */
public class Identificateur {

    private int valCur;
    private Map<Integer, Object> ketToObject;
    private Map<Object, Integer> objectToKey;

    public Identificateur(int valCur) {
        this.valCur = valCur;
        this.ketToObject = new HashMap<Integer, Object>();
        this.objectToKey = new HashMap<Object, Integer>();
    }
    
    public Identificateur (Object o){
        this.valCur =0;
    }

    public int getOrSetKey (Object o){
        
        Integer val = this.objectToKey.get(o);
        if (val != null){
            return val; //Car cela signifie que la clé existe déjà
        }
        else{
            this.objectToKey.put(o, this.valCur);
            this.ketToObject.put(this.valCur, o);
            this.valCur++;
            return this.valCur-1;
        }
    }
    
    public boolean objetPresent (Object o){
        return this.objectToKey.get(o) != null;
    }
    
    //toString
    public String toString()
    {
        String s = "";
        for(int i=0; i<this.valCur; i++)
        {
            s = s + this.objectToKey.get(i) + "\n";
        }
        return s;
    }
    
}
