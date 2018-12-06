package model.game;

import model.player.*;

public enum Epoch {

    _1855,
    _1908,
    _1985,
    _2015;

    public static Resources getRes1(Epoch epoque)
    {
        switch (epoque)
        {
            case _1855:
                return Resources.Metal;
            case _1908:
                return Resources.K7;
            case _1985:
                return Resources.Moteur;
            case _2015:
                return Resources.Plutonium;
        }
        return null;
    }

    public static Resources getRes2(Epoch epoque)
    {
        switch (epoque)
        {
            case _1855:
                return Resources.Rail;
            case _1908:
                return Resources.Roue;
            case _1985:
                return Resources.SabreLaser;
            case _2015:
                return Resources.Kryptonite;
        }
        return null;
    }

    public static String toString(Epoch epoque)
    {
        switch(epoque)
        {
            case _1855:
                return "1855";
            case _1908:
                return "1908";
            case _1985:
                return "1985";
            case _2015:
                return "2015";
        }
        return null;
    }

}
