package model.player;

import javafx.scene.paint.Color;
import model.game.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private String nom;
    private int num;
    private Color col;
    private String avatar;
    private HashMapRes res;
    private int accesEpoque;
    private HashMap<Invention,Boolean> inv;

    private ArrayList<Point> deloreans;      //villagesConstruits;
    private ArrayList<Point> superDeloreans; //m_villesConstruites;
    private ArrayList<Arrete> routes;
    private ArrayList<Arrete> autoroutes;

    private int nbeRoute;
    private int nbeAutoroute;
    private int nbeDeloreans;
    private int nbeSuperDeloreans;

    private int nbCartesDev;
    private int nbCartesDeplacerVoleur;
    private Game jeu;

    public Player(String nom, int num)
    {
        nom = nom;
        num = num;
        avatar = "textures/Avatar" + num + ".jpg";

        switch(num)
        {
            case 1:
                col = Color.RED;
                break;
            case 2:
                col = Color.BLUE;
                break;
            case 3:
                col = Color.YELLOW;
                break;
            case 4:
                col = Color.GREEN;
                break;
        }
        res = new HashMapRes();
        inv = new HashMap<>();
        deloreans        = new ArrayList<>();
        superDeloreans   = new ArrayList<>();
        routes           = new ArrayList<>();
        autoroutes       = new ArrayList<>();

        for (Invention i : Invention.values())
            inv.put(i, false);
        init();
    }

    public void init()
    {
        nbeRoute = 2;
        nbeAutoroute = 0;
        nbeDeloreans = 2;
        nbeSuperDeloreans = 0;
        nbCartesDeplacerVoleur =0;
        accesEpoque = 1;

        // Pour avoir toutes les ressources nécessaires pour tester toutes les fonctionnalités
        res.add(new HashMapRes(Invention.Train.cout(null)));
        res.add(new HashMapRes(Invention.Voiture.cout(null)));
        res.add(new HashMapRes(Invention.Avion.cout(null)));
        res.add(new HashMapRes(Invention.Hoverboard.cout(null)));

        res.add(new HashMapRes(Resources.Bois, 50, Resources.Moteur, 50, Resources.Plutonium, 50));
    }

    /*
     * Fonction pour dépenser un certain nombre d'une ressources : dépense les ressources
     */
    public void depenserRessources(HashMapRes pack)
    {
        res.remove(pack);
    }

    /*
     *  Ajoute la ressource au joueur
     */
    public void recevoirRessources(HashMapRes pack)
    {
        res.add(pack);
    }

    /*
     * Teste si le joueur a les ressources passées en paramètres
     */
    public boolean possede(HashMapRes pack)
    {
        return res.contains(pack);
    }

    /*
     * Test si le joueur possède un point de type type en stock
     */
    public boolean possede(BuildPoint type)
    {
        if(type == BuildPoint.Deloreans)
            return nbeDeloreans >= 1;
        else if(type == BuildPoint.SuperDeloreans)
            return nbeSuperDeloreans >= 1;
        return false;
    }

    /*
     * Test si le joueur possède un point de type type en stock
     */
    public boolean possede(BuildArrete type)
    {
        if(type == BuildArrete.Autoroute)
            return nbeAutoroute >= 1;
        else if(type == BuildArrete.Route)
            return nbeRoute >= 1;
        return false;
    }

    /*
     * Retourne le nombre de ressources que le joueur
     * correspondant à la ressource pass"e en paramètres
     */
    public int nbRessource(Resources r)
    {
        return res.count(r);
    }

    /*
     * Retourne vrai si le joueur possède l'invention
     * passée en paramètre
     */
    public boolean possedeInvention(Invention i)
    {
        return (inv.get(i));
    }

    /*
     * Vérifie si le joueur peut construire nbr objet(s) de type passé en paramètre
     */
    public boolean peutConstruire(Bank obj, int nbr, Epoch epoque)
    {
        HashMapRes cout = obj.cout(epoque);
        cout.mult(nbr);
        return possede(cout);
    }

    /*
     * Vérifie si le joueur peut construire un objet de type passé en paramètre
     */
    public boolean peutConstruire(Bank obj, Epoch epoque)
    {
        return peutConstruire(obj, 1, epoque);
    }

    public void construireInvention(HashMapRes pack, Invention i)
    {
        depenserRessources(pack);
        inv.put(i, true);
        testVictoire();

    }
    /*
     * Si le joueur achete la derniere invention
     * cette fonction lance l'écran de victoire
     */
    private void testVictoire()
    {
        boolean possedeTout = true;
        for(Invention i : Invention.values())
        {
            if(! possedeInvention(i))
            {
                possedeTout=false;
            }
        }

        if(possedeTout)
        {
            jeu.getFenetre().afficheVainqueur();
        }
    }

    public void acheterCarte(Cards tc)
    {
        depenserRessources(tc.cout(jeu.getEpoqueActuelle()));
        if (tc == Cards.DeplacerVoleur)
        {
            nbCartesDeplacerVoleur++;
            jeu.getFenetre().setStatus("Vous venez de piocher une carte voleur. Vous gagnez 1 déplacement de voleur à utiliser quand vous voulez.");
        }
        else
        {
            nbeRoute += 2;
            jeu.getFenetre().setStatus("Vous venez de piocher une carte developpement. Vous gagnez 2 routes constructibles.");
        }
    }

    public int getNbCartesDev() {
        return nbCartesDev;
    }


    public int getNbCartesDeplacerVoleur() {
        return nbCartesDeplacerVoleur;
    }

    public void construirePoint(BuildPoint type, Point point)
    {
        if(type == BuildPoint.Deloreans)
        {
            deloreans.add(point);
            nbeDeloreans--;
        }
        else
        {
            superDeloreans.add(point);
            nbeSuperDeloreans--;
        }
    }

    public void construireArete(BuildArrete type, Arrete arete)
    {
        if(type == BuildArrete.Autoroute)
        {
            boolean accesEpoque =true;
            for(Arrete a : autoroutes)
            {
                if(jeu.getEpoqueActuelle() == a.getEpoque())
                {
                    accesEpoque = false;
                }
            }
            if(accesEpoque)
            {
                accesNouvelleEpoque();
            }

            autoroutes.add(arete);
            nbeAutoroute--;
        }
        else
        {
            routes.add(arete);
            nbeRoute--;
        }
    }

    /*
     * Echange des ressources avec un autre joueur
     * le joueur courant (this) échange 'donne' ressources à autre joueur
     * et recoit 'recois' ressources en retour
     */
    public void echangerRessources(Player autre, HashMapRes donne, HashMapRes recois)
    {
        this.depenserRessources(donne);
        this.recevoirRessources(recois);
        autre.depenserRessources(recois);
        autre.recevoirRessources(donne);
    }

    public void acheter(Bank obj, int nbr)
    {
        HashMapRes cout = obj.cout(jeu.getEpoqueActuelle());
        cout.mult(nbr);
        depenserRessources(cout);


        if(obj == BuildArrete.Route)
        {
            nbeRoute+=nbr;
        }
        else if(obj == BuildArrete.Autoroute)
        {
            nbeAutoroute+=nbr;
        }
        else if(obj == BuildPoint.Deloreans)
        {
            nbeDeloreans+=nbr;
        }
        else if(obj == BuildPoint.SuperDeloreans)
        {
            nbeSuperDeloreans+=nbr;
        }
    }



    /*
     * Retourne le nombre de points correspondant au type passé en paramètre
     */
    public int getNbPoints(BuildPoint tp)
    {
        switch(tp)
        {
            case Deloreans:
                return deloreans.size();
            case SuperDeloreans:
                return superDeloreans.size();
            default:
                return 0;
        }
    }

    /*
     * Retourne le nombre d'arètes correspondant au type passé en paramètre
     */
    public int getNbAretes(BuildArrete ta)
    {
        switch(ta)
        {
            case Route:
                return routes.size();
            case Autoroute:
                return autoroutes.size();
            default:
                return 0;
        }
    }

    public String toString()
    {
        return nom;
    }

    public String getNom()
    {
        return nom;
    }

    public int getNumJoueur()
    {
        return num;
    }

    public Color getCouleur()
    {
        return col;
    }

    public void setJeu(Game j) {
        this.jeu = j;
    }


    public int getNbeRoute() {
        return nbeRoute;
    }


    public int getNbeAutoroute() {
        return nbeAutoroute;
    }


    public int getNbeDeloreans() {
        return nbeDeloreans;
    }


    public int getNbeSuperDeloreans() {
        return nbeSuperDeloreans;
    }

    public String getAvatar(){
        return avatar;
    }

    public HashMap<Invention, Boolean> getInventions() {
        return inv;
    }

    public void recevoirRessource(Resources r)
    {
        res.add(r);
    }

    public boolean isPremierPointSurPlateau()
    {
        for(Point point: deloreans)
            if(point.getEpoque() == jeu.getEpoqueActuelle())
                return false;
        return true;
    }

    public boolean peutDeplacerVoleur() {

        return(nbCartesDeplacerVoleur > 0);
    }

    public void utiliserCarteVoleur() {
        nbCartesDeplacerVoleur--;
    }

    public void accesNouvelleEpoque()
    {
        accesEpoque++;
    }

    public boolean aAcces(Epoch epoque)
    {
        switch (epoque)
        {
            case _1985:
                return accesEpoque>=1;
            case _2015:
                return accesEpoque>=2;
            case _1855:
                return accesEpoque>=3;
            case  _1908:
                return accesEpoque>=4;
        }
        return false;
    }

}
