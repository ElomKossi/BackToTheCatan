package model.coordinate;

import model.game.*;

public class CCase {

    /*
     * La case de coordonée nulle est la case centrale.
     * La variable ligne augmente quand on prend la case à l'est de la case initiale.
     * La variable column augmente quand on prend la case au SudEst de la case initiale
     */

    private int line;
    private int column;
    private Epoch epoch;

    public CCase(int l, int c, Epoch epoch)
    {
        line = l;
        column = c;
        epoch = epoch;
    }

    public int getLine()
    {
        return line;
    }

    public int getColumn()
    {
        return column;
    }

    public Epoch getEpoque()
    {
        return epoch;
    }

    public CCase northEast()
    {
        return new CCase(line - 1, column + 1, epoch);
    }

    public CCase east()
    {
        return new CCase(line, column + 1, epoch);
    }

    public CCase southEast()
    {
        return new CCase(line + 1, column, epoch);
    }

    public CCase southWest()
    {
        return new CCase(line + 1, column - 1, epoch);
    }

    public CCase west()
    {
        return new CCase(line, column-1, epoch);
    }

    public CCase northWest()
    {
        return new CCase(line-1, column, epoch);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CCase aCCase = (CCase) o;

        if (line != aCCase.line)
            return false;
        return column == aCCase.column;
    }

    public boolean equals(CCase cc)
    {
        return (line == cc.getLine() && column == cc.getColumn() && epoch.equals(cc.getEpoque()));
    }

    @Override
    public String toString()
    {
        return "(" + line + ", " + column + ")";
    }

    @Override
    public int hashCode()
    {
        int result = line;
        result = 31*result + column;
        return result;
    }
}
