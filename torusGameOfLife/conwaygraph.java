//every node gets a swuqare that controls its color 
//figure out animation on how to make this move
//make sure the rows and cols can be adjustable based on the user input


import java.util.*;

public class conwaygraph{
	private int rows;
	private int columns;
	private List<Node> nodes;

	public conwaygraph(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.nodes = new ArrayList<>();
		createGrid();
		connectionPath();
	}

	private void createGrid() {
		for (int i =0; i< rows * columns; i++){
			nodes.add(new Node());
		}
	}

	private void connectionPath() {
		for (int i = 0; i< rows; i++) {
			for (int j = 0; j< columns; j++) {
				int currentIndex = i * columns + j;
				Node currentNode = nodes.get(currentIndex);

				int right = i * columns + (j+1) % columns;
				currentNode.addNeighbor(nodes.get(right));

				int left = i * columns + (j-1+columns) % columns;
				currentNode.addNeighbor(nodes.get(left));

				int up = ((i-1+rows) % rows) * columns + j;
				currentNode.addNeighbor(nodes.get(up));

				int down = ((i+1) % rows) * columns + j;
				currentNode.addNeighbor(nodes.get(down));

				int upRight = ((i - 1 + rows) % rows) * columns + (j + 1) % columns;
				currentNode.addNeighbor(nodes.get(upRight));

				int upLeft = ((i - 1 + rows) % rows) * columns + (j - 1 + columns) % columns; 
				currentNode.addNeighbor(nodes.get(upLeft));

				int downRight = ((i+1) % rows) * columns + (j+1) % columns;
				currentNode.addNeighbor(nodes.get(downRight));

				int downLeft = ((i+1) % rows) * columns + (j-1 + columns) % columns;
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
			} else {
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

}
