package  algoritmer;

import java.util.ArrayList;

public class Node {
	private String word;
	private boolean marked;
	private ArrayList<Node> adjNodes;
	private int distance;
	
	public Node(String word) {
		adjNodes = new ArrayList<Node>();
		this.marked = false;
		this.word = word;
	}
	
	public void addEdge(Node node) {
		adjNodes.add(node);
	}
	
	public ArrayList<Node> getEdges() {
		return this.adjNodes;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public boolean getMarked() {
		return this.marked;
	}
	
	public void setMarked(boolean value) {
		this.marked = value;
	}
	
	public void setDistance(int dist) {
		this.distance = dist;
	}
	
	public int getDistance() {
		return this.distance;
	}
}
