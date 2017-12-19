package graph;

import exceptions.graph.GraphException;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to model a graph using an adjacency matrix, and using the <code>Edge</code> class.
 */
public class Graph {
    private double[][] graph;
    private int vertexCount;
    private int edgeCount;
    private String title;
    
    /**
     * Initialises an empty graph object
     */
    
    public Graph() {
        this("", 0);
    }
    
    /**
     * Initialise a new graph object using the given adjacency matrix and title
     *
     * @param graph the adjacency matrix to base the object on
     * @param title the title of the new graph
     */
    
    public Graph(String title, double[][] graph) {
        if(graph == null) throw new IllegalArgumentException("Cannot create graph object from null source");
        
        this.graph = graph;
        
        if(graph.length != 0) this.vertexCount = graph[0].length;
        else this.vertexCount = 0;
        
        this.edgeCount = countEdges(graph);
        
        this.title = title;
    }
    
    public Graph(double[][] graph)
    {
        this("", graph);
    }
    
    /**
     * Initialise a new graph with the given number of vertices.
     *
     * @param vertexCount the number of vertices that the new graph should have.
     */
    
    public Graph(int vertexCount) {
        this("", vertexCount);
    }
    
    /**
     * Initialise a new graph with the given number of vertices.
     *
     * @param vertexCount the number of vertices that the new graph should have.
     */
    
    public Graph(String title, int vertexCount) {
        if(vertexCount < 0) throw new IllegalArgumentException("Cannot create a graph with negative vertices.");
        
        this.graph = new double[vertexCount][vertexCount];
        this.vertexCount = vertexCount;
        this.edgeCount = 0;
        
        this.title = title;
    }
    
    private static int countEdges(double[][] graph) {
        int edges = 0;
        
        for(double[] row : graph)
            for(double v : row)
                if(v != 0) edges++;
        
        return edges / 2;
    }
    
    /**
     * Removes an edge from the graph
     *
     * @param startV the index of the start vertex
     * @param endV   the index of the end vertex
     */
    
    public void removeEdge(int startV, int endV) {
        if(startV > this.vertexCount || startV < 0 || endV < 0 || endV > this.vertexCount)
            throw new IllegalArgumentException("Vertex index out of bounds.");
        
        if(this.graph[startV][endV] == 0 || this.graph[endV][startV] == 0)
            throw new IllegalArgumentException("Cannot remove an edge that doesn't exist.");
        
        this.graph[startV][endV] = 0;
        this.graph[endV][startV] = 0;
        this.edgeCount--;
    }
    
    /**
     * Connects the two given vertices with the given weight
     *
     * @param v1 the starting vertex
     * @param v2 the ending vertex
     * @param weight the weight of the new edge
     */
    
    public void connect(int v1, int v2, double weight)
    {
        this.addEdge(new Edge(v1, v2, weight));
    }
    
    /**
     * Removes an edge from the graph
     *
     * @param edge the edge to remove from the graph
     */
    
    public void removeEdge(Edge edge) {
        removeEdge(edge.getStartV(), edge.getEndV());
    }
    
    /**
     * Adds a vertex to the graph.
     *
     * @return the index of the new vertex.
     */
    public int addVertex() {
        double[][] tempGraph = this.graph;
        
        this.graph = new double[this.vertexCount][this.vertexCount]; // vertices counts from 1, since the index of the array counts from zero, using the vertices field adds one to the size of the array.
        
        System.arraycopy(tempGraph, 0, this.graph, 0, this.vertexCount - 1);
        
        this.vertexCount++;
        
        return this.vertexCount - 1;
    }
    
    
    //TODO for the love of god test this pls
    
    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the index of the vertex to be removed.
     */
    public void removeVertex(int vertex) {
        double[][] tempGraph = this.graph;
        
        this.graph = new double[this.vertexCount - 1][this.vertexCount - 1];
        
        for(int i = 0, tempGraphLength = tempGraph.length; i < tempGraphLength; i++) {
            double[] d = tempGraph[i];
            if(i == vertex) continue;
            
            for(int j = 0, dLength = d.length; j < dLength; j++) {
                if(j == vertex) continue;
                
                double v = d[j];
                
                if(j > vertex) {
                    if(i > vertex) this.graph[i - 1][j - 1] = v;
                    else this.graph[i][j - 1] = v;
                }
                else {
                    if(i > vertex) this.graph[i - 1][j] = v;
                    else this.graph[i][j] = v;
                }
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder adj = new StringBuilder("    ");
        for(int i = 0; i < this.graph.length; i++) {
            adj.append((char) ('A' + i));
            
            if(i + 1 != this.graph.length) adj.append(" | ");
        }
        
        adj.append("\n    ----");
        
        for(int i = 1; i < this.graph.length - 1; i++) {
            adj.append("----");
        }
        
        adj.append("-\n");
        
        char lastLetter = 'A';
        
        for(double[] line : this.graph) {
            adj.append(lastLetter);
            adj.append(" | ");
            adj.append(toStringForAdjacencyMatrix(line));
            adj.append("\n");
            
            lastLetter += 1;
        }
        
        return adj.toString();
    }
    
    private static String toStringForAdjacencyMatrix(double[] line) {
        StringBuilder result = new StringBuilder();
        
        for(int i1 = 0, lineLength = line.length; i1 < lineLength; i1++) {
            double i = line[i1];
            result.append((int) i);
            
            if(i1 + 1 != lineLength) {
                if(i > 10) result.append("| ");
                else result.append(" | ");
            }
        }
        
        return result.toString();
    }
    
    /**
     * Checks if the graph is cyclic.
     *
     * @return true if the graph is cyclic, false otherwise.
     */
    
    public boolean isCyclic() {
        
        boolean[] discovered = new boolean[this.vertexCount];
        
        for(int i = 0; i < this.vertexCount; i++) {
            if(!discovered[i]) {
                if(isCyclicDFS(i, discovered, -1)) return true;
            }
        }
        
        return false;
    }
    
    private boolean isCyclicDFS(int vertex, boolean[] discovered, int parent) {
        discovered[vertex] = true;
        
        java.util.List<Edge> edges = this.getEdgesConnectedTo(vertex);
        
        for(Edge edge : edges) {
            int other;
            
            if(edge.getStartV() == vertex) other = edge.getEndV();
            else other = edge.getStartV();
            
            if(!discovered[other]) {
                if(isCyclicDFS(other, discovered, vertex)) {
                    return true; // cycle
                }
            }
            else if(other != parent) {
                return true; // cycle
            }
        }
        
        return false;
    }
    
    /**
     * Checks if adding the given edge to the graph will form a cycle.
     *
     * @param edge The edge to be added.
     *
     * @return true if adding the edge will form a cycle, false otherwise.
     *
     * @throws GraphException if the graph is already cyclic a <code>GraphException</code> is thrown.
     */
    
    public boolean formsCycle(Edge edge) throws GraphException {
        if(this.isCyclic())
            throw new GraphException("Cannot check if new edge creates cycle when graph is already cyclic.");
        
        this.addEdge(edge);
        
        boolean res = this.isCyclic();
        
        this.removeEdge(edge);
        
        return res;
    }
    
    /**
     * Generates a list of <code>WeightedEdge</code> objects
     *
     * @return the list of edges
     */
    
    public LinkedList<Edge> getEdgeList() {
        LinkedList<Edge> edges = new LinkedList<>();
        
        double[][] graph = this.graph;
        
        for(int i = 0, graph1Length = graph.length; i < graph1Length; i++) {
            double[] row = graph[i];
            for(int j = i + 1, rowLength = row.length; j < rowLength; j++) {
                double weight = row[j];
                
                if(weight != 0) edges.add(new Edge(i, j, weight));
            }
        }
        
        return edges;
    }
    
    /**
     * Checks if the graph is a minimum spanning tree.
     *
     * @return whether the graph is a minimum spanning tree.
     */
    
    public boolean isMST() {
        return this.edgeCount == this.vertexCount - 1 && isConnected();
    }
    
    /**
     * Checks if the graph is connected.
     * <p>
     * A connected graph is a graph where each vertex
     * is connected to another by a single minimum path.
     *
     * @return whether the graph is connected or not.
     */
    
    public boolean isConnected() {
        return connectedDFSCheck(0, new boolean[this.vertexCount]) == this.vertexCount;
    }
    
    private int connectedDFSCheck(int vertex, boolean[] discovered) {
        int vertices = 1;
        discovered[vertex] = true;
        
        List<Edge> edges = this.getEdgesConnectedTo(vertex);
        
        for(Edge edge : edges) {
            int other;
            
            if(edge.getStartV() == vertex) other = edge.getEndV();
            else other = edge.getStartV();
            
            if(!discovered[other]) vertices += connectedDFSCheck(other, discovered);
        }
        
        return vertices;
    }
    
    /**
     * Generates a list of edges that are connected to the given vertex.
     *
     * @param vertex the vertex to generate the list from
     *
     * @return a list of edges connected to <code>vertex</code>x.
     */
    
    public LinkedList<Edge> getEdgesConnectedTo(int vertex) {
        LinkedList<Edge> edges = new LinkedList<>();
        double[] line = this.graph[vertex];
        
        for(int i = 0; i < line.length; i++) {
            if(i == vertex) continue;
            
            double weight = line[i];
            
            if(weight > 0) edges.add(new Edge(vertex, i, weight));
        }
        
        return edges;
    }
    
    /**
     * Adds an edge to the graph
     *
     * @param edge the <code>WeightedEdge</code> object to add to the graph.
     */
    
    public void addEdge(Edge edge) {
        if(edge.getStartV() > this.vertexCount || edge.getStartV() < 0 || edge.getEndV() < 0 || edge.getEndV() > this.vertexCount)
            throw new IllegalArgumentException("Vertex index out of bounds.");
        if(edge.getStartV() == edge.getEndV())
            throw new IllegalArgumentException("Cannot create an edge between the same vertex.");
        if(edge.getWeight() <= 0) throw new IllegalArgumentException("Weight out of range ( <= 0 )");
        if(this.graph[edge.getStartV()][edge.getEndV()] != 0 && this.graph[edge.getEndV()][edge.getStartV()] != 0)
            throw new IllegalArgumentException("Cannot add an edge between two vertices that are already connected.");
        
        this.graph[edge.getStartV()][edge.getEndV()] = edge.getWeight();
        this.graph[edge.getEndV()][edge.getStartV()] = edge.getWeight();
        
        this.edgeCount++;
    }
    
    /**
     * Updates the weight of a given edge.
     *
     * @param edge   the edge to be updated.
     * @param weight the new weight.
     */
    
    public void updateEdge(Edge edge, double weight) {
        if(edge.getStartV() > this.vertexCount || edge.getStartV() < 0 || edge.getEndV() < 0 || edge.getEndV() > this.vertexCount)
            throw new IllegalArgumentException("Vertex index out of bounds.");
        if(edge.getStartV() == edge.getEndV())
            throw new IllegalArgumentException("Cannot create an edge between the same vertex.");
        if(weight <= 0) throw new IllegalArgumentException("Weight out of range ( <= 0 )");
        if(this.graph[edge.getStartV()][edge.getEndV()] == 0 && this.graph[edge.getEndV()][edge.getStartV()] == 0)
            throw new IllegalArgumentException("Cannot add an edge between two vertices that are already connected.");
        
        this.graph[edge.getStartV()][edge.getEndV()] = weight;
        this.graph[edge.getEndV()][edge.getStartV()] = weight;
    }
    
    public int getVertexCount() {
        return vertexCount;
    }
    
    public int getEdgeCount() {
        return edgeCount;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
