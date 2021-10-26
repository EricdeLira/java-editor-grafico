package transformations;

import point.GraphicPoint;

public class Transformations {
    public static double[] translate(double m[], double vectorX, double vectorY){
        double result[];
        double mt[][] = new double[3][3];

        mt[0][0] = 1;
        mt[0][1] = 0;
        mt[0][2] = vectorX;
        mt[1][0] = 0;
        mt[1][1] = 1;
        mt[1][2] = vectorY;
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);

        return result;
    }

    public static double[] rotate(double m[]){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 0;
        mt[0][1] = -1;
        mt[0][2] = 0;
        mt[1][0] = 1;
        mt[1][1] = 0;
        mt[1][2] = 0;
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        
        result = MatrixMultiplication.multiplication(m, mt);

        return result;
    }

    public static double[] rotateToPoint(double m[], GraphicPoint p){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 0;
        mt[0][1] = -1;
        mt[0][2] = p.getX()+p.getY();
        mt[1][0] = 1;
        mt[1][1] = 0;
        mt[1][2] = p.getY()-p.getX();
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);
    
        return result;
    }


    public static double[] scale(double m[], GraphicPoint p){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 2;
        mt[0][1] = 0;
        mt[0][2] = 0;
        mt[1][0] = 0;
        mt[1][1] = 2;
        mt[1][2] = 0;
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);

        return result;
    }

    public static double[] scaleUp(double m[], GraphicPoint p){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 2;
        mt[0][1] = 0;
        mt[0][2] = -p.getX();
        mt[1][0] = 0;
        mt[1][1] = 2;
        mt[1][2] = -p.getY();
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);

        return result;
    }

    public static double[] scaleUp(double m[], GraphicPoint p, double radius){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 2;
        mt[0][1] = 0;
        mt[0][2] = -p.getX();
        mt[1][0] = 0;
        mt[1][1] = 2;
        mt[1][2] = -p.getY();
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);

        result[2] = radius*2.0;

        return result;
    }

    public static double[] scaleDown(double m[], GraphicPoint p){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 0.5;
        mt[0][1] = 0;
        mt[0][2] = p.getX()/2;
        mt[1][0] = 0;
        mt[1][1] = 0.5;
        mt[1][2] = p.getY()/2;
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);

        return result;
    }

    public static double[] scaleDown(double m[], GraphicPoint p, double radius){
        double result[];
        double mt[][] = new double[3][3];
        
        mt[0][0] = 0.5;
        mt[0][1] = 0;
        mt[0][2] = p.getX()/2;
        mt[1][0] = 0;
        mt[1][1] = 0.5;
        mt[1][2] = p.getY()/2;
        mt[2][0] = 0;
        mt[2][1] = 0;
        mt[2][2] = 1;

        result = MatrixMultiplication.multiplication(m, mt);

        result[2] = radius/2.0;

        return result;
    }
}
