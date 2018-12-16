package view.mainBoard;

import javafx.scene.Group;
import model.game.*;
import model.coordinate.*;
import view.mainBoard.*;

public class ViewBoard extends Group {

    private int nbeCasesLarge;
    private Group cases;
    private Group arretes;
    private Group points;
    private Board board;

    private Game game;

    public Board getboard() {
        return board;
    }

    public ViewBoard(int x, int y, Board plateau, Game jeu)
    {
        super();
        setTranslateX(x);
        setTranslateY(y);
        board = plateau;
        game = jeu;

        initCases();
        initAretes();
        initPoints();
    }

    private void initPoints()
    {
        SimpleFloatCoo center;
        points = new Group();
        for (Point pt : board.getPoints())
        {
            center = getCoord(pt.getCoo());
            ViewPoint vue = new ViewPoint(pt,center.x, center.y, game);
            points.getChildren().add(vue);
            pt.setVue(vue);
        }
        getChildren().add(points);
    }

    private void initAretes()
    {
        arretes = new Group();
        SimpleFloatCoo debut, fin;
        ViewArrete vueArete;
        for (Arrete arrete : board.getArretes())
        {
            debut = getCoord(arrete.getCoord().getDebut());
            fin = getCoord(arrete.getCoord().getFin());
            vueArete = new ViewArrete(arrete, debut.x, debut.y, fin.x, fin.y, game);
            arrete.setVue(vueArete);
            arretes.getChildren().add(vueArete);
        }
        getChildren().add(arretes);
    }

    private void initCases()
    {
        // Retrouve la taille du plateau
        // /!\ doit être hexagonal
        int min, max;
        min = max = board.getListCases().get(0).getCoo().getLine();
        for (Case tuile : board.getListCases())
        {
            int i;
            i = tuile.getCoo().getLine();
            if (i < min)
                min = i;
            if (i > max)
                max = i;
        }
        nbeCasesLarge = max - min + 1;

        // Initialise toutes les cases
        cases = new Group();
        ViewCase viewCase;
        CCase coo;
        SimpleFloatCoo simplefCoo;
        for (Case tuile : board.getListCases())
        {
            coo = tuile.getCoo();
            simplefCoo = getCoord(coo);
            viewCase = new ViewCase(simplefCoo.x - Constant.hexWidth/2, simplefCoo.y - Constant.hexHeight/2, tuile,game);
            cases.getChildren().add(viewCase);
            tuile.setVue(viewCase);
        }
        getChildren().add(cases);
    }

    public Board getBoard()
    {
        return board;
    }

    /*
     * Retourne la position du centre de l'hexagone en fonction des coordonnés d'entrée
     */
    private SimpleFloatCoo getCoord(CCase coordCase)
    {
        //Warning : Ne marche que pour une taille de 7
        int rayon = (nbeCasesLarge + 1)/2;//en nombre d'hexagones
        float x, y, x_offset, y_offset;
        //Positions de l'hexa (0,0) :
        x_offset = (1 + (1.5f*(rayon - 1)))*Constant.hexWidth*2/(float) Math.sqrt(3);
        y_offset = Constant.hexHeight/2*nbeCasesLarge;
        x = x_offset + Constant.hexWidth/2*coordCase.getLine() + Constant.hexWidth*coordCase.getColumn();
        y = y_offset + Constant.hexHeight/2*1.5f*coordCase.getLine();
        return new SimpleFloatCoo(x, y);
    }

    /*
     * Retourne la position du centre du point
     */
    private SimpleFloatCoo getCoord(CPoint coordPoint)
    {
        SimpleFloatCoo c1, c2, c3;
        c1 = getCoord(coordPoint.getLeft());
        c2 = getCoord(coordPoint.getRight());
        c3 = getCoord(coordPoint.getVertical());
        return new SimpleFloatCoo((c1.x + c2.x + c3.x)/3, (c1.y + c2.y + c3.y)/3);
    }

    /*
     * Classe simple pour retourner 2 valeurs dans une fonction
     */
    private class SimpleFloatCoo {
        private float x, y;

        SimpleFloatCoo(float p_x, float p_y)
        {
            x = p_x;
            y = p_y;
        }
    }

}
