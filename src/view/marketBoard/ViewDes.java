package view.marketBoard;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import view.game.*;

public class ViewDes extends GridPane implements Etat {

    private Button lancer;
    private Label de1;
    private Label de2;
    private Label de3;
    private Label somme;
    private Label recup;

    public ViewDes()
    {
        setHgap(20);
        setVgap(20);

        de1 = new Label("Dé n° 1 :");
        de1.setId("divisions");
        de2 = new Label("Dé n° 2 :");
        de2.setId("divisions");
        de3 = new Label("Dé n° 3 :");
        de3.setId("divisions");
        somme = new Label("Somme :");
        somme.setId("divisions");
        recup = new Label("Récupération :");
        recup.setId("divisions");
        lancer = new Button("Lancer les dés");
        lancer.setDefaultButton(true);
        ImageView imgDes = new ImageView("image/Des.jpg");

        imgDes.setFitWidth(50);
        imgDes.setPreserveRatio(true);
        add(imgDes, 0, 1);
        add(lancer, 2, 1);
        add(de1, 0, 3, 2, 1);
        add(de2, 2, 3);
        //add(de3, 0, 3);
        add(somme, 0, 5);
        //add(recup, 0, 5);
    }

    public Button getLancer()
    {
        return lancer;
    }

    /*
     * Modifie les résultats afichés en fonction
     * du tableau d'int en parametre
     */
    public void actualiserResultats(int[] tab)
    {
        de1.setText("Dé n° 1 : " + tab[0]);
        de2.setText("Dé n° 2 : " + tab[1]);
        //de3.setText("Dé n° 3 : " + tab[0]);
        somme.setText("Somme : " + (tab[0]+tab[1]));
    }

    /*
     * Permet d'activer ou desactiver le lancer
     * de dés.
     */

    @Override
    public void desactiver() {

        lancer.setDisable(true);
    }

    @Override
    public void activer() {
        lancer.setDisable(false);
    }

}
