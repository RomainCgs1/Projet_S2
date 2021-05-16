/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.syslin;

import java.util.ArrayList;

public class Matrice {

    private int nbrLig;
    private int nbrCol;
    private double[][] coeffs;

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
    
    public void setCoeffs(double[][] coeffs) {
        this.coeffs = coeffs;
    }
    

    public Matrice() {
        this.nbrLig = 1;
        this.nbrCol = 1;
        this.coeffs = new double[1][1];
        this.coeffs[0][0] = -404.02;
    }
    
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


    //Get et Set
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
        //System.out.println("Permutation de la ligne " + i1 + "avec la ligne " + i2 + " effectuée");
    }

    public void transvection(int lignePivot, int ligneModifiee) {

        double p = 0;

        if (this.coeffs[lignePivot][lignePivot] == 0) {
            throw new Error("La matrice n'est pas inversible");
        }

        p = this.coeffs[ligneModifiee][lignePivot] / this.coeffs[lignePivot][lignePivot];
        //System.out.println("p vaut " + p);

        for (int j = 0; j < this.nbrCol; j++) {
            if (j == lignePivot) {
                this.coeffs[ligneModifiee][j] = 0;
            } else {
                this.coeffs[ligneModifiee][j] = this.coeffs[ligneModifiee][j] - (p * this.coeffs[lignePivot][j]);
            }
        }
        //System.out.println("Transvection de la ligne " + ligneModifiee + " a l'aide du pivot " + this.coeffs[lignePivot][lignePivot] + " effectuée");
       // System.out.println("Transvection \n " + this);
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
            System.out.println("Ligne + grand pivot "+lignePlusGrandPivot);
            if (lignePlusGrandPivot == -1) {
                System.out.println("Matrice non inversible");
                Matrice matErreur = new Matrice();
                return matErreur;
            } else {
                if ((lignePlusGrandPivot != i) && (lignePlusGrandPivot != -1)) {
                    permuteLigne(i, lignePlusGrandPivot);
                    System.out.println("Lignes permutées \n " + this);
                }
                while (j < this.nbrLig) {
                    transvection(i, j);
                    j++;
                }
              
            }
        }
        System.out.println("Descente de Gauss effectuée ");

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
        System.out.println("après remontée : \n" + Montee);
        return Montee;
    }

    //Diagonales en 1
    public void transfoDiagonales1() {

        for (int i = 0; i < this.nbrLig; i++) {
            double diviseur = this.coeffs[i][i];

            for (int j = 0; j < this.nbrCol; j++) {
                this.coeffs[i][j] = this.coeffs[i][j] / diviseur;
            }
        }
        System.out.println("Diagonales transformées");
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
//        //On parcout la matrice et on décalle les colonnes nulles vers la droite
//        for (int i = 0; i < this.nbrCol; i++) {
//            for (int j = 0; j < this.nbrLig; j++) {
//                if(this.coeffs[j][i]==0){
//                    nb0++;
//                }
//            }
//            if(nb0==this.nbrCol-1){
//               // System.out.println("La colonne "+i+" est remplie de 0");
//                if(i != this.nbrCol-1){
//                    listeRef.remove(i);
//                    nbColNulle++;
//                    this.decalageCol(i);
//                    System.out.println("Décalage effectué");
//                    System.out.println("Matrice actuelle :\n"+this.toString());
//                    System.out.println(listeRef.toString());
//                }
//            }
//            nb0=0;
//        }
//        
//        //System.out.println("Nombre de colonnes nulles : "+(nbColNulle-1));
//        
//        Matrice newMatrice = new Matrice (this.nbrLig, this.nbrCol-nbColNulle);
//        for (int i = 0; i < newMatrice.getNbrLig(); i++) {
//            for (int j = 0; j < newMatrice.getNbrCol(); j++) {
//                newMatrice.getCoeffs()[i][j]=this.coeffs[i][j];
//            }
//            
//        }
//        System.out.println("Matrice modifiée : \n"+newMatrice.toString());
//        
//        return newMatrice;
    }
    
    public void decalageCol(int i){
        for (int k = i; k < this.nbrCol-1; k++) {
            for (int j = 0; j < this.nbrLig; j++) {
                if(j != this.nbrCol-1){
                    //System.out.println("La case ["+j+"]["+k+"]"+this.coeffs[j][k]+" devient ["+j+"]["+(k+1)+"]"+this.coeffs[j][k+1]);
                    this.coeffs[j][k]=this.coeffs[j][k+1];
                }
                else{
                    //System.out.println("La case ["+j+"]["+k+"]"+this.coeffs[j][k]+" devient ["+j+"]["+(k+1)+"]"+this.coeffs[j][k+1]);
                    this.coeffs[j][k] = 0;
                }
            }
        }
    }

    
/*
    //Main
    public static void main(String[] args) {
        Matrice.testAlea();
        Matrice.testGauss();
        
        Matrice inversible = matInversible();
        inversible.testSyslin();
    }
*/
}
