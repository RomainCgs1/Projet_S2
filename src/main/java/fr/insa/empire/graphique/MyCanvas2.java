/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.empire.graphique;

import fr.insa.empire.treillis.Treillis;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author romai
 */
public class MyCanvas2 extends Canvas {

    private ResultatGraphique resultatGraphique;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private Treillis contient;

    //Constructeur
    public MyCanvas2(ResultatGraphique resultatGraphique) {
        this.resultatGraphique = resultatGraphique;
        super.setHeight(500);
        super.setWidth(500);
    }

    @Override
    public double minHeight(double width) {
        return 64;
    }

    @Override
    public double maxHeight(double width) {
        return 1000;
    }

    @Override
    public double prefHeight(double width) {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height) {
        return 64;
    }

    @Override
    public double maxWidth(double height) {
        return 10000;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        super.setWidth(width - 10);
        super.setHeight(height - 10);

        /*GraphicsContext graphicsContext2D = this.getGraphicsContext2D();
        graphicsContext2D.clearRect(0, 0, width, height);

        graphicsContext2D.setStroke(Color.GRAY);
        graphicsContext2D.strokeRect(0, 0, width-10, height-10);*/
        //redraw ce qui a été ajouté
        resultatGraphique.recontruction();
    }
}
