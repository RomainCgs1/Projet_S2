package fr.insa.empire.graphique;

import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class Transformation
{
    private Scale zoom;
    private Translate translation;

    public Transformation()
    {
        this.zoom = new Scale();
        this.translation = new Translate();
    }

    public Transformation(Scale scale, Translate translate)
    {
        this.zoom = scale;
        this.translation = translate;
    }

    public Scale getZoom() {
        return zoom;
    }

    public Scale getAntiZoom() {
        return null;
    }

    public Translate getAntiTranslation() {
        Translate s = new Translate();
        s.setX(this.translation.getX());
        s.setY(this.translation.getY());

        return s;
    }

    public Translate getTranslation() {
        return translation;
    }

    public void setZoom(Scale zoom) {
        this.zoom = zoom;
    }

    public void setTranslation(Translate translation) {
        this.translation = translation;
    }
}
