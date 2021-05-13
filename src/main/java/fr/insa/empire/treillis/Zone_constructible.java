package fr.insa.empire.treillis;

import fr.insa.empire.utils.Identificateur;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Zone_constructible {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private int identifiant;
    
     public Zone_constructible(double xMin,double xMax, double yMin, double yMax){
         this.xMin = xMin;
         this.xMax = xMax;
         this.yMin = yMin;
         this.yMax = yMax;
     }      
     
     public double getxMin(){
         return this.xMin;
     }
     public double getxMax(){
         return this.xMax;
     }
     public double getyMin(){
         return this.yMin;
     }
     public double getyMax(){
         return this.yMax;
     }
     public void setIdentifiant(int identifiant){
         this.identifiant = identifiant;
     }


    public void draw(GraphicsContext graphicsContext) 
    {
        graphicsContext.setStroke(Color.GREEN);
        graphicsContext.strokeLine(this.xMax, this.yMin, this.xMax, this.yMax);
        graphicsContext.strokeLine(this.xMin, this.yMin, this.xMin, this.yMax);
        graphicsContext.strokeLine(this.xMax, this.yMax, this.xMin, this.yMax);
        graphicsContext.strokeLine(this.xMax, this.yMin, this.xMin, this.yMin);
    }
}