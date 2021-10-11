package polygon;

import java.awt.Color;
import java.awt.Graphics2D;

import window.Window;
import point.Point;
import point.GraphicPoint;

public class PolygonDrawings {
    public static void drawPolygonOnWindow(Graphics2D g, GraphicPoint p[], String name, int lineWeight, Color color){
        GraphicPolygon polygon = new GraphicPolygon(p, color, name, lineWeight);
        polygon.drawPolygon(g);
    }

    public static void drawPolygonOnViewport(Graphics2D g, Window window, Window viewport, Point p[], String name, int lineWeight, Color color){
        Point pAux[] = new Point[p.length];
        for (int i = 0; i < pAux.length; i++){
            pAux[i] = window.mapToViewport(viewport, p[i]);
        }

        GraphicPoint pAux2[] = new GraphicPoint[pAux.length];
        for (int i = 0; i < pAux2.length; i++){
            pAux2[i] = new GraphicPoint((int)pAux[i].getX(), (int)pAux[i].getY());
        }

        GraphicPolygon polygon = new GraphicPolygon(pAux2, Color.BLUE, name, lineWeight);

        polygon.drawPolygon(g);
    }
}
