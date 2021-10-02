package point;

public class Point {
    private double x;
    private double y;

    public Point(){
        setX(0);
        setY(0);
    }

    public Point(Point p){
        setX(p.getX());
        setY(p.getY());
    }

    public Point(double x, double y){
        setX(x);
        setY(y);
    }


    /* setters and getters */
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    
    public double getDistance(Point p) {
        double d;
        d = Math.sqrt(Math.pow(p.getY()-getY(), 2) + Math.pow(p.getX()-getX(), 2));
        return d;
    }

    @Override
    public String toString() {
        return "Point [" + getX() + ", " + getY() + "]";
    }
}