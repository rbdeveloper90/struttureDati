package graph;


public class ComponentsBFS<V, E> extends BFS<V, E, Object, Integer> {
	protected Integer compNumber; 
	protected Object COMPONENT = new Object(); 
	protected void setup() { compNumber = 1; }
	protected void startVisit(Vertex<V> v) { v.put(COMPONENT, compNumber);}

	protected Integer finalResult(Integer bfsResult) { 
		for (Vertex<V> v : graph.vertices()) 
			if (v.get(STATUS) == UNVISITED) { 
				compNumber += 1;  
				bfsTrasversal(v);  
			}
		return compNumber;
	}
}