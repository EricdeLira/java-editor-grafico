package triangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import point.GraphicPoint;

public class GraphicTriangle extends Triangle{
    Color triangleColor = Color.BLACK;
    String triangleName = "";
    Color triangleColorName = Color.BLACK;
    int lineWeight = 1;

    public GraphicTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, String name, int lineWeight){
        super (x1, y1, x2, y2, x3, y3);
        setTriangleColor(color);
        setTriangleName(name);
        setLineWeight(lineWeight);
    }

    public GraphicTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color){
        super (x1, y1, x2, y2, x3, y3);
        setTriangleColor(color);
        setTriangleName("");
    }

    public GraphicTriangle(int x1, int y1, int x2,int y2, int x3, int y3, Color color, int lineWeight){
        super (x1, y1, x2, y2, x3, y3);
        setTriangleColor(color);
        setTriangleName("");
        setLineWeight(lineWeight);
    }

    public GraphicTriangle(GraphicPoint p1, GraphicPoint p2, GraphicPoint p3){
        super (p1, p2, p3);
    }

    public GraphicTriangle(GraphicPoint p1, GraphicPoint p2, GraphicPoint p3, Color color){
        super (p1, p2, p3);
        setTriangleColor(color);
    }

    public GraphicTriangle(GraphicPoint p1, GraphicPoint p2, GraphicPoint p3, Color color, String name){
        super (p1, p2, p3);
        setTriangleColor(color);
        setTriangleName(name);
    }

    /* Setters and getters */
    public void setTriangleColor(Color color){
        this.triangleColor = color;
    }
    public Color getTriangleColor(){
        return triangleColor;
    }
    public void setTriangleName(String name){
        this.triangleName = name;
    }
    public String getTriangleName(){
        return triangleName;
    }
    public void setLineWeight(int lineWeight){
        this.lineWeight = lineWeight;
    }
    public int getLineWeight(){
        return lineWeight;
    }
    public void setTriangleColorName(Color lineColorName) {
        this.triangleColorName = lineColorName;
    }
    /*--------------------------------------*/

    public void drawTriangle(Graphics2D g){
        int x1 = (int)getP1().getX();
        int x2 = (int)getP2().getX();
        int y1 = (int)getP1().getY();
        int y2 = (int)getP2().getY();
        int x3 = (int)getP3().getX();
        int y3 = (int)getP3().getY();
        
        g.setColor(getTriangleColor());
        g.setStroke(new BasicStroke((float)getLineWeight()));

        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x2, y2, x3, y3);
        g.setColor(getTriangleColor());
        g.drawString(getTriangleName(), (int)getP1().getX() + getLineWeight(), (int)getP1().getY());
    }
}
