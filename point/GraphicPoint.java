package point;

import java.awt.Color;
import java.awt.Graphics2D;

public class GraphicPoint extends Point {
    Color pointColor = Color.BLACK;
    String pointName = "";
    Color nameColor = Color.BLACK;
    int diameter = 1;

    public GraphicPoint(int x, int y){
        super((double)x, (double)y);
        setColor(Color.black);
        setNameColor(Color.black);
        setName("");
    }

    GraphicPoint(int x, int y, Color color){
        super((double)x, (double)y);
        setColor(color);
        setNameColor(Color.black);
        setName("");
    }

    GraphicPoint(int x, int y, Color color, String name){
        super((double)x, (double)y);
        setColor(color);
        setNameColor(Color.black);
        setName(name);
    }

    public GraphicPoint(int x, int y, Color color, int diameter){
        this(x, y, color);
        setDiameter(diameter);
    }

    public GraphicPoint(int x, int y, Color color, String name, int diameter){
        this(x, y, color, diameter);
        setName(name);
    }

    GraphicPoint(GraphicPoint p2d, Color color){
        super(p2d);     
        setColor(color);     
        setNameColor(Color.black);     
        setName("");     
    }

    public GraphicPoint(){
        super();     
        setColor(Color.black);     
        setNameColor(Color.black);     
        setName("");     
    }

    public void drawPoint(Graphics2D g){
        // draw point
        g.setColor(getColor());
        g.fillOval((int)getX()-(getDiameter()/2), (int)getY()-(getDiameter()/2), getDiameter(), getDiameter());
       
        // draw point name
        g.setColor(getNameColor());
        g.drawString(getName(), (int)getX() + getDiameter(), (int)getY());
    }

    /* setters and getters */
    public Color getColor(){
        return pointColor;
    }
    public void setColor(Color color){
        this.pointColor = color;
    }
    public String getName(){
        return pointName;
    }
    public void setName(String name){
        this.pointName = name;
    }
    public Color getNameColor(){
        return nameColor;
    }
    public void setNameColor(Color color){
        this.nameColor = color;
    }
    public int getDiameter(){
        return diameter;
    }
    public void setDiameter(int d){
        this.diameter = d;
    }
}
