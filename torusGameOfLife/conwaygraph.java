//every node gets a swuqare that controls its color 
//figure out animation on how to make this move
//make sure the rows and cols can be adjustable based on the user input
package torusGameOfLife;

import java.util.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


/**
 * Class that helps deal with the interation between nodes and squares
 * Contains the graph data structure
 */
public class conwaygraph{
	private int rows;
	private int columns;
	private List<Node> nodes;
	public TorusRender torusRender;
	public Torus classTorus;

	public conwaygraph(int rows, int columns, TorusRender t, Torus torus) {
		this.rows = rows;
		this.columns = columns;
		this.nodes = new ArrayList<>();
		this.torusRender = t;
		this.classTorus = torus;
		createGrid();
		connectionPath();
		giveSquares();
	}

	private void createGrid() {
		for (int i =0; i < rows * columns; i++){
			nodes.add(new Node());
		}
	}

	/**
	 * Connects each node with its 8 neighbors
	 */
	private void connectionPath() {
		for (int i = 0; i< rows; i++) {
			for (int j = 0; j< columns; j++) {
				int currentIndex = i * columns + j;
				Node currentNode = nodes.get(currentIndex);

				int right = i * columns + ((j+1) % columns);
				currentNode.addNeighbor(nodes.get(right));

				int left = i * columns + ((j-1+columns) % columns);
				currentNode.addNeighbor(nodes.get(left));

				int up = ((i-1+rows) % rows) * columns + j;
				currentNode.addNeighbor(nodes.get(up));

				int down = ((i+1) % rows) * columns + j;
				currentNode.addNeighbor(nodes.get(down));

				int upRight = ((i - 1 + rows) % rows) * columns + ((j + 1) % columns);
				currentNode.addNeighbor(nodes.get(upRight));

				int upLeft = ((i - 1 + rows) % rows) * columns + ((j - 1 + columns) % columns); 
				currentNode.addNeighbor(nodes.get(upLeft));

				int downRight = ((i+1) % rows) * columns + ((j+1) % columns);
				currentNode.addNeighbor(nodes.get(downRight));

				int downLeft = ((i+1) % rows) * columns + ((j-1 + columns) % columns);
				currentNode.addNeighbor(nodes.get(downLeft));

			}
		}
	}

	/**
	 * Calculates the next iteration of Conway's Game of Life
	 */
	public void update(){

		for (Node node : nodes) {
			int aliveNeighbors = countAliveNeighbors(node);

			if (aliveNeighbors < 2 || aliveNeighbors > 3) {
				node.status(false);
			}
			else if (aliveNeighbors == 3) {
				node.status(true);
			}
		}
		for (Node node : nodes) {
			node.updateLife();
		}
	}

	/**
	 * @return the number of alive neighbors for a given node.
	 */
    private int countAliveNeighbors(Node node) {
        int count = 0;
        for (Node neighbor : node.getNeighbors()) {
            if (neighbor.isAlive()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gives each node in the graph its respective square to control.
     * Should be black if dead, and white if alive.
     */
    public void giveSquares() {
   		ArrayList<Square> sqArr = classTorus.arrayOfSquares;
    	for(int i = 0; i < sqArr.size(); i++) {
    		nodes.get(i).setSquare(sqArr.get(i));
    		//Makes sure that nodes that start black are dead, otherwise they are alive.
    		//Note: code currently needed due to every node starting dead and being colored black automatically
    		if(sqArr.get(i).color.equals(Color.BLACK)) { 
    			nodes.get(i).status(false);
    			nodes.get(i).updateLife();
    		}
    		else {
    			nodes.get(i).status(true);
    			nodes.get(i).updateLife();
    		}
    	}


    	/**
    	 * ! ! !
    	 * 
    	 * NOTE: Here is where you could make certain nodes dead
    	 * or alive via makeNodeAlive() or makeNeighborsAlive()
    	 * to play/see the patterns.
    	 * 
    	 * ! ! !
    	 * 
    	 */
    	makeNodeAlive(1);
    	makeNeighborsAlive(1);


    }

    /**
     * We didn't have time to get the grid of squares to select at the beginning to be alive
     * like we planed. However, this method (along with makeNeighborsAlive) can be used to make certain nodes
     * alive and see the results.
     */
    //Makes a given node alive.
    public void makeNodeAlive(int nodeNum) {
    	nodes.get(nodeNum).status(true);
    	nodes.get(nodeNum).updateLife();
    	
    }

    //Makes a given node's neighbors alive.
    public void makeNeighborsAlive(int nodeNum) {
    	for(Node n : nodes.get(nodeNum).getNeighbors()) {
    		n.status(true);
    		n.updateLife();
    	}
    }

    /**
     * The timer in between iterations of Conway's Game of Life
     * @param delay the delay in between iterations in milliseconds
     */
    public void startGameOfLife(int delay) {
	    Timer timer = new Timer(delay, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            update();
	            torusRender.repaint();
	        }
	    });

	    timer.start();
	}

}
