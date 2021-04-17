package fr.insa.empire.graphique;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenuBar extends MenuBar {

    private Menu mFichier;
    private Menu mParametre;
    private Menu mAide;
    private MenuItem miEnregistrer;
    private MenuItem miOuvrir;

    private MenuItem miLangue;
    private MenuItem miTheme;

    public MyMenuBar()
    {
        this.mFichier = new Menu("Fichier");
        this.miEnregistrer = new MenuItem("Enregistrer");
        this.miOuvrir = new MenuItem("Ouvrir");
        mFichier.getItems().addAll(this.miEnregistrer, this.miOuvrir);

        this.mParametre = new Menu("Paramètres");
        this.miLangue = new MenuItem("Langue");
        this.miTheme = new MenuItem("Thème");
        this.mParametre.getItems().setAll(miLangue, miTheme);

        this.mAide = new Menu("Aide");





        this.getMenus().addAll(this.mFichier, this.mParametre, this.mAide);
    }
}
