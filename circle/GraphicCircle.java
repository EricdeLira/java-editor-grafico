package circle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GraphicCircle extends Circle{
    Color circleColor = Color.BLACK;
    String circleName = "";
    Color circleNameColor = Color.BLACK;
    int lineWeight = 1; 

    public GraphicCircle(double x, double y, double radius){
        super(x, y, radius);
    }
    public GraphicCircle(int xCenter, int yCenter, int radius, Color color, String name, int lineWeight){
        super((double) xCenter, (double) yCenter, (double) radius);
        setCircleColor(color);
        setCircleName(name);
        setLineWeight(lineWeight);
    }

    /* Setters and getters */
    public void setCircleColor(Color color){
        this.circleColor = color;
    }
    public Color getCircleColor(){
        return circleColor;
    }
    public void setCircleName(String name){
        this.circleName = name;
    }
    public String getCircleName(){
        return circleName;
    }
    public void setCircleNameColor(Color color){
        this.circleNameColor = color;
    }
    public Color getCircleNameColor(){
        return circleNameColor;
    }
    public void setLineWeight(int lineWeight){
        this.lineWeight = lineWeight;
    }
    public int getLineWeight(){
        return lineWeight;
    }
    /*-------------------------------------*/

    public void drawCircle(Graphics2D g){
        int xCenter = (int)getCenter().getX();
        int yCenter = (int)getCenter().getY();
        int radius = (int)getRadius();

        g.setColor(getCircleColor());

        g.setStroke(new BasicStroke((float)getLineWeight()));

        g.drawOval(xCenter - radius, yCenter - radius, 2*radius, 2*radius);

        g.setColor(getCircleNameColor());
        g.drawString(getCircleName(), xCenter, yCenter);
    }
}
