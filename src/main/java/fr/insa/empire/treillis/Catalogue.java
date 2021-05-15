package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Catalogue {

    public ArrayList <Type_de_barre> contient;
    //public Arraytlist catalogue;

    public Catalogue() {
        
        this.contient = new ArrayList<Type_de_barre>();
        
    }

    public ArrayList<Type_de_barre> getContient() {
        return contient;
    }
    
    
    public void saveCatalogue(BufferedWriter bW, Identificateur idNum) throws IOException {
        //On sauvegarde tous les types de barre existant
        for (int i = 0; i < this.getContient().size(); i++) {
                this.getContient().get(i).saveTypeDeBarre(bW, idNum);
        }
    }
}