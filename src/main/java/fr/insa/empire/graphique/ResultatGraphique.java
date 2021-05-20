/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Appui_double;
import fr.insa.empire.treillis.Appui_simple;
import fr.insa.empire.treillis.Barre;
import fr.insa.empire.treillis.Noeud_appui;
import fr.insa.empire.treillis.Noeud_simple;
import fr.insa.empire.treillis.Treillis;
import fr.insa.empire.treillis.Triangle_terrain;
import fr.insa.empire.treillis.Zone_constructible;
import java.io.IOException;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author romai
 */
public class ResultatGraphique extends BorderPane {
    
    private final Treillis treillis;
    private final VBox vbContientResultat;
    private final MyCanvas2 canvas;
    private Text text;
    
    public ResultatGraphique (String text , Treillis treillis) throws IOException{
        
        this.treillis = treillis;
        this.text = new Text(text);
        this.vbContientResultat = new VBox (this.text);
        this.canvas = new MyCanvas2(this);
        
        this.setCenter(canvas);
        this.setTop(vbContientResultat);
        
    }


    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public VBox getVbContientResultat() {
        return vbContientResultat;
    }
    
    public void recontruction()
    {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();

        graphicsContext.clearRect(0,0, this.canvas.getWidth(), this.canvas.getHeight());

        for (Map.Entry mapentry : this.treillis.identificateur.getKetToObject().entrySet())
        {
            Object key = mapentry.getKey();
            Object val = mapentry.getValue();
            if(val.getClass() == Appui_double.class)
            {
                ((Appui_double) val).draw(graphicsContext);
                graphicsContext.setStroke(Color.SILVER);
                graphicsContext.strokeOval(((Appui_double) val).getPx() - 5, ((Appui_double) val).getPy() - 5, 10, 10);
            }
            else if(val.getClass() == Appui_simple.class)
            {
                ((Appui_simple) val).draw(graphicsContext);
            }
            else if(val.getClass() == Noeud_appui.class)
            {
                //normalement on sera passé par appui double ou simple
            }
            else if(val.getClass() == Noeud_simple.class)
            {
                ((Noeud_simple) val).draw(graphicsContext);
            }
            else if(val.getClass() == Barre.class)
            {
                ((Barre) val).draw(graphicsContext);
            }
            else if(val.getClass() == Triangle_terrain.class)
            {
                ((Triangle_terrain) val).draw(graphicsContext);
            }
            else if(val.getClass() == Zone_constructible.class)
            {
                ((Zone_constructible) val).draw(graphicsContext);
            }
        }

        graphicsContext.setStroke(Color.GRAY);
        graphicsContext.strokeRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

    }
    
}
