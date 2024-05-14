package torusGameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Class for Torus
 */
public class Torus {

    //Sides = 76, csSides = 30
    public int sides = 76; //The number of vertices for the entire circle (large, the donut part looking from the top)
    public int csSides = 30; //The number of vertices for the cross section circle (small, would need to cut donut)
    public double radius = 3 * 45.0; //Should be a scalar times some number to keep track of ratios between radis & csRadius
    public double csRadius = 1 * 45.0;

    public Square[][] squares; //Right now a 2D array, but probably will change to a graph when Graph class is worked on.
    public Vertex[][] vertices;
    public ArrayList<Square> drawableSquares; //Arraylist of squares with a normal vector that faces the screen. 
    public ArrayList<Square> arrayOfSquares = new ArrayList<Square>();


    /**
     * Calculates the vertices based on sides, csSides, radius, and csRadius
     * Then, uses those vertices to set up (as of now) a double array with square objects
     */
    public void initializeTorus() {

        // Calculate the vertices' x, y, and z location
        double theta, phi, x, y, z, helper;
        vertices = new Vertex[csSides][sides];
        for(int i = 0; i < csSides; i ++) { //Iterate along the cross section
            theta = 2 * Math.PI * i / csSides; //Radians of angle of cross section
            for(int j = 0; j < sides; j++) { //Iterate around the torus
                phi = 2 * Math.PI * j / sides; //Radians of angle of "top down" of torus.
                helper = radius + (csRadius * Math.cos(theta)); //Cross sectional radius from center, based on theta
                z = csRadius * Math.sin(theta);
                x = helper * Math.cos(phi);
                y = helper * Math.sin(phi);
                vertices[i][j] = new Vertex(x,y,z);
            }
        }


        // Calculate the squares
        /**
         * Need (i + 1) % sides to loop back to zero if i + 1 = csSides, same for j/sides
         * v1, v2, v3, and v4 should go in square formation to help with drawing the square
         */ 
        Vertex v1, v2, v3, v4;
        int nextI, nextJ, count = 0;
        squares = new Square[csSides][sides];
        for (int i = 0; i < csSides; i++) {
            for (int j = 0; j < sides; j++) {
                nextI = (i + 1) % csSides;
                nextJ = (j + 1) % sides;
                v1 = vertices[i][j];
                v2 = vertices[i][nextJ];
                v3 = vertices[nextI][nextJ];
                v4 = vertices[nextI][j];
                Square tempS = new Square(v1, v2, v3, v4, colorHelper(i,j), count);
                squares[i][j] = tempS;
                arrayOfSquares.add(tempS);
                count++;
            }
        }

    }

    /**
     * Right now just checkerboard pattern, but could change later
     * so that black = dead squares and white = alive squares
     * 
     * FUTURE IDEA: change it so the user can control this!!!
     * 
     */
    public Color colorHelper(int i, int j) {
        /** ALT
        if(i == 2) {
            return Color.WHITE;
        }
        return Color.BLACK;
        */
        return Color.BLACK; //USING THIS FOR NOW.
        /**
        if ((i + j) % 2 == 0) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
        */
    }

    /**
     * Transposes all the squares in a given direction
     */
    public void transposeSquares(double dx, double dy, double dz) {
        for (Square[] row : squares) {
            for (Square square : row) {
                square.transpose(dx,dy,dz);
            }
        }
    }

    public void rotateSquares(Matrix3D tMatrix) {
        for (Square[] row : squares) {
            for (Square square : row) {
                for(int i = 0; i < 4; i++) {
                    square.sqVertices[i] = tMatrix.calcNewPosition(square.sqVertices[i]);
                }
            }
        }
    }

    /**
     * Draw every square in the torus to draw the entire torus
     */
    public void drawTorus(Graphics2D g2) {
        drawableSquares = new ArrayList<Square>();

        for (Square[] row : squares) {
            for (Square square : row) {
                if(square.crossProductPostitive()) {
                    drawableSquares.add(square);
                }
            }
        }

        //System.out.println("test0");
        quickSort(new SquareComparator(), drawableSquares);
        //System.out.println("test1");

        for(Square square : drawableSquares) {
            square.drawSquare(g2);
        }

    }

    /**
     * My quicksort code from lab 5
     */
    public void quickSortHelper(Comparator<Square> c, int lowIndex, int highIndex, int pivotIndex, ArrayList<Square> arrList) {
        int i = lowIndex, j = highIndex;
        while(true) {
            if(i >= j) {
                if(lowIndex < highIndex) {
                    quickSortHelper(c,lowIndex,pivotIndex - 1, (int) (Math.random() * (pivotIndex - lowIndex) + lowIndex), arrList);
                    quickSortHelper(c,pivotIndex + 1,highIndex, (int) (Math.random() * (highIndex - pivotIndex) + pivotIndex), arrList);
                }
                break;
            }
            else if(c.compare(arrList.get(j),arrList.get(pivotIndex)) > 0) {
                j--;
            }
            else if(c.compare(arrList.get(i),arrList.get(pivotIndex)) < 0) {
                i++;
            }
            else {
                Square temp = arrList.get(i);
                arrList.set(i,arrList.get(j));
                arrList.set(j,temp);
                if(i == pivotIndex) {
                    pivotIndex = j;
                    i++;
                }
                else if(j == pivotIndex) {
                    pivotIndex = i;
                    j--;
                }
                else if(c.compare(arrList.get(i),arrList.get(j)) == 0) {
                    i++;
                    j--;
                }
            }
        }
    }

    public void quickSort(Comparator<Square> c, ArrayList<Square> arrList) {
        int randomPivot = (int) (Math.random() * arrList.size());
        quickSortHelper(c, 0,arrList.size() - 1,randomPivot, arrList);

    }

    /**
     * Currently not in use, but don't delete yet.
     * 
    public ArrayList<Square> convertToArr() {
        ArrayList<Square> array = new ArrayList<Square>();
        for (int i = 0; i<csSides; i++) {
            for (int j = 0; j < sides; j++) {
                array.add(squares[i][j]);
            }
        }
        quickSort(new SquareComparator2(), array);
        //System.out.println(array.size());
        return array;
    }
    */

    class SquareComparator implements Comparator<Square>{
        public int compare(Square s1, Square s2){
            if (s1.sqVertices[0].z < s2.sqVertices[0].z) return 1; 
            if (s1.sqVertices[0].z > s2.sqVertices[0].z) return -1; 
            return 0; 
        }
    }

    /**
     * Currently not in use, but don't delete yet.
     *
    class SquareComparator2 implements Comparator<Square> {
        public int compare(Square s1, Square s2){
            return  s1.indentityInt - s2.indentityInt;
        }
    }
    */


}




