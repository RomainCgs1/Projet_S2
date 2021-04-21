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

        bW.append("debut Treillis \n");
        for (Map.Entry mapentry : this.identificateur.getKetToObject().entrySet()) {
            Object objectClass = mapentry.getClass();

            if (objectClass == Noeud_simple.class) {
                ((Noeud_simple) objectClass).save(bW, this.identificateur);
            }

            if (objectClass == Noeud_appui.class) {
                ((Noeud_appui) objectClass).save(bW, this.identificateur);
            }

            if (objectClass == Barre.class) {
                ((Barre) objectClass).save(bW, this.identificateur);
            }

            if (objectClass == Segment_terrain.class) {
                ((Segment_terrain) objectClass).save(bW, this.identificateur);
            }

            if (objectClass == Triangle_terrain.class) {
                ((Triangle_terrain) objectClass).save(bW, this.identificateur);
            }
        }
        bW.append("Fin Treilli");
    }

}
