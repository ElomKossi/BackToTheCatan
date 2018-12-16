package model.game;

import model.player.*;
import view.game.*;
import view.mainBoard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {

    /*
     * Gère tous les joueurs ainsi que tous les plateaux.
     */
    private HashMap<Epoch, Board> plateaux;
    private ArrayList<Player> joueurs;
    private int nbJoueurs, joueurActuel;
    private Epoch epoqueActuelle;

    private boolean constructionActive, deplacementVoleurActif;
    private boolean desLances;
    private boolean initPeriod;
    private Fenetre vue;

    private Random random = new Random();

    /*
     * Construit le modele de jeu à partir de la liste des joueurs
     * fournie par le menu principal, intialise les plateux de jeu
     */
    public Game(ArrayList<Player> p_joueurs)
    {
        epoqueActuelle = Epoch._1985;
        joueurs = p_joueurs;
        nbJoueurs = joueurs.size();
        for (Player j : joueurs)
            j.setJeu(this);

        plateaux = new HashMap<Epoch, Board>();
        for (Epoch epoque : Epoch.values())
            plateaux.put(epoque, new Board(epoque, 7));
    }

    public void initJeu()
    {
        joueurActuel = 0;
        desLances = false;
        initPeriod = true;
        constructionActive = true;
        deplacementVoleurActif = false;
        epoqueModifiee();
        vue.initTourInitiaux();
    }

    /**
     * Setters classiques
     **/
    public void setVue(Fenetre fen)
    {
        vue = fen;
    }

    /**
     * getters classiques
     **/
    public int getNbJoueurs()
    {
        return nbJoueurs;
    }

    public Board getPlateau(Epoch epoque)
    {
        return plateaux.get(epoque);
    }

    public void lancerDes()
    {
        int de1 = random.nextInt(6) + 1;
        int de2 = random.nextInt(6) + 1;

        int tab[] = {de1, de2};
        desLances = true;
        vue.lanceDes(tab);
        recolterRessources(de1 + de2);
        vue.updateJoueur();
    }

    public void tirerCarte()
    {
        int res = random.nextInt(3);
        Cards typeCarte;
        if (res < 2)
        {
            typeCarte = Cards.Developpement;
        }
        else
        {
            typeCarte = Cards.DeplacerVoleur;
        }
        if (getPlayer().peutConstruire(typeCarte, epoqueActuelle))
            getPlayer().acheterCarte(typeCarte);
        else
            vue.setStatus("Vous ne possédez pas assez de ressources pour acheter une carte");
    }

    public void joueurSuivant()
    {
        if(initPeriod)
        {
            if(++joueurActuel >= nbJoueurs)
            {
                joueurActuel=0;
                initPeriod = false;
                vue.initTourJoueur();
            }
            else
            {
                vue.initTourInitiaux();
                constructionActive = true;
            }
        }
        else
        {
            desLances = false;
            if (++joueurActuel >= nbJoueurs)
                joueurActuel = 0;
            vue.initTourJoueur();
        }
    }

    public boolean isDeplacementVoleurActif() {
        return deplacementVoleurActif;
    }

    public void setDeplacementVoleurActif(boolean deplacementVoleurActif) {
        this.deplacementVoleurActif = deplacementVoleurActif;
    }

    public Player getPlayer()
    {
        return joueurs.get(joueurActuel);
    }

    public Player getPlayer(int i)
    {
        return joueurs.get(i);
    }

    public Epoch getEpoqueActuelle()
    {
        return epoqueActuelle;
    }

    public void epoqueSuivante()
    {
        switch (epoqueActuelle)
        {
            case _1855:
                epoqueActuelle = Epoch._1908;
                break;
            case _1908:
                epoqueActuelle = Epoch._1985;
                break;
            case _1985:
                epoqueActuelle = Epoch._2015;
                break;
            case _2015:
                epoqueActuelle = Epoch._1855;
                break;
        }
        epoqueModifiee();
    }

    public void epoquePrecedente()
    {
        switch (epoqueActuelle)
        {
            case _1855:
                epoqueActuelle = Epoch._2015;
                break;
            case _1908:
                epoqueActuelle = Epoch._1855;
                break;
            case _1985:
                epoqueActuelle = Epoch._1908;
                break;
            case _2015:
                epoqueActuelle = Epoch._1985;
                break;
        }
        epoqueModifiee();
    }

    public void epoqueModifiee()
    {
        vue.chargerPlateau(epoqueActuelle);
        vue.getCTCards().update();
        vue.getcTC().update();
    }

    public void clicPoint(Point point)
    {
        if (constructionActive)
        {
            BuildPoint type = point.getType();
            if (type == BuildPoint.Vide)
                type = BuildPoint.Deloreans;
            else if (type == BuildPoint.Deloreans)
                type = BuildPoint.SuperDeloreans;
            construirePoint(type, point);
        }
    }

    public void clicArete(Arrete arete)
    {
        if (constructionActive && !initPeriod)
        {
            BuildArrete type = arete.getType();
            if (type == BuildArrete.Vide)
            {
                if (arete.peutEtreAutoroute())
                    type = BuildArrete.Autoroute;
                else
                    type = BuildArrete.Route;
            }
            construireArete(type, arete);
        }
    }

    public void clicCase(Case c, ViewCase vc)
    {
        if(deplacementVoleurActif && getPlayer().peutDeplacerVoleur())
        {
            deplacerVoleur(c,vc);
            getPlayer().utiliserCarteVoleur();
            vue.updateJoueur();
            deplacementVoleurActif=false;
        }
    }

    private void deplacerVoleur(Case c, ViewCase vc)
    {
        c.setVoleurPresent(true);
        Case voleurActuelle = plateaux.get(epoqueActuelle).getCases().get(plateaux.get(epoqueActuelle).getCoordVoleur());
        voleurActuelle.setVoleurPresent(false);
        plateaux.get(epoqueActuelle).setCoordVoleur(c.getCoo());
    }

    public void changeConstructionActive()
    {
        constructionActive = !constructionActive;
        if (constructionActive)
            vue.setMessageClassique("Sélectionner la case sur laquelle vous souhaitez construire");
        else
            vue.setMessageClassique(getPlayer().getNom() + " échangez, achetez, construisez puis terminez votre tour pour passer au joueur suivant");
    }

    public boolean isConstructionActive()
    {
        return constructionActive;
    }

    public Fenetre getFenetre()
    {
        return vue;
    }

    public boolean isDeLance()
    {
        return desLances;
    }

    public boolean peutConstruirePoint(BuildPoint type, Point point)
    {
        if (!getPlayer().possede(type))
        {
            vue.setStatus("Vous n'avez pas de " + type + " disponible, achetez-en au marché !");
            return false;
        }
        String res = point.peutConstruire(getPlayer(), type);
        if (res != null)
        {
            vue.setStatus(res);
            return false;
        }
        return true;
    }

    public void construirePoint(BuildPoint type, Point point)
    {
        if (peutConstruirePoint(type, point))
        {
            getPlayer().construirePoint(type, point);
            point.construire(getPlayer(), type);
            vue.updateJoueur();
            point.getVue().update();
            if(initPeriod)
            {
                joueurSuivant();
            }
        }
    }

    public boolean peutConstruireArete(BuildArrete type, Arrete arete)
    {
        if (!getPlayer().possede(type))
        {
            vue.setStatus("Vous n'avez pas de " + type + " disponible, achetez-en au marché !");
            return false;
        }
        String res = arete.peutConstruire(getPlayer(), type);
        if (res != null)
        {
            vue.setStatus(res);
            return false;
        }
        return true;
    }

    public void construireArete(BuildArrete type, Arrete arete)
    {
        if (peutConstruireArete(type, arete))
        {
            getPlayer().construireArete(type, arete);
            arete.construire(getPlayer(), type);
            vue.updateJoueur();
            arete.getVue().update();
        }
    }


    public boolean testAchatConstructions(int nbRoute, int nbAutoroute, int nbVillage, int nbVille, Epoch epoque, Player joueur)
    {
        HashMapRes cout = new HashMapRes();
        cout.add(BuildArrete.Route.cout(epoque), nbRoute);
        cout.add(BuildArrete.Autoroute.cout(epoque), nbAutoroute);
        cout.add(BuildPoint.Deloreans.cout(epoque), nbVillage);
        cout.add(BuildPoint.SuperDeloreans.cout(epoque), nbVille);

        return joueur.possede(cout);
    }

    public void recolterRessources(int val)
    {
        for (Board plateau : plateaux.values())
            plateau.recolterResources(val);
    }

}
