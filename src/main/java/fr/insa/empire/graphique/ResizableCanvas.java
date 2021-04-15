package fr.insa.empire.graphique;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {

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
        return 0;
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
        super.setWidth(width);
        super.setHeight(height);

        GraphicsContext graphicsContext2D = this.getGraphicsContext2D();
        graphicsContext2D.clearRect(0, 0, width, height);

        graphicsContext2D.setStroke(Color.GRAY);
        graphicsContext2D.strokeRect(5, 5, width-10, height-10);

        //redraw ce qui a été ajouté
    }
}
