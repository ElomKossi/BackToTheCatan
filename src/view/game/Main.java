package view.game;

import javafx.application.Application;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.game.Game;
import view.game.Fenetre;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            MainMenu menuView = new MainMenu();
            Scene menu = new Scene(menuView, 1400, 850);
            //menu.getStylesheets().add(getClass().getResource("StyleMenu.css").toExternalForm());

            //VueRegles reglesView = new VueRegles(primaryStage);
            //Scene regles = new Scene(reglesView,1400,850);

            primaryStage.setScene(menu);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Back To The Catane");
            //primaryStage.getIcons().add(new Image("textures/favicon.jpg"));
            primaryStage.show();

            menuView.getNouvellePartie().setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event)
                {

                    menuView.setParametersVisible();

                }
            });
            menuView.getValider().setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event)
                {

                    Game modelJeu = new Game(menuView.getListeJoueurs());
                    Fenetre fen = new Fenetre(modelJeu,primaryStage);
                    Scene EcranJeu = new Scene(fen, 1400, 850);
                    //EcranJeu.getStylesheets().add(getClass().getResource("StyleEcranJeu.css").toExternalForm());

                    primaryStage.setScene(EcranJeu);
                    primaryStage.show();


                    /*final File file = new File("src/sons/nomdezeus.mp3");
                    final Media media = new Media(file.toURI().toString());
                    final MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();*/
                }
            });

            menuView.getQuitter().setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    primaryStage.close();

                }
            });

            /*menuView.getRegles().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    primaryStage.setScene(regles);
                    primaryStage.show();

                }
            });

            reglesView.getRetour().setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    primaryStage.setScene(menu);
                }
            });*/

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
