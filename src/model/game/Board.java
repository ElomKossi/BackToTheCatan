package model.game;

import model.coordinate.*;
import model.player.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Board {

    private Epoch epoque;
    private CCase coordVoleur;
    private HashMap<CCase, Case> cases;
    private HashMap<CArrete, Arrete> arretes;
    private HashMap<CPoint, Point> points;
    private int size;

    public Board(Epoch ep, int size1)
    {
        epoque = ep;
        size = size1;
        init();
        coordVoleur = new CCase(0, 0, ep) ;
    }

    private void init()
    {
        initCases();
        initPoints();
        initArretes();
    }

    /*
     * Initialise la map de cases.
     * Les cases sont initialisées avec une ressource et une coordonée.
     */
    private void initCases()
    {
        int[][] listRessources = getRessourceTab(); // Initialise le tableau des ressources
        int[][] listValeurs = getValeursTab();
        cases = new HashMap<>();
        CCase coord;
        Case tuile;
        Resources res;
        for (int j = 1; j <= size; j++)
        {
            for (int i = 1; i <= size; i++)
            {
                coord = new CCase(j - 4, i - 4, epoque);
                if ((res = getResources(coord, listRessources)) != null)
                {
                    tuile = new Case(coord, res, listValeurs[j - 1][i - 1]);
                    cases.put(coord, tuile);
                }
            }
        }
    }

    /*
     * Initialise les points
     * Chaqe point passèe uniquement une coordonée à l'initialisation
     * Le type et le propriétaires sont initialilsés à null tant que personne n'a construit desssus.
     */
    private void initPoints()
    {
        points = new HashMap<>();
        CCase coo;
        CPoint cooPoint;
        for (Case tuile : cases.values())
        {
            coo = tuile.getCoo();
            cooPoint = new CPoint(coo.west(), coo, coo.northWest());
            if (!points.containsKey(cooPoint))
                points.put(cooPoint, new Point(cooPoint,this));

            cooPoint = new CPoint(coo.northWest(), coo.northEast(), coo);
            if (!points.containsKey(cooPoint))
                points.put(cooPoint, new Point(cooPoint,this));

            cooPoint = new CPoint(coo, coo.east(), coo.northEast());
            if (!points.containsKey(cooPoint))
                points.put(cooPoint, new Point(cooPoint,this));

            cooPoint = new CPoint(coo, coo.east(), coo.southEast());
            if (!points.containsKey(cooPoint))
                points.put(cooPoint, new Point(cooPoint,this));

            cooPoint = new CPoint(coo.southWest(), coo.southEast(), coo);
            if (!points.containsKey(cooPoint))
                points.put(cooPoint, new Point(cooPoint,this));

            cooPoint = new CPoint(coo.west(), coo, coo.southWest());
            if (!points.containsKey(cooPoint))
                points.put(cooPoint, new Point(cooPoint,this));
        }
    }

    /*
     * Initialise la table des arrete.
     * Les arretes sont initialisées avec une position,
     * le propriétaire et le type sont null tant que personne n'a construit dessus.
     */
    private void initArretes()
    {
        arretes = new HashMap<>();
        CArrete coodArrete;
        for (Case tuile : cases.values())
        {
            for (int i = 0; i < 6; i++)
            {
                coodArrete = new CArrete(getCooPoint(tuile, i), getCooPoint(tuile, (i + 1)%6));
                if (!arretes.containsKey(coodArrete))
                {
                    arretes.put(coodArrete, new Arrete(coodArrete, this));
                }
            }
        }
    }

    public List<Case> getListCases()
    {
        ArrayList<Case> cases1 = new ArrayList<Case>(cases.values());
        return cases1;
    }

    public HashMap<CCase, Case> getCases()
    {
        return cases;
    }

    public List<Arrete> getArretes()
    {
        ArrayList<Arrete> arrete1 = new ArrayList<Arrete>(arretes.values());
        return arrete1;
    }

    public List<Point> getPoints()
    {
        ArrayList<Point> point1 = new ArrayList<>(points.values());
        return point1;
    }

    public Epoch getEpoch()
    {
        return epoque;
    }

    /*
     * Retourne la coordonnée du point selon une case et un index
     * L'index représente quel point est sélectionné pour une case donnée
     * Les points sont ordonnées de 0 à 5 pour une case.
     * L'index 0 correspond au point situé en haut de la case, les suivants tant sélectionés par ordre horaire.
     * Si l'index n'est pas compris entre 0 et 5, la fonction renvoie une Exception de type IndexOutOfBoundsException
     */
    private CPoint getCooPoint(Case tuile, int index) throws IndexOutOfBoundsException
    {
        CCase coo = tuile.getCoo();
        switch (index)
        {
            case 0:
                return new CPoint(coo.northWest(), coo.northEast(), coo);
            case 1:
                return new CPoint(coo, coo.east(), coo.northEast());
            case 2:
                return new CPoint(coo, coo.east(), coo.southEast());
            case 3:
                return new CPoint(coo.southWest(), coo.southEast(), coo);
            case 4:
                return new CPoint(coo.west(), coo, coo.southWest());
            case 5:
                return new CPoint(coo.west(), coo, coo.northWest());
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    /*
     * Reourne la ressource possédée par la case dons les coordonées sont passés en paramètres.
     * La liste passée en paramètre correspond à la liste de toutes les ressources du plateau selon l'index.
     */
    private Resources getResources(CCase coordCase, int[][] list)
    {
        Resources r1, r2;
        if (epoque == Epoch._1855)
        {
            r1 = Resources.Metal;
            r2 = Resources.Rail;
        }
        else if (epoque == Epoch._1908)
        {
            r1 = Resources.K7;
            r2 = Resources.Roue;
        }
        else if (epoque == Epoch._1985)
        {
            r1 = Resources.Moteur;
            r2 = Resources.Laser;
        }
        else
        {
            r1 = Resources.Plutonium;
            r2 = Resources.Kryptonite;
        }
        switch (list[coordCase.getLine() + size/2][coordCase.getColumn() + size/2])
        {
            case 0:
                return null;
            case 1:
                return Resources.Vortex;
            case 2:
                return Resources.Bois;
            case 3:
                return r1;
        }
        return r2;

    }

    /*
     * Initialise le tableau des ressources.
     * Il sera ensuite utilisé pour retrouver quelle ressource correspond à quelle case du plateau.
     */
    private int[][] getRessourceTab()
    {
        //Warning: Le fichier doit contenir une matrice 7x7 avec un getNombre puis un espace, si on modifie la taille rien ne vas plus !
        // Crée un tableau avec les ressources présentes dans chaque case
        // Le tableau tab est initialisé à partir d'un fichier
        int[][] list = new int[size][size];
        String[] tab = null;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("F:\\Projet L043\\BackToTheCatan\\src\\image\\BoardPlan\\board1"));
            for (int i = 0; i < size; i++)
            {
                tab = reader.readLine().split(" ");
                for (int j = 0; j < size; j++)
                {
                    list[i][j] = Integer.parseInt(tab[j]);
                }
            }
            reader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    /*
     * Initialise le tableau des valeurs des cases.
     * Il sera ensuite utilisé pour retrouver quelle ressource correspond à quelle case du plateau.
     */
    private int[][] getValeursTab()
    {
        //Warning: Le fichier doit contenir ne matrice 7x7 avec un getNombre puis un espace, si on modifie la taille rien ne vas plus !
        // Crée un tableau avec les ressources présentes dans chaque case
        // Le tableau tab est initialisé à partir d'un fichier
        int[][] list = new int[size][size];
        String[] tab = null;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("F:\\Projet L043\\BackToTheCatan\\src\\image\\BoardPlan\\board2"));
            for (int i = 0; i < size; i++)
            {
                tab = reader.readLine().split(" ");
                for (int j = 0; j < size; j++)
                {
                    list[i][j] = Integer.parseInt(tab[j]);
                }
            }
            reader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    /*
     * Permet de récupérer les cases virtuelles du bord
     * qui n'existent pas en tant que case mais en tant
     * que coordonnée. (A utiliser avec précautions)
     */
    public Case getCasesVirtuelles(CCase coo)
    {
        Case c = cases.get(coo);
        if(c == null)
        {
            c = new Case(coo,Resources.Vortex,0);
        }

        return c;
    }

    public void recolterResources(int val)
    {
        for (Point pt : points.values())
            if (pt.getType() != BuildPoint.Vide)
                for (CCase coordCase : new CCase[]{pt.getCoo().getLeft(), pt.getCoo().getRight(), pt.getCoo().getVertical()})
                {
                    Case tuile = cases.get(coordCase);
                    if (tuile.getRessource() != Resources.Vortex && tuile.getVal() == val && !(tuile.isVoleurPresent()))
                    {
                        pt.getPlayer().recevoirRessource(tuile.getRessource());
                        if (pt.getType() == BuildPoint.SuperDeloreans)
                            pt.getPlayer().recevoirRessource(tuile.getRessource());
                    }
                }
    }

    public CCase getCoordVoleur() {
        return coordVoleur;
    }

    public void setCoordVoleur(CCase coordVoleur) {
        this.coordVoleur = coordVoleur;
    }

    public ArrayList<Point> getAdjacentPoints(Arrete arrete)
    {
        ArrayList<Point> list = new ArrayList<>(2);
        list.add(points.get(arrete.getCoord().getDebut()));
        list.add(points.get(arrete.getCoord().getFin()));
        return list;
    }

    public ArrayList<Arrete> getAdjacentArete(Arrete arrete) {
        ArrayList<Point> listPts = getAdjacentPoints(arrete);
        ArrayList<Arrete> res = new ArrayList<>();
        for(Arrete a : arretes.values())
        {
            if(listPts.get(0).getCoo().equals(a.getCoord().getDebut()))
            {
                res.add(a);
            }
            else if(listPts.get(0).getCoo().equals(a.getCoord().getFin()))
            {
                res.add(a);
            }
            else if(listPts.get(1).getCoo().equals(a.getCoord().getDebut()))
            {
                res.add(a);
            }
            else if(listPts.get(1).getCoo().equals(a.getCoord().getFin()))
            {
                res.add(a);
            }
        }
        while (res.contains(arrete))
            res.remove(arrete);
        return res;
    }

    public ArrayList<Arrete> getAdjacentArete(Point point)
    {
        ArrayList<Arrete> listArrete = new ArrayList<>(3);
        for (Arrete a : arretes.values())
        {
            if(point.getCoo().equals(a.getCoord().getDebut()) || point.getCoo().equals(a.getCoord().getFin()))
            {
                listArrete.add(a);
            }
        }
        return listArrete;
    }

}
