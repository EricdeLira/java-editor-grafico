import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import javax.swing.JLabel;
import javax.swing.JPanel;

import point.GraphicPoint;

import point.PointDrawings;
import line.LineDrawings;
import circle.CircleDrawings;
import triangle.TriangleDrawings;
import rectangle.RectangleDrawings;
import polygon.PolygonDrawings;
import window.Window;
import constants.Constants;
import constants.PrimitiveTypes;
import save.SaveData;

public class DrawingPainel extends JPanel implements MouseListener, MouseMotionListener {

    JLabel msg;
    PrimitiveTypes type;
    boolean usingViewport;
    Color currentColor;
    int lineWeight;
    int radius;
    int x1, y1, x2, y2, x3, y3;
    int numClicks = 0;
    GraphicPoint p[] = new GraphicPoint[0];

    FileWriter writeFile = null;
    Gson gson = new Gson();

    SaveData save = new SaveData();

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

            double coord[] = new double[2];
            coord[0] = (double)(x1-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
            coord[1] = (double)(y1-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);
            
            String nome = getMsg().getText();
            int esp = getLineWeight() + 1;
            int rgb[] = getRgb();
            
            save.addPoint(nome, coord, rgb, esp);

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

                double coord[][] = new double[2][2];
                coord[0][0] = (double)(x1-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                coord[0][1] = (double)(y1-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);
                coord[1][0] = (double)(x2-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                coord[1][1] = (double)(y2-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);

                String nome = getMsg().getText();
                int esp = getLineWeight() + 1;
                int rgb[] = getRgb();
                
                save.addLine(nome, coord, rgb, esp);

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

                double coord[] = new double[2];
                coord[0] = (double)(x1-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                coord[1] = (double)(y1-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);

                String nome = getMsg().getText();
                int esp = getLineWeight() + 1;
                int rgb[] = getRgb();
                //OLHAR O RAIO
               save.addCircle(nome, coord, radius, rgb, esp);

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

                    double coord[][] = new double[3][2];
                    coord[0][0] = (double)(x1-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[0][1] = (double)(y1-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);
                    coord[1][0] = (double)(x2-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[1][1] = (double)(y2-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[2][0] = (double)(x3-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[2][1] = (double)(y3-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);

                    String nome = getMsg().getText();
                    int esp = getLineWeight() + 1;
                    int rgb[] = getRgb();

                    save.addTriangle(nome, coord, rgb, esp);

                    paint(g);
                }else{
                    this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getType() + " - INVALID POINTS TO DRAW A TRIANGLE!!!");
                }
            }
        } else if( type == PrimitiveTypes.RECTANGLE){
            if(numClicks == 0){
                x1 = e.getX();
                y1 = e.getY();
                numClicks++;
            }else{
                x2 = e.getX();
                y2 = e.getY();
                numClicks = 0;
                if(!(x1 == x2 || y1 == y2)){

                    double coord[][] = new double[2][2];
                    coord[0][0] = (double)(x1-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[0][1] = (double)(y1-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);
                    coord[1][0] = (double)(x2-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[1][1] = (double)(y2-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
    
                    String nome = getMsg().getText();
                    int esp = getLineWeight() + 1;
                    int rgb[] = getRgb();

                    save.addRectangle(nome, coord, rgb, esp);

                    paint(g);
                }else{
                    this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getType() + " - INVALID POINTS TO DRAW A RECTANGLE!!!");
                } 
            }
        } else if(type == PrimitiveTypes.POLYGON){
            if (e.getClickCount() == 2 && !e.isConsumed()){
                e.consume();
                if(numClicks > 2){
                    
                    double coord[][] = new double[p.length][2];

                    for(int i = 0; i < p.length; i++){
                        coord[i][0] = (p[i].getX() - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                        coord[i][1] = (p[i].getY() - Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    }

                    String nome = getMsg().getText();
                    int esp = getLineWeight() + 1;
                    int rgb[] = getRgb();

                    save.addPolygon(nome, coord, rgb, esp);

                    paint(g);
                }else{
                    this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getType() + " - INVALID POINTS TO DRAW A POLYGON!!!");
                }
                p = new GraphicPoint[0];
                numClicks = 0;
           }else{
                GraphicPoint pAux[] = new GraphicPoint[p.length + 1];
                for (int i = 0; i < p.length; i++){
                    pAux[i] = p[i];
                }
                pAux[pAux.length-1] = new GraphicPoint(e.getX(), e.getY());
                p = pAux;
                numClicks++;
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
        }else if(type == PrimitiveTypes.RECTANGLE){
            RectangleDrawings.drawRectangleOnWindow(g, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                RectangleDrawings.drawRectangleOnViewport(g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        } else if(type == PrimitiveTypes.POLYGON){
            PolygonDrawings.drawPolygonOnWindow(g, p, "", getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PolygonDrawings.drawPolygonOnViewport(g, window, viewport, p, "", getLineWeight(), getCurrentColor());
            }
        }
    }

    public int[] getRgb(){
        Color cor = getCurrentColor();
        int rgb[] = new int[3];
        rgb[0] = cor.getRed();
        rgb[1] = cor.getGreen();
        rgb[2] = cor.getBlue();
        return rgb;
    }

    public void saveFile(){
        String json = gson.toJson(save);
        try {
            writeFile = new FileWriter("save.json");
            writeFile.write(json);
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
