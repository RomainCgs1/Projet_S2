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

    public Identificateur(int valCur, Object o) {
        this.valCur = valCur;

        this.ketToObject = new HashMap<Integer, Object>();
        this.ketToObject.put(valCur, o);

        this.objectToKey = new HashMap<Object, Integer>();
        this.objectToKey.put(o, valCur);
    }
    
    public Identificateur (Object o){
        this.ketToObject = new HashMap<Integer, Object>();
        this.ketToObject.put(valCur, o);

        this.objectToKey = new HashMap<Object, Integer>();
        this.objectToKey.put(o, valCur);
    }

    public int getValCur() {
        return valCur;
    }

    public Map<Integer, Object> getKetToObject() {
        return ketToObject;
    }

    public Map<Object, Integer> getObjectToKey() {
        return objectToKey;
    }
    
    //TODO Méthode pour set un identificateur à un objet s'il en a pas et check s'il en a un 
}
