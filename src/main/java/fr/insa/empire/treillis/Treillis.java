package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

public class Treillis {

    private Zone_constructible appartient;
    private Identificateur idNum;
    private int identifiant = idNum.getOrSetKey(this);

    public Treillis() {
        this.idNum = new Identificateur();
    }

    public Identificateur getIdNum() {
        return idNum;
    }



}