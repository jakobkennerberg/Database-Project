package algoritmer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
	private int vertices;
	private ArrayList<Edge> edges;
	private int edgesAmount;
	private boolean[] marked;
	private Node[] edgeTo;
	private int[] distTo;
	
	public Graph(int v) {
		this.vertices = v;
		edges = new ArrayList<Edge>();
		marked = new boolean[v];
		edgeTo = new Node[v];
		distTo = new int[v];
	}
	
	public void addEdge(Node v, Node w) {
		edges.add(new Edge(v, w));
		edgesAmount++;
	}
	
	public ArrayList<Node> adj(Node v) {
		ArrayList<Node> adjWords = new ArrayList<Node>();
		for(Edge e : edges) {
			if(e.getStart().getWord() == v.getWord()) {
				adjWords.add(e.getEnd());
				//System.out.println(e.getStart().getWord() + "  " + e.getEnd().getWord());
			}
		}
		return adjWords;
	}
	
//	public ArrayList<Node> getWords() {
//		return this.words;
//	}
	
	public int v() {
		return this.vertices;
	}
	
	public int e() {
		return this.edgesAmount;
	}
	
	public void search(Node startWord, Node endWord) {
		Queue<Node> queue = new LinkedList<>();
		marked[startWord.getPostition()] = true;
		distTo[startWord.getPostition()] = 0;
		queue.add(startWord);
		while (!queue.isEmpty()) {
			Node currentWord = queue.remove();
			for (Node w : adj(currentWord)) {
				//System.out.println(w.getWord() +"  "+currentWord.getWord());
				if (marked[w.getPostition()] == false) {
					System.out.println(w.getWord());
					//System.out.println(marked[w.getPostition()]);
					edgeTo[w.getPostition()] = currentWord;
					distTo[w.getPostition()] = distTo[currentWord.getPostition()] + 1;
					marked[w.getPostition()] = true;
					queue.add(w);
					// graph.addEdge(currentWord, w);
					if (w.getWord() == endWord.getWord()) {
						System.out.println(distTo[w.getPostition()] + " i mål");
						break;
					}
				}
			}

		}

	}
}
