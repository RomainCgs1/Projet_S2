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

    public void save(BufferedWriter bW, Identificateur idNum) throws IOException {
        //Format : TREILLIS
        //<objets contenus dans le treillis, 1 barre par ligne>
        //Fin Treillis
        
        bW.append("Treillis \n");
        Map<Integer, Object> map = idNum.getKetToObject();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry) it.next();
            boolean bns = entry.getClass().isInstance(Noeud_simple.class);
            boolean bna = entry.getClass().isInstance(Noeud_appui.class);
            boolean bbarre = entry.getClass().isInstance(Barre.class);
            boolean bSegT = entry.getClass().isInstance(Segment_terrain.class);
            boolean bTriangleT = entry.getClass().isInstance(Triangle_terrain.class);
            
            if (bns==true){
              //TODO  
            }
            if (bna==true){
                //TODO  
            }
            if (bbarre==true){
                //TODO  
            }
            if (bSegT==true){
                //TODO  
            }
            if (bTriangleT==true){
                //TODO  
            }
        }

    }

   
    
}
