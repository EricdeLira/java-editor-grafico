package triangle;

import java.awt.Color;
import java.awt.Graphics2D;

import window.Window;
import point.Point;

public class TriangleDrawings {
    public static void drawTriangleOnWindow(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3, String name, int lineWeight, Color color){
        GraphicTriangle triangle = new GraphicTriangle(x1, y1, x2, y2, x3, y3, color, name, lineWeight);
        triangle.drawTriangle(g);
    }

    public static void drawTriangleOnViewport(Graphics2D g, Window window, Window viewport, int x1, int y1, int x2, int y2, int x3, int y3, String name, int lineWeight, Color color){
        Point auxPoint1 = window.mapToViewport(viewport, new Point(x1, y1));
        Point auxPoint2 = window.mapToViewport(viewport, new Point(x2, y2));
        Point auxPoint3 = window.mapToViewport(viewport, new Point(x3, y3));

        GraphicTriangle line = new GraphicTriangle((int)auxPoint1.getX(), (int)auxPoint1.getY(), (int)auxPoint2.getX(), (int)auxPoint2.getY(), (int)auxPoint3.getX(), (int)auxPoint3.getY(), Color.BLUE, name, lineWeight);

        line.drawTriangle(g);
    }
}
