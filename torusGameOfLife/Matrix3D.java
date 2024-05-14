package torusGameOfLife;

import java.util.*;

/**
 * Matrix class
 * Helps with math for rotation by using 3 by 3 matrices
 */
public class Matrix3D {

    private double[] matrix;

    public Matrix3D(double[] arr) {
        this.matrix = arr;
    }

    public double[] getMatrix() {
        return matrix;
    }

    /**
     * Combines the current matrix with another matrix
     * @param anotherArr should be an array of length 9
     */
    public void combine(double[] anotherArr) {
        int count = 0;
        double sum;
        double[] newMatrix = new double[9];
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                sum = 0;
                for(int i = 0; i < 3; i++) {
                    sum += (matrix[(3*row) + i] * anotherArr[(3*i) + col]);
                }
                newMatrix[count++] = sum;
            }
        }
        matrix = newMatrix;
        //System.out.println(Arrays.toString(newMatrix));
    }

    /** 
     * Calculates a new Vertex position by mulitplying an old postion Vertex
     * with a 3 by 3 (rotation) matrix 
     * @param oldV old position vertex
     * @return new position vertex
     */
    public Vertex calcNewPosition(Vertex oldV) {
        double x = oldV.x * matrix[0] + oldV.y * matrix[3] + oldV.z * matrix[6];
        double y = oldV.x * matrix[1] + oldV.y * matrix[4] + oldV.z * matrix[7];
        double z = oldV.x * matrix[2] + oldV.y * matrix[5] + oldV.z * matrix[8];
        return new Vertex(x,y,z);
    }

    public static void main(String[] args) {

        //Testing code
        Matrix3D m = new Matrix3D(new double[] {
            2,3,2,
            1,1,-1,
            4,2,2
        });
        Matrix3D n = new Matrix3D(new double[] {
            2,1,0,
            0,2,5,
            -3,0,2
        });
        m.combine(n.getMatrix());
        System.out.println(Arrays.toString(m.getMatrix()));

        m.calcNewPosition(new Vertex(2,1,4)).printVertex();


    }

}