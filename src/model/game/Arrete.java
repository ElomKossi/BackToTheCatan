package model.game;

import model.coordinate.*;
import model.player.*;
import view.*;
import view.mainBoard.ViewArrete;

import java.util.ArrayList;

public class Arrete {

    /*
     * Correspond à un coté d'une case.
     */
    private CArrete coord;
    private BuildArrete typeA;
    private Player player;
    private Board board;

    private ViewArrete vue;

    public Arrete(CArrete coord, BuildArrete type, Player player, Board board)
    {
        coord = coord;
        type = type;
        player = player;
        board = board;
    }

    public Arrete(CArrete coord, Board board)
    {
        this(coord, BuildArrete.Vide, null, board); // A vérifier le dernier NULL
    }

    public CArrete getCoord()
    {
        return coord;
    }

    public Player getPlayer()
    {
        return player;
    }

    public BuildArrete getType()
    {
        return typeA;
    }

    public void setVue(ViewArrete vue)
    {
        vue = vue;
    }

    public ViewArrete getVue()
    {

        return vue;
    }

    public void construire(Player player, BuildArrete type)
    {
        typeA = type;
        player = player;
    }

    public String peutConstruire(Player player, BuildArrete type)
    {
        if(!player.aAcces(board.getEpoch()))
        {
            return "Vous n'avez pas accès à cette époque";
        }
        else if (player != null && player != player)
        {
            return "Cette arète appartient déjà à un autre joueur";
        } else if (type != BuildArrete.Vide)
        {//
            return "Cette arète est déjà construite";
        } else if (!isAttache(player))
        {
            return "Cette arète doit être ratachéz à une autre vous appartenant";
        }
        return null;
    }
    /*
     * Renvoie true si la constructions sur l'arete doit
     * être une autoroute
     */
    public boolean peutEtreAutoroute()
    {
        CPoint x = coord.getDebut();
        CPoint y = coord.getFin();
        CCase dx = x.getRight();
        CCase gx = x.getLeft();
        CCase vx = x.getVertical();
        CCase dy = y.getRight();
        CCase gy = y.getLeft();
        CCase vy = y.getVertical();


        if (dx.equals(dy) || gx.equals(gy))
        {
            if (board.getCasesVirtuelles(dx).getRessource() == Resources.Autoroute || board.getCasesVirtuelles(gx).getRessource() == Resources.Autoroute)
            {
                return true;
            }
            else
            {
                return false;
            }
        } else if (gx.equals(vy) || dy.equals(vx))
        {
            if (board.getCasesVirtuelles(dy).getRessource() == Resources.Autoroute || board.getCasesVirtuelles(gx).getRessource() == Resources.Autoroute)
            {
                return true;

            }
            else
            {
                return false;
            }
        } else if (vx.equals(gy) || vy.equals(dx))
        {
            if (board.getCasesVirtuelles(vx).getRessource() == Resources.Autoroute || board.getCasesVirtuelles(dx).getRessource() == Resources.Autoroute)
            {
                return true;
            }
            else
            {
                return false;
            }
        } else
        {
            return false;
        }
    }

    public String toString()
    {
        return coord.toString();
    }

    public boolean isAttache(Player player)
    {
        // Test si un point adjacent est occupé
        ArrayList<Point> ptsAdjacents = board.getAdjacentPoints(this);
        for(Point pt : ptsAdjacents)
            if(pt.getPlayer() == player)
            {
                return true;
            }
        // Test si une arete adjacent est occupé
        ArrayList<Arrete> areteAdjacents = board.getAdjacentArete(this);
        for(Arrete a : areteAdjacents)
        {
            if(a.getPlayer() == player)
            {
                return true;
            }
        }
        return false;
    }

    public Epoch getEpoque()
    {
        return board.getEpoch();
    }

}
