import java.awt.geom.Point2D;

/**
 * Created by JakeMullit on 05/05/15.
 */
public class PointWithAcceleration extends Point2D.Double {

    Integer xAcceleration;
    Integer yAcceleration;


    public PointWithAcceleration(int x, int y, int xAcceleration, int yAcceleration)
    {
        super(x,y);
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
    }



    public int getxAcceleration() {
        return xAcceleration;
    }

    public void setxAcceleration(int xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public int getyAcceleration() {
        return yAcceleration;
    }

    public void setyAcceleration(int yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    @Override
    public int hashCode()
    {
        int hash = xAcceleration*3*5*7;
        hash    += yAcceleration*11*13*17;
        hash    += x*19*21;
        hash    += y*23*27;

        return hash;
    }




}
