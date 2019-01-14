package  algoritmer;

import java.util.ArrayList;

public class Node {
	private String word;
	private int markedPos;
	//private boolean marked;
	//private ArrayList<Node> adjNodes;
	
	public Node(String word, int arrayPosition) {
	//	adjNodes = new ArrayList<Node>();
	//	marked = false;
		this.word = word;
		this.markedPos = arrayPosition;
	}
	
//	public void addEdge(Edge edge) {
//		edges.add(edge);
//	}
//	
//	public ArrayList<Node> getEdges() {
//		return this.adjNodes;
//	}
	
	public String getWord() {
		return this.word;
	}
	
	
	public int getPostition() {
		return this.markedPos;
	}
}
