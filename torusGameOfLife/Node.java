import java.util.*;

public class Node {
	private boolean alive; 
	private boolean aliveNextRound;
	private List<Node> neighbors; 

	public Node(){
		this.alive = false;
		this.aliveNextRound = false;
		this.neighbors = new ArrayList<>();
	}

	public boolean isAlive(){
		return alive;
	}

	public void setSquare(Square someSquare) {
		this.square = someSquare;
	}

	public void status(boolean fate) {
		//System.out.println("I am running!");
		//System.out.println("test!");
		this.aliveNextRound = fate;
	}

	public void updateLife() {
		this.alive = this.aliveNextRound;
		if(alive) {
			square.changeColor(Color.WHITE);
		}
		else {
			square.changeColor(Color.BLACK);
		}


	public List<Node> getNeighbors() {
		return neighbors;
	} 

	public void addNeighbor(Node neighbor){ //we need to make sure the neighbor is able to be added in the code 
		neighbors.add(neighbor);
	}
}