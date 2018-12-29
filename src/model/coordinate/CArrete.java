package model.coordinate;

public class CArrete {

    /*
     * coordArete : Classe qui permet de caractériser une arrete
     * Attributs :
     *  - debut, fin : coordonées des points délimitants l'arrete
     * Les deux attributs sont interchangeables.
     */
    private CPoint debut, fin;

    public CArrete(CPoint d, CPoint f)
    {
        debut = d;
        fin = f;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CArrete that = (CArrete) o;
        return (debut.equals(that.debut) && fin.equals(that.fin)) || (fin.equals(that.debut) && debut.equals(that.fin));
    }

    @Override
    public int hashCode()
    {
        // Le hashCode est le même si on intervertis debut et fin.
        return debut.hashCode()*fin.hashCode();
    }

    public CPoint getDebut()
    {
        return debut;
    }

    public CPoint getFin()
    {
        return fin;
    }

    public String toString()
    {
        return debut.toString()+"|"+fin.toString();
    }

}
