package polygon;

import point.Point;

public class PolygonTest {
    public static void main(String args[]) {
        Point points[] = {new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)};
        Polygon p = new Polygon(points);
        System.out.println(p);
    }
}
