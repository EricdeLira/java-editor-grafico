package triangle;

import point.Point;

public class Triangle {
    public Point p1, p2, p3;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3){
        setP1(new Point(x1, y1));
        setP2(new Point(x2, y2));
        setP3(new Point(x3, y3));
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3){
        setP1(new Point(x1, y1));
        setP2(new Point(x2, y2));
        setP3(new Point(x3, y3));
    }

    public Triangle(Point p1, Point p2, Point p3){
        setP1(p1);
        setP2(p2);
        setP3(p3);
    }

    public  Triangle(Triangle t){
        setP1(t.getP1());
        setP2(t.getP2());
    }

    /* Setters and getters */
    public void setP1(Point p){
        this.p1 = p;
    }
    public void setP2(Point p){
        this.p2 = p;
    }
    public void setP3(Point p){
        this.p3 = p;
    }
    public Point getP1(){
        return this.p1;
    }
    public Point getP2(){
        return this.p2;
    }
    public Point getP3(){
        return this.p3;
    }
    /*----------------------------*/

    public String toString(){
        return "P1: " + getP1().toString() + " P2: " + getP2().toString() + " P3: " + getP3().toString();
    }
}
