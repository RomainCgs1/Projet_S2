package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Treillis {

    private Zone_constructible appartient;

    public Treillis() {

    }

    //Calcul prix treilli
    public double calculPrixTreilli(Identificateur idNum) {

        double prixTreilli = 0;

        for (Map.Entry mapentry : idNum.getKetToObject().entrySet()) {
            Object objectClass = mapentry.getClass();

            if (objectClass == Barre.class) {
                prixTreilli = prixTreilli + ((Barre) objectClass).calculPrixBarre(((Barre) objectClass).getPrix(), ((Barre) objectClass).getLongueur());
            }
        }
        return prixTreilli;
    }

    //Sauvegarde
    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : TREILLIS
        //<objets contenus dans le treillis, 1 barre par ligne>
        //Fin Treillis

        bW.append("debut Treillis \n");
        for (Map.Entry mapentry : idNum.getKetToObject().entrySet()) {
            Object objectClass = mapentry.getClass();

            if (objectClass == Noeud_simple.class) {
                ((Noeud_simple) objectClass).save(bW, idNum);
            }

            if (objectClass == Noeud_appui.class) {
                ((Noeud_appui) objectClass).save(bW, idNum);
            }

            if (objectClass == Barre.class) {
                ((Barre) objectClass).save(bW, idNum);
            }

            if (objectClass == Segment_terrain.class) {
                ((Segment_terrain) objectClass).save(bW, idNum);
            }

            if (objectClass == Triangle_terrain.class) {
                ((Triangle_terrain) objectClass).save(bW, idNum);
            }
        }
        bW.append("Fin Treilli");
    }

}
