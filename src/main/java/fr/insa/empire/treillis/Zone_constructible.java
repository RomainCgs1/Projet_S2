package fr.insa.empire.treillis;


public class Zone_constructible {
    private double Xmin;
    private double Xmax;
    private double Ymin;
    private double Ymax;
    
     public Zone_constructible(double Xmin,double Xmax, double Ymin, double Ymax){
         this.Xmin = Xmin;
         this.Xmax = Xmax;
         this.Ymin = Ymin;
         this.Ymax = Ymax;
     }      
     
     public double getXmin(){
         return this.Xmin;
     }
     public double getXmax(){
         return this.Xmax;
     }
     public double getYmin(){
         return this.Ymin;
     }
     public double getYmax(){
         return this.Ymax;
     }
     
}