package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Type_de_barre {

    //Attributs
    private int identifiant;
    private String nom;
    private double coutAuMetre;
    private double longMin;
    private double longMax;
    private double resMaxTens;
    private double resMaxComp;
    private Color couleur;
    private ArrayList<Barre> appartient = new ArrayList<Barre>();

    //Constructeur
    public Type_de_barre(String nom, double coutAuMetre, double longMax, double longMin, double resMaxTension, double resMaxCompression, Color couleur) {
        this.nom = nom;
        this.coutAuMetre = coutAuMetre;
        this.longMax = longMax;
        this.longMin = longMin;
        this.resMaxComp = resMaxCompression;
        this.resMaxTens = resMaxTension;
        this.couleur = couleur;
    }

    public Type_de_barre() {
        this.coutAuMetre = 10;
        this.longMax = 500;
        this.longMin = 0.5;
        this.resMaxComp = 1000;
        this.resMaxTens = 1000;
    }

    //Encapsulation
    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
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

    public void addBarreSet(Barre barre) {
        this.appartient.add(barre);
    }

    public void removeBarreSet(Barre barre) {
        this.appartient.remove(barre);
    }

    public void removeAllBarre() {
        this.appartient.removeAll(appartient);
    }

    public String getNom() {
        return nom;
    }

    public Color getCouleur() {
        return couleur;
    }

    public ArrayList<Barre> getAppartient() {
        return appartient;
    }

    //toString
    public String toString() {
        String s = "";
        s = s + "Nom : " + this.nom + "\n";
        s = s + "Identifiant : " + this.identifiant + "\n";
        s = s + "Coût au mètre : " + this.coutAuMetre + "\n";
        s = s + "La longueur max est : " + this.longMax + "\n";
        s = s + "La longueur min est : " + this.longMin + "\n";
        s = s + "La Tension Max est : " + this.resMaxTens + "\n";
        s = s + "La Compression Max est : " + this.resMaxComp + "\n";
        s = s + "Couleur : " + this.couleur;

        return s;
    }

    public void saveTypeDeBarre(BufferedWriter bW, Identificateur idNum) throws IOException {
        //id;cout;longeur min; longeur max; resistance max trac; resistance max compression
        bW.append("Type;");
        bW.append(this.identifiant + ";");
        bW.append(this.getCoutAuMetre()+";");
        bW.append(this.getLongMax()+";");
        bW.append(this.getResMaxTens()+";");
        bW.append(this.getResMaxComp()+"\n");
    }
}
