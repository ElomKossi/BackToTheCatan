package view.mainBoard;

//import sun.rmi.rmic.Main;
//import sun.security.acl.GroupImpl;
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

        //Affichage de l'image correspondant à la ressource
        /*if(cases.getRessource().toString() != "Autoroute")
        {

        }*/

        String s = "Hex"+cases.getRessource().toString()+".png";
        System.out.println(s);
        imgRessource = new ImageView("/image/Hexagone/" + s);
        //imgRessource = new ImageView("/Users/joke/IdeaProjects/BackToTheCatan/src/image/Hexagone/Hex" + s + ".png");
        //imgRessource = new ImageView("/image/Hexagone/HexBois.png");
        //imgRessource = new ImageView(Main.getString("/Users/joke/IdeaProjects/BackToTheCatan/src/image/Hexagone/Hex" + cases.getRessource() + ".png"));
        imgRessource.setFitHeight(Constant.hexHeight - Constant.roadWidth);
        imgRessource.setFitWidth(Constant.hexWidth - Constant.roadWidth);
        setImageVoleur(cases.isVoleurPresent());

        getChildren().add(imgRessource);

        if (cases.getRessource() != Resources.Vortex)//on ajoute un numéro que s'il ne s'agit pas d'une case autoroute
        {
            /*Affichage du numéro des hexagones*/
            double milieuX = imgRessource.getFitWidth()/2;
            double milieuY = imgRessource.getFitHeight()/2;
            Circle cercleNoir = new Circle(milieuX + 50, milieuY + 50, 13, Color.BLACK);
            Circle cercleBlanc = new Circle(milieuX + 50, milieuY + 50, 11, Color.WHITE);
            Label numero = new Label("" + cases.getVal());
            numero.setTranslateX(milieuX + 45);
            numero.setTranslateY(milieuY + 40);
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
            imgRessource.setImage(new Image("/image/Player/Biff.png"));
        else
        {
            if(cases.getRessource().toString() != "Autoroute")
            {
                String s = "Hex"+cases.getRessource().toString()+".png";
                //System.out.println(s);
                imgRessource = new ImageView("/image/Hexagone/" + s);
            }
        }
            //imgRessource.setImage(new Image("/image/Hexagone/HexBois.png"));
            //imgRessource.setImage(new Image("/image/Hexagone/Hex" + cases.getRessource() + ".png"));
    }

}
