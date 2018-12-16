package view.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.game.*;
import model.player.*;
import view.game.*;

public class PlayerState extends GridPane implements Etat {

    private Game game;
    private Button construire, utiliserCarteVoleur;
    private ImageView imgPlayer;
    private Label labelPseudo;
    private Label ressources;
    private Label aConstruire;
    private Label inventions;
    private Label cartes;

    public PlayerState(Game game)
    {
        game = game;
        init();
        update();
    }

    public void init(){

        imgPlayer = new ImageView();
        labelPseudo = new Label();
        setPadding(new Insets(20, 20, 20, 20));
        setHgap(15);
        setVgap(15);

        imgPlayer.setFitWidth(100);
        imgPlayer.setFitHeight(100);
        ressources = new Label("Ressources");
        ressources.setId("divisions");

        construire = new Button("Construire");
        construire.setDefaultButton(true);
        construire.setOnMouseClicked((e)->{
            game.changeConstructionActive();
            if(game.isConstructionActive())
                construire.setEffect(new InnerShadow());
            else
                construire.setEffect(null);
        });

        aConstruire = new Label("A construire");
        aConstruire.setId("divisions");

        utiliserCarteVoleur = new Button("Utiliser");
        utiliserCarteVoleur.setDefaultButton(true);
        utiliserCarteVoleur.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                game.setDeplacementVoleurActif(true);


            }
        });
        inventions = new Label("Inventions : ");
        inventions.setId("divisions");


        cartes = new Label("Cartes : ");
        cartes.setId("divisions");
    }

    public void update()
    {
        Player j= game.getPlayer();
        getChildren().removeAll(getChildren());

        imgPlayer.setImage(new Image(j.getAvatar()));
        labelPseudo.setText(j.getNom());
        labelPseudo.setId("labelPseudo");

        add(imgPlayer, 0, 0);
        add(labelPseudo, 1, 0);
        add(ressources, 0, 2);

        add(new Label("Bois : " + j.nbRessource(Resources.Bois)), 0, 3);
        add(new Label("Métal : " + j.nbRessource(Resources.Metal)), 0, 4);
        add(new Label("Rail : " + j.nbRessource(Resources.Rail)), 0, 5);
        add(new Label("K7 : " + j.nbRessource(Resources.K7)), 0, 6);
        add(new Label("Roue : " + j.nbRessource(Resources.Roue)), 0, 7);
        add(new Label("Moteur : " + j.nbRessource(Resources.Moteur)), 1, 3);
        add(new Label("Sabre Laser : " + j.nbRessource(Resources.SabreLaser)), 1, 4);
        add(new Label("Plutonium : " + j.nbRessource(Resources.Plutonium)), 1, 5);
        add(new Label("Kryptonite : " + j.nbRessource(Resources.Kryptonite)), 1, 6);

        add(new Label("Routes : " + j.getNbeRoute()), 0, 10);
        add(new Label("Autoroutes : " + j.getNbeAutoroute()), 0, 11);
        add(new Label("Delorean : " + j.getNbeDeloreans()), 1, 10);
        add(new Label("Super Delorean : " + j.getNbeSuperDeloreans()), 1, 11);

	/*	if(game.isConstructionActive())
		{
			game.changeConstructionActive();
			construire.setEffect(null);
		}*/

        add(aConstruire, 0, 9);
        add(construire,1,9);
        add(inventions, 0, 13);

        add(new Label("Train : " + (j.possedeInvention(Invention.Train)?" Acquis": " Non Acquis")), 0, 14,2,1);
        add(new Label("Radio : " + (j.possedeInvention(Invention.Voiture)?" Acquis": " Non Acquis")), 0, 15,2,1);
        add(new Label("Conv. temp. : " +(j.possedeInvention(Invention.Avion)?" Acquis": " Non Acquis")), 0, 16,2,1);
        add(new Label("Hoverboard : " + (j.possedeInvention(Invention.Hoverboard)?" Acquis": " Non Acquis")), 0, 17,2,1);

        add(cartes, 0, 19);
        add(new Label("Dépl. Voleur : " + j.getNbCartesDeplacerVoleur()), 0, 20);
        add(utiliserCarteVoleur,1,20);
    }

    @Override
    public void desactiver()
    {
        construire.setDisable(true);
        utiliserCarteVoleur.setDisable(true);
    }

    @Override
    public void activer()
    {
        construire.setDisable(false);
        utiliserCarteVoleur.setDisable(false);
    }

}
