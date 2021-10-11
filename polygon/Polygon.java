package polygon;

import point.Point;

public class Polygon {
    public Point p[];

    public Polygon(Point point[]) {
        this.p = new Point[point.length];
        for (int i = 0; i < p.length; i++){
            setP(point[i], i);
        }
    }

    /* Setters and getters */
    public void setP(Point p, int index){
        this.p[index] = p;
    }
    
    public Point getP(int index){
        return this.p[index];
    }
    /*----------------------------*/

    public String toString(){
        String s = "";
        for (int i = 0; i < p.length; i++){
            s += "P"+i+": "+p[i]+" ";
        }
        return s;
    }
}
