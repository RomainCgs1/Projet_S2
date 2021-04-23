package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Noeud_appui extends Noeuds {

    //Attributs
    private double position_alpha;
    private Segment_terrain segment_appui;
    private Set<Barre> appartientABarre;
    private Set<Segment_terrain> appartientASeg;

    //Constructeurs
    public Noeud_appui()
    {
        
    }

    public Noeud_appui(double px, double py, Segment_terrain segment_appui)
    {
        this.segment_appui = segment_appui;
        this.position_alpha = calcPosAlpha(px, py, segment_appui);
        this.appartientABarre = new HashSet<Barre>();
        this.appartientASeg= new HashSet<Segment_terrain>();
        this.appartientASeg.add(segment_appui);
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

    public void addBarreSet(Barre barre){
        this.appartientABarre.add(barre);
    }
    
    public void removeBarreSet(Barre barre){
        this.appartientABarre.remove(barre);
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
        //Format : NOEUD_Appui/id/alpha/Lié à idSEGT
        bW.append("Noeud_Appui/");
        bW.append(this.identifiant +"/");
        bW.append(this.getPosition_alpha()+"/");
        bW.append(this.segment_appui.getIdentifiant()+"\n");
    }

    @Override
    public double getDistanceAuClick(double px, double py) {
        
        double PX = this.getPx();
        double PY = this.getPy();
        
        return Math.sqrt(Math.pow(PX-px,2)+Math.pow(PY-py, 2));     
                       
    }

    public double getPx()
    {
        double xSeg = this.segment_appui.getPointFin().getPx() - this.segment_appui.getPointDebut().getPx();
        return this.segment_appui.getPointDebut().getPx() + this.position_alpha * xSeg;
    }

    public double getPy()
    {
        double ySeg = this.segment_appui.getPointFin().getPy() - this.segment_appui.getPointDebut().getPy();
        return this.segment_appui.getPointDebut().getPy() + this.position_alpha * ySeg;
    }

}