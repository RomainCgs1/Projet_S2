package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Treillis {

    private Zone_constructible appartient;
    public Identificateur identificateur;

    public Treillis() {
        this.identificateur = new Identificateur();
    }

    //Calcul prix treilli
    public double calculPrixTreilli() {

        double prixTreilli = 0;

        for (Map.Entry mapentry : this.identificateur.getKetToObject().entrySet()) {
            Object objectClass = mapentry.getClass();

            if (objectClass == Barre.class) {
                prixTreilli = prixTreilli + ((Barre) objectClass).calculPrixBarre();
            }
        }
        return prixTreilli;
    }

    //Sauvegarde
    public void save(BufferedWriter bW) throws IOException {
        //Format : TREILLIS
        //<objets contenus dans le treillis, 1 barre par ligne>
        //Fin Treillis
        System.out.println("Je suis passé dans sauvegarde treilli");
        bW.append("DEBUT TREILLI \n");
        for (Map.Entry mapentry : this.identificateur.getKetToObject().entrySet()) {
            Object val = mapentry.getValue();
            System.out.println(val.getClass());
            if (val.getClass() == Noeud_simple.class) {
                ((Noeud_simple) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Noeud_appui.class) {
                ((Noeud_appui) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Barre.class) {
                ((Barre) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Segment_terrain.class) {
                ((Segment_terrain) val).save(bW, this.identificateur);
            }

            if (val.getClass() == Triangle_terrain.class) {
                ((Triangle_terrain) val).save(bW, this.identificateur);
            }
        }
        bW.append("FIN TREILLI\n");
    }

}
