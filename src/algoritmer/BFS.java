package algoritmer;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	private boolean[] marked;
	private Node[] edgeTo;
	private int[] distTo;

	public BFS(Graph graph) {
		marked = new boolean[graph.v()];
		edgeTo = new Node[graph.v()];
		distTo = new int[graph.v()];
	}

	public void search(Graph graph, Node startWord, Node endWord) {
		Queue<Node> queue = new LinkedList<>();
		marked[startWord.getPostition()] = true;
		distTo[startWord.getPostition()] = 0;
		queue.add(startWord);
		while (!queue.isEmpty()) {
			Node currentWord = queue.remove();
			for (Node w : graph.adj(currentWord)) {
				System.out.println(w.getWord() +"  "+currentWord.getWord());
				if (marked[w.getPostition()] == false) {
					System.out.println(marked[w.getPostition()]);
					edgeTo[w.getPostition()] = currentWord;
					distTo[w.getPostition()] = distTo[currentWord.getPostition()] + 1;
					marked[w.getPostition()] = true;
					queue.add(w);
					// graph.addEdge(currentWord, w);
					if (w.getWord() == endWord.getWord()) {
						System.out.println(distTo[w.getPostition()]);
						break;
					}
				}
			}

		}

	}
}
