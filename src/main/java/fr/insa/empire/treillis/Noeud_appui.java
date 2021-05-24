package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Noeud_appui extends Noeuds {

    //Attributs
    private double position_alpha;
    private Segment_terrain segment_appui;
    private ArrayList<Barre> appartientABarre;
    private int j;
    private int k;

    //Constructeurs
    public Noeud_appui(double px, double py, Segment_terrain segment_appui)
    {
        this.segment_appui = segment_appui;
        this.j = this.segment_appui.getAppartient().numerotation(segment_appui);
        this.k = (this.j+1)%3;
        this.position_alpha = calcPosAlpha(px, py, segment_appui);
        this.appartientABarre = new ArrayList<Barre>();
        this.segment_appui.addNASet(this);
    }

    private static double calcPosAlpha(double px, double py,Segment_terrain segment_appui) {
        Point p1 = segment_appui.getPointDebut();
        Point p2 = segment_appui.getPointFin();
        double px1 = p1.getPx();
        double py1 = p1.getPy();

        double px2 = p2.getPx();
        double py2 = p2.getPy();

        return Math.sqrt(Math.pow(px-px1, 2) + Math.pow(py-py1, 2)) / Math.sqrt(Math.pow(px2-px1, 2) + Math.pow(py2-py1, 2));
    }

    public Noeud_appui(double position_alpha, Segment_terrain segment_appui) {
        this.position_alpha = position_alpha;
        this.segment_appui = segment_appui;
        this.j = this.segment_appui.getAppartient().numerotation(segment_appui);
        this.k = (this.j+1)%3;
        this.appartientABarre = new ArrayList<Barre>();
        this.segment_appui.addNASet(this);
    }

    //Encpasulation
    public double getPosition_alpha() {
        return position_alpha;
    }

    private void setPosition_alpha(double position_alpha) {
        this.position_alpha = position_alpha;
    }

    public Segment_terrain getSegment_appui() {
        return segment_appui;
    }

    private void setSegment_appui(Segment_terrain segment_appui) {
        this.segment_appui = segment_appui;
    }

    public ArrayList<Barre> getAppartientABarre() {
        return appartientABarre;
    }

    
    

    public void addBarreArray(Barre barre){
        if (!this.appartientABarre.contains(barre)){
            this.appartientABarre.add(barre);  
        }else{
            System.out.println("Cette barre appartient déjà au noeud");
        }
    }
    
    public void removeBarreArray(Barre barre){
       if (this.appartientABarre.contains(barre)){
            this.appartientABarre.remove(barre);  
        }else{
            System.out.println("Cette barre n'appartient pas au noeud");
        }
    }
    
    public void removeAllBarre(){
        this.appartientABarre.removeAll(appartientABarre);
    }
    
    public String toString(){
        String s="";
        s=s+"Position du noeud appui : "+this.position_alpha+"\n";
        s=s+"Le noeud appuis se trouve sur le segment : "+this.segment_appui+"\n";
        
        return s;
    }
    
    @Override
    public void save (BufferedWriter bW, Identificateur idNum)throws IOException{
        //Format : NOEUD_Appui/id appui/ id TriangleTerrain / j / pos_alpha
        bW.append(this.identifiant +";");
        bW.append(this.getSegment_appui().getAppartient().getIdentifiant()+";");
        bW.append(this.j +";");
        bW.append(this.getPosition_alpha()+"\n");
    }

    @Override
    public double getDistanceAuClick(double px, double py) {
        
        double PX = this.getPx();
        double PY = this.getPy();
        
        return Math.sqrt(Math.pow(PX-px,2)+Math.pow(PY-py, 2));     
                       
    }

    @Override
    public double getPx()
    {
        double xSeg = this.segment_appui.getPointFin().getPx() - this.segment_appui.getPointDebut().getPx();
        return this.segment_appui.getPointDebut().getPx() + this.position_alpha * xSeg;
    }

    @Override
    public double getPy()
    {
        double ySeg = this.segment_appui.getPointFin().getPy() - this.segment_appui.getPointDebut().getPy();
        return this.segment_appui.getPointDebut().getPy() + this.position_alpha * ySeg;
    }

}