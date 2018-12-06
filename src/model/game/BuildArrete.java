package model.game;

import model.player.*;

public enum BuildArrete implements Bank {

    Route
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Metal, Epoch.getRes2(epoque));
        }
    },

    Autoroute
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Metal, Resources.Metal, Epoch.getRes2(epoque));
        }
    },

    Vide
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return null;
        }
    }

}
