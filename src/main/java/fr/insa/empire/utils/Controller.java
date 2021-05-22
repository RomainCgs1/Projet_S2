package fr.insa.empire.utils;

import fr.insa.empire.graphique.AfficheInfosTypeBarre;
import fr.insa.empire.graphique.MainGraphique;
import fr.insa.empire.graphique.ResultatGraphique;
import fr.insa.empire.treillis.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.media.Media;

public class Controller {

    //Attributs
    private MainGraphique vue;
    private int etat;
    private int etatPrecedent;
    private int etatPreprecedent;
    private int etatPrepreprecedent;
    private int etatPreprepreprecedent;
    Point p1;
    Point p2;
    Point p3;
    Point p4;
    Noeuds noeudDebut;
    Noeuds noeudFin;

    //Constructeur
    public Controller(MainGraphique vue) {
        this.vue = vue;
        this.etat = -10;

        this.p1 = new Point();
        this.p2 = new Point();
        this.p3 = new Point();
    }

    public void changeEtat(int newState) {
        this.etatPrecedent = this.etat;
        this.etat = newState;

        System.out.println(newState);

        if (newState != -10 && newState != -11) {
            this.vue.getMbNoeud().setDisable(false);
            this.vue.getMtbTerrain().setDisable(false);
            this.vue.getMbGomme().setDisable(false);
            this.vue.getMtbBarre().setDisable(false);
            this.vue.getMbTypeDeBarre().setDisable(false);
            this.vue.getMbLancerCalculs().setDisable(false);
            this.vue.getMbPrix().setDisable(false);
        }

        switch (newState) {
            case -10: // initialisation zone_constructible
                this.vue.getText().setText(" Dessinez votre zone de construction (2 clics sont attentus)");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbNoeud().setDisable(true);
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbTerrain().setDisable(true);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMbGomme().setDisable(true);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbBarre().setDisable(true);
                this.vue.getMbTypeDeBarre().setDisable(true);
                this.vue.getMbLancerCalculs().setDisable(true);
                this.vue.getMbPrix().setDisable(true);
                break;
            case 0: //remise à zero
                this.vue.getText().setText(" Selectionnez un bouton et lancez vous !");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 110: //noeud appui simple
                this.vue.getText().setText(" Placez votre noeud appui simple");
                this.vue.getMbNoeud().setText("Noeud Appui simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 120: //noeud simple
                this.vue.getText().setText(" Placez votre noeud simple");
                this.vue.getMbNoeud().setText("Noeud Simple");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 130: //noeud appui double
                this.vue.getText().setText(" Placez votre noeud appui double");
                this.vue.getMbNoeud().setText("Noeud Appui double");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 140: //noeud appui encastré
                this.vue.getMbNoeud().setText("Noeud Appui encasté");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 20: //barre
                this.vue.getText().setText(" Cliquer 1 fois pour le neoud début et une seconde fois pour le noeud fin");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 30: //terrain
                this.vue.getText().setText(" Trois clics sont attendus pour créer votre triangle terrain");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 50: //gomme noeuds
                this.vue.getText().setText(" Sélectionnez un noeud");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbGomme().setText("Gomme : noeuds");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 51: //gomme terrain
                this.vue.getText().setText(" Sélectionnez un triangle terrain");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbGomme().setText("Gomme : terrain");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 52: //gomme barre
                this.vue.getText().setText(" Sélectionnez une barre");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMbGomme().setText("Gomme : barre");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                break;
            case 53: //gomme : tout effacer
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                eraseAll();
                break;
            case 54: //recommencer
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMtbBarre().setSelected(false);
                recommencer();
                changeEtat(-10);
                break;
            case 600:
                String[] typeAll = {"Bois", "Acier"}; //On rentre le nom des différents types existants
                ChoiceDialog<String> dialogTypeCurrent = new ChoiceDialog<>(typeAll[0], typeAll);
                dialogTypeCurrent.setTitle("Choix Type de Barre");
                dialogTypeCurrent.setHeaderText("Selectionnez un type de barre pour changer de type actif");
                dialogTypeCurrent.setContentText("Types :");
                Optional<String> selectionTypeCurrent = dialogTypeCurrent.showAndWait();

                if (selectionTypeCurrent.get().contains("Bois")) {
                    this.vue.getTreillis().setCurrentType(this.vue.getTreillis().getCatalogue().getContient().get(0));

                } else if (selectionTypeCurrent.get().contains("Acier")) { //C'est acier
                    this.vue.getTreillis().setCurrentType(this.vue.getTreillis().getCatalogue().getContient().get(1));
                }
                this.changeEtat(20);
                break;
            case 601:
                this.vue.getText().setText(" Sélectionnez une barre");
                break;
            case 602:
                String[] types = {"Bois", "Acier"}; //On rentre le nom des différents types existants
                ChoiceDialog<String> dialogType = new ChoiceDialog<>(types[0], types);
                dialogType.setTitle("Choix Type de Barre");
                dialogType.setHeaderText("Selectionnez un type de barre");
                dialogType.setContentText("Types :");
                Optional<String> selectionType = dialogType.showAndWait();

                Type_de_barre type = this.vue.getTreillis().getCatalogue().getContient().get(0); //Type par défaut est le bois

                if (selectionType.get().contains("Bois")) {
                    type = this.vue.getTreillis().getCatalogue().getContient().get(0);

                } else if (selectionType.get().contains("Acier")) { //C'est acier
                     type = this.vue.getTreillis().getCatalogue().getContient().get(1);
                }
                this.changeEtat(0);
                break;
            case 603:
                String[] typesInfos = {"Bois", "Acier"}; //On rentre le nom des différents types existants
                ChoiceDialog<String> dialogInfosType = new ChoiceDialog<>(typesInfos[0], typesInfos);
                dialogInfosType.setTitle("Choix Type de Barre");
                dialogInfosType.setHeaderText("Selectionnez un type de barre");
                dialogInfosType.setContentText("Types :");
                Optional<String> slectionTypeInfos = dialogInfosType.showAndWait();

                if (slectionTypeInfos.get().contains("Bois")) {
                    System.out.println("Le type " + slectionTypeInfos.get());
                    try {
                        Scene sceneInfosType = new Scene(new AfficheInfosTypeBarre(this.vue.getTreillis().getCatalogue().getContient().get(0)));
                        Stage fenetreInfosType = new Stage();
                        fenetreInfosType.setTitle("Infos Types MeshApp");
                        fenetreInfosType.setScene(sceneInfosType);
                        fenetreInfosType.setX(500);
                        fenetreInfosType.setY(800);
                        fenetreInfosType.showAndWait();

                    } catch (IOException ie) {
                        System.out.println("Erreur de chargement");
                    }

                } else if (slectionTypeInfos.get().contains("Acier")) { //C'est acier
                    System.out.println("Le type " + slectionTypeInfos.get());
                    try {
                        Scene sceneInfosType = new Scene(new AfficheInfosTypeBarre(this.vue.getTreillis().getCatalogue().getContient().get(1)));
                        Stage fenetreInfosType = new Stage();
                        fenetreInfosType.setTitle("Infos Types MeshApp");
                        fenetreInfosType.setScene(sceneInfosType);
                        fenetreInfosType.setX(500);
                        fenetreInfosType.setY(800);
                        fenetreInfosType.showAndWait();

                    } catch (IOException ie) {
                        System.out.println("Erreur de chargement");
                    }
                }
                break;
            case 70: //calculs
                this.vue.getText().setText(" Sélectionnez le noeud simple sur lequel la force va s'appliquer");
                this.vue.getMbNoeud().setText("Noeud");
                this.vue.getMtbBarre().setSelected(false);
                this.vue.getMtbTerrain().setSelected(false);
                this.vue.getMbGomme().setText("Gomme");
                break;
            case 80:
                Alert dialogPrix = new Alert(AlertType.INFORMATION);
                dialogPrix.setTitle("Résultat Prix");
                dialogPrix.setHeaderText("Prix du treilli : ");
                String s = "";
                s = s + this.vue.getTreillis().calculPrixTreilli() + " €";
                dialogPrix.setContentText(s);
                dialogPrix.showAndWait();
                break;
            case 200: //thème
                choixTheme();
                changeEtat(etatPrecedent);
                break;

            case 300: //langue mais tests de transformation
                //pas encore de transf
                break;
        }

    }

    public void canvasClicked(MouseEvent E) {
        double px = E.getX();
        double py = E.getY();
        System.out.println("Canvas cliqué en " + px + " " + py);

        if (E.getButton() == MouseButton.PRIMARY) {

            if (this.vue.getTreillis().getAppartient() == null) {
                switch (this.etat) {
                    case -10:
                        p4 = new Point(px, py);
                        this.changeEtat(-11);
                        break;

                    case -11:
                        double Xmax;
                        double Xmin;
                        double Ymax;
                        double Ymin;
                        if (px > p4.getPx()) {
                            Xmax = px;
                            Xmin = p4.getPx();
                        } else {
                            Xmax = p4.getPx();
                            Xmin = px;
                        }
                        if (py > p4.getPy()) {
                            Ymax = py;
                            Ymin = p4.getPy();
                        } else {
                            Ymax = p4.getPy();
                            Ymin = py;
                        }
                        Zone_constructible zoneConstructible = new Zone_constructible(Xmin, Xmax, Ymin, Ymax);
                        zoneConstructible.draw(this.vue.getCanvas().getGraphicsContext2D());

                        zoneConstructible.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(zoneConstructible));
                        this.changeEtat(0);
                        this.vue.getTreillis().setAppartient(zoneConstructible);
                        break;
                }
            } else {
                //si on est en dehors de la zone constructible
                if (px > this.vue.getTreillis().getAppartient().getxMax() || px < this.vue.getTreillis().getAppartient().getxMin() || py > this.vue.getTreillis().getAppartient().getyMax() || py < this.vue.getTreillis().getAppartient().getyMin()) {
                    switch (this.etat) {
                        case 30:
                            p1 = new Point(px, py);
                            //this.p1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p1));
                            changeEtat(31);
                            System.out.println("point 1");
                            break;

                        case 31:
                            p2 = new Point(px, py);
                            //this.p2.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p2));
                            changeEtat(32);
                            System.out.println("point 2");
                            break;

                        case 32:
                            p3 = new Point(px, py);
                            //this.p3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p3));
                            System.out.println("point 3");
                            creationTriangleTerrain(this.p1, this.p2, this.p3);

                            System.out.println(this.p1 + "\n"
                                    + this.p2 + "\n"
                                    + this.p3);

                            changeEtat(30);
                            break;

                        case 51:
                            Segment_terrain segmentTerrain = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (segmentTerrain != null) {
                                clearTriangleTerrain(segmentTerrain);
                                this.vue.recontruction();
                            }
                            break;
                    }
                } else {
                    switch (this.etat) {
                        case -10:
                            p4 = new Point(px, py);
                            this.changeEtat(-11);
                            break;
                        case -11:

                            double Xmax;
                            double Xmin;
                            double Ymax;
                            double Ymin;
                            if (px > p4.getPx()) {
                                Xmax = px;
                                Xmin = p4.getPx();
                            } else {
                                Xmax = p4.getPx();
                                Xmin = px;
                            }
                            if (py > p4.getPy()) {
                                Ymax = py;
                                Ymin = p4.getPy();
                            } else {
                                Ymax = p4.getPy();
                                Ymin = py;
                            }
                            Zone_constructible zoneConstructible = new Zone_constructible(Xmin, Xmax, Ymin, Ymax);
                            zoneConstructible.draw(this.vue.getCanvas().getGraphicsContext2D());
                            zoneConstructible.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(zoneConstructible));
                            this.changeEtat(0);
                            this.vue.getTreillis().setAppartient(zoneConstructible);
                            break;

                        case 110:
                            Segment_terrain segment_terrain = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                            System.out.println("Segment terrain le plus proche : " + segment_terrain);
                            if (segment_terrain == null) {
                                //demander si on passe en mode de création segment terrain
                            } else {
                                //remplacer coord par le pt le plus proche sur le segment
                                //creer le point sur le segment
                                Point temp = segment_terrain.getPointSegmTerrPlusProche(px, py);
                                Appui_simple appuiSimple = new Appui_simple(temp.getPx(), temp.getPy(), segment_terrain);
                                appuiSimple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(appuiSimple));
                                this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.ORANGE);
                                this.vue.getCanvas().getGraphicsContext2D().strokeOval(appuiSimple.getPx() - 5, appuiSimple.getPy() - 5, 10, 10);
                                System.out.println("Noeud appui simple créé !");
                            }
                            break;

                        case 120:
                            Noeud_simple noeud_simple = this.vue.getCanvas().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur);
                            System.out.println("Noeud simple le plus proche : " + noeud_simple);
                            if (this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur) == null) {
                                if (noeud_simple == null) {
                                    noeud_simple = new Noeud_simple(px, py);
                                    noeud_simple.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(noeud_simple));
                                    System.out.println(noeud_simple.getID());
                                    this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.RED);
                                    this.vue.getCanvas().getGraphicsContext2D().strokeOval(px - 5, py - 5, 10, 10);
                                } else {
                                    System.out.println("noeud déjà créé : " + noeud_simple);
                                }
                            }
                            break;

                        case 130:
                            Noeuds noeuds = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (noeuds == null) {
                                Segment_terrain segment_terrain1 = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                                System.out.println("Segment terrain le plus proche : " + segment_terrain1);
                                if (segment_terrain1 == null) {
                                    //demander si on passe en mode de création segment terrain
                                } else {
                                    //remplacer coord par le pt le plus proche sur le segment
                                    //creer le point sur le segment
                                    Point temp = segment_terrain1.getPointSegmTerrPlusProche(px, py);
                                    System.out.println("Point temporaire : " + temp);
                                    Appui_double appuiDouble = new Appui_double(temp.getPx(), temp.getPy(), segment_terrain1);
                                    appuiDouble.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(appuiDouble));
                                    System.out.println("test : " + appuiDouble);
                                    this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.SILVER);
                                    this.vue.getCanvas().getGraphicsContext2D().strokeOval(appuiDouble.getPx() - 5, appuiDouble.getPy() - 5, 10, 10);
                                    System.out.println("Noeud appui double créé !");
                                }
                            } else {
                                System.out.println("Il y a déjà un noeud ici");
                            }
                            break;
                        case 20:
                            System.out.println("noeud début");
                            if (this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur) != null) {
                                noeudDebut = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                                if (noeudDebut == null) {
                                    this.changeEtat(110);
                                    this.canvasClicked(E);
                                    this.changeEtat(20);
                                    this.canvasClicked(E);
                                }
                            } else {
                                noeudDebut = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                                if (noeudDebut == null) {
                                    this.changeEtat(120);
                                    this.canvasClicked(E);
                                    this.changeEtat(20);
                                    this.canvasClicked(E);
                                }
                            }
                            this.changeEtat(21);
                            break;

                        case 21:
                            System.out.println("noeud fin");
                            if (this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur) != null) {
                                noeudFin = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                                if (noeudFin == null) {
                                    this.changeEtat(110);
                                    this.canvasClicked(E);
                                    this.changeEtat(21);
                                    this.canvasClicked(E);
                                } else {
                                    creationBarre(noeudDebut, noeudFin);
                                    this.changeEtat(20);
                                }
                            } else {
                                noeudFin = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                                if (noeudFin == null) {
                                    this.changeEtat(120);
                                    this.canvasClicked(E);
                                    this.changeEtat(21);
                                    this.canvasClicked(E);
                                } else {
                                    creationBarre(noeudDebut, noeudFin);
                                    this.changeEtat(20);
                                }
                            }
                            break;

                        case 30:
                            if (this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur) == null) {
                                p1 = new Point(px, py);
                                this.p1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p1));
                                changeEtat(31);
                                System.out.println("point 1");
                            } else {
                                p1 = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur).getPointSegmTerrPlusProche(px, py);
                                this.p1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p1));
                                changeEtat(31);
                                System.out.println("point 1");
                            }
                            break;

                        case 31:
                            if (this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur) == null) {
                                p2 = new Point(px, py);
                            } else {
                                p2 = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur).getPointSegmTerrPlusProche(px, py);
                            }
                            this.p2.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p2));
                            changeEtat(32);
                            System.out.println("point 2");
                            break;

                        case 32:
                            if (this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur) == null) {
                                p3 = new Point(px, py);
                                this.p3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p3));
                            } else {
                                p3 = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur).getPointSegmTerrPlusProche(px, py);
                                this.p3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(this.p3));
                            }
                            System.out.println("point 3");

                            creationTriangleTerrain(this.p1, this.p2, this.p3);

                            System.out.println(this.p1 + "\n"
                                    + this.p2 + "\n"
                                    + this.p3);

                            changeEtat(30);
                            break;

                        case 50:
                            Noeuds noeud = this.vue.getCanvas().getNoeudPlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (noeud != null) {
                                int key = this.vue.getTreillis().identificateur.getObjectToKey().get(noeud);
                                this.vue.getTreillis().identificateur.getObjectToKey().remove(noeud);
                                this.vue.getTreillis().identificateur.getKetToObject().remove(key);
                                if (noeud instanceof Noeud_simple) {//On regarde si c'est un noeud simple
                                    this.vue.getTreillis().getTreilliContientNoeudSimple().remove(noeud);
                                } else {//Sinon c'est un noeud appui
                                    this.vue.getTreillis().getTreilliContientNoeudAppui().remove(noeud);
                                }
                                this.vue.recontruction();
                            } else {
                                Alert diagAlertMauvaiseDonnee = new Alert(AlertType.ERROR);
                                diagAlertMauvaiseDonnee.setTitle("Erreur Gomme noeud");
                                diagAlertMauvaiseDonnee.setHeaderText("Erreur noeud");
                                diagAlertMauvaiseDonnee.setContentText("Erreur : aucun noeud sélectionné");
                                diagAlertMauvaiseDonnee.showAndWait();
                                this.changeEtat(50);
                            }

                            break;
                        case 51:
                            Segment_terrain segmentTerrain = this.vue.getCanvas().getSegmentTerrainPlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (segmentTerrain != null) {
                                clearTriangleTerrain(segmentTerrain);
                                this.vue.recontruction();
                            } else {
                                Alert diagAlertMauvaiseDonnee = new Alert(AlertType.ERROR);
                                diagAlertMauvaiseDonnee.setTitle("Erreur Gomme Segment");
                                diagAlertMauvaiseDonnee.setHeaderText("Erreur Segment");
                                diagAlertMauvaiseDonnee.setContentText("Erreur : aucun segment sélectionné");
                                diagAlertMauvaiseDonnee.showAndWait();
                                this.changeEtat(51);
                            }
                            break;

                        case 52:
                            Barre barre = this.vue.getCanvas().getBarrePlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (barre != null) {
                                this.vue.getTreillis().getTreilliContientBarre().remove(barre);
                                clearBarre(barre);
                            } else {
                                Alert diagAlertMauvaiseDonnee = new Alert(AlertType.ERROR);
                                diagAlertMauvaiseDonnee.setTitle("Erreur Gomme barre");
                                diagAlertMauvaiseDonnee.setHeaderText("Erreur barre");
                                diagAlertMauvaiseDonnee.setContentText("Erreur : aucune barre sélectionnée");
                                diagAlertMauvaiseDonnee.showAndWait();
                                this.changeEtat(52);
                            }
                            break;
                        case 601:
                            //On sélectionne la barre souhaitée
                            Barre barreChoisie = this.vue.getCanvas().getBarrePlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (barreChoisie == null) {
                                Alert diagAlertMauvaiseDonnee = new Alert(AlertType.ERROR);
                                diagAlertMauvaiseDonnee.setTitle("Erreur Type de barre");
                                diagAlertMauvaiseDonnee.setHeaderText("Erreur pas top");
                                diagAlertMauvaiseDonnee.setContentText("Erreur : aucune barre sélectionnée");
                                diagAlertMauvaiseDonnee.showAndWait();
                                this.changeEtat(601);
                                break;
                            }
                            System.out.println("Barre la plus proche : " + barreChoisie);

                            String[] tabTypesExistants = {"Bois", "Acier"}; //On rentre le nom des différents types existants
                            ChoiceDialog<String> dialogBoxTypeDeBarre = new ChoiceDialog<>(tabTypesExistants[0], tabTypesExistants);
                            dialogBoxTypeDeBarre.setTitle("Choix Type de Barre");
                            dialogBoxTypeDeBarre.setHeaderText("Selectionnez un type de barre");
                            dialogBoxTypeDeBarre.setContentText("Types :");
                            Optional<String> selection = dialogBoxTypeDeBarre.showAndWait();

                            if (selection.get().contains("Bois")) {
                                System.out.println("Le type " + selection.get());
                                barreChoisie.setType(this.vue.getTreillis().getCatalogue().getContient().get(0));
                            }

                            if (selection.get().contains("Acier")) {
                                System.out.println("Le type " + selection.get());
                                barreChoisie.setType(this.vue.getTreillis().getCatalogue().getContient().get(1));
                            }
                            break;
                        case 70:
                            //On sélectionne le noeud souhaité
                            Noeud_simple noeudSimpleChoisit = this.vue.getCanvas().getNoeud_simplePlusProche(px, py, this.vue.getTreillis().identificateur);
                            if (noeudSimpleChoisit == null) {
                                Alert diagAlertMauvaiseDonnee = new Alert(AlertType.ERROR);
                                diagAlertMauvaiseDonnee.setTitle("Erreur Calcul");
                                diagAlertMauvaiseDonnee.setHeaderText("Erreur Noeud Simple");
                                diagAlertMauvaiseDonnee.setContentText("Erreur : aucun Noeud_simple sélectionné");
                                diagAlertMauvaiseDonnee.showAndWait();
                                this.changeEtat(70);
                                break;
                            }
                            System.out.println("Noeud simple le plus proche : " + noeudSimpleChoisit);

                            //On demande la valeur de la composante sur X de la force
                            TextInputDialog boiteDiagPX = new TextInputDialog();
                            boiteDiagPX.setTitle("Ajout de la force");
                            boiteDiagPX.setHeaderText("Entrez la composante sur X de la force");
                            boiteDiagPX.setContentText("Composante sur X :");
                            Optional<String> textPX = boiteDiagPX.showAndWait();

                            System.out.println(textPX.get());
                            if (textPX.get().toLowerCase().contains("chat")) {
                                File mediaFile = new File("src/main/java/fr/insa/empire/autres/secret/secrets_d'etat/easter_eggs/EEEAAAOOO.mp4");
                                Media media;
                                try {
                                    media = new Media(mediaFile.toURI().toURL().toString());
                                    MediaPlayer mediaPlayer = new MediaPlayer(media);

                                    MediaView mediaView = new MediaView(mediaPlayer);
                                    DoubleProperty mvw = mediaView.fitWidthProperty();
                                    DoubleProperty mvh = mediaView.fitHeightProperty();
                                    mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                                    mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                                    mediaView.setPreserveRatio(true);
                                    BorderPane borderPane = new BorderPane();
                                    borderPane.setCenter(mediaView);
                                    Scene scene = new Scene(borderPane, 1024, 590);

                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.show();

                                    stage.setOnCloseRequest(
                                            event -> {
                                                mediaPlayer.stop();
                                            }
                                    );

                                    mediaPlayer.play();

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                changeEtat(0);
                            } else {
                                double pxForce = Double.parseDouble(textPX.get());

                                //On demande la valeur de la composante sur Y de la force
                                TextInputDialog boiteDiagPY = new TextInputDialog();
                                boiteDiagPY.setTitle("Ajout de la force");
                                boiteDiagPY.setHeaderText("Entrez la composante sur Y de la force");
                                boiteDiagPY.setContentText("Composante sur Y :");
                                Optional<String> textPY = boiteDiagPY.showAndWait();
                                double pyForce = Double.parseDouble(textPY.get());

                                //On crée la force ajoutée
                                Force forceAjoutee = new Force(noeudSimpleChoisit, pxForce, pyForce);

                                //Calcul
                                System.out.println("Lancement des calculs");
                                System.out.println("Calculs en cours...");

                                //On lance les calculs et on les récupère dans un tableau de String pour afficher les résultats
                                String[][] resultat = this.vue.getTreillis().lancerCalculGeneraux(noeudSimpleChoisit, forceAjoutee);

                                //On regarde s'il y a une erreur
                                if (resultat[0][0].contains("erreur")) {
                                    Alert diagAlertMauvaiseDonnee = new Alert(AlertType.ERROR);
                                    diagAlertMauvaiseDonnee.setTitle("Erreur Calcul");
                                    diagAlertMauvaiseDonnee.setHeaderText("Erreur Matrice");
                                    diagAlertMauvaiseDonnee.setContentText("Erreur : Le treilli saisi n'est pas valide");
                                    diagAlertMauvaiseDonnee.showAndWait();
                                    this.changeEtat(0);
                                    break;
                                } else {
                                    System.out.println("Pas d'erreur");
                                    String s = "";
                                    for (int i = 0; i < resultat.length; i++) {
                                        s = s + resultat[i][0] + " " + resultat[i][1] + " " + resultat[i][2] + "\n";
                                    }
                                    try {
                                        Scene sceneResultat = new Scene(new ResultatGraphique(s, this.vue.getTreillis()));
                                        Stage fenetreResultat = new Stage();
                                        fenetreResultat.setTitle("Résultat Calculs MeshApp");
                                        fenetreResultat.setScene(sceneResultat);
                                        fenetreResultat.setX(500);
                                        fenetreResultat.setY(800);
                                        fenetreResultat.showAndWait();

                                    } catch (IOException ie) {
                                        System.out.println("Erreur de chargement");
                                    }

                                    //On efface les forces créés pour effectuer à nouveau le calcul
                                    this.vue.getTreillis().getIdentificateurForce().clear();
                                    this.changeEtat(0);
                                }

                            }
                            break;
                    }
                }

            }
        }
    }

    public void canvasOver(MouseEvent E) {
        double px = E.getX();
        double py = E.getY();

        GraphicsContext graphicsContext = this.vue.getCanvas().getGraphicsContext2D();

        this.vue.recontruction();

        switch (etat) {
            case -11:
                graphicsContext.setStroke(Color.GREEN);
                graphicsContext.strokeRect(this.p4.getPx(), this.p4.getPy(), px - this.p4.getPx(), py - this.p4.getPy());
                break;

            case 21:
                graphicsContext.setStroke(Color.BLUE);
                graphicsContext.strokeLine(noeudDebut.getPx(), noeudDebut.getPy(), px, py);
                break;

            case 31:
                graphicsContext.setStroke(Color.BLACK);
                graphicsContext.strokeLine(this.p1.getPx(), this.p1.getPy(), px, py);
                break;

            case 32:
                graphicsContext.setStroke(Color.BLACK);
                graphicsContext.strokeLine(this.p1.getPx(), this.p1.getPy(), this.p2.getPx(), this.p2.getPy());
                graphicsContext.strokeLine(this.p1.getPx(), this.p1.getPy(), px, py);
                graphicsContext.strokeLine(this.p2.getPx(), this.p2.getPy(), px, py);
                break;
        }

    }

    private void creationTriangleTerrain(Point p1, Point p2, Point p3) {
        Segment_terrain seg1 = new Segment_terrain(p1, p2);
        seg1.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(seg1));
        Segment_terrain seg2 = new Segment_terrain(p2, p3);
        seg2.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(seg2));
        Segment_terrain seg3 = new Segment_terrain(p3, p1);
        seg3.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(seg3));

        Triangle_terrain triangle_terrain = new Triangle_terrain(seg1, seg2, seg3);
        triangle_terrain.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(triangle_terrain));
        seg1.setAppartient(triangle_terrain);
        seg2.setAppartient(triangle_terrain);
        seg3.setAppartient(triangle_terrain);

        this.vue.getCanvas().getGraphicsContext2D().setStroke(Color.BLACK);
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p1.getPx(), p1.getPy(), p2.getPx(), p2.getPy());
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p2.getPx(), p2.getPy(), p3.getPx(), p3.getPy());
        this.vue.getCanvas().getGraphicsContext2D().strokeLine(p3.getPx(), p3.getPy(), p1.getPx(), p1.getPy());

        System.out.println("Triangle n°" + triangle_terrain.getIdentifiant() + " a été créé.");
    }

    private void clearTriangleTerrain(Segment_terrain segmentTerrain) {
        Triangle_terrain triangle_terrain = segmentTerrain.getAppartient();
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain.getSegment1());
        this.vue.getTreillis().identificateur.getKetToObject().remove(triangle_terrain.getSegment1().getIdentifiant());

        int key = this.vue.getTreillis().identificateur.getObjectToKey().get(triangle_terrain.getSegment2());
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain.getSegment2());
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);

        key = this.vue.getTreillis().identificateur.getObjectToKey().get(triangle_terrain.getSegment3());
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain.getSegment3());
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);

        key = this.vue.getTreillis().identificateur.getObjectToKey().get(triangle_terrain);
        this.vue.getTreillis().identificateur.getObjectToKey().remove(triangle_terrain);
        this.vue.getTreillis().identificateur.getKetToObject().remove(key);
    }

    private void clearBarre(Barre barre) {
        int id = this.vue.getTreillis().identificateur.getOrSetKey(barre);
        this.vue.getTreillis().identificateur.getKetToObject().remove(id, barre);
        this.vue.getTreillis().identificateur.getObjectToKey().remove(barre, id);
    }

    private void creationBarre(Noeuds noeudDebut, Noeuds noeudFin) {

        Barre barre = new Barre(noeudDebut, noeudFin, this.vue.getTreillis());
        barre.setIdentifiant(this.vue.getTreillis().identificateur.getOrSetKey(barre));
        barre.setType(this.vue.getTreillis().getCurrentType());
        barre.draw(this.vue.getCanvas().getGraphicsContext2D());

        System.out.println("barre n°" + barre.getIdentifiant() + " a été créé.");
    }

    private void eraseAll() {
        Zone_constructible zone_constructible = (Zone_constructible) this.vue.getTreillis().identificateur.getKetToObject().get(1);
        this.vue.getIdentificateur().clear();
        this.vue.getCanvas().getGraphicsContext2D().clearRect(0, 0, this.vue.getCanvas().getWidth(), this.vue.getCanvas().getHeight());
        this.vue.getTreillis().getIdentificateurForce().clear();
        this.vue.getTreillis().getTreilliContientBarre().clear();
        this.vue.getTreillis().getTreilliContientNoeudAppui().clear();
        this.vue.getTreillis().getTreilliContientNoeudSimple().clear();
        this.vue.getIdentificateur().getOrSetKey(zone_constructible);
    }

    private void recommencer() {
        this.vue.getIdentificateur().clear();
        this.vue.getCanvas().getGraphicsContext2D().clearRect(0, 0, this.vue.getCanvas().getWidth(), this.vue.getCanvas().getHeight());
        this.vue.getTreillis().getIdentificateurForce().clear();
        this.vue.getTreillis().getTreilliContientBarre().clear();
        this.vue.getTreillis().getTreilliContientNoeudAppui().clear();
        this.vue.getTreillis().getTreilliContientNoeudSimple().clear();
        this.vue.getTreillis().setAppartient(null);
    }

    private void choixTheme() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choix du thème");
        dialog.setHeaderText("Veuillez choisir votre thème favoris :");

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
        // Set the button types.
        ButtonType validationButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validationButtonType, ButtonType.CANCEL);

        // Create the tfPYX and tfPY labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        RadioButton rbClair = new RadioButton("Clair");
        RadioButton rbsombre = new RadioButton("Sombre");
        ToggleGroup toggleGroup = new ToggleGroup();
        rbClair.setToggleGroup(toggleGroup);
        rbsombre.setToggleGroup(toggleGroup);
        String theme = this.vue.getTheme();
        if (theme == "clair") {
            rbClair.setSelected(true);
        } else {
            rbsombre.setSelected(true);
        }
        grid.add(rbClair, 0, 0);
        grid.add(rbsombre, 0, 1);

        // Enable/Disable login button depending on whether a tfPYX was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(validationButtonType);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the tfPYX field by default.
        // Convert the result to a tfPYX-tfPY-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validationButtonType) {
                return null;
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(tfPYXtfPY -> {
            if (rbClair.isSelected()) {
                this.vue.setTheme("clair");
            } else {
                this.vue.setTheme("sombre");
            }
            System.out.println("px = " + tfPYXtfPY.getKey() + ", py = " + tfPYXtfPY.getValue());
            //à terminer
        });
    }
}
