package transformations;

public class MatrixMultiplication{
    public static double[] multiplication(double m[], double mt[][]){
        double result[] = {0, 0, 0};

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                result[i] += (mt[i][j]*m[j]);
            }
        }
        return result;
    }
}