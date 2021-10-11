package rectangle;

import point.Point;

public class Rectangle {
    public Point p1, p2; // diagonals 

    public Rectangle(int x1, int y1, int x2, int y2){
        setP1(new Point(x1, y1));
        setP2(new Point(x2, y2));
    }

    public Rectangle(double x1, double y1, double x2, double y2){
        setP1(new Point(x1, y1));
        setP2(new Point(x2, y2));
    }

    public Rectangle(Point p1, Point p2) {
        setP1(p1);
        setP2(p2);
    }

    public Rectangle(Rectangle r){
        setP1(r.getP1());
        setP2(r.getP2());
    }

    /* Setters and getters */
    public void setP1(Point p){
        this.p1 = p;
    }
    public void setP2(Point p){
        this.p2 = p;
    }
    public Point getP1(){
        return this.p1;
    }
    public Point getP2(){
        return this.p2;
    }
    /*----------------------------*/

    public String toString(){
        return "P1: " + getP1().toString() + " P2: " + getP2().toString();
    }
}
