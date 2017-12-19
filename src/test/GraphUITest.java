package test;

import exceptions.graph.GraphException;
import graph.Graph;
import graph.algorithms.KruskalsAlgorithm;
import graph.algorithms.PrimsAlgorithm;
import gui.GraphViewer;

public class GraphUITest {
    public static void main(String[] args) throws GraphException {
        Graph testGraph = new Graph("Test Graph", 5);
    
        testGraph.connect(0, 1, 1);
        testGraph.connect(0, 2, 2);
        testGraph.connect(0, 4, 1);
        testGraph.connect(1, 2, 3);
        testGraph.connect(1, 3, 4);
        testGraph.connect(2, 3, 1);
        testGraph.connect(2, 4, 2);
        testGraph.connect(3, 4, 5);
    
        Graph prims = PrimsAlgorithm.run(testGraph, 0);
        Graph kruskals = KruskalsAlgorithm.run(testGraph);
        
        GraphViewer.newWindow(testGraph);
        GraphViewer.newWindow(prims);
        GraphViewer.newWindow(kruskals);
    }
}
