/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.graphique;

import com.sun.javafx.scene.shape.RectangleHelper;
import fr.insa.empire.treillis.Noeud_appui;
import fr.insa.empire.treillis.Noeud_simple;
import fr.insa.empire.treillis.Noeuds;
import fr.insa.empire.treillis.Treillis;
import fr.insa.empire.utils.Identificateur;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public class MyCanvas extends Canvas {

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private Treillis contient;
    private double distMax = 10;
    //public Set<Triangle_terrain> contient;

    public MyCanvas() {
    }

    @Override
    public double minHeight(double width)
    {
        return 64;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double prefHeight(double width)
    {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height)
    {
        return 64;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width-10);
        super.setHeight(height-10);

        GraphicsContext graphicsContext2D = this.getGraphicsContext2D();
        //graphicsContext2D.clearRect(0, 0, width, height);

        graphicsContext2D.setStroke(Color.GRAY);
        graphicsContext2D.strokeRect(0, 0, width-10, height-10);

        //redraw ce qui a été ajouté
    }

    public Noeud_simple getNoeud_simplePlusProche(double px, double py, Identificateur identificateur)
    {
        Object s = new Object();
        if(identificateur.getKetToObject().isEmpty())
        {
            return null;
        }
        else
        {
            double distance = 100000000;
            double distAct;
            for (Map.Entry mapentry : identificateur.getKetToObject().entrySet())
            {
                Object key = mapentry.getKey();
                Object val = mapentry.getValue();
                if(val.getClass() == Noeud_simple.class)
                {
                    distAct = ((Noeud_simple) val).getDistanceAuClick(px, py);
                    if(distAct < distance)
                    {
                        distance = distAct;
                        s = val;
                    }
                }

                if(distance >= distMax)
                {
                    s = null;
                }
            }
        }

        return (Noeud_simple) s;
    }

    public Noeud_appui getNoeud_appuiPlusProche(double px, double py, Identificateur identificateur)
    {
        Object s = new Object();
        if(identificateur.getKetToObject().isEmpty())
        {
            return null;
        }
        else
        {
            double distance = 100000000;
            double distAct;
            for (Map.Entry mapentry : identificateur.getKetToObject().entrySet())
            {
                Object key = mapentry.getKey();
                Object val = mapentry.getValue();
                if(val.getClass() == Noeud_appui.class)
                {
                    distAct = ((Noeud_appui) val).getDistanceAuClick(px, py);
                    if(distAct < distance)
                    {
                        distance = distAct;
                        s = val;
                    }
                }

                if(distance >= distMax)
                {
                    s = null;
                }
            }
        }

        return (Noeud_appui) s;
    }

    public Noeuds getNoeudPlusProche(double px, double py, Identificateur identificateur)
    {
        Noeud_appui noeudAppuiPlusProche = getNoeud_appuiPlusProche(px, py, identificateur);
        Noeud_simple noeudSimplePlusProche = getNoeud_simplePlusProche(px, py, identificateur);
        if(noeudSimplePlusProche == null && noeudAppuiPlusProche != null)
        {
            return noeudAppuiPlusProche;
        }
        else if(noeudAppuiPlusProche == null && noeudSimplePlusProche != null)
        {
            return noeudSimplePlusProche;
        }
        else if(noeudSimplePlusProche == null && noeudAppuiPlusProche == null)
        {
            return null;
        }
        else if(noeudSimplePlusProche.getDistanceAuClick(px, py) > noeudAppuiPlusProche.getDistanceAuClick(px, py))
        {
            return noeudAppuiPlusProche;
        }
        else
        {
            return noeudSimplePlusProche;
        }
    }
}