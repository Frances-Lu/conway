package torusGameOfLife;

import java.util.*;
import javax.swing.*;
import java.awt.Color;

public class Node {
	private boolean alive; 
	private List<Node> neighbors; 
	private Square square;

	public Node(){
		this.alive = false;
		this.neighbors = new ArrayList<>();
	}

	public boolean isAlive(){
		return alive;
	}

	public void setSquare(Square someSquare) {
		this.square = someSquare;
	}

	public void status(boolean alive) {
		//System.out.println("I am running!");
		//System.out.println("test!");
		this.alive = alive;
		if(alive) {
			square.changeColor(Color.WHITE);
		}
		else {
			square.changeColor(Color.BLACK);
		}
	}

	public List<Node> getNeighbors() {
		return neighbors;
	} 

	public void addNeighbor(Node neighbor){ //we need to make sure the neighbor is able to be added in the code 
		neighbors.add(neighbor);
	}
}