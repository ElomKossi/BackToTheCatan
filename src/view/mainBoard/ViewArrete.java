package view.mainBoard;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.game.*;
import view.game.*;
import view.mainBoard.*;

public class ViewArrete extends Group {

    private Arrete arrete;
    private Game game;
    private Fenetre fenetre;

    private Line ligne;
    private Line ligneJoueur;

    public ViewArrete(Arrete a, float debutX, float debutY, float finX, float finY, Game jeu)
    {
        arrete = a;
        game = jeu;
        fenetre = jeu.getFenetre();

        ligne = new Line(debutX, debutY, finX, finY);
        ligne.setStrokeWidth(Constant.roadWidth - 4);
        ligne.setStroke(Color.GRAY);

        ligneJoueur = new Line(debutX, debutY, finX, finY);
        ligneJoueur.setStrokeWidth(Constant.roadWidth);

        getChildren().addAll(ligneJoueur, ligne);

        /***** Evènements sur les objets *****/
        setOnMouseEntered((e) -> {
            ligne.setStroke(Color.LIGHTGRAY);
            ligneJoueur.setStroke(Color.LIGHTGRAY);
            if (game.isDeLance())
            {
                if (game.isConstructionActive())
                    fenetre.setStatus("Construire une nouvelle arète");
                else
                    fenetre.setStatus("Cliquez sur le boutton construire si vous souhaitez construire une liaison ici");
            }
        });
        setOnMouseExited((e) -> {
            fenetre.resetStatus();
            update();
        });
        setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event)
            {
                game.clicArete(arrete);
            }
        });

        update();
    }

    public void update()
    {
        if (arrete.getType() == BuildArrete.Route)
            ligne.setStroke(Color.GRAY);
        else if (arrete.getType() == BuildArrete.Autoroute)
            ligne.setStroke(Color.BLACK);
        else
            ligne.setStroke(Color.GRAY);
        if (arrete.getPlayer() != null)
            ligneJoueur.setStroke(arrete.getPlayer().getCouleur());
        else
            ligneJoueur.setStroke(ligne.getStroke());
    }

}
