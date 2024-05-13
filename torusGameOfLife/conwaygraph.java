//every node gets a swuqare that controls its color 
//figure out animation on how to make this move
//make sure the rows and cols can be adjustable based on the user input
package torusGameOfLife;

import java.util.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;



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

	public void update(){
		List<Node> newGen = new ArrayList<>();

		for (Node node : nodes) {
			int aliveNeighbors = countAliveNeighbors(node);

			if (node.isAlive()) {
				if (aliveNeighbors < 2 || aliveNeighbors > 3) {
					node.status(false);
				}
			} 
			else {
				if (aliveNeighbors == 3) {
					node.status(true);
				}
			}
			newGen.add(node);
		}
		nodes = newGen;
	}

    private int countAliveNeighbors(Node node) {
        int count = 0;
        for (Node neighbor : node.getNeighbors()) {
            if (neighbor.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public void giveSquares() {
    	//ArrayList<Square> sqArr = classTorus.convertToArr();
   		ArrayList<Square> sqArr = classTorus.arrayOfSquares;
    	for(int i = 0; i < sqArr.size(); i++) {
    		/** Crappy code? Need to fingure out what I was doing here. Don't delete yet.
    		if(i >= 100 && i <= 110) {
 				sqArr.get(i).changeColor(Color.WHITE);
 			}
 			if((i % 2) == 0 || (i + 1) == sqArr.size()) {
 				nodes.get(i).setSquare(sqArr.get(i));
 			}
 			else {
 				nodes.get(i).setSquare(sqArr.get(i+1));
 			}
 			*/
    		nodes.get(i).setSquare(sqArr.get(i));
    		if(sqArr.get(i).color.equals(Color.BLACK)) { //Makes sure that nodes that are black are dead, otherwise they are alive.
    			nodes.get(i).status(false);
    		}
    		else {
    			nodes.get(i).status(true);
    		}
    	}

    	//Inital set up of alive nodes. Can be changed.
    	for(Node n : nodes.get(10).getNeighbors()) {
    		n.status(true);
    		System.out.println("this ran!");
    	}
    	for(Node n : nodes.get(23).getNeighbors()) {
    		n.status(true);
    		System.out.println("this ran!");
    	}
    	for(Node n : nodes.get(14).getNeighbors()) {
    		n.status(true);
    		System.out.println("this ran!");
    	}
    	for(Node n : nodes.get(20).getNeighbors()) {
    		n.status(true);
    		System.out.println("this ran!");
    	}


    }

    public void startGameOfLife(int delay) {
	    Timer timer = new Timer(delay, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//System.out.println("Updated"); //Used to make sure nodes are being updated
	            update();
	            torusRender.repaint();
	        }
	    });

	    timer.start();
	}

}

//transfer to 1D array, take first square and put that in the node, amke a constructor and have it as a variable
/*public int [] convert2DTo1() {
	int [] array = new [csSides * sides];
	int index = 0;
	for (int i = 0; i<csSides; i++) {
		for (int j = 0; j < sides; j++) {
			array[index]= squares[i][j].getValue();
			index++;
		}
	}
	return array;
}*/

//put this in torus rendder 



//every node have one square 
//create on the other file, have that code call this method 

//make a method 