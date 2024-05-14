import java.util.*;

public class Node {
	private boolean alive; 
	private List<Node> neighbors; 

	public Node(){
		this.alive = false;
		this.neighbors = new ArrayList<>();
	}

	public boolean isAlive(){
		return alive;
	}

	public void status(boolean alive) {
		this.alive = alive;
	}

	public List<Node> getNeighbors() {
		return neighbors;
	} 

	public void addNeighbor(Node neighbor){ //we need to make sure the neighbor is able to be added in the code 
		neighbors.add(neighbor);
	}
}