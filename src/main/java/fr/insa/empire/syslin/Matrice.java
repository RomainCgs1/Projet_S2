/*
 Cette classe permet la création de matrice
Elle comporte l'ensemble des fonctions modifiants une matrice
Elle comprent aussi la résolution par le pivot de Gauss
 */
package fr.insa.empire.syslin;

import java.util.ArrayList;

public class Matrice {

    private int nbrLig;
    private int nbrCol;
    private double[][] coeffs;

    
    //Constructeurs
    public Matrice(int nl, int nc) {
        this.nbrLig = nl;
        this.nbrCol = nc;
        this.coeffs = new double[nl][nc];
        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                this.coeffs[i][j] = 0;
            }
        }
    }

    public Matrice (int nbLig, int nbCol, double[][] coeffs){
        this.nbrLig = nbLig;
        this.nbrCol = nbCol;
        this.coeffs = coeffs;
    }
    
    
    public Matrice() {
        this.nbrLig = 1;
        this.nbrCol = 1;
        this.coeffs = new double[1][1];
        this.coeffs[0][0] = -404.02;
    }
    
    //Méthode toString
    public String toString() {
        String s;
        s = "";
        for (int i = 0; i < this.nbrLig; i++) {
            s = s + "[";
            for (int j = 0; j < this.getNbrCol(); j++) {
                s = s + String.format("%+4.2E", this.coeffs[i][j]);
                if (j != this.getNbrCol() - 1) {
                    s = s + " ";
                }

            }
            s = s + "]\n";

        }
        return s;
    }


    //Encapsulation
    public int getNbrLig() {
        return this.nbrLig;
    }

    public double get(int lig, int col) {
        return this.coeffs[lig][col];
    }

    public void set(int lig, int col, double nouvelVal) {
        this.coeffs[lig][col] = nouvelVal;
    }

    public int getNbrCol() {
        return nbrCol;
    }

    public double[][] getCoeffs() {
        return coeffs;
    }
    
       public void setCoeffs(double[][] coeffs) {
        this.coeffs = coeffs;
    }

    //Concaténation
    public Matrice concatLig(Matrice n) {
        if (this.nbrCol != n.nbrCol) {
            throw new Error("nombre de cols incompatibles");
        }
        Matrice r = new Matrice(this.nbrLig + n.nbrLig, this.nbrCol);
        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                r.coeffs[i][j] = this.coeffs[i][j];
            }
        }
        for (int i = this.nbrLig; i < this.nbrLig + n.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                r.coeffs[i][j] = n.coeffs[i - this.nbrLig][j];
            }
        }
        return r;
    }
    
     public Matrice concatCol(Matrice m2) {
        if (this.getNbrLig() != m2.getNbrLig()) {
            throw new Error("les matrices doivent avoir même nombre de lignes");
        }
        Matrice res = new Matrice(this.getNbrLig(), this.getNbrCol() + m2.getNbrCol());
        for (int i = 0; i < res.getNbrLig(); i++) {
            for (int j = 0; j < res.getNbrCol(); j++) {
                if (j < this.getNbrCol()) {
                    res.set(i, j, this.get(i, j));
                } else {
                    res.set(i, j, m2.get(i, j - this.getNbrCol()));
                }
            }
        }
        return res;
    }

    //Méthodes pour le pivaut de Gauss
    public void permuteLigne(int i1, int i2) {

        double intermediaire;

        for (int i = 0; i < this.nbrCol; i++) {
            intermediaire = this.coeffs[i1][i];
            this.coeffs[i1][i] = this.coeffs[i2][i];
            this.coeffs[i2][i] = intermediaire;
        }
    }

    public void transvection(int lignePivot, int ligneModifiee) {

        double p = 0;

        if (this.coeffs[lignePivot][lignePivot] == 0) {
            throw new Error("La matrice n'est pas inversible");
        }

        p = this.coeffs[ligneModifiee][lignePivot] / this.coeffs[lignePivot][lignePivot];

        for (int j = 0; j < this.nbrCol; j++) {
            if (j == lignePivot) {
                this.coeffs[ligneModifiee][j] = 0;
            } else {
                this.coeffs[ligneModifiee][j] = this.coeffs[ligneModifiee][j] - (p * this.coeffs[lignePivot][j]);
            }
        }
    }

   public int lignePlusGrandPivot(int e) {
       
       double epsilon_pivot = 0.000000001;
       
        if (e >= this.getNbrLig() || e >= this.getNbrCol()) {
            throw new Error("mauvais indice de pivot : M_e,e doit exister");
        }
        double pivot = Math.abs(this.get(e, e));
        int imax = e;
        for (int i = e + 1; i < this.getNbrLig(); i++) {
            if (Math.abs(this.get(i, e)) > pivot) {
                pivot = Math.abs(this.get(i, e));
                imax = i;
            }
        }
        if (pivot <= epsilon_pivot) {
            return -1;
        } else {
            return imax;
        }

    }

    //Descente de Gauss
    public Matrice descenteGauss() {

        for (int i = 0; i < this.nbrCol-1; i++) {

            int j = i + 1;
            int lignePlusGrandPivot = lignePlusGrandPivot(i);

            if (lignePlusGrandPivot == -1) { //La matrice n'est pas inversible
                Matrice matErreur = new Matrice();
                return matErreur;
            } else {
                if ((lignePlusGrandPivot != i) && (lignePlusGrandPivot != -1)) {
                    permuteLigne(i, lignePlusGrandPivot);
                }
                while (j < this.nbrLig) {
                    transvection(i, j);
                    j++;
                }
              
            }
        }

       return this;
    }

    //Monté de Gauss
    public Matrice monterGauss() {

        Matrice Montee = new Matrice(this.nbrLig, this.nbrCol);
        
        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                Montee.coeffs[i][j] = this.coeffs[i][j] / this.coeffs[i][i];
            }
        }
        for (int i = this.nbrLig - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                    Montee.transvection(i, j);
            }
        }
        return Montee;
    }

    //Transformation des diagonales de la matrice en 1 après descente et montée de Gauss
    public void transfoDiagonales1() {

        for (int i = 0; i < this.nbrLig; i++) {
            double diviseur = this.coeffs[i][i];

            for (int j = 0; j < this.nbrCol; j++) {
                this.coeffs[i][j] = this.coeffs[i][j] / diviseur;
            }
        }
    }

    //Recupération de la matrice solution
    public Matrice recupSolution() {

        Matrice res = new Matrice(this.nbrLig, 1);
        for (int i = 0; i < this.nbrLig; i++) {
            res.coeffs[i][0] = this.coeffs[i][this.nbrCol - 1];
        }

        return res;
    }
    
    //Modification de la matrice si elle possède une colonne de 0
    public Matrice modifMatrice(ArrayList<Integer> listeRef){
        double epsilon = 0.00001;
        int nb0 = 0;
        int nbColNulle = 0;
        
        //On parcourt la matrice pour que les valeurs inférieures à epsilon deviennent nulles
        for (int i = 0; i < this.nbrLig; i++) {
            for (int j = 0; j < this.nbrCol; j++) {
                    if(Math.abs(this.coeffs[i][j])<=epsilon){
                        this.coeffs[i][j] =0;
                    }
            }
        }
        
        return this;
    }
}
