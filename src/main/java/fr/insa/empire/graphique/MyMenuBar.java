package fr.insa.empire.graphique;

import fr.insa.empire.utils.Controller;
import fr.insa.empire.utils.PickAFile;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MyMenuBar extends MenuBar {

    private Controller controller;

    private Menu mFichier;
    private Menu mParametre;
    private Menu mAide;
    private MenuItem miEnregistrer;
    private MenuItem miOuvrir;
    private MenuItem miRecommencer;

    private MenuItem miLangue;
    private MenuItem miTheme;
    private MainGraphique mainGraphique;

    public MyMenuBar(MainGraphique mainGraphique, Controller controller) {

        this.controller = controller;
        this.mainGraphique = mainGraphique;

        this.mFichier = new Menu("Fichier");
        this.miEnregistrer = new MenuItem("Enregistrer");
        this.miOuvrir = new MenuItem("Ouvrir");
        this.miRecommencer = new MenuItem("Recommencer");
        mFichier.getItems().addAll(this.miEnregistrer, this.miOuvrir, this.miRecommencer);

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

                    controller.changeEtat(300);
                }
        );

        this.miTheme.setOnAction(
                action -> {
                    controller.changeEtat(200);
                }
        );

        //Action enregistrer PAS FINIE
        this.miEnregistrer.setOnAction(
                Action -> {
                    /*TextInputDialog inDialog = new TextInputDialog("Magnfique treilli");
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
                    }*/

                    //choisir un dossier pour l'enregistrement
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("Enregistrer mon beau Treilli");
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    File selectedFile = directoryChooser.showDialog(new Stage());
                    System.out.println(selectedFile);

                    if(selectedFile != null)
                    {
                        //saisie du nom de fichier
                        TextInputDialog inDialog = new TextInputDialog("Magnfique treilli");
                        inDialog.setTitle("Veuillez entrer le nom de votre treilli");
                        inDialog.setHeaderText(null);
                        inDialog.setContentText("Nom du fichier :");
                        //inDialog.setGraphic(new ImageView("C:/Users/romai/OneDrive/Bureau/save.png"));
                        Optional<String> textIn = inDialog.showAndWait();
                        //Renvoie un boolean true si OK et ferme si false
                        if (textIn.isPresent()) {
                            String fichier = selectedFile + "/" + textIn.get() + ".txt";
                            System.out.println("Nom du fichier : " + textIn.get());
                            System.out.println(fichier);
                            System.out.println("Sauvegarde en cours");
                            try (BufferedWriter bf = new BufferedWriter(new FileWriter(fichier))) {
                                this.mainGraphique.getTreillis().save(bf);
                            } catch (IOException ex) {
                                System.out.println("Erreur " + ex + "impossible d'effectuer la sauvegarde");
                            }
                            System.out.println("Sauvegarde terminee");
                        }
                    }
                }
        );
        this.miOuvrir.setOnAction(
                action -> {
                    String selectedFile = PickAFile.main();
                    this.mainGraphique.treillis.ouvronsToutca(selectedFile);
                }
        );
        
        this.miRecommencer.setOnAction(
                action -> {
                    controller.changeEtat(54);
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
