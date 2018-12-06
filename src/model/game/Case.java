package model.game;

import model.coordinate.*;
import model.player.*;
import view.*;

public class Case {

    private CCase coord;
    private Resources res;
    private int val;
    private boolean voleurPresent;
    private ViewCase m_vue;

    public Case(CCase coo, Resources res, int val )
    {
        coord = coo;
        res = res;
        val = val;
        if(val ==7)
            voleurPresent = true;
        else
            voleurPresent=false;
    }

    //Permet de créer des 'fausses' cases autour du plateau, elles n'apparaitront pas dans la vue
    public Case(CCase coo)
    {
        this(coo, Resources.Autoroute, 0);
    }

    public CCase getCoo()
    {
        return coord;
    }

    public Resources getRessource()
    {
        return res;
    }

    public int getVal() {
        return val;
    }

    public boolean isVoleurPresent() {
        return voleurPresent;
    }

    public void setVoleurPresent(boolean voleurPresent) {
        this.voleurPresent = voleurPresent;
        m_vue.setImageVoleur(voleurPresent);
    }
    public void setVue(ViewCase vue)
    {
        m_vue = vue;
    }

    @Override
    public String toString()
    {
        return "Case : " + coord + ", contient : " + res;
    }

}
