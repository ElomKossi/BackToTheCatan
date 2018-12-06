package model.player;

import java.util.HashMap;

public class HashMapRes {

    private HashMap<Resources, Integer> me;

    public HashMapRes()
    {
        me = new HashMap<>();
        for (Resources r : Resources.values())
        {
            me.put(r, 0);
        }
    }

    public HashMapRes(Resources... rs)
    {
        this();
        for (Resources r : rs)
        {
            add(r);
        }
    }

    public HashMapRes(Resources rs1, int nb1, Resources rs2, int nb2)
    {
        this();
        add(rs1, nb1);
        add(rs2, nb2);
    }

    public HashMapRes(Resources rs1, int nb1, Resources rs2, int nb2, Resources rs3, int nb3)
    {
        this();
        add(rs1, nb1);
        add(rs2, nb2);
        add(rs3, nb3);
    }

    public HashMapRes(HashMapRes pack)
    {
        this();
        me.forEach((r, s) -> set(r, pack.me.get(r)));
    }

    public void add(Resources r)
    {
        me.put(r, me.get(r) + 1);
    }

    public void add(Resources r, int nbr)
    {
        me.put(r, me.get(r) + nbr);
    }

    public void remove(Resources ress)
    {
        me.put(ress, me.get(ress) - 1);
    }

    public void remove(Resources r, int nbr)
    {
        me.put(r, me.get(r) - nbr);
    }

    public void set(Resources r, int nbr)
    {
        me.put(r, nbr);
    }

    public void mult(int factor)
    {
        /* Multiplie tous les champs par factor */
        me.forEach((r, i) -> me.put(r, i*factor));
    }

    public void add(HashMapRes pack)
    {
        for (Resources r : Resources.values())
        {
            set(r, me.get(r) + pack.me.get(r));
        }
    }

    public void add(HashMapRes pack, int nbr)
    {
        for (Resources Resources : Resources.values())
        {
            set(Resources, me.get(Resources) + nbr*pack.me.get(Resources));
        }
    }

    public void remove(HashMapRes pack)
    {
        for (Resources Resources : Resources.values())
        {
            set(Resources, me.get(Resources) - pack.me.get(Resources));
        }
    }

    public boolean contains(HashMapRes pack)
    {
        boolean res = true;
        for (Resources ress : Resources.values())
        {
            res &= me.get(ress) >= pack.me.get(ress);
        }
        return res;
    }

    public int count(Resources ress)
    {
        return me.get(ress);
    }
    
}
