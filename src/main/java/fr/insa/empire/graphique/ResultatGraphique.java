/*
 Cette classe permet de générer un boderPane personnalisé pour l'affichage des résultas 
des calculs des forces
 */
package fr.insa.empire.graphique;

import fr.insa.empire.treillis.*;

import java.io.IOException;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


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
                graphicsContext.fillText(((Appui_double) val).getID() + "", ((Appui_double) val).getPx() - 20, ((Appui_double) val).getPy());
            }
            else if(val.getClass() == Appui_simple.class)
            {
                ((Appui_simple) val).draw(graphicsContext);

                graphicsContext.fillText(((Appui_simple) val).getID() + "", ((Appui_simple) val).getPx() - 20, ((Appui_simple) val).getPy());
            }
            else if(val.getClass() == Noeud_appui.class)
            {
                //normalement on sera passé par appui double ou simple
            }
            else if(val.getClass() == Noeud_simple.class)
            {
                ((Noeud_simple) val).draw(graphicsContext);
                graphicsContext.fillText(((Noeud_simple) val).getID() + "", ((Noeud_simple) val).getPx() - 20, ((Noeud_simple) val).getPy());
            }
            else if(val.getClass() == Barre.class)
            {
                ((Barre) val).draw(graphicsContext);
                Point milieu = ((Barre) val).getMilieu();
                graphicsContext.fillText(((Barre) val).getIdentifiant() + "", milieu.getPx() - 20, milieu.getPy());
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
