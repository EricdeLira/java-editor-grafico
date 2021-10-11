package rectangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import point.GraphicPoint;

public class GraphicRectangle extends Rectangle{
    Color rectangleColor = Color.BLACK;
    String rectangleName = "";
    Color rectangleColorName = Color.BLACK;
    int lineWeight = 1;

    public GraphicRectangle(int x1, int y1, int x2, int y2, Color color, String name, int lineWeight){
        super (x1, y1, x2, y2);
        setRectangleColor(color);
        setRectangleName(name);
        setLineWeight(lineWeight);
    }

    public GraphicRectangle(int x1, int y1, int x2, int y2, Color color){
        super (x1, y1, x2, y2);
        setRectangleColor(color);
        setRectangleName("");
    }

    public GraphicRectangle(int x1, int y1, int x2,int y2, Color color, int lineWeight){
        super (x1, y1, x2, y2);
        setRectangleColor(color);
        setRectangleName("");
        setLineWeight(lineWeight);
    }

    public GraphicRectangle(GraphicPoint p1, GraphicPoint p2){
        super (p1, p2);
    }

    public GraphicRectangle(GraphicPoint p1, GraphicPoint p2, Color color){
        super (p1, p2);
        setRectangleColor(color);
    }

    public GraphicRectangle(GraphicPoint p1, GraphicPoint p2, Color color, String name){
        super (p1, p2);
        setRectangleColor(color);
        setRectangleName(name);
    }

    /* Setters and getters */
    public void setRectangleColor(Color color){
        this.rectangleColor = color;
    }
    public Color getRectangleColor(){
        return rectangleColor;
    }
    public void setRectangleName(String name){
        this.rectangleName = name;
    }
    public String getRectangleName(){
        return rectangleName;
    }
    public void setLineWeight(int lineWeight){
        this.lineWeight = lineWeight;
    }
    public int getLineWeight(){
        return lineWeight;
    }
    public void setRectangleColorName(Color lineColorName) {
        this.rectangleColorName = lineColorName;
    }
    /*--------------------------------------*/

    public void drawRectangle(Graphics2D g){
        int x1 = (int)getP1().getX();
        int x2 = (int)getP2().getX();
        int y1 = (int)getP1().getY();
        int y2 = (int)getP2().getY();

        g.setColor(getRectangleColor());
        g.setStroke(new BasicStroke((float)getLineWeight()));

        g.drawLine(x1, y1, x2, y1);
        g.drawLine(x1, y1, x1, y2);
        g.drawLine(x1, y2, x2, y2);
        g.drawLine(x2, y1, x2, y2);
        g.setColor(getRectangleColor());
        g.drawString(getRectangleName(), (int)getP1().getX() + getLineWeight(), (int)getP1().getY());
    }
}
