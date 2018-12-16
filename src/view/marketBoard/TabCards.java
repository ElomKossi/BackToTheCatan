package view.marketBoard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.game.*;
import model.player.*;
import view.game.*;

public class TabCards extends GridPane implements Etat {

    private Button tirer;
    private Game game;
    private PlayerState plS;
    private Label cout;

    public TabCards(Game p_jeu, PlayerState plSB)
    {

        game = p_jeu;
        plS = plSB;

        ImageView cartes = new ImageView(new Image("image/cards.jpg"));
        tirer = new Button("Tirer une carte");
        tirer.setDefaultButton(true);
        cartes.setPreserveRatio(true);
        cartes.setFitWidth(100);

        setPadding(new Insets(20, 20, 20, 20));
        setHgap(15);
        setVgap(15);
        cout = new Label("Coût : \n - 1 x "+Resources.toString(Epoch.getRes1(p_jeu.getEpoqueActuelle()))+"\n - 1 x "+Resources.toString(Epoch.getRes2(p_jeu.getEpoqueActuelle())));
        add(cout, 0, 0,2,1);
        add(cartes, 0, 2);
        add(tirer,1,2);

        tirer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                game.tirerCarte();
                plS.update();

            }
        });

    }

    public void update()
    {
        cout.setText("Coût : \n - 1 x "+Resources.toString(Epoch.getRes1(game.getEpoqueActuelle()))+"\n - 1 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));
    }

    @Override
    public void desactiver() {
        tirer.setDisable(true);

    }

    @Override
    public void activer() {
        tirer.setDisable(false);

    }

}
