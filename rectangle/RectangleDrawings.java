package rectangle;

import java.awt.Color;
import java.awt.Graphics2D;

import window.Window;
import point.Point;

public class RectangleDrawings {
    public static void drawRectangleOnWindow(Graphics2D g, int x1, int y1, int x2, int y2, String name, int lineWeight, Color color){
        GraphicRectangle rectangle = new GraphicRectangle(x1, y1, x2, y2, color, name, lineWeight);
        rectangle.drawRectangle(g);
    }

    public static void drawRectangleOnViewport(Graphics2D g, Window window, Window viewport, int x1, int y1, int x2, int y2, String name, int lineWeight, Color color){
        Point auxPoint1 = window.mapToViewport(viewport, new Point(x1, y1));
        Point auxPoint2 = window.mapToViewport(viewport, new Point(x2, y2));

        GraphicRectangle rectangle = new GraphicRectangle((int)auxPoint1.getX(), (int)auxPoint1.getY(), (int)auxPoint2.getX(), (int)auxPoint2.getY(), Color.BLUE, name, lineWeight);

        rectangle.drawRectangle(g);
    }
}
