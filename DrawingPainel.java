import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;
import transformations.Transformations;
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
import save.SaveCircle;
import save.SaveLine;
import save.SavePoint;

public class DrawingPainel extends JPanel implements MouseListener, MouseMotionListener {
    JLabel msg;
    String name;
    PrimitiveTypes type;
    boolean usingViewport;
    Color currentColor;
    int lineWeight;
    int radius;
    int x1, y1, x2, y2, x3, y3;
    int numClicks = 0;
    GraphicPoint p[] = new GraphicPoint[0];

    int pointCount = 0;
    int circleCount = 0;
    int lineCount = 0;
    int triangleCount = 0;
    int rectangleCount = 0;
    int polygonCount = 0;

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
            
            name = "";
            JSONParser parser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("save.json"));
                JSONArray jsonObjectArray = (JSONArray)jsonObject.get("pontos");
                name = "ponto"+ (jsonObjectArray.size()+ 1 + pointCount);
                pointCount++;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            int esp = getLineWeight();
            int rgb[] = getRgb();
            
            save.addPoint(name, coord, rgb, esp);

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
                coord[1][1] = (double)(y2-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);

                name = "";
                JSONParser parser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("save.json"));
                    JSONArray jsonObjectArray = (JSONArray)jsonObject.get("retas");
                    name = "reta"+ (jsonObjectArray.size()+ 1 + lineCount);
                    lineCount++;
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                int esp = getLineWeight();
                int rgb[] = getRgb();
                
                save.addLine(name, coord, rgb, esp);

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

                name = "";
                JSONParser parser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("save.json"));
                    JSONArray jsonObjectArray = (JSONArray)jsonObject.get("circulos");
                    name = "circulo"+ (jsonObjectArray.size()+ 1 + circleCount);
                    circleCount++;
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                int esp = getLineWeight();
                int rgb[] = getRgb();
                //OLHAR O RAIO
               save.addCircle(name, coord, radius, rgb, esp);

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
                    coord[1][1] = (double)(y2-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);
                    coord[2][0] = (double)(x3-Constants.XW_MIN)/(Constants.XW_MAX-Constants.XW_MIN);
                    coord[2][1] = (double)(y3-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);

                    name = "";
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("save.json"));
                        JSONArray jsonObjectArray = (JSONArray)jsonObject.get("triangulos");
                        name = "triangulo"+ (jsonObjectArray.size()+ 1 + triangleCount);
                        triangleCount++;
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                    int esp = getLineWeight();
                    int rgb[] = getRgb();

                    save.addTriangle(name, coord, rgb, esp);

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
                    coord[1][1] = (double)(y2-Constants.YW_MIN)/(Constants.YW_MAX-Constants.YW_MIN);
    
                    name = "";
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("save.json"));
                        JSONArray jsonObjectArray = (JSONArray)jsonObject.get("retangulos");
                        name = "retangulo"+ (jsonObjectArray.size()+ 1 + rectangleCount);
                        rectangleCount++;
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                    int esp = getLineWeight();
                    int rgb[] = getRgb();

                    save.addRectangle(name, coord, rgb, esp);

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

                    name = "";
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("save.json"));
                        JSONArray jsonObjectArray = (JSONArray)jsonObject.get("poligonos");
                        name = "poligono"+ (jsonObjectArray.size()+ 1 + polygonCount);
                        polygonCount++;
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    
                    int esp = getLineWeight();
                    int rgb[] = getRgb();

                    save.addPolygon(name, coord, rgb, esp);
                    
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
            PointDrawings.drawPointOnWindow(g, x1, y1, name, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PointDrawings.drawPointOnViewport(g, window, viewport, x1, y1, "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.LINE){
            LineDrawings.drawLineOnWindow(g, x1, y1, x2, y2, name, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                LineDrawings.drawLineOnViewport(g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.CIRCLE){
            CircleDrawings.drawCircleOnWindow(g, x1, y1, getRadius(), name, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                CircleDrawings.drawCircleOnViewport(g, window, viewport, x1, y1, getRadius(), "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.TRIANGLE){
            TriangleDrawings.drawTriangleOnWindow(g, x1, y1, x2, y2, x3, y3, name, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                TriangleDrawings.drawTriangleOnViewport(g, window, viewport, x1, y1, x2, y2, x3, y3, "", getLineWeight(), getCurrentColor());
            }
        }else if(type == PrimitiveTypes.RECTANGLE){
            RectangleDrawings.drawRectangleOnWindow(g, x1, y1, x2, y2, name, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                RectangleDrawings.drawRectangleOnViewport(g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        } else if(type == PrimitiveTypes.POLYGON){
            PolygonDrawings.drawPolygonOnWindow(g, p, name, getLineWeight(), getCurrentColor());
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

            pointCount = 0;
            circleCount = 0;
            lineCount = 0;
            triangleCount = 0;
            rectangleCount = 0;
            polygonCount = 0;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clearMemory(){
        save.clearMemory();
    }

    public void readFile(String file, String name, int esp, int[] color, String op){
        JSONObject jsonObject;
        JSONArray jsonObjectArray;
        JSONParser parser = new JSONParser();
        Gson gson = new Gson();

        ArrayList<SavePoint> pontos = new ArrayList<SavePoint>();
        ArrayList<SaveLine> retas = new ArrayList<SaveLine>();
        ArrayList<SaveCircle> circulos = new ArrayList<SaveCircle>();
        ArrayList<SaveLine> triangulos = new ArrayList<SaveLine>();
        ArrayList<SaveLine> retangulos = new ArrayList<SaveLine>();
        ArrayList<SaveLine> poligonos = new ArrayList<SaveLine>();

        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(file)); 
            
            jsonObjectArray = (JSONArray)jsonObject.get("pontos");
            for(int i = 0; i < jsonObjectArray.size(); i++){
                SavePoint p = gson.fromJson(jsonObjectArray.get(i).toString(), SavePoint.class);
                pontos.add(p);
            }
            jsonObjectArray = (JSONArray)jsonObject.get("retas");
            for(int i = 0; i < jsonObjectArray.size(); i++){
                SaveLine p = gson.fromJson(jsonObjectArray.get(i).toString(), SaveLine.class);
                retas.add(p);
            }
            jsonObjectArray = (JSONArray)jsonObject.get("circulos");
            for(int i = 0; i < jsonObjectArray.size(); i++){
                SaveCircle p = gson.fromJson(jsonObjectArray.get(i).toString(), SaveCircle.class);
                circulos.add(p);
            }
            jsonObjectArray = (JSONArray)jsonObject.get("triangulos");
            for(int i = 0; i < jsonObjectArray.size(); i++){
                SaveLine p = gson.fromJson(jsonObjectArray.get(i).toString(), SaveLine.class);
                triangulos.add(p);
            }
            jsonObjectArray = (JSONArray)jsonObject.get("retangulos");
            for(int i = 0; i < jsonObjectArray.size(); i++){
                SaveLine p = gson.fromJson(jsonObjectArray.get(i).toString(), SaveLine.class);
                retangulos.add(p);
            }
            jsonObjectArray = (JSONArray)jsonObject.get("poligonos");
            for(int i = 0; i < jsonObjectArray.size(); i++){
                SaveLine p = gson.fromJson(jsonObjectArray.get(i).toString(), SaveLine.class);
                poligonos.add(p);
            }
            if(op.equals("none")){
                drawReadFile(pontos, retas, circulos, triangulos, retangulos, poligonos, name, esp, color);
            }else{
                drawReadFile(pontos, retas, circulos, triangulos, retangulos, poligonos, name, op, x1, y1);
            }
            
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

    public void drawReadFile(ArrayList<SavePoint> pontos, ArrayList<SaveLine> retas,ArrayList<SaveCircle> circulos,ArrayList<SaveLine> triangulos, ArrayList<SaveLine> retangulos,ArrayList<SaveLine> poligonos, String DrawingName, int DrawingEsp, int[] DrawingColor){
        Graphics g = getGraphics();
        Window window = new Window(Constants.XW_MIN, Constants.YW_MIN, Constants.XW_MAX, Constants.YW_MAX);
        Window viewport = new Window(Constants.XV_MIN, Constants.YV_MIN, Constants.XV_MAX, Constants.YV_MAX);

        for(SavePoint p: pontos){
            String nome = p.getNome();
            double[] coord = p.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                cor = DrawingColor;
                esp = DrawingEsp;
            }else{
                cor = p.getCor();
                esp = p.getEsp();
            }

            save.addPoint(nome, coord, cor, esp);

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);
            
            double x1_ = coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            this.x1 = (int)x1_;
            this.y1 = (int)y1_;

            setCurrentColor(color);
            setLineWeight(esp);
            
            PointDrawings.drawPointOnWindow((Graphics2D)g, x1, y1, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PointDrawings.drawPointOnViewport((Graphics2D)g, window, viewport, x1, y1, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine l: retas){
            String nome = l.getNome();
            double[][] coord = l.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                cor = DrawingColor;
                esp = DrawingEsp;
            }else{
                cor = l.getCor();
                esp = l.getEsp();
            }

            save.addLine(nome, coord, cor, esp);

            double x1_ = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x2_ = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y2_ = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            this.x1 = (int)x1_;
            this.y1 = (int)y1_;
            this.x2 = (int)x2_;
            this.y2 = (int)y2_;

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            LineDrawings.drawLineOnWindow((Graphics2D)g, x1, y1, x2, y2, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                LineDrawings.drawLineOnViewport((Graphics2D)g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveCircle c: circulos){
            String nome = c.getNome();
            double[] coord = c.getCoord();
            double raio = c.getRaio();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                cor = DrawingColor;
                esp = DrawingEsp;
            }else{
                cor = c.getCor();
                esp = c.getEsp();
            }

            double x1_ = coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;

            this.x1 = (int)x1_;
            this.y1 = (int)y1_;

            setRadius((int)raio);

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            CircleDrawings.drawCircleOnWindow((Graphics2D)g, x1, y1, getRadius(), nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                CircleDrawings.drawCircleOnViewport((Graphics2D)g, window, viewport, x1, y1, getRadius(), "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine t: triangulos){
            String nome = t.getNome();
            double[][] coord = t.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                cor = DrawingColor;
                esp = DrawingEsp;
            }else{
                cor = t.getCor();
                esp = t.getEsp();
            }

            save.addTriangle(nome, coord, cor, esp);

            double x1_ = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x2_ = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y2_ = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x3_ = coord[2][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y3_ = coord[2][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            this.x1 = (int)x1_;
            this.y1 = (int)y1_;
            this.x2 = (int)x2_;
            this.y2 = (int)y2_;
            this.x3 = (int)x3_;
            this.y3 = (int)y3_;

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            TriangleDrawings.drawTriangleOnWindow((Graphics2D)g, x1, y1, x2, y2, x3, y3, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                TriangleDrawings.drawTriangleOnViewport((Graphics2D)g, window, viewport, x1, y1, x2, y2, x3, y3, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine r: retangulos){
            String nome = r.getNome();
            double[][] coord = r.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                cor = DrawingColor;
                esp = DrawingEsp;
            }else{
                cor = r.getCor();
                esp = r.getEsp();
            }

            save.addRectangle(nome, coord, cor, esp);

            double x1_ = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x2_ = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y2_ = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            this.x1 = (int)x1_;
            this.y1 = (int)y1_;
            this.x2 = (int)x2_;
            this.y2 = (int)y2_;

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            RectangleDrawings.drawRectangleOnWindow((Graphics2D)g, x1, y1, x2, y2, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                RectangleDrawings.drawRectangleOnViewport((Graphics2D)g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine p: poligonos){
            String nome = p.getNome();
            double[][] coord = p.getCoord();
            int[] cor;
            int esp;

            if(DrawingName.equals(nome)){
                cor = DrawingColor;
                esp = DrawingEsp;
            }else{
                cor = p.getCor();
                esp = p.getEsp();
            }

            save.addPolygon(nome, coord, cor, esp);

            GraphicPoint pt[] = new GraphicPoint[coord.length];
            for(int i = 0; i < coord.length; i++){
                double x1_ = coord[i][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                double y1_ = coord[i][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                pt[i] = new GraphicPoint((int)x1_, (int)y1_);
            }
            
            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            PolygonDrawings.drawPolygonOnWindow((Graphics2D)g, pt, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PolygonDrawings.drawPolygonOnViewport((Graphics2D)g, window, viewport, pt, "", getLineWeight(), getCurrentColor());
            }

        }
    }

    
    public void drawReadFile(ArrayList<SavePoint> pontos, ArrayList<SaveLine> retas,ArrayList<SaveCircle> circulos,ArrayList<SaveLine> triangulos, ArrayList<SaveLine> retangulos,ArrayList<SaveLine> poligonos, String DrawingName, String op, int x, int y){
        Graphics g = getGraphics();
        Window window = new Window(Constants.XW_MIN, Constants.YW_MIN, Constants.XW_MAX, Constants.YW_MAX);
        Window viewport = new Window(Constants.XV_MIN, Constants.YV_MIN, Constants.XV_MAX, Constants.YV_MAX);

        for(SavePoint p: pontos){
            String nome = p.getNome();
            double[] coord = p.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                if(op.equals("translate")){
                    double aux[] = new double[3];
                    double vectorX = x;
                    double vectorY = y;
                    aux[0] = coord[0];
                    aux[1] = coord[1];
                    aux[2] = 1;
                    
                    coord[0] = (Transformations.translate(aux, vectorX, vectorY)[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1] = (Transformations.translate(aux, vectorX, vectorY)[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("scaleUp")){
                    double aux[] = new double[3];
                    aux[0] = coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("scaleDown")){
                    double aux[] = new double[3];
                    aux[0] = coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("rotate")){
                    double aux[] = new double[3];
                    aux[0] =  coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }
            }
                
            cor = p.getCor();
            esp = p.getEsp();
            
            save.addPoint(nome, coord, cor, esp);
            
            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            double x1_ = coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            this.x1 = (int)x1_;
            this.y1 = (int)y1_;
        
            setCurrentColor(color);
            setLineWeight(esp);
            
            PointDrawings.drawPointOnWindow((Graphics2D)g, (int)x1_, (int)y1_, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PointDrawings.drawPointOnViewport((Graphics2D)g, window, viewport, (int)x1_, (int)y1_, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine l: retas){
            String nome = l.getNome();
            double[][] coord = l.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                if(op.equals("translate")){
                    double aux[] = new double[3];
                    double vectorX = x;
                    double vectorY = y;
                    
                    aux[0] = coord[0][0];
                    aux[1] = coord[0][1];
                    aux[2] = 1;
                    coord[0][0] = (Transformations.translate(aux, vectorX, vectorY)[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.translate(aux, vectorX, vectorY)[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);

                    vectorX -= ((aux[0]-coord[1][0])*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN); 
                    vectorY -= ((aux[1]-coord[1][1])*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN); 

                    aux[0] = coord[1][0];
                    aux[1] = coord[1][1];
                    aux[2] = 1;
                    coord[1][0] = (Transformations.translate(aux, vectorX, vectorY)[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.translate(aux, vectorX, vectorY)[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("scaleUp")){
                    double aux[] = new double[3];
                    aux[0] = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("scaleDown")){
                    double aux[] = new double[3];
                    aux[0] = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("rotate")){
                    double aux[] = new double[3];
                    aux[0] =  coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] =  coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }
            }
            
            cor = l.getCor();
            esp = l.getEsp();


            save.addLine(nome, coord, cor, esp);

            double x1_ = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x2_ = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y2_ = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            LineDrawings.drawLineOnWindow((Graphics2D)g, (int)x1_, (int)y1_, (int)x2_, (int)y2_, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                LineDrawings.drawLineOnViewport((Graphics2D)g, window, viewport, (int)x1_, (int)y1_, (int)x2_, (int)y2_, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveCircle c: circulos){
            String nome = c.getNome();
            double[] coord = c.getCoord();
            double raio = c.getRaio();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                
            }
                cor = c.getCor();
                esp = c.getEsp();
            

            double x1_ = coord[0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;

            this.x1 = (int)x1_;
            this.y1 = (int)y1_;

            setRadius((int)raio);

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            CircleDrawings.drawCircleOnWindow((Graphics2D)g, x1, y1, getRadius(), nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                CircleDrawings.drawCircleOnViewport((Graphics2D)g, window, viewport, x1, y1, getRadius(), "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine t: triangulos){
            String nome = t.getNome();
            double[][] coord = t.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                if(op.equals("translate")){
                    /*
                    double aux[] = new double[3];
                    aux[0] = coord[0][0];
                    aux[1] = coord[0][1];
                    aux[2] = 1;
                    coord[0][0] = (Transformations.translate(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.translate(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0];
                    aux[1] = coord[1][1];
                    aux[2] = 1;
                    coord[1][0] = (Transformations.translate(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.translate(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[2][0];
                    aux[1] = coord[2][1];
                    aux[2] = 1;
                    coord[2][0] = (Transformations.translate(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[2][1] = (Transformations.translate(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    */
                }else if(op.equals("scaleUp")){
                    double aux[] = new double[3];
                    aux[0] = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[2][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[2][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[2][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[2][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("scaleDown")){
                    double aux[] = new double[3];
                    aux[0] = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[2][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[2][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[2][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[2][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("rotate")){
                    double aux[] = new double[3];
                    aux[0] =  coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] =  coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] =  coord[2][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[2][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[2][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[2][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }
            }
                cor = t.getCor();
                esp = t.getEsp();
            

            save.addTriangle(nome, coord, cor, esp);

            double x1_ = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x2_ = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y2_ = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x3_ = coord[2][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y3_ = coord[2][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            TriangleDrawings.drawTriangleOnWindow((Graphics2D)g, (int)x1_, (int)y1_, (int)x2_, (int)y2_, (int)x3_, (int)y3_, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                TriangleDrawings.drawTriangleOnViewport((Graphics2D)g, window, viewport, (int)x1_, (int)y1_, (int)x2_, (int)y2_, (int)x3_, (int)y3_, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine r: retangulos){
            String nome = r.getNome();
            double[][] coord = r.getCoord();
            int[] cor;
            int esp;
            if(DrawingName.equals(nome)){
                if(op.equals("translate")){
                    /*
                    double aux[] = new double[3];
                    aux[0] = coord[0][0];
                    aux[1] = coord[0][1];
                    aux[2] = 1;
                    coord[0][0] = (Transformations.translate(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.translate(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0];
                    aux[1] = coord[1][1];
                    aux[2] = 1;
                    coord[1][0] = (Transformations.translate(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.translate(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    */
                }else if(op.equals("scaleUp")){
                    double aux[] = new double[3];
                    aux[0] = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.scaleUp(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("scaleDown")){
                    double aux[] = new double[3];
                    aux[0] = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.scaleDown(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }else if(op.equals("rotate")){
                    double aux[] = new double[3];
                    aux[0] =  coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[0][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[0][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                    aux[0] =  coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                    aux[1] =  coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                    aux[2] = 1;
                    coord[1][0] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[0] - Constants.XW_MIN)/(Constants.XW_MAX - Constants.XW_MIN);
                    coord[1][1] = (Transformations.rotateToPoint(aux, new GraphicPoint(x, y))[1]- Constants.YW_MIN)/(Constants.YW_MAX - Constants.YW_MIN);
                }
            }
                cor = r.getCor();
                esp = r.getEsp();
            
            save.addRectangle(nome, coord, cor, esp);

            double x1_ = coord[0][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y1_ = coord[0][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            double x2_ = coord[1][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
            double y2_ = coord[1][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
            this.x1 = (int)x1_;
            this.y1 = (int)y1_;
            this.x2 = (int)x2_;
            this.y2 = (int)y2_;

            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            RectangleDrawings.drawRectangleOnWindow((Graphics2D)g, x1, y1, x2, y2, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                RectangleDrawings.drawRectangleOnViewport((Graphics2D)g, window, viewport, x1, y1, x2, y2, "", getLineWeight(), getCurrentColor());
            }
        }

        for(SaveLine p: poligonos){
            String nome = p.getNome();
            double[][] coord = p.getCoord();
            int[] cor;
            int esp;

            if(DrawingName.equals(nome)){
                
            }

            cor = p.getCor();
            esp = p.getEsp();

            save.addPolygon(nome, coord, cor, esp);

            GraphicPoint pt[] = new GraphicPoint[coord.length];
            for(int i = 0; i < coord.length; i++){
                double x1_ = coord[i][0]*(Constants.XW_MAX - Constants.XW_MIN) + Constants.XW_MIN;
                double y1_ = coord[i][1]*(Constants.YW_MAX - Constants.YW_MIN) + Constants.YW_MIN;
                pt[i] = new GraphicPoint((int)x1_, (int)y1_);
            }
            
            Color color = new ColorUIResource(cor[0], cor[1], cor[2]);

            setCurrentColor(color);
            setLineWeight(esp);

            PolygonDrawings.drawPolygonOnWindow((Graphics2D)g, pt, nome, getLineWeight(), getCurrentColor());
            if(isUsingViewport()){
                PolygonDrawings.drawPolygonOnViewport((Graphics2D)g, window, viewport, pt, "", getLineWeight(), getCurrentColor());
            }

        }
    }
}