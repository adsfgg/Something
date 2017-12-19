package graph.algorithms;

import exceptions.graph.GraphException;
import graph.Edge;
import graph.Graph;

import java.util.List;
import java.util.stream.Collectors;

public class KruskalsAlgorithm {
    
    /**
     * Runs Kruskal's Algorithm on the given graph.
     *
     * @param source the graph to run Kruskal's Algorithm on.
     *
     * @return the minimum spanning tree created by Kruskal's Algorithm.
     *
     * @throws GraphException thrown if a minimum spanning tree is unable to be created
     */
    public static Graph run(Graph source) throws GraphException {
        if(source.getVertexCount() == 0)
            throw new IllegalArgumentException("Cannot compute minimum spanning tree on empty graph");
        
        Graph graph = new Graph(source.getVertexCount());
        graph.setTitle(source.getTitle() + " - (Kruskal's Result)");
        
        List<Edge> edges = source.getEdgeList().stream().sorted().collect(Collectors.toList());
        
        for(Edge edge : edges)
            if(!graph.formsCycle(edge))
                graph.addEdge(edge);
        
        if(!graph.isMST())
            throw new GraphException("Unable to create a minimum spanning tree using Kruskal's algorithm!");
        
        return graph;
    }
}