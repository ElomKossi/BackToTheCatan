package model.player;

import model.game.*;

public enum  Cards implements Bank {

    Developpement
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Epoch.getRes1(epoque), Epoch.getRes2(epoque));
        }
    },

    DeplacerVoleur
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Epoch.getRes1(epoque), Epoch.getRes2(epoque));
        }
    };

}
