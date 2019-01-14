package algoritmer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
	private int vertices;
	private ArrayList<Node> nodes;
	
	public Graph(ArrayList<Node> list) {
		this.vertices = list.size();
		nodes = list;
	}
	
	public int v() {
		return this.vertices;
	}
	
	public void setUnmarked() {
		for(Node node : nodes) {
			node.setMarked(false);
		}
	}
	
	public int search(Node startWord, Node endWord) {
		Queue<Node> queue = new LinkedList<>();
		startWord.setMarked(true);
		startWord.setDistance(0);
		queue.add(startWord);
		while (!queue.isEmpty()) {
			Node currentWord = queue.remove();
			if (currentWord.getWord() == endWord.getWord()) {
				setUnmarked();
				return currentWord.getDistance();
			}
			for (Node w : currentWord.getEdges()) {
				if (!w.getMarked()) {
					w.setMarked(true);
					w.setDistance(currentWord.getDistance()+1);
					queue.add(w);
				}
			}
		}
		setUnmarked();
		return -1;
	}
	
}
