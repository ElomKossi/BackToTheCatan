package model.player;

public enum  Resources {

    Autoroute,
    Vortex,
    Bois,
    Metal,
    Rail,
    K7,
    Roue,
    Moteur,
    SabreLaser,
    Plutonium,
    Kryptonite;

    public static String toString(Resources r)
    {
        switch(r)
        {
            case Autoroute:
                return "Autoroute";
            case Vortex:
                return "Vortex";
            case Bois:
                return "Bois";
            case Metal:
                return "Metal";
            case Rail:
                return "Rail";
            case K7:
                return "K7";
            case Roue:
                return "Roue";
            case Moteur:
                return "Moteur";
            case SabreLaser:
                return "Sabre Laser";
            case Plutonium:
                return "Plutonium";
            case Kryptonite:
                return "Kryptonite";
        }

        return "";
    }
}
