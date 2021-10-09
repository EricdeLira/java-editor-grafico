package circle;

import point.Point;

public class Circle {
    private Point center;
    private double radius;

    public Circle(Point center, double radius){
        setCenter(center);
        setRadius(radius);
    }
    public Circle(double x, double y, double radius){
        setCenter(new Point(x, y));
        setRadius(radius);
    }
    public Circle(Circle c){
        setCenter(new Point(c.getCenter()));
        setRadius(c.getRadius());
    }

    /* Setters and getters */
    public void setCenter(Point center){
        this.center = center;
    }
    public Point getCenter(){
        return center;
    }
    public void setRadius(double radius){
        this.radius = radius;
    }
    public double getRadius(){
        return radius;
    }
    public String toString() {
        return "=>Circle\n  Center: " + getCenter().toString()+ " Radius: " + getRadius(); 
    }
}
