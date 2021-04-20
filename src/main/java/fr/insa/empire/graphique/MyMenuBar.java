package fr.insa.empire.graphique;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.*;

import java.util.Optional;
import fr.insa.empire.treillis.Treillis;

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

        this.mParametre = new Menu("Paramètres ");
        this.miLangue = new MenuItem("Langue");
        this.miTheme = new MenuItem("Thème");
        this.mParametre.getItems().setAll(miLangue, miTheme);

        this.miLangue.setOnAction(
                event -> {
                    Alert dBox = new Alert(Alert.AlertType.CONFIRMATION);
                    dBox.setTitle("A confirmation dialog-box with custom actions");
                    dBox.setHeaderText("Java-Pizza : The Very Best in Town !");
                    dBox.setContentText("Choose your pizza size :");
                    ButtonType btnSmall  = new ButtonType("Small");
                    ButtonType btnMedium = new ButtonType("Medium");
                    ButtonType btnBig    = new ButtonType("Big");
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dBox.getButtonTypes().setAll(btnSmall, btnMedium, btnBig, btnCancel);
                    Optional<ButtonType> choice = dBox.showAndWait();
                    if (choice.get() == btnSmall) {
                        System.out.println("User chose Small");
                    }
                    else if (choice.get() == btnMedium) {
                        System.out.println("User chose Medium");
                    }
                    else if (choice.get() == btnBig) {
                        System.out.println("User chose Big");
                    } else {
                        System.out.println("Cancel or Close"); }
                }
        );
        
        //Action enregistrer 
        this.miEnregistrer.setOnAction(
                Action ->{
                   Alert dBox = new Alert(Alert.AlertType.CONFIRMATION);
                    dBox.setTitle("Enregistrer sous...");
                    dBox.setHeaderText("Sauvegarde : Entrez le nom de votre fichier");
                    TextField tfNomFichier = new TextField();
                    tfNomFichier.setMinWidth(50);
                    ButtonType btnValide = new ButtonType("Sauvegarder");
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dBox.getButtonTypes().setAll(btnValide,btnCancel);
                    //AFFICHER LE TEXT FIELD
                    Optional<ButtonType> choice = dBox.showAndWait();
                    if (choice.get() == btnCancel) {
                        System.out.println("Cancel or Close");
                    }
                    else if (choice.get() == btnValide) {
                        System.out.println("Sauvegarde en cours");
                       try (BufferedWriter bf = new BufferedWriter (new FileWriter(tfNomFichier.getText()))){
                         // Treillis.save(bf,MainGraphique.getIdentificateur());
                       } catch (IOException ex) {
                           System.out.println("Erreur"+ex+ ", la sauvegarde n'a pas pu etre effectuee");
                       }
                    }
                }
        );

        this.mAide = new Menu("Aide");

        final String os = System.getProperty("os.name");
        if(os != null && os.startsWith("Mac"))
        {
            this.useSystemMenuBarProperty().set(true);
        }
        this.getMenus().addAll(this.mFichier, this.mParametre, this.mAide);
    }
}
