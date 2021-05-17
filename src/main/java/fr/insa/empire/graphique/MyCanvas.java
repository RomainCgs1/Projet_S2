/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.graphique;

import com.sun.javafx.scene.shape.RectangleHelper;
import fr.insa.empire.treillis.*;
import fr.insa.empire.utils.Identificateur;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.App;

import java.util.Map;
import java.util.Set;

public class MyCanvas extends Canvas {

    private MainGraphique mainGraphique;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private Treillis contient;
    private double distMax = 10;
    //public Set<Triangle_terrain> contient;

    public MyCanvas(MainGraphique mainGraphique)
    {
        this.mainGraphique = mainGraphique;
        super.setHeight(500);
        super.setWidth(500);
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

        /*GraphicsContext graphicsContext2D = this.getGraphicsContext2D();
        graphicsContext2D.clearRect(0, 0, width, height);

        graphicsContext2D.setStroke(Color.GRAY);
        graphicsContext2D.strokeRect(0, 0, width-10, height-10);*/


        //redraw ce qui a été ajouté
        mainGraphique.recontruction();
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
     

    public Appui_simple getNoeud_appui_simplePlusProche(double px, double py, Identificateur identificateur)
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
                if(val.getClass() == Appui_simple.class)
                {
                    distAct = ((Appui_simple) val).getDistanceAuClick(px, py);
                    if(distAct < distance)
                    {
                        distance = distAct;
                        s = val;
                    }
                }
            }

            if(distance >= distMax)
            {
                s = null;
            }
        }

        return (Appui_simple) s;
    }

    public Appui_double getNoeud_appui_doublePlusProche(double px, double py, Identificateur identificateur)
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
                if(val.getClass() == Appui_double.class)
                {
                    distAct = ((Appui_double) val).getDistanceAuClick(px, py);
                    if(distAct < distance)
                    {
                        distance = distAct;
                        s = val;
                    }
                }
            }

            if(distance >= distMax)
            {
                s = null;
            }
        }

        return (Appui_double) s;
    }

    public Noeud_appui getNoeud_appuiPlusProche(double px, double py, Identificateur identificateur)
    {
        /*Object s = new Object();
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
            }

            if(distance >= distMax)
            {
                s = null;
            }
        }

        return (Noeud_appui) s;*/

        Appui_double appuiDouble = getNoeud_appui_doublePlusProche(px, py, identificateur);
        Appui_simple appuiSimple = getNoeud_appui_simplePlusProche(px, py, identificateur);
        if(appuiDouble != null && appuiSimple != null)
        {
            if(appuiDouble.getDistanceAuClick(px, py) >= appuiSimple.getDistanceAuClick(px, py))
            {
                return appuiDouble;
            }
            else
            {
                return appuiSimple;
            }
        }
        else if(appuiDouble == null && appuiSimple != null)
        {
            return appuiSimple;
        }
        else
        {
            return appuiDouble;
        }
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

    public Segment_terrain getSegmentTerrainPlusProche(double px, double py, Identificateur identificateur) {
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
                if(val.getClass() == Segment_terrain.class)
                {
                    distAct = ((Segment_terrain) val).getDistanceAuClic(px, py);
                    if(distAct < distance)
                    {
                        distance = distAct;
                        s = val;
                    }
                }
            }

            if(distance >= distMax)
            {
                s = null;
            }
            System.out.println("Distance : " + distance);
        }

        return (Segment_terrain) s;
    }

    public Barre getBarrePlusProche(double px, double py, Identificateur identificateur) {
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
                if(val.getClass() == Barre.class)
                {
                    distAct = ((Barre) val).getDistanceAuClic(px, py);
                    if(distAct < distance)
                    {
                        distance = distAct;
                        s = val;
                    }
                }
            }

            if(distance >= distMax)
            {
                s = null;
            }
            System.out.println("Distance : " + distance);
        }

        return (Barre) s;
    }
}