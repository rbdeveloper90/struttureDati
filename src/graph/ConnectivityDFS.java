package graph;


public class ConnectivityDFS<V, E> extends DFS <V, E, Object, Boolean> {
	protected int reached;
	protected void setup() { reached = 0; }
	protected void startVisit(Vertex<V> v) { reached++; }
	protected Boolean finalResult(Boolean dfsResult) { 
		return new Boolean(reached == graph.numVertices());
	}
}