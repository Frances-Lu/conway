package torusGameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Square { 

        Vertex[] sqVertices = new Vertex[4];
        Color color;
        int indentityInt;

        public Square(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Color color, int someInt) {
            sqVertices[0] = v1;
            sqVertices[1] = v2;
            sqVertices[2] = v3;
            sqVertices[3] = v4;
            this.color = color;
            this.indentityInt = someInt;
        }

        public boolean crossProductPostitive() {
            return (Vertex.crossProduct(sqVertices[0], sqVertices[1], sqVertices[3]).z < 0);
        }

        //Moves the square
        public void transpose(double dx, double dy, double dz) {
            for(int i = 0; i <= 3; i++) {
                sqVertices[i].x += dx;
                sqVertices[i].y += dy;
                sqVertices[i].z += dz;
            }
        }

        //Used to change the color of the square.
        public void changeColor(Color newColor) { 
            this.color = newColor;
        }

        public void drawSquare(Graphics2D g2) {

            int[] xPoints = new int[4];
            int[] yPoints = new int[4];

            for (int i = 0; i < 4; i++) {
                xPoints[i] = (int) sqVertices[i].x;
                yPoints[i] = (int) sqVertices[i].y;
            }

            g2.setColor(color);
            g2.fillPolygon(xPoints, yPoints, 4);

        }

    }