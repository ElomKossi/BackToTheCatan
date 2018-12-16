package view.mainBoard;

import javafx.scene.control.TitledPane;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.player.*;
import view.game.*;

public class ViewBank extends TitledPane {

    private Player jGauche, jDroite;
    private Button fermer, echanger;
    private AffichePlayer aj1, aj2;
    private PlayerState plS;


    public ViewBank(PlayerState p_plS)
    {
        aj1 = new AffichePlayer();
        aj2 = new AffichePlayer();
        plS = p_plS;

        setVisible(false);
        setText("Echange");
        setCollapsible(false);
        setMaxHeight(450);
        setMaxWidth(1000);
        setId("popup");

        fermer = new Button("Fermer");
        fermer.setDefaultButton(true);
        echanger = new Button("Echanger");
        echanger.setDefaultButton(true);

        HBox contenu = new HBox(aj1,new Separator(Orientation.VERTICAL),aj2);
        contenu.setSpacing(100);
        contenu.setAlignment(Pos.CENTER);

        fermer.setAlignment(Pos.BASELINE_RIGHT);
        echanger.setAlignment(Pos.BASELINE_RIGHT);
        HBox boutons = new HBox(fermer,echanger);
        VBox vbox = new VBox(contenu,boutons);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER_RIGHT);

        //Gestion des Evenements

        fermer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hide();

            }
        });

        echanger.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                HashMapRes donne = new HashMapRes();
                HashMapRes recois = new HashMapRes();

                for(Entry<Resources, Spinner<Integer>> e : aj1.getQuantites().entrySet())
                {
                    donne.add(e.getKey(),e.getValue().getValue());
                }

                for(Entry<Resources, Spinner<Integer>> e : aj2.getQuantites().entrySet())
                {
                    recois.add(e.getKey(),e.getValue().getValue());
                }

                jGauche.echangerRessources(jDroite, donne, recois);

                show(jGauche,jDroite);
                plS.update();

            }
        });

        setContent(vbox);

    }

    private void setPlayers(Player p_jGauche, Player p_jDroite)
    {
        jGauche = p_jGauche;
        jDroite = p_jDroite;
    }

    public void show(Player j1, Player j2)
    {
        setPlayers(j1,j2);
        aj1.updateFields(j1);
        aj2.updateFields(j2);

        setVisible(true);
    }

    private void hide()
    {
        setVisible(false);
    }


    private class AffichePlayer extends GridPane
    {
        private HashMap<Resources,Spinner<Integer>> quantites;
        private HashMap<Resources,Label> disponibles;
        private Label nomJoueur;
        private ImageView imgAvatar;

        public  AffichePlayer()
        {
            int i=0;
            quantites = new HashMap<Resources, Spinner<Integer>>();
            disponibles = new HashMap<Resources,Label>();
            imgAvatar = new ImageView();
            setHgap(20);
            setVgap(20);

            for(Resources r : Resources.values())
            {
                if(!(r.equals(Resources.Autoroute)))
                {
                    //Ajout des spinners
                    Spinner<Integer> spinTemp = new Spinner<Integer>(0,99,0);
                    spinTemp.setPrefWidth(100);
                    quantites.put(r,spinTemp);
                    add(spinTemp,2,i+3);

                    //Ajout des labels de type
                    add(new Label(Resources.toString(r)+" :"),0,i+3);

                    //Ajout des labels de ressources disponibles
                    Label lTemp = new Label("0");
                    disponibles.put(r, lTemp);
                    add(lTemp,1,i+3);

                    i++;
                }

            }

            imgAvatar.setFitWidth(50);
            imgAvatar.setPreserveRatio(true);
            add(imgAvatar,0,0);

            nomJoueur = new Label("");
            nomJoueur.setId("divisions");
            nomJoueur.setAlignment(Pos.CENTER);
            add(nomJoueur,1,0,3,1);

            Label lType = new Label("Type");
            lType.setId("divisions");
            add(lType,0,1);

            Label lDispo = new Label("Disponibles");
            lDispo.setId("divisions");
            add(lDispo,1,1);

            Label lEchange = new Label("A Echanger");
            lEchange.setId("divisions");
            add(lEchange,2,1);

        }

        public HashMap<Resources, Spinner<Integer>> getQuantites() {
            return quantites;
        }

        public void updateFields(Player j)
        {
            nomJoueur.setText(j.getNom());
            imgAvatar.setImage(new Image(j.getAvatar()));

            for(Entry<Resources,Label> e : disponibles.entrySet())
            {
                e.getValue().setText(""+j.nbRessource(e.getKey()));
            }

            for(Entry<Resources,Spinner<Integer>> e : quantites.entrySet())
            {
                e.getValue().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.parseInt(disponibles.get(e.getKey()).getText())));
            }

        }
    }

}
