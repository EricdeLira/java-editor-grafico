package transformations;

import point.GraphicPoint;

public class TestTransformation {
    public static void main(String[] args){
        double m[] = {200, 200, 1};
        System.out.println("test");
        double result[] = Transformations.scale(m, new GraphicPoint(400, 200));

        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);
    }
}
