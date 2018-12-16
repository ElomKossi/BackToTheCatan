package view.mainBoard;

import sun.security.acl.GroupImpl;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.game.*;
import model.player.*;
import view.mainBoard.*;

public class ViewCase extends Group {

    private Case cases;
    private ImageView imgRessource;
    private Game game;

    public ViewCase(float p_x, float p_y, Case caseB, Game jeu)
    {
        super();
        game = jeu;
        cases = caseB;
        setTranslateX(p_x + Constant.roadWidth/2);
        setTranslateY(p_y + Constant.roadWidth/2);

        /*Affichage de l'image correspondant à la ressource*/
        imgRessource = new ImageView("image/Haxagone/Hex" + cases.getRessource() + ".png");
        imgRessource.setFitHeight(Constant.hexHeight - Constant.roadWidth);
        imgRessource.setFitWidth(Constant.hexWidth - Constant.roadWidth);
        setImageVoleur(cases.isVoleurPresent());

        getChildren().add(imgRessource);

        if (cases.getRessource() != Resources.Autoroute)//on ajoute un numéro que s'il ne s'agit pas d'une case autoroute
        {

            /*Affichage du numéro*/
            double milieuX = imgRessource.getFitWidth()/2;
            double milieuY = imgRessource.getFitHeight()/2;
            Circle cercleNoir = new Circle(milieuX, milieuY + 15, 10, Color.BLACK);
            Circle cercleBlanc = new Circle(milieuX, milieuY + 15, 9, Color.WHITE);
            Label numero = new Label("" + cases.getVal());
            numero.setTranslateX(milieuX - 7);
            numero.setTranslateY(milieuY + 7);
            numero.setTextAlignment(TextAlignment.CENTER);
            numero.setId("gras");

            getChildren().add(cercleNoir);
            getChildren().add(cercleBlanc);
            getChildren().add(numero);

        }

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                ViewCase view = (ViewCase) event.getSource();
                game.clicCase(cases, view);
            }
        });
    }

    public void setImageVoleur(boolean voleurPresent)
    {
        if(voleurPresent)
            imgRessource.setImage(new Image("image/Player/Biff.png"));
        else
            imgRessource.setImage(new Image("image/Haxagone/Hex" + cases.getRessource() + ".png"));
    }

}
