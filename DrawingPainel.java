import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import point.PointDrawings;
import line.LineDrawings;
import circle.CircleDrawings;
import triangle.TriangleDrawings;
import window.Window;
import constants.Constants;
import constants.PrimitiveTypes;


public class DrawingPainel extends JPanel implements MouseListener, MouseMotionListener {

    JLabel msg;
    PrimitiveTypes type;
    boolean usingViewport;
    Color currentColor;
    int lineWeight;
    int radius;
    int x1, y1, x2, y2, x3, y3;
    int numClicks = 0;

    public DrawingPainel(JLabel msg, PrimitiveTypes type, Color currentColor, int lineWeight){
        setType(type);
        setMsg(msg);
        setCurrentColor(currentColor);
        setLineWeight(lineWeight);

        this.addMouseListener(this); 
        this.addMouseMotionListener(this);
    }

    /* setters and getters */
    public void setType(PrimitiveTypes type){
        this.type = type;
    }
    public PrimitiveTypes getType(){
        return this.type;
    }

    public void setLineWeight(int lineWeight){
        this.lineWeight = lineWeight;
    }
    public int getLineWeight(){
        return this.lineWeight;
    }

    public void setCurrentColor(Color color){
        this.currentColor = color;
    }
    public Color getCurrentColor(){
        return this.currentColor;
    }

    public void setMsg(JLabel msg){
        this.msg = msg;
    }
    public JLabel getMsg(){
        return this.msg;
    }

    public int getRadius(){
        return radius;
    }
    public void setRadius(int radius){
        this.radius = radius;
    }

    public void setUsingViewport(boolean usingViewport){
        this.usingViewport = usingViewport;
    }
    public boolean isUsingViewport(){
        return usingViewport;
    }

    /* Mouse Events */
    public void mousePressed(MouseEvent e){
        Graphics g = getGraphics();

        if(type == PrimitiveTypes.POINT){
            x1 = e.getX();
            y1 = e.getY();
            paint(g);
        } else if(type == PrimitiveTypes.LINE){
            if(numClicks == 0){
                x1 = e.getX();
                y1 = e.getY();
                numClicks++;
            }else{
                x2 = e.getX();
                y2 = e.getY();
                numClicks = 0;
                paint(g);
            }
        } else if(type == PrimitiveTypes.CIRCLE){
            if(numClicks == 0){
                x1 = e.getX();
                y1 = e.getY();
                numClicks++;
            }else{
                x2 = (int)e.getX();
                y2 = (int)e.getY();
                numClicks = 0;

                radius = (int)Math.sqrt(Math.pow((y2-y1), 2) + Math.pow((x2-x1), 2));
                setRadius(radius);
                paint(g);
            }
        } else if(type == PrimitiveTypes.TRIANGLE){
            if(numClicks == 0){
                x1 = e.getX();
                y1 = e.getY();
                numClicks++;
            }else if(numClicks == 1){
                x2 = e.getX();
                y2 = e.getY();
                numClicks++;
            }else if(numClicks == 2){
                x3 = e.getX();
                y3 = e.getY();
                numClicks = 0;
                if(!((x1 == x2 && x2 == x3) || (y1 == y2 && y2 == y3))){
                    paint(g);
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e){}           
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}

    public void mouseMoved(MouseEvent e){
        this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getType());
    }
    /******************************************/

    public void paintComponent(Graphics g) {   
        drawPrimitives((Graphics2D)g);
    }

    public void drawPrimitives(Graphics2D g) { 
        Window window = new Window(Constants.XW_MIN, Constants.YW_MIN, Constants.XW_MAX, Constants.YW_MAX);

        Window viewport = new Window(Constants.XV_MIN, Constants.YV_MIN, Constants.XV_MAX, Constants.YV_MAX);

        if(type == PrimitiveTypes.POINT){
            PointDrawings.drawPointOnWindow(g, x1, y1, "", getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PointDrawings.drawPointOnViewport(g, window, viewport, x1, y1, "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.LINE){
            LineDrawings.drawLineOnWindow(g, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                LineDrawings.drawLineOnViewport(g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.CIRCLE){
            CircleDrawings.drawCircleOnWindow(g, x1, y1, getRadius(), "", getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                CircleDrawings.drawCircleOnViewport(g, window, viewport, x1, y1, getRadius(), "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.TRIANGLE){
            TriangleDrawings.drawTriangleOnWindow(g, x1, y1, x2, y2, x3, y3, "", getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                TriangleDrawings.drawTriangleOnViewport(g, window, viewport, x1, y1, x2, y2, x3, y3, "", getLineWeight(), getCurrentColor());
            }
        }
    }
}
