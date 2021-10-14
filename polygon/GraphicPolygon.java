package polygon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import point.GraphicPoint;

public class GraphicPolygon extends Polygon{
    Color polygonColor = Color.BLACK;
    String polygonName = "";
    Color polygonColorName = Color.BLACK;
    int lineWeight = 1;

    public GraphicPolygon(GraphicPoint p[], Color color, String name, int lineWeight){
        super (p);
        setPolygonColor(color);
        setPolygonName(name);
        setLineWeight(lineWeight);
    }

    public GraphicPolygon(GraphicPoint p[], Color color){
        super (p);
        setPolygonColor(color);
        setPolygonName("");
    }

    public GraphicPolygon(GraphicPoint p[], Color color, int lineWeight){
        super (p);
        setPolygonColor(color);
        setPolygonName("");
        setLineWeight(lineWeight);
    }

    public GraphicPolygon(GraphicPoint p[]){
        super (p);
    }

    public GraphicPolygon(GraphicPoint p[], Color color, String name){
        super (p);
        setPolygonColor(color);
        setPolygonName(name);
    }

    /* Setters and getters */
    public void setPolygonColor(Color color){
        this.polygonColor = color;
    }
    public Color getPolygonColor(){
        return polygonColor;
    }
    public void setPolygonName(String name){
        this.polygonName = name;
    }
    public String getPolygonName(){
        return polygonName;
    }
    public void setLineWeight(int lineWeight){
        this.lineWeight = lineWeight;
    }
    public int getLineWeight(){
        return lineWeight;
    }
    public void setPolygonColorName(Color lineColorName) {
        this.polygonColorName = lineColorName;
    }
    /*--------------------------------------*/

    public void drawPolygon(Graphics2D g){
        for (int i = 0; i < p.length; i++){
            if(i != p.length-1){
                int x1 = (int)getP(i).getX();
                int x2 = (int)getP(i+1).getX();
                int y1 = (int)getP(i).getY();
                int y2 = (int)getP(i+1).getY();

                g.setColor(getPolygonColor());
                g.setStroke(new BasicStroke((float)getLineWeight()));

                g.drawLine(x1, y1, x2, y2);
            }else{
                int x1 = (int)getP(i).getX();
                int x2 = (int)getP(0).getX();
                int y1 = (int)getP(i).getY();
                int y2 = (int)getP(0).getY();

                g.setColor(getPolygonColor());
                g.setStroke(new BasicStroke((float)getLineWeight()));
                
                g.drawLine(x1, y1, x2, y2);
            }
        }
        
        

        g.setColor(getPolygonColor());
        //g.drawString(getPolygonName(), (int)getP().getX() + getLineWeight(), (int)getP().getY());
    }
}
