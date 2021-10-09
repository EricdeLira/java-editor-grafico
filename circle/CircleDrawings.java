package circle;
import java.awt.Color;
import java.awt.Graphics2D;

import window.Window;
import point.Point;

public class CircleDrawings {
    public static void drawCircleOnWindow(Graphics2D g, int xCenter, int yCenter, int radius, String name, int lineWeight, Color color){
        GraphicCircle circle = new GraphicCircle(xCenter, yCenter, radius, color, name, lineWeight);
        circle.drawCircle(g);
    }

    public static void drawCircleOnViewport(Graphics2D g, Window window, Window viewport, int xCenter, int yCenter, int radius, String name, int lineWeight, Color color){
        Point auxPoint = window.mapToViewport(viewport, new Point(xCenter, yCenter));
        int auxRadius = window.mapToViewport(viewport, radius);

        GraphicCircle circle = new GraphicCircle((int)auxPoint.getX(), (int)auxPoint.getY(), auxRadius, Color.BLUE, name, lineWeight);

        circle.drawCircle(g);
    }
}
