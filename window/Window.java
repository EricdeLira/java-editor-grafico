package window;

//import point.Point;

public class Window {
    private int xMin, xMax, yMin, yMax;

    public Window(){
        setXmin(0);
        setXmax(0);
        setYmin(0);
        setYmax(0);
    }

    public Window(int xwMin, int xMax, int yMin, int yMax){
        super();
        setXmin(xwMin);
        setXmax(xMax);
        setYmin(yMin);
        setYmax(yMax);
    }

    /*public Point mapToViewport(Window viewport, Point point){
        Point viewportPoint = new Point();

        viewportPoint.setX(viewport.getXmin());

        return viewportPoint;
    }*/

    /* setters and getters */
    public int getXmin() {
        return xMin;
    }
    public void setXmin(int xMin) {
        this.xMin = xMin;
    }
    public int getYmin() {
        return yMin;
    }
    public void setYmin(int yMin) {
        this.yMin = yMin;
    }
    public int getXmax() {
        return xMax;
    }
    public void setXmax(int xMax) {
        this.xMax = xMax;
    }
    public int getYmax() {
        return yMax;
    }
    public void setYmax(int yMax) {
        this.yMax = yMax;
    }
}
