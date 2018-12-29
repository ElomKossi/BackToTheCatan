package model.player;

import model.game.*;
import model.player.*;

public enum Invention implements Bank {

    Train
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Metal, 4, Resources.Rail, 5);
        }
    },

    Voiture
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.K7, 2, Resources.Roue, 4, Resources.Bois, 3);
        }
    },

    Avion
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Moteur, 3, Resources.Laser, 6);
        }
    },

    Hoverboard
    {
        @Override
        public HashMapRes cout(Epoch epoque)
        {
            return new HashMapRes(Resources.Plutonium, 4, Resources.Kryptonite, 2, Resources.Bois, 3);
        }
    }

}
