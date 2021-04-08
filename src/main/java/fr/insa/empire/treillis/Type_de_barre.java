package fr.insa.empire.treillis;

public class Type_de_barre {

    private int identificateur;
    private double coutAuMetre;
    private double longMin;
    private double longMax;
    private double resMaxTens;
    private double resMaxComp;

    public Type_de_barre(int id, double coutAuMetre, double longMax, double longMin, double resMaxTension, double resMaxCompression) {
        this.identificateur = id;
        this.coutAuMetre = coutAuMetre;
        this.longMax = longMax;
        this.longMin = longMin;
        this.resMaxComp = resMaxCompression;
        this.resMaxTens = resMaxTension;
    }

}