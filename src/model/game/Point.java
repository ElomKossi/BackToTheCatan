package model.game;

import java.util.ArrayList;

import model.player.*;
import model.game.*;
import model.coordinate.*;
import view.*;

public class Point {

    private CPoint coord;
    private BuildPoint m_type;
    private Player proprio; //Si null, le point n'est pas encore utilisé
    private VuePoint m_vue;
    private Board board;

    public Point(CPoint coord, BuildPoint type, Player propietaire, Board plateau)
    {
        coord = coord;
        m_type = type;
        proprio = propietaire;
        board = plateau;
    }

    public Point(CPoint coord, Board plateau)
    {
        this(coord, BuildPoint.Vide, null, plateau);
    }

    public Player getPlayer()
    {
        return proprio;
    }

    public BuildPoint getType()
    {
        return m_type;
    }

    public CPoint getCoo()
    {
        return coord;
    }

    public void construire(Player player, BuildPoint type)
    {
        m_type = type;
        proprio = player;
    }

    public String peutConstruire(Player player, BuildPoint type)
    {
        if(!player.aAcces(board.getEpoch()))
        {
            return "Vous n'avez pas accès à cette époque";
        }
        else if(proprio != null && proprio != player)
        {// Chez un autre joueur
            return "Ce point appartient déjà à un autre joueur";
        }
        else if(m_type == BuildPoint.Vide && type==BuildPoint.SuperDeloreans)
        {// ville sur rien
            return "Une Ville ne peut être construite que sur un Village déjà existant";
        }
        else if(m_type == BuildPoint.SuperDeloreans && type == BuildPoint.Deloreans)
        {// village sur ville
            return "Vous ne pouvez pas construire de Village sur une Ville";
        }
        else if(type == m_type)
        {// t sur t
            return "Ce point est déjà de type " + type;
        }
        else if(player.isPremierPointSurPlateau())
        {
            CCase init = new CCase(0, 0, getEpoque());
            if(coord.getVertical().equals(init) || coord.getLeft().equals(init) || coord.getRight().equals(init))
                return null;
            else
                return "Votre première colonie du plateau doit se trouver autour du point central";
        }
        else if(!isAttache(player))
        {
            return "Le point que vous souhaitez construire doit rejoindre une de vos routes.";
        }
        // Sinon on peut construire
        return null;
    }

    public boolean isAttache(Player player)
    {
        ArrayList<Arrete> listArete = board.getAdjacentArete(this);

        for(Arrete a : listArete)
        {
            if(a.getPlayer()==player)
            {
                return true;
            }
        }
        return false;
    }

    public String toString()
    {
        return coord.toString();
    }

    public void setVue(VuePoint vue)
    {
        m_vue = vue;
    }

    public VuePoint getVue()
    {
        return m_vue;
    }

    public Epoch getEpoque()
    {
        return coord.getLeft().getEpoque();
    }

}