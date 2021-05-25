/*
 *Cette classe fait référence à des fonctions liées à l'identificateur
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

    //Attributs
    private final int valInit = 1;
    private int valCur;
    private Map<Integer, Object> ketToObject;
    private Map<Object, Integer> objectToKey;

    //Constructeurs
    public Identificateur(int valCur) {
        this.valCur = valCur;
        this.ketToObject = new HashMap<Integer, Object>();
        this.objectToKey = new HashMap<Object, Integer>();
    }
    
    public Identificateur() {
        this.valCur = this.valInit;
        this.ketToObject = new HashMap<Integer, Object>();
        this.objectToKey = new HashMap<Object, Integer>();
    }

    //Encapsulation
     public Map<Integer, Object> getKetToObject() {
        return ketToObject;
    }

    public Map<Object, Integer> getObjectToKey() {
        return objectToKey;
    }
    
     public Object getObject(int id){
        if( ! this.idExist(id)){
            throw new  Error("identifiant inexistant");
        }
        return this.ketToObject.get(id);
    }
    
    //Cette méthode permet de donner à un objet un identifiant s'il n'en possède pas et de le renvoyer
    //Si l'objet possède déjà un identifiant, la fonction le renvoie également
    public int getOrSetKey (Object o) {

        Integer val = this.objectToKey.get(o);

        if (val != null) {
            return val; //Car cela signifie que la clé existe déjà
        }
        else
        {
            int i = 1;
            while (this.ketToObject.get(i) != null)
            {
                i++;
            }
            this.objectToKey.put(o, i);
            this.ketToObject.put(i, o);
            return i;
        }
    }
    
    //Permet de vérifier si un objet existe au sien de l'identificateur
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
    
    //Pour savoir si un identifiant existe au sien de l'identificateur
    public boolean idExist(int id){
        return this.ketToObject.containsKey(id);
    }
    
    //Permet d'associer un identifiant précis a un objet
    public void associe(int id,Object obj){
        if(this.idExist(id)){
            throw new Error ("identificateur existant");
        }
        this.ketToObject.put(id,obj);
        this.objectToKey.put(obj,id);
    }

    //Permet de vider complètement l'identificateur
    public void clear() {
        this.ketToObject.clear();
        this.objectToKey.clear();
        this.valCur = this.valInit;
    }
}
