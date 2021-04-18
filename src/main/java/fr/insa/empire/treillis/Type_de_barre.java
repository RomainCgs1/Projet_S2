package fr.insa.empire.treillis;

public class Type_de_barre {

    //Attributs
    private int identifiant;
    private double coutAuMetre;
    private double longMin;
    private double longMax;
    private double resMaxTens;
    private double resMaxComp;

    //Constructeur
    public Type_de_barre(double coutAuMetre, double longMax, double longMin, double resMaxTension, double resMaxCompression) {
        this.coutAuMetre = coutAuMetre;
        this.longMax = longMax;
        this.longMin = longMin;
        this.resMaxComp = resMaxCompression;
        this.resMaxTens = resMaxTension;
    }

    //Encapsulation

    public int getIdentificateur() {
        return identifiant;
    }

    private void setIdentificateur(int identificateur) {
        this.identifiant = identificateur;
    }

    public double getCoutAuMetre() {
        return coutAuMetre;
    }

    private void setCoutAuMetre(double coutAuMetre) {
        this.coutAuMetre = coutAuMetre;
    }

    public double getLongMin() {
        return longMin;
    }
    
    private void setLongMin(double longMin) {
        this.longMin = longMin;
    }

    public double getLongMax() {
        return longMax;
    }

    private void setLongMax(double longMax) {
        this.longMax = longMax;
    }

    public double getResMaxTens() {
        return resMaxTens;
    }

    public void setResMaxTens(double resMaxTens) {
        this.resMaxTens = resMaxTens;
    }

    public double getResMaxComp() {
        return resMaxComp;
    }

    private void setResMaxComp(double resMaxComp) {
        this.resMaxComp = resMaxComp;
    }
    
    
    //toString
    public String toString(){
        String s = "";
        s=s+"Identificateur : "+this.identifiant+"\n";
        s=s+"Coût au mètre : "+this.coutAuMetre+"\n";
        s=s+"La longueur max est : "+this.longMax+"\n";
        s=s+"La longueur min est : "+this.longMin+"\n";
        s=s+"La Tension Max est : "+this.resMaxTens+"\n";
        s=s+"La Compression Max est : "+this.resMaxComp+"\n";
        
        return s;
    }
}