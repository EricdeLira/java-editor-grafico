package line;

import point.Point;

public class Line {
    public Point p1, p2;

    public Line(int x1, int y1, int x2, int y2){
        setP1(new Point(x1, y1));
        setP2(new Point(x2, y2));
    }

    public Line(double x1, double y1, double x2, double y2){
        setP1(new Point(x1, y1));
        setP2(new Point(x2, y2));
    }

    public Line(Point p1, Point p2) {
        setP1(p1);
        setP2(p2);
    }

    public Line(Line l){
        setP1(l.getP1());
        setP2(l.getP2());
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

    public double getA(){ // f(x) = ax + b
        double a = (getP2().getY() - getP1().getY())/(getP2().getX() - getP1().getX());
        return a;
    }
    public double getB(){ // f(x) = ax + b
        double b = getP1().getY() - getA()*getP1().getX();
        return b;
    }

    public String toString(){
        String s = "P1: " + getP1().toString() + " P2: " + getP2().toString() + "\nLinear Equation: y = " + getA() + "*x + " + getB();
        return s;
    }
}
