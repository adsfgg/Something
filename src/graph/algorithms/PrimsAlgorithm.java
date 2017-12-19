package graph.algorithms;

import exceptions.graph.GraphException;
import graph.Edge;
import graph.Graph;

import java.util.List;

/**
 * Class that contains functions related to Prim's algorithm.
 */
public class PrimsAlgorithm {
    /**
     * Runs Prim's algorithm on the given graph.
     *
     * @param source      the graph to run Prim's algorithm on
     * @param startVertex the vertex to start the algorithm on
     *
     * @return the minimum spanning tree created using Prim's algorithm
     *
     * @throws GraphException throws <code>GraphException</code> if a minimum spanning tree cannot be created.
     */
    public static Graph run(Graph source, int startVertex) throws GraphException {
        if(source.getVertexCount() == 0)
            throw new IllegalArgumentException("Cannot compute minimum spanning tree on empty graph");
        
        Graph graph = new Graph(source.getVertexCount());
        graph.setTitle(source.getTitle() + " - (Prim's Result)");
        
        boolean[] discovered = new boolean[graph.getVertexCount()];
        discovered[startVertex] = true;
        
        for(int a = 0; a < source.getVertexCount() - 1; a++) {
            Edge minEdge = new Edge();
            for(int i = 0; i < source.getVertexCount(); i++) {
                if(discovered[i]) {
                    List<Edge> edges = source.getEdgesConnectedTo(i);
                    
                    for(Edge edge : edges)
                        if(minEdge.getWeight() > edge.getWeight() && !fullyDiscovered(edge, discovered)) minEdge = edge;
                }
            }
            
            graph.addEdge(minEdge);
            discovered[minEdge.getEndV()] = true;
        }
        
        if(!graph.isMST()) throw new GraphException("Unable to create a minimum spanning tree using Prim's algorithm!");
        
        return graph;
    }
    
    private static boolean fullyDiscovered(Edge minEdge, boolean[] discovered) {
        return discovered[minEdge.getStartV()] && discovered[minEdge.getEndV()];
    }
}