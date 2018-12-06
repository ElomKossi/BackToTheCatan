package model.coordinate;

public class CPoint {

    private CCase left, right, vertical;

    public CPoint(CCase l, CCase r, CCase v)
    {
        left = l;
        right = r;
        vertical = v;
    }

    public CCase getLeft()
    {
        return left;
    }

    public CCase getRight()
    {
        return right;
    }

    public CCase getVertical()
    {
        return vertical;
    }

    public String toString()
    {
        return"("+left.toString()+";"+right.toString()+";"+vertical.toString()+")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CPoint that = (CPoint) o;

        if (left != null ? !left.equals(that.left) : that.left != null)
            return false;
        if (right != null ? !right.equals(that.right) : that.right != null)
            return false;
        return vertical != null ? vertical.equals(that.vertical) : that.vertical == null;

    }

    @Override
    public int hashCode()
    {
        int result = left != null ? left.hashCode() : 0;
        result = 31*result + (right != null ? right.hashCode() : 0);
        result = 31*result + (vertical != null ? vertical.hashCode() : 0);
        return result;
    }

}
