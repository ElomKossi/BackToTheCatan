package model.game;

import model.coordinate.*;
import model.player.*;
import view.mainBoard.*;

public class Case {

    private CCase coord;
    private Resources res;
    private int val;
    private boolean voleurPresent;
    private ViewCase m_vue;

    public Case(CCase coo1, Resources res1, int val1 )
    {
        coord = coo1;
        res = res1;
        val = val1;
        if(val ==7)
            voleurPresent = true;
        else
            voleurPresent=false;
    }

    //Permet de créer des 'fausses' cases autour du plateau, elles n'apparaitront pas dans la vue
    public Case(CCase coo)
    {
        this(coo, Resources.Vortex, 0);
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
