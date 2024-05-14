package torusGameOfLife;

import java.util.*;
import javax.swing.*;
import java.awt.Color;

/**
 * Node class
 */
public class Node {
	private boolean alive; 
	private boolean aliveNextRound;
	private List<Node> neighbors; 
	private Square square;

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

	/**
	 * Sets the node to life/death for the next iteration.
	 * @param fate either true (for alive) or false (for dead)
	 */
	public void status(boolean fate) {
		this.aliveNextRound = fate;
	}

	/**
	 * Updates the status for next iteration to become this iteration.
	 */
	public void updateLife() {
		this.alive = this.aliveNextRound;
		if(alive) {
			square.changeColor(Color.WHITE);
		}
		else {
			square.changeColor(Color.BLACK);
		}
	}

	/**
	 * @return the list of the node's neighbors
	 */
	public List<Node> getNeighbors() {
		return neighbors;
	} 

	public void addNeighbor(Node neighbor){ //we need to make sure the neighbor is able to be added in the code 
		neighbors.add(neighbor);
	}
}