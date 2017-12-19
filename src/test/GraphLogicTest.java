package test;

import exceptions.graph.GraphException;
import graph.Graph;
import graph.algorithms.KruskalsAlgorithm;
import graph.algorithms.PrimsAlgorithm;

public class GraphLogicTest {
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
        
        System.out.printf("=====%s=====%n%n", testGraph.getTitle());
    
        System.out.println(testGraph);
    
        System.out.println("\n=====PRIM'S RESULT=====\n");
        
        Graph result;
        
        result = PrimsAlgorithm.run(testGraph, 0);
    
        System.out.println(result);
    
        System.out.println("\n=====KRUSKAL'S RESULT=====\n");
        
        result = KruskalsAlgorithm.run(testGraph);
    
        System.out.println(result);
    
        System.out.println();
    }
}
