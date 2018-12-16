package view.game;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.game.*;
import view.marketBoard.*;
import view.mainBoard.*;
import view.game.*;

public class Fenetre extends AnchorPane {

    private StackPane stack;
    private TitledPane MarketBoard;
    private TitledPane MainBoard;
    private TitledPane PlayerBoard;
    private Button suiv, prec, finTour;
    private Button[] bt_echangerList;
    private Label numPlateau;
    private Label statusBar;

    private ViewDes des = new ViewDes();
    private ArrayList<ViewBoard> plateaux;
    private TabConstruction cTC;
    private TabInvention cTI;
    private TabCards CTCards;

    private PlayerState plS;

    private Game game;

    private String messageClassique;
    private ViewBank echange;
    Stage primaryStage;

    public Fenetre(Game modelJeu, Stage p_primaryStage)
    {
        game = modelJeu;
        game.setVue(this);

        primaryStage = p_primaryStage;

        finTour = new Button("Fin du Tour");
        suiv = new Button("> Suivant");
        prec = new Button("Précédent <");

        finTour.setDefaultButton(true);
        suiv.setDefaultButton(true);
        prec.setDefaultButton(true);

        Tab TabConstructions = new Tab("Constructions");
        Tab TabCartes = new Tab("Cartes");
        Tab TabInventions = new Tab("Inventions");
        TabPane TabsMarche = new TabPane(TabConstructions, TabInventions, TabCartes);
        TabsMarche.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        TabsMarche.setPrefHeight(450);

        VBox VGauche = new VBox();
        VBox VMilieu = new VBox();
        VBox VDroite = new VBox();

        GridPane gridJoueurs = new GridPane();
        gridJoueurs.setHgap(40);
        gridJoueurs.setVgap(10);
        gridJoueurs.setAlignment(Pos.CENTER);
        gridJoueurs.setPadding(new Insets(20, 10, 10, 10));
        bt_echangerList = new Button[game.getNbJoueurs()];
        bt_echangerList[game.getNbJoueurs()].setDefaultButton(true);

        for (int i = 0; i < game.getNbJoueurs(); ++i)
        { // Initialise les images des joueurs en bas et les boutons échanger
            final int index = i;
            ImageView avatar = new ImageView(game.getPlayer(i).getAvatar());
            bt_echangerList[i] = new Button("Echanger");
            bt_echangerList[i].setDefaultButton(true);
            bt_echangerList[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    echange.show(game.getPlayer(), game.getPlayer(index));

                }
            });
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);
            gridJoueurs.add(avatar, i, 0);
            gridJoueurs.add(new Label(game.getPlayer(i).getNom()), i, 1);
            gridJoueurs.add(bt_echangerList[i], i, 2);
        }
        gridJoueurs.add(finTour, game.getNbJoueurs(), 1);

        plateaux = new ArrayList<>();

        for(Epoch epoque : Epoch.values())
        {
            plateaux.add(new ViewBoard(0, 0, game.getPlateau(epoque), game));
        }

        numPlateau = new Label();
        statusBar = new Label();
        plS = new PlayerState(game);
        echange = new ViewBank(plS);
        stack = new StackPane();

        cTC = new TabConstruction(game,plS);
        CTCards = new TabCards(game,plS);
        cTI = new TabInvention(game,plS);

        TabConstructions.setContent(cTC);
        TabInventions.setContent(cTI);
        TabCartes.setContent(CTCards);

        VGauche.getChildren().add(TabsMarche);
        VGauche.getChildren().add(new Separator());
        VGauche.getChildren().add(des);
        VGauche.setMinWidth(300);
        VGauche.setPrefWidth(300);
        VGauche.setMaxWidth(300);
        VGauche.setId("VGauche");

        VMilieu.getChildren().add(numPlateau);
        VMilieu.getChildren().add(new HBox(10, prec, suiv));
        VMilieu.getChildren().add(stack);
        VMilieu.getChildren().add(gridJoueurs);

        VMilieu.setMinWidth(800);
        VMilieu.setPrefWidth(800);
        VMilieu.setMaxWidth(800);

        VDroite.getChildren().add(plS);
        VDroite.setMinWidth(300);
        VDroite.setPrefWidth(300);
        VDroite.setMaxWidth(300);
        VDroite.setId("VDroite");

        MarketBoard = new TitledPane("Marché", VGauche);
        MarketBoard.setCollapsible(false);
        MarketBoard.setPrefHeight(800);

        MainBoard = new TitledPane("Carte", VMilieu);
        MainBoard.setCollapsible(false);
        MainBoard.setPrefHeight(800);

        PlayerBoard = new TitledPane("Joueur", VDroite);
        PlayerBoard.setCollapsible(false);
        PlayerBoard.setPrefHeight(800);

        statusBar.setPadding(new Insets(10));

        getChildren().add(new StackPane(new VBox(new HBox(MarketBoard, MainBoard, PlayerBoard), statusBar),echange));

        /****************************************************\
         *              Gestion des evenements              *
         \****************************************************/
        suiv.setOnAction(new EventHandler<ActionEvent>() {
            // Passer au plateau suivant
            @Override
            public void handle(ActionEvent event)
            {
                game.epoqueSuivante();
            }
        });

        prec.setOnAction(new EventHandler<ActionEvent>() {
            // Passer au plateau précédent
            @Override
            public void handle(ActionEvent event)
            {

                game.epoquePrecedente();
            }
        });

        des.getLancer().setOnAction(new EventHandler<ActionEvent>() {
            // Lance les dés
            @Override
            public void handle(ActionEvent event)
            {
                game.lancerDes();
            }

        });

        finTour.setOnAction(new EventHandler<ActionEvent>() {
            // Finit le tour d'un joueur et passer au joueur suivant
            @Override
            public void handle(ActionEvent event)
            {
                game.joueurSuivant();
            }
        });

        // Initialise la vue
        game.initJeu();
    }

    public void initTourJoueur()
    {
        cTI.update();
        plS.update();
        cTC.desactiver();
        cTI.desactiver();
        cTC.desactiver();
        CTCards.desactiver();
        plS.desactiver();
        des.activer();
        finTour.setDisable(true);
        messageClassique = game.getPlayer().getNom() + " - Lancez les dés pour commencer";
        resetStatus();
        desactiveEchangeBouttonJoueurActuel();
        cTC.setPlayer(game.getPlayer());
    }

    public TabConstruction getcTC() {
        return cTC;
    }

    public PlayerState getPlayerState() {
        return plS;
    }

    public void desactiveEchangeBouttonJoueurActuel()
    {
        // Active tous les boutons pour les échanges
        for(int i=0;i<game.getNbJoueurs();++i)
            bt_echangerList[i].setDisable(false);
        // Désactive le bouton pour le joueur actuel
        bt_echangerList[game.getPlayer().getNumJoueur()-1].setDisable(true);
    }

    public void lanceDes(int[] tab)
    {
        des.actualiserResultats(tab);
        des.desactiver();
        plS.activer();
        finTour.setDisable(false);
        cTC.activer();
        cTI.activer();
        CTCards.activer();
        messageClassique = game.getPlayer().getNom() + " échangez, achetez, construisez puis terminez votre tour pour passer au joueur suivant";
        resetStatus();
    }

    public void chargerPlateau(Epoch epoque)
    {
        ViewBoard newPlateau = getViewBoard(epoque);
        stack.getChildren().removeAll(stack.getChildren());
        stack.getChildren().add(newPlateau);
        numPlateau.setText("Epoque : " + Epoch.toString(epoque));
        cTC.setEpoch(epoque);
    }

    public void setStatus(String str)
    {
        statusBar.setText(str);
    }

    public void resetStatus()
    {
        statusBar.setText(messageClassique);
    }

    public ViewBoard getViewBoard(Epoch epoque)
    {
        for(ViewBoard pl : plateaux)
            if(pl.getBoard().getEpoch() == epoque)
                return pl;
        return null;
    }

    public void setMessageClassique(String str)
    {
        messageClassique = str;
        resetStatus();
    }

    public void updateJoueur()
    {
        plS.update();
    }

    public TabCards getCTCards() {
        return CTCards;
    }


    public void initTourInitiaux()
    {
        initTourJoueur();
        des.desactiver();
        setMessageClassique(game.getPlayer() + " - Construisez votre première colonie !");
        game.changeConstructionActive();
    }

    public void afficheVainqueur()
    {
        GridPane grid = new GridPane();
        Button ok = new Button("OK");
        ok.setDefaultButton(true);
        grid.add(new Label(game.getPlayer().getNom()+", vous avez construit la dernière invention et donc vous \npossédez maintenant le TrainKiVol !!!\n\n Félicitations !"), 0, 0);
        grid.add(ok, 0, 1);
        TitledPane panneauVainqueur = new TitledPane("Félicitations !",grid);
        panneauVainqueur.setTranslateX(450);
        panneauVainqueur.setTranslateY(325);
        panneauVainqueur.setPrefSize(500, 200);
        panneauVainqueur.setId("divisions");
        panneauVainqueur.setCollapsible(false);
        getChildren().add(panneauVainqueur);
        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                videoFin();
            }
        });


    }

    public void videoFin()
    {
        final File f = new File("src/sons/VideoFinLO.mp4");
        final Media m = new Media(f.toURI().toString());
        final MediaPlayer mp = new MediaPlayer(m);
        final MediaView mv = new MediaView(mp);

        final DoubleProperty width = mv.fitWidthProperty();
        final DoubleProperty height = mv.fitHeightProperty();

        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

        mv.setPreserveRatio(true);
        StackPane root = new StackPane();
        root.getChildren().add(mv);
        final Scene scene = new Scene(root, 960, 540);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        mp.play();

    }

}
