package view.marketBoard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.game.*;
import model.player.*;
import view.game.*;

public class TabInvention extends GridPane implements Etat {

    private Button acheterTrain;
    private Button acheterVoiture;
    private Button acheterAvion;
    private Button acheterHoverboard;
    private Game game;

    public TabInvention(Game jeu, PlayerState ctj )
    {
        game = jeu;

        acheterTrain = new Button("Acheter");
        acheterVoiture = new Button("Acheter");
        acheterAvion = new Button("Acheter");
        acheterHoverboard = new Button("Acheter");

        acheterTrain.setDefaultButton(true);
        acheterVoiture.setDefaultButton(true);
        acheterAvion.setDefaultButton(true);
        acheterHoverboard.setDefaultButton(true);


        Label hoverboard = new Label("Avion Temporel");
        Label detailsTrain = new Label("Détails");
        Label detailsVoiture = new Label("Détails");
        Label detailsAvion = new Label("Détails");
        Label detailsHoverboard = new Label("Détails");
        StackPane stack = new StackPane();

        hoverboard.setWrapText(true);
        hoverboard.setPrefWidth(65);

        setPadding(new Insets(20, 20, 20, 20));
        setHgap(15);
        setVgap(15);
        setHeight(800);

        add(new Label("Train"), 0, 0);
        add(new Label("Voiture"), 0, 1);
        add(new Label("Avion"), 0, 2);
        add(new Label("Hoverboard"), 0, 3);


        acheterTrain.setPrefWidth(75);
        acheterVoiture.setPrefWidth(75);
        acheterAvion.setPrefWidth(75);
        acheterHoverboard.setPrefWidth(75);
        add(acheterTrain, 2, 0);
        add(acheterVoiture, 2, 1);
        add(acheterAvion, 2, 2);
        add(acheterHoverboard, 2, 3);
        add(detailsTrain, 3, 0);
        add(detailsVoiture, 3, 1);
        add(detailsAvion, 3, 2);
        add(detailsHoverboard, 3, 3);
        add(stack, 0, 5, 4, 10);


        //Gestion des Evenements

        detailsTrain.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event)
            {
                stack.getChildren().removeAll(stack.getChildren());
                stack.getChildren().add(new DetailInvention("Train", " - 4 x Roue\n           - 5 x Bois", "image/Inventions/train.jpg", "Ceci est un Train"));


            }
        });
        detailsVoiture.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event)
            {
                stack.getChildren().removeAll(stack.getChildren());
                stack.getChildren().add(new DetailInvention("Voiture", " - 2 x Antenne \n           - 4 x Haut-Parleur \n           - 3 x Métal", "image/Inventions/Voiture.jpg", "Ceci est une Voiture"));



            }
        });
        detailsAvion.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event)
            {
                stack.getChildren().removeAll(stack.getChildren());
                stack.getChildren().add(new DetailInvention("Avion Temporel", " - 3 x Morceau de Schéma \n           - 6 x Plutonium", "image/Inventions/Avion.jpeg", "Ceci est un Avion"));
            }
        });
        detailsHoverboard.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event)
            {
                stack.getChildren().removeAll(stack.getChildren());
                stack.getChildren().add(new DetailInvention("Hoverboard", " - 4 x Ventilateur \n           - 2 x Aimant \n           - 3 x Métal", "image/Inventions/hover.jpeg", "Ceci est un Hoverboard"));
            }
        });

        acheterTrain.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (game.getPlayer().peutConstruire(Invention.Train, Epoch._2015))
                {
                    game.getPlayer().construireInvention(Invention.Train.cout(Epoch._1855), Invention.Train);
                    acheterTrain.setDisable(true);
                    ctj.update();

                }
            }
        });

        acheterVoiture.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (game.getPlayer().peutConstruire(Invention.Voiture, Epoch._1908))
                {
                    game.getPlayer().construireInvention(Invention.Voiture.cout(Epoch._1908), Invention.Voiture);
                    acheterVoiture.setDisable(true);
                    ctj.update();
                }
            }
        });

        acheterAvion.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (game.getPlayer().peutConstruire(Invention.Avion, Epoch._1985))
                {
                    game.getPlayer().construireInvention(Invention.Avion.cout(Epoch._1985), Invention.Avion);
                    acheterAvion.setDisable(true);
                    ctj.update();
                }

            }
        });

        acheterHoverboard.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (game.getPlayer().peutConstruire(Invention.Hoverboard, Epoch._2015))
                {
                    game.getPlayer().construireInvention(Invention.Hoverboard.cout(Epoch._2015), Invention.Hoverboard);
                    acheterHoverboard.setDisable(true);
                    ctj.update();
                }
            }
        });


    }

    public void update()
    {
        activer();
        if(game.getPlayer().possedeInvention(Invention.Avion) );
        {
            acheterAvion.setDisable(true);
        }
        if(game.getPlayer().possedeInvention(Invention.Hoverboard));
        {
            acheterHoverboard.setDisable(true);
        }
        if(game.getPlayer().possedeInvention(Invention.Voiture));
        {
            acheterVoiture.setDisable(true);
        }
        if(game.getPlayer().possedeInvention(Invention.Train));
        {
            acheterTrain.setDisable(true);
        }
    }

    @Override
    public void desactiver() {

        acheterAvion.setDisable(true);
        acheterHoverboard.setDisable(true);
        acheterVoiture.setDisable(true);
        acheterTrain.setDisable(true);

    }

    @Override
    public void activer() {
        acheterAvion.setDisable(false);
        acheterHoverboard.setDisable(false);
        acheterVoiture.setDisable(false);
        acheterTrain.setDisable(false);
    }
}
