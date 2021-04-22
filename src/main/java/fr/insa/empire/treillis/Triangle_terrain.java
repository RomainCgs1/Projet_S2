package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import javafx.scene.canvas.Canvas;

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
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : Debut_Triangle_terrain/id/Segment1/id/Segment2/id/Segment3/id/Fin_Traingle_terrain
        bW.append("Debut_Triangle_terrain/");
        bW.append(this.identifiant + "/\n");
        bW.append("Segment1/\n");
        if (!idNum.objetPresent(this.segment1)) {
            this.segment1.save(bW, idNum);
        } else {
            bW.append(idNum.getOrSetKey(this.segment1) + "\n");
        }
        bW.append("Segment2/\n");
        if (!idNum.objetPresent(this.segment2)) {
            this.segment2.save(bW, idNum);
        } else {
            bW.append(idNum.getOrSetKey(this.segment2) + "\n");
        }
        bW.append("Segment3/\n");
        if (!idNum.objetPresent(this.segment3)) {
            this.segment3.save(bW, idNum);
        } else {
            bW.append(idNum.getOrSetKey(this.segment3) + "\n");
        }
        bW.append("Fin_Triangle_terrain\n");
    }
}
