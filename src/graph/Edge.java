package graph;

public class Edge implements Comparable<Edge> {
    private final double weight;
    private final int startV;
    private final int endV;
    
    public Edge() {
        this.startV = 0;
        this.endV = 0;
        this.weight = Double.POSITIVE_INFINITY;
    }
    
    /**
     * Initialises a weighted edge connected to the given vertices with the given weight.
     *
     * @param startV the first vertex of the edge
     * @param endV   the second vertex of the edge
     * @param weight the weight of the edge.
     */
    public Edge(int startV, int endV, double weight) {
        if(startV < 0 || endV < 0) throw new IllegalArgumentException("Vertex index out of bounds.");
        
        this.startV = startV;
        this.endV = endV;
        
        if(weight <= 0) {
            throw new IllegalArgumentException("Weight out of range ( <= 0 )!");
        }
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d) = %.1f", getStartV(), getEndV(), weight);
    }
    
    @Override
    public int compareTo(Edge e) {
        return Double.compare(this.getWeight(), e.getWeight());
    }
    
    public int getStartV() {
        return startV;
    }
    
    public int getEndV() {
        return endV;
    }
}
