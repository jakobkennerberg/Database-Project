package algoritmer;

public class Edge {
	public Node start;
	public Node end;
	
	public Edge(Node start, Node end) {
		this.start = start;
		this.end = end;
	}
	public Node getStart() {
		return this.start;
	}
	
	public Node getEnd() {
		return this.end;
	}
}
