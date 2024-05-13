package torusGameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Panel {


	public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        Torus t = new Torus(); //As of now, Torus constructor has no params (but we could change that)
        t.initializeTorus();

        TorusRender renderPanel = new TorusRender(t);
        pane.add(renderPanel, BorderLayout.CENTER);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);

        conwaygraph g = new conwaygraph(30, 76, renderPanel, t);
        g.startGameOfLife(750);

    }

}