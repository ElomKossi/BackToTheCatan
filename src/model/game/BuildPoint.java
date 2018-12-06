package model.game;

import model.player.*;

public enum BuildPoint implements Bank {

    Deloreans
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Bois, 2, Epoch.getRes2(epoque), 2);
        }
    },

    SuperDeloreans
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Bois, 4, Epoch.getRes2(epoque), 3);
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
