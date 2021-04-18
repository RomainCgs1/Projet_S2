package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Treillis {

    private Zone_constructible appartient;

    public Treillis() {

    }

    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : TREILLIS
        //<objets contenus dans le treillis, 1 barre par ligne>
        //Fin Treillis
        bW.append("Treillis \n");
        Map<Integer, Object> map = idNum.getKetToObject();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry) it.next();

        }

    }

   /* public void saveCase(String nomClass, Identificateur idNum, Object entry) {

        switch (nomClass) {
            case (nomClass.contains("Noeud_simple"):
                
                if (!idNum.objetPresent(entry)) {
            .save(bW, idNum);
        }
            
        }*/
    
}
