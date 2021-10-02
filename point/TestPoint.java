package point;

public class TestPoint {
    public static void main(String args[]) {
        Point p1 = new Point();
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2.0, 2.0);
        Point p4 = new Point(p3);
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 = " + p3);
        System.out.println("p4 = " + p4);
        System.out.println("Distance between p2 and p3 = " + p2.getDistance(p3));
    }
}
