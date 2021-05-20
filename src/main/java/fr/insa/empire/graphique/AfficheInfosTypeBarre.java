/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Type_de_barre;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author romai
 */
public class AfficheInfosTypeBarre extends BorderPane {
    

    private final VBox vbInfosType;
    private Text text;
    
    public AfficheInfosTypeBarre(Type_de_barre typeBarre)throws IOException{
        
        this.text = new Text(typeBarre.toString());
        this.vbInfosType = new VBox(text);
        this.setCenter(vbInfosType);
        
    }
    
            
}
