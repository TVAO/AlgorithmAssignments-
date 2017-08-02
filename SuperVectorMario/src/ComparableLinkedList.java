import java.util.LinkedList;

/**
 * Created by JakeMullit on 01/05/15.
 */
public class ComparableLinkedList extends LinkedList<int[]> implements Comparable<ComparableLinkedList> {

    public int xAcceleration;
    public int yAcceleration;


    @Override
    public int compareTo(ComparableLinkedList list)
    {
        if(size()<list.size())
            return -1;
        else if(size()==list.size())
            return 0;
        else return 1;
    }

    public int getxAcceleration()
    {
        return xAcceleration;
    }

    public int getyAcceleration()
    {
        return yAcceleration;
    }

    public void xAccelerate()
    {
        this.xAcceleration++;
    }

    public void DeXAccelerate()
    {
        this.xAcceleration--;
    }

    public void yAccelerate()
    {
        this.yAcceleration++;
    }

    public void DeYAccelerate()
    {
        this.yAcceleration--;
    }

    public void setAcceleration(int xAcceleration,int yAcceleration)
    {
        this.yAcceleration = yAcceleration;
        this.xAcceleration = xAcceleration;
    }

    /**Add the next coordinate to the path next coordiante = coordinate+ acceleration
    */
    public void insertNextCoordinate()
    {
        int x = getLast()[0]+getxAcceleration();
        int y = getLast()[1]+getyAcceleration();
        if(x<0) //We are only dealing with positive coordinates.
            x= 0;
        if(y<0) //We are only dealing with positive coordinates.
            y=0;
        add(new int[]{x,y});
    }


    public PointWithAcceleration getPoint()
    {
        int x = getLast()[0];
        int y = getLast()[1];
        return new PointWithAcceleration(x,y, xAcceleration,yAcceleration);
    }
}





