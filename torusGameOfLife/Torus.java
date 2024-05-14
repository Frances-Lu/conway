package torusGameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Class for Torus
 * Calculates the math for squares/nodes of the torus
 * Note: all static finals (SIDES, CS_SIDES, etc.) can be changed.
 * We just selected these values to best see the torus.
 */
public class Torus {

    public static final int SIDES = 76; //The number of vertices for the entire circle (large, the donut part looking from the top)
    public static final int CS_SIDES = 30; //The number of vertices for the cross section circle (small, would need to cut donut)
    public static final double RADIUS = 3 * 45.0; //Should be a scalar times some number to keep track of ratios between radis & csRadius
    public static final double CS_RADIUS = 1 * 45.0;

    public Square[][] squares; //Right now a 2D array, but probably will change to a graph when Graph class is worked on.
    public Vertex[][] vertices;
    public ArrayList<Square> drawableSquares; //Arraylist of squares with a normal vector that faces the screen. 
    public ArrayList<Square> arrayOfSquares = new ArrayList<Square>();


    /**
     * Calculates the vertices based on SIDES, CS_SIDES, RADIUS, and CS_RADIUS
     * Then, uses those vertices to set up (as of now) a double array with square objects
     */
    public void initializeTorus() {

        // Calculate the vertices' x, y, and z location
        double theta, phi, x, y, z, helper;
        vertices = new Vertex[CS_SIDES][SIDES];
        for(int i = 0; i < CS_SIDES; i ++) { //Iterate along the cross section
            theta = 2 * Math.PI * i / CS_SIDES; //Radians of angle of cross section
            for(int j = 0; j < SIDES; j++) { //Iterate around the torus
                phi = 2 * Math.PI * j / SIDES; //Radians of angle of "top down" of torus.
                helper = RADIUS + (CS_RADIUS * Math.cos(theta)); //Cross sectional radius from center, based on theta
                z = CS_RADIUS * Math.sin(theta);
                x = helper * Math.cos(phi);
                y = helper * Math.sin(phi);
                vertices[i][j] = new Vertex(x,y,z);
            }
        }


        // Calculate the squares
        /**
         * Need (i + 1) % SIDES to loop back to zero if i + 1 = CS_SIDES, same for j/SIDES
         * v1, v2, v3, and v4 should go in square formation to help with drawing the square
         */ 
        Vertex v1, v2, v3, v4;
        int nextI, nextJ, count = 0;
        squares = new Square[CS_SIDES][SIDES];
        for (int i = 0; i < CS_SIDES; i++) {
            for (int j = 0; j < SIDES; j++) {
                nextI = (i + 1) % CS_SIDES;
                nextJ = (j + 1) % SIDES;
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
        return Color.BLACK; //Makes all nodes dead.

        /**
         * Checkerboard pattern.
         * Currently node in use, useful to see torus.
         *
        if ((i + j) % 2 == 0) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
        */

    }

    /**
     * Rotates all squares in the torus based on a given rotation matrix.
     * @param tMartrix the transformation (or rotation) matrix
     */
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

        //Calculates the cross product, keep the ones that we have to draw
        for (Square[] row : squares) {
            for (Square square : row) {
                if(square.crossProductNeg()) {
                    drawableSquares.add(square);
                }
            }
        }

        //Sort the squares from back to front to be drawn.
        quickSort(new SquareComparator(), drawableSquares);

        //Draws all the sqaures
        for(Square square : drawableSquares) {
            square.drawSquare(g2);
        }

    }

    /**
     * My quicksort code from lab 5, can work with any given ArrayList<Square>
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

    //Quick sort
    public void quickSort(Comparator<Square> c, ArrayList<Square> arrList) {
        int randomPivot = (int) (Math.random() * arrList.size());
        quickSortHelper(c, 0,arrList.size() - 1,randomPivot, arrList);

    }

    /**
     * Comparator class to help sort squares' z-value from back to front.
     * Back = largest z-value; front = smallest z-value
     */
    class SquareComparator implements Comparator<Square>{
        public int compare(Square s1, Square s2){
            if (s1.sqVertices[0].z < s2.sqVertices[0].z) return 1; 
            if (s1.sqVertices[0].z > s2.sqVertices[0].z) return -1; 
            return 0; 
        }
    }

}




