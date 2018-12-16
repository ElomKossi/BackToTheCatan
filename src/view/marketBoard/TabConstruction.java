package view.marketBoard;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import model.game.*;
import model.player.*;
import view.game.*;

public class TabConstruction extends GridPane implements Etat {

    private Spinner<Integer> spinRoute;
    private Spinner <Integer> spinAutoroute;
    private Spinner <Integer> spinVillage;
    private Spinner <Integer> spinVille;
    private Button  acheter;
    private Game game;
    private	Epoch epoque;
    private	Player joueur;

    Label coutRoute;
    Label coutAutoroute;
    Label coutDelorean;
    Label coutSuperDelorean;

    public TabConstruction(Game jeu, PlayerState ctj)
    {
        game = jeu;
        spinRoute = new Spinner<Integer>(0, 100, 0);
        spinAutoroute = new Spinner <Integer>(0, 100, 0);
        spinVillage = new Spinner <Integer>(0, 100, 0);
        spinVille = new Spinner <Integer>(0, 100, 0);

        coutAutoroute = new Label("- 2 x Bois\n- 1 x "+Resources.toString(Epoch.getRes1(game.getEpoqueActuelle())));
        coutRoute = new Label("- 1 x Bois\n- 1 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));
        coutDelorean = new Label("- 2 x Bois\n- 2 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));
        coutSuperDelorean = new Label("- 4 x Bois\n- 3 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));

        acheter = new Button("Acheter");
        acheter.setDefaultButton(true);


        spinRoute.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

                if(!(game.testAchatConstructions(newValue, spinAutoroute.getValue(), spinVillage.getValue(), spinVille.getValue(),epoque,joueur)))
                {
                    spinRoute.decrement();
                }

            }
        });

        spinAutoroute.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue)
            {
                if(!(game.testAchatConstructions(spinRoute.getValue(),newValue, spinVillage.getValue(),spinVille.getValue(),epoque,joueur)))
                {
                    spinAutoroute.decrement();
                }
            }
        });

        spinVillage.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if(!(game.testAchatConstructions(spinRoute.getValue(), spinAutoroute.getValue(), newValue,  spinVille.getValue(),epoque,joueur)))
                {
                    spinVillage.decrement();
                }

            }
        });

        spinVille.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if(!(game.testAchatConstructions(spinRoute.getValue(), spinAutoroute.getValue(), spinVillage.getValue(), newValue,epoque,joueur)))
                {
                    spinVille.decrement();
                }
            }
        });

        acheter.setOnMouseClicked((e)->{
            if( spinRoute.getValue() > 0)
                joueur.acheter(BuildArrete.Route, spinRoute.getValue());
            if( spinAutoroute.getValue() > 0)
                joueur.acheter(BuildArrete.Autoroute, spinAutoroute.getValue());
            if( spinVillage.getValue() > 0)
                joueur.acheter(BuildPoint.Deloreans, spinVillage.getValue());
            if( spinVille.getValue() > 0)
                joueur.acheter(BuildPoint.SuperDeloreans,  spinVille.getValue());

            ctj.update();
            reset();
        });

        setPadding(new Insets(20,5,20,5));
        setHgap(5);
        setVgap(15);

        add(new Label("Route"), 0, 0);
        add(new Label("Autoroute"), 0, 1);
        add(new Label("Village"), 0, 2);
        add(new Label("Ville"), 0, 3);

        spinRoute.setPrefWidth(50);
        spinAutoroute.setPrefWidth(50);
        spinVillage.setPrefWidth(50);
        spinVille.setPrefWidth(50);
        add(spinRoute, 2, 0);
        add(spinAutoroute, 2, 1);
        add(spinVillage, 2, 2);
        add(spinVille, 2, 3);
        add(coutRoute, 3, 0);
        add(coutAutoroute, 3, 1);
        add(coutDelorean, 3, 2);
        add(coutSuperDelorean, 3, 3);
        add(acheter, 3, 4);

        reset();
    }

    public void reset()
    {
        spinRoute.getValueFactory().setValue(0);
        spinAutoroute.getValueFactory().setValue(0);
        spinVillage.getValueFactory().setValue(0);
        spinVille.getValueFactory().setValue(0);
    }



    public void setPlayer(Player j)
    {
        joueur = j;
    }

    public void setEpoch(Epoch e)
    {
        epoque = e;
    }

    @Override
    public void desactiver() {

        acheter.setDisable(true);

    }

    @Override
    public void activer() {

        acheter.setDisable(false);

    }

    public void update()
    {
        coutAutoroute.setText("- 2 x Bois\n- 1 x "+Resources.toString(Epoch.getRes1(game.getEpoqueActuelle())));
        coutRoute.setText("- 1 x Bois\n- 1 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));
        coutDelorean.setText("- 2 x Bois\n- 2 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));
        coutSuperDelorean.setText("- 4 x Bois\n- 3 x "+Resources.toString(Epoch.getRes2(game.getEpoqueActuelle())));
    }

}
