/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.utils;

import java.util.HashMap;
import java.util.Map;
import jdk.vm.ci.meta.PlatformKind.Key;

/**
 * but : permets d'attribuer une clef a un objet et inversement
 *
 * @author romai
 */
public class Identificateur {

    private int valCur;
    private Map<Key, Object> ketToObject;
    private Map<Object, Key> objectToKey;

    public Identificateur(int valCur, Object o) {
        this.valCur = valCur;
        //this.ketToObject = new HashMap<this.valCur,> ();
       // this.objectToKey = new HashMap<o, this.valCur > ();
    }
}
