package point;

import java.awt.Color;
import java.awt.Graphics2D;
import window.Window;

public class PointDrawings {

    public static void drawPointOnWindow(Graphics2D g, int x, int y, String name, int diameter, Color color){
        GraphicPoint point = new GraphicPoint(x, y, color, name, diameter+1);
        point.drawPoint(g);
    }

    public static void drawPointOnViewport(Graphics2D g, Window window, Window viewport, int x, int y, String name, int diameter, Color color){

        Point auxPoint = window.mapToViewport(viewport, new Point(x, y));

        GraphicPoint point = new GraphicPoint((int)auxPoint.getX(), (int)auxPoint.getY(), Color.BLUE, name, diameter+1);

        point.drawPoint(g);
    }

    public static void drawMultiplePoints(Graphics2D g, int qty, int diameter){
        for(int i=0; i < qty; i++){
            int x = (int) (Math.random() * 801);
            int y = (int) (Math.random() * 801);

            Color color = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));

            GraphicPoint point = new GraphicPoint(x, y, color, diameter);

            point.drawPoint(g);
        }
    }
}
