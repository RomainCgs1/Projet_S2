package fr.insa.empire.graphique;

import javafx.scene.control.*;

import java.util.Optional;
import javafx.scene.image.ImageView;

public class MyMenuBar extends MenuBar {

    private Menu mFichier;
    private Menu mParametre;
    private Menu mAide;
    private MenuItem miEnregistrer;
    private MenuItem miOuvrir;

    private MenuItem miLangue;
    private MenuItem miTheme;

    public MyMenuBar() {
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
                    ButtonType btnSmall = new ButtonType("Small");
                    ButtonType btnMedium = new ButtonType("Medium");
                    ButtonType btnBig = new ButtonType("Big");
                    ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dBox.getButtonTypes().setAll(btnSmall, btnMedium, btnBig, btnCancel);
                    Optional<ButtonType> choice = dBox.showAndWait();
                    if (choice.get() == btnSmall) {
                        System.out.println("User chose Small");
                    } else if (choice.get() == btnMedium) {
                        System.out.println("User chose Medium");
                    } else if (choice.get() == btnBig) {
                        System.out.println("User chose Big");
                    } else {
                        System.out.println("Cancel or Close");
                    }
                }
        );

        //Action enregistrer PAS FINIE
        this.miEnregistrer.setOnAction(
                Action -> {
                    TextInputDialog inDialog = new TextInputDialog("Magnfique treilli");
                    inDialog.setTitle("Enregistrer mon beau Treilli");
                    inDialog.setHeaderText(null);
                    inDialog.setContentText("Nom du fichier :");
                    //inDialog.setGraphic(new ImageView("C:/Users/romai/OneDrive/Bureau/save.png"));
                    Optional<String> textIn = inDialog.showAndWait();
                    //Renvoie un boolean true si OK et ferme si false
                    if (textIn.isPresent()) {
                        System.out.println("Nom du fichier : " + textIn.get());
                        System.out.println("Sauvegarde en cours");
                        //MainGraphique.saveGenerale(tfNomFichier.getText());
                        System.out.println("Sauvegarde terminee");
                    }

                }
        );

        this.mAide = new Menu("Aide");

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            this.useSystemMenuBarProperty().set(true);
        }
        this.getMenus().addAll(this.mFichier, this.mParametre, this.mAide);
    }
}
