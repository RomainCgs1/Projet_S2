/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.syslin;

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

    //Generation Matrice
    public static Matrice identite(int taille) {
        Matrice m = new Matrice(taille, taille);
        for (int i = 0; i < m.nbrLig; i++) {
            m.coeffs[i][i] = 1;
        }
        return m;
    }

    public static int aleaUnOuDeux() {
        double i;
        i = Math.random();
        if (i <= 0.5) {
            return 1;
        } else {
            return 2;
        }
    }

    public static int aleaUnOuDeuxV2() {
        return (int) (Math.random() * 2 + 1);
    }

    public static Matrice mataleaZeroUnDeux(int nl, int nc,
            double p2) {
        Matrice m = new Matrice(nl, nc);
        double coeff;
        for (int i = 0; i < nl; i++) {
            for (int j = 0; j < nc; j++) {
                coeff = Math.random();
                if (coeff < p2) {
                    m.coeffs[i][j] = 0;
                } else {
                    m.coeffs[i][j] = Matrice.aleaUnOuDeux();
                }
            }
        }
        return m;
    }

    public static Matrice matTest1() {
        int NB = 0;
        Matrice Test1 = new Matrice(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Test1.coeffs[i][j] = NB;
                NB++;
            }
        }
        return Test1;
    }

    public static Matrice matInversible() {
        int NB = 0;
        Matrice Test1 = new Matrice(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Test1.coeffs[i][j] = NB;
                NB++;
            }
        }
        Test1.coeffs[1][1] = -4;
        Test1.coeffs[2][2] = -8;
        return Test1;
    }
    
    public static Matrice vecteur123(){
        Matrice vecteur = new Matrice (3,1);
        for (int i = 0; i < vecteur.nbrLig; i++) {
            vecteur.coeffs[i][0]=i+1;
        }
        return vecteur;
    }

    //TESTS
    public static void testAlea() {
        Matrice m = Matrice.mataleaZeroUnDeux(4, 5, 0.33);
        System.out.println("mat alea : \n" + m);

    }

    public static void testGauss() {
        Matrice m = matInversible();
        System.out.println("mat m initiale \n" + m);
        ResultatSyslin res = m.descenteGauss();
        System.out.println("mat triangulaire inf \n" + m);
        System.out.println("res \n" + res.toString());
        m.monterGauss();
        System.out.println("mat triangulaire sup \n" + m);
        m.transfoDiagonales1();
        System.out.println("mat finale \n" + m);
    }
    
    public void testSyslin (){
        Matrice vecteur = vecteur123();
        ResultatSyslin res = resolution(this,vecteur);
        System.out.println(res);
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
        System.out.println("Permutation de la ligne " + i1 + "avec la ligne " + i2 + " effectuée");
    }

    public void transvection(int lignePivot, int ligneModifiee) {

        double p = 0;

        if (this.coeffs[lignePivot][lignePivot] == 0) {
            throw new Error("La matrice n'est pas inversible");
        }

        p = this.coeffs[ligneModifiee][lignePivot] / this.coeffs[lignePivot][lignePivot];
        System.out.println("p vaut " + p);

        for (int j = 0; j < this.nbrCol; j++) {
            if (j == lignePivot) {
                this.coeffs[ligneModifiee][j] = 0;
            } else {
                this.coeffs[ligneModifiee][j] = this.coeffs[ligneModifiee][j] - (p * this.coeffs[lignePivot][j]);
            }
        }
        System.out.println("Transvection de la ligne " + ligneModifiee + " a l'aide du pivot " + this.coeffs[lignePivot][lignePivot] + " effectuée");
        System.out.println("Transvection \n " + this);
    }

    public int lignePlusGrandPivot(int colPivot) {

        double pivot = 0.00000001; //Correspond à epsilon_pivot
        int ligneplusGrandPivot = -1;

        for (int i = colPivot; i < this.nbrLig; i++) {
            if (Math.abs(this.coeffs[i][colPivot]) > pivot) {
                pivot = this.coeffs[i][colPivot];
                ligneplusGrandPivot = i;
            }
        }
        System.out.println("Le plus grand pivot de la colonne " + colPivot + " se trouve à la ligne " + ligneplusGrandPivot);

        return ligneplusGrandPivot;
    }

    //Descente de Gauss
    public ResultatSyslin descenteGauss() {

        int etape = 0;
        ResultatSyslin res;

        for (int i = 0; i < this.nbrCol; i++) {

            int j = i + 1;
            int lignePlusGrandPivot = lignePlusGrandPivot(i);
            if (lignePlusGrandPivot == -1) {
                //throw new Error ("La matrice n'est pas inversible");
                etape = etape;
            } else {
                if ((lignePlusGrandPivot != i) && (lignePlusGrandPivot != -1)) {
                    permuteLigne(i, lignePlusGrandPivot);
                    System.out.println("Lignes permutées \n " + this);
                }
                while (j < this.nbrLig) {
                    transvection(i, j);
                    j++;
                }
                etape++;
            }
        }
        System.out.println("Descente de Gauss effectuée ");

        if (etape == this.nbrLig) {
            Matrice rep = this.recupSolution();
            res = new ResultatSyslin(rep);
        } else {
            res = new ResultatSyslin();
        }

        return res;
    }

    //Monté de Gauss
    public void monterGauss() {

        for (int i = this.nbrCol - 1; i > -1; i--) {
            int j = i - 1;
            while (j > -1) {
                transvection(i, j);
                j--;
            }
        }
        System.out.println("Montée de Gauss effectuée ");
    }

    //Diagonales en 1
    public void transfoDiagonales1() {

        for (int i = 0; i < this.nbrLig; i++) {
            double diviseur = this.coeffs[i][i];

            for (int j = 0; j < this.nbrCol; j++) {
                this.coeffs[i][j] = this.coeffs[i][j] / diviseur;
            }
        }
        System.out.println("Diagonales trasnformées");
    }

    //Recupération de la matrice solution
    public Matrice recupSolution() {

        Matrice res = new Matrice(this.nbrLig, 1);
        for (int i = 0; i < this.nbrLig; i++) {
            res.coeffs[i][0] = this.coeffs[i][this.nbrCol - 1];
        }

        return res;
    }
    
    
    

    //Résolution du syslin
    public ResultatSyslin resolution(Matrice initiale, Matrice secondMembre) {
        Matrice syslin = initiale.concatCol(secondMembre);
        ResultatSyslin res = syslin.descenteGauss();
        return res;
    }

    //Main
    public static void main(String[] args) {
        Matrice.testAlea();
        Matrice.testGauss();
        
        Matrice inversible = matInversible();
        inversible.testSyslin();
    }

}
