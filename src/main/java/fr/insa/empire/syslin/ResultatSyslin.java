/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.syslin;

/**
 *
 * @author romai
 */
public class ResultatSyslin {
    
    private boolean solutionUnique;
    private Matrice solution;
    
    //Première méthode
    
    //Si il n'a pas trouvé de solution
    public ResultatSyslin () {
        this.solutionUnique = false;
        this.solution = null;
    }
    
    
    //Si un résultat a été trouvé
    public ResultatSyslin (Matrice solution){
        if (solution.getNbrCol() != 1) {
            throw new Error ("Ce n'est pas un vecteur !");
        }
        else {
            this.solutionUnique = true;
            this.solution = solution;
        }
    }
    
    public String toString(){
        
        String s =("Solution unique ? " + this.solutionUnique);
        if (this.solutionUnique == true){
            s= s+"\n";
            s=s+ "La matrice solution est \n"+this.solution;
        }
        
        return s;
    }
    
    //Deuxième méthode : on peut faire avec des statics
    //On peut alors passer les constructeurs en private si on a peur que des gogoles fassent n'importent quoi avec 
    public static ResultatSyslin PasDeSol (){
        return new ResultatSyslin();
    }
    
    public static ResultatSyslin UneSol (Matrice s){
        return new ResultatSyslin(s);
    }
}
