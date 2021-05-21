/*
 *Cette classe permet de générer un borderPane personnalisé pour l'affichage des 
informations sur le type de barre qui est sélectionné par l'utilisateur en amont
 */
package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Type_de_barre;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class AfficheInfosTypeBarre extends BorderPane {
    

    private final VBox vbInfosType;
    private Text text;
    
    public AfficheInfosTypeBarre(Type_de_barre typeBarre)throws IOException{
        
        this.text = new Text(typeBarre.toString());
        this.vbInfosType = new VBox(text);
        this.setCenter(vbInfosType);
        
    }
    
            
}
