package line;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import point.GraphicPoint;

public class GraphicLine extends Line{
    Color lineColor = Color.BLACK;
    String lineName = "";
    Color lineColorName = Color.BLACK;
    int lineWeight = 1;

    public GraphicLine(int x1, int y1, int x2, int y2, Color color, String name, int lineWeight){
        super (x1, y1, x2, y2);
        setLineColor(color);
        setLineName(name);
        setLineWeight(lineWeight);
    }

    public GraphicLine(int x1, int y1, int x2, int y2, Color color){
        super (x1, y1, x2, y2);
        setLineColor(color);
        setLineName("");
    }

    public GraphicLine(int x1, int y1, int x2,int y2, Color color, int lineWeight){
        super (x1, y1, x2, y2);
        setLineColor(color);
        setLineName("");
        setLineWeight(lineWeight);
    }

    public GraphicLine(GraphicPoint p1, GraphicPoint p2){
        super (p1, p2);
        setLineName("");
    }

    public GraphicLine(GraphicPoint p1, GraphicPoint p2, Color color){
        super (p1, p2);
        setLineColor(color);
        setLineName("");
    }

    public GraphicLine(GraphicPoint p1, GraphicPoint p2, Color color, String name){
        super (p1, p2);
        setLineColor(color);
        setLineName(name);
    }

    /* Setters and getters */
    public void setLineColor(Color color){
        this.lineColor = color;
    }
    public Color getLineColor(){
        return lineColor;
    }
    public void setLineName(String name){
        this.lineName = name;
    }
    public String getLineName(){
        return lineName;
    }
    public void setLineWeight(int lineWeight){
        this.lineWeight = lineWeight;
    }
    public int getLineWeight(){
        return lineWeight;
    }
    public void setLineColorName(Color lineColorName) {
        this.lineColorName = lineColorName;
    }
    /*--------------------------------------*/

    public void drawLine(Graphics2D g){
        int x1 = (int)getP1().getX();
        int x2 = (int)getP2().getX();
        int y1 = (int)getP1().getY();
        int y2 = (int)getP2().getY();

        g.setColor(getLineColor());
        g.setStroke(new BasicStroke((float)getLineWeight()));

        g.drawLine(x1, y1, x2, y2);
        g.setColor(getLineColor());
        g.drawString(getLineName(), (int)getP1().getX() + getLineWeight(), (int)getP1().getY());
    }
}
