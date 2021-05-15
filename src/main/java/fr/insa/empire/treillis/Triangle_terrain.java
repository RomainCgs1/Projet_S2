package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.IOException;

public class Triangle_terrain {

    private int identifiant;
    private Segment_terrain segment1;
    private Segment_terrain segment2;
    private Segment_terrain segment3;
    
    
    public Triangle_terrain(Segment_terrain seg1, Segment_terrain seg2, Segment_terrain seg3) {
        this.segment1 = seg1;
        this.segment2 = seg2;
        this.segment3 = seg3;
        
        //On set l'attribut des segs
        this.segment1.setAppartient(this);
        this.segment2.setAppartient(this);
        this.segment3.setAppartient(this);
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getIdentifiant() {
        return identifiant;
    }

     public Segment_terrain getSegment1() {
        return segment1;
    }

    public Segment_terrain getSegment2() {
        return segment2;
    }

    public Segment_terrain getSegment3() {
        return segment3;
    }

    public int numerotation(Segment_terrain seg){
        //On regarde à quel segment du triangle correspond le segment en entrée pour donner la bonne valeur de j
        int j ;
        
        //On regarde si c'est le seg 1 du triangle
        if((this.getSegment1().getPointDebut()==seg.getPointDebut())&&(this.getSegment1().getPointFin()==seg.getPointFin())){
            return j=0;
        } else if ((this.getSegment2().getPointDebut()==seg.getPointDebut())&&(this.getSegment2().getPointFin()==seg.getPointFin())){ //Idem pour seg2
            return j=1;
        } else{ //Car si c'est ni le seg1 ni le seg2 c'est seg 3
            return j=2;
        }
    }
    
    public void save(BufferedWriter bW, Identificateur idNum, Point p1, Point p2, Point p3) throws IOException {
        //Format : id/point1/point2/point3/
        bW.append("Triangle;");
        bW.append(this.identifiant + ";");
        p1.save(bW, idNum);
        p2.save(bW, idNum);
        p3.save(bW, idNum);
    }

    public void draw(GraphicsContext graphicsContext)
    {
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeLine(this.segment1.getPointDebut().getPx(), this.segment1.getPointDebut().getPy(), this.segment1.getPointFin().getPx(), this.segment1.getPointFin().getPy());
        graphicsContext.strokeLine(this.segment2.getPointDebut().getPx(), this.segment2.getPointDebut().getPy(), this.segment2.getPointFin().getPx(), this.segment2.getPointFin().getPy());
        graphicsContext.strokeLine(this.segment3.getPointDebut().getPx(), this.segment3.getPointDebut().getPy(), this.segment3.getPointFin().getPx(), this.segment3.getPointFin().getPy());

    }
}
