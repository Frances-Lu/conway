package torusGameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class to help render/view the torus
 */
public class TorusRender extends JPanel {

    public Torus torus;
    public Graphics2D g2;

    public TorusRender(Torus torus) {
        this.torus = torus;

        torusMouse listener = new torusMouse(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);

    }

    public void recalcPosition(Matrix3D tMatrix) {
        torus.rotateSquares(tMatrix);
        repaint();
    }

    /**
     * Mouse class
     */
    private class torusMouse implements MouseListener, MouseMotionListener {

        public TorusRender t;

        public torusMouse(TorusRender t) {
            this.t = t;
        }

        public void mousePressed(MouseEvent event) {}
        public void mouseReleased(MouseEvent event) {}
        public void mouseClicked(MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseMoved(MouseEvent event) {}

        public void mouseDragged(MouseEvent event) {

            double y = -2 * Math.PI * (event.getY() - 250) / t.getHeight() * .01; //Angle of movement (times .01 to make rotation slower)
            double x = 2 * Math.PI * (event.getX() - 250) / t.getWidth() * .01;

            //Construct transformation matrices (heading and pitch)
            double heading = x;
            Matrix3D headingTransform = new Matrix3D(new double[]
                {Math.cos(heading), 0, -Math.sin(heading),
                0, 1, 0,
                Math.sin(heading), 0, Math.cos(heading)});

            double pitch = y;
            Matrix3D pitchTransform = new Matrix3D(new double[]
                {1, 0, 0,
                0, Math.cos(pitch), Math.sin(pitch),
                0, -Math.sin(pitch), Math.cos(pitch)});

            //Combine the two matrices
            headingTransform.combine(pitchTransform.getMatrix());

            //Use rotation matrix to calculate new position for every square in the torus
            t.recalcPosition(headingTransform);
            
        }

    }
    

    /**
     * Paint method
     * @param g graphics
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g; //Need to cast graphics into graphics2D
        this.g2 = g2;

        g2.setColor(Color.GRAY); // Choose your background color
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.translate(getWidth() / 2, getHeight() / 2);

        torus.drawTorus(g2);

    }

}