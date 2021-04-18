package fr.insa.empire.treillis;

public class Triangle_terrain {

    private int identifiant;
    private Segment_terrain segment1;
    private Segment_terrain segment2;
    private Segment_terrain segment3;
    

    public Triangle_terrain(Segment_terrain seg1, Segment_terrain seg2, Segment_terrain seg3) {
        this.segment1 = seg1;
        this.segment2 = seg2;
        this.segment3 = seg3;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getIdentifiant() {
        return identifiant;
    }
    
}