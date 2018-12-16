package view.mainBoard;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.game.*;
import view.mainBoard.Constant;
import view.game.*;

public class ViewPoint extends Group {

    private Point point;
    private Circle cercle;
    private ImageView imageSuperDelorean;
    private ImageView imageDelorean;
    private Rectangle rectangle;

    private Game game;
    private Fenetre fenetre;

    public ViewPoint(Point p, float centreX, float centreY, Game jeu)
    {
        point = p;
        game = jeu;
        fenetre = game.getFenetre();

        setOnMouseEntered((e)->{
            cercle.setFill(Color.WHITE);
            if(game.isDeLance())
            {
                if (game.isConstructionActive())
                    fenetre.setStatus("Construire un nouveau point");
                else
                    fenetre.setStatus("Cliquez sur le boutton construire si vous souhaitez construire un bâtiment ici");
            }
        });
        setOnMouseExited((e)->{
            fenetre.resetStatus();
            cercle.setFill(Color.BLACK);
        });
        setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                game.clicPoint(point);
            }
        });

        /** Initialisation du cercle **/
        cercle = new Circle(centreX,centreY, Constant.pointRadius);
        cercle.setStroke(Color.BLACK);
        cercle.setFill(Color.BLACK);

        /** Initialisation du rectangle **/
        rectangle = new Rectangle();
        rectangle.setWidth(35);
        rectangle.setHeight(35);
        rectangle.setX(centreX-rectangle.getWidth()/2);
        rectangle.setY(centreY-rectangle.getHeight()/2);

        /** Initialisation de l'image de la Delorean **/
        imageDelorean = new ImageView("image/Delorean.png");
        imageDelorean.setFitHeight(35);
        imageDelorean.setFitWidth(35);
        imageDelorean.setX(centreX-imageDelorean.getFitHeight()/2);
        imageDelorean.setY(centreY-imageDelorean.getFitWidth()/2);

        /** Initialisation de l'image de la Super Delorean **/
        imageSuperDelorean = new ImageView("image/SuperD.png");
        imageSuperDelorean.setFitHeight(35);
        imageSuperDelorean.setFitWidth(35);
        imageSuperDelorean.setX(centreX-imageSuperDelorean.getFitHeight()/2);
        imageSuperDelorean.setY(centreY-imageSuperDelorean.getFitWidth()/2);

        update();
    }

    public void update()
    {
        getChildren().removeAll(getChildren());
        if(point.getPlayer() != null)
            rectangle.setFill(point.getPlayer().getCouleur());
        if(point.getType() == BuildPoint.Deloreans)
            getChildren().addAll(rectangle, imageDelorean);
        else if(point.getType() == BuildPoint.SuperDeloreans)
            getChildren().addAll(rectangle, imageSuperDelorean);
        else
            getChildren().add(cercle);
    }

}
