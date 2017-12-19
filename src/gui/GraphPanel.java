package gui;

import graph.Edge;
import graph.Graph;
import helpers.MathHelper;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;

public class GraphPanel extends JPanel {
    private Graph graph;
    public static final byte VERTEX_RADIUS = 50;
    private int[][] coords;
    
    public GraphPanel(Graph graph, int[][] coords) {
        this.setGraph(graph);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(800, 600));
        
        this.coords = coords;
    }
    
    public GraphPanel(Graph graph) {
        this(graph, getCoords(graph.getVertexCount()));
    }
    
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        
        Graphics2D g = (Graphics2D) graphics;
        LinkedList<Edge> edges = this.graph.getEdgeList();
        
        g.setFont(new Font("Helvetica", Font.PLAIN, 24));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for(Edge edge : edges) {
            double weight = edge.getWeight();
            int startX = coords[edge.getStartV()][0];
            int startY = coords[edge.getStartV()][1];
            int endX = coords[edge.getEndV()][0];
            int endY = coords[edge.getEndV()][1];
            
            drawEdge(g, startX, startY, endX, endY, weight);
        }
        
        for(int i = 0; i < this.graph.getVertexCount(); i++) {
            drawVertex(g, i, coords[i][0], coords[i][1]);
        }
    }
    
    public void setGraph(Graph graph) {
        Objects.requireNonNull(graph);
        this.graph = graph;
    }
    
    private static int[][] getCoords(int v) {
        int[][] coords = new int[v][2];
        
        if(v == 2) {
            coords[0][0] = 50;
            coords[0][1] = 50;
            
            coords[1][0] = 200;
            coords[1][1] = 50;
        }
        else if(v == 3) {
            coords[0][0] = 50;
            coords[0][1] = 50;
            
            coords[1][0] = 200;
            coords[1][1] = 50;
            
            coords[2][0] = 200;
            coords[2][1] = 200;
        }
        else if(v == 5) {
            coords[0][0] = 150;
            coords[0][1] = 50;
            
            coords[1][0] = 50;
            coords[1][1] = 150;
            
            coords[2][0] = 150;
            coords[2][1] = 150;
            
            coords[4][0] = 250;
            coords[4][1] = 150;
            
            coords[3][0] = 150;
            coords[3][1] = 250;
        }
        
        return coords;
    }
    
    private void drawEdge(Graphics2D g, int startX, int startY, int endX, int endY, double weight) {
        String s;
        
        if(MathHelper.isInt(weight)) {
            s = String.format("%.0f", weight);
        }
        else {
            s = String.format("%.2f", weight);
        }
        int centerX1 = startX + (VERTEX_RADIUS / 2);
        int centerY1 = startY + (VERTEX_RADIUS / 2);
        int centerX2 = endX + (VERTEX_RADIUS / 2);
        int centerY2 = endY + (VERTEX_RADIUS / 2);
        int middleX = ((centerX1 + centerX2) / 2) - (g.getFontMetrics().stringWidth(s) / 2);
        int middleY = ((centerY1 + centerY2) / 2) + (g.getFontMetrics().getHeight() / 2);
        
        g.setColor(Color.GRAY);
        g.drawLine(centerX1, centerY1, centerX2, centerY2);
        g.setColor(Color.BLACK);
        g.drawString(s, middleX, middleY);
    }
    
    private void drawVertex(Graphics2D g, int i, int x, int y) {
        char name = (char) ('A' + i);
        
        g.setColor(Color.WHITE);
        g.fillOval(x, y, VERTEX_RADIUS, VERTEX_RADIUS);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, VERTEX_RADIUS, VERTEX_RADIUS);
        
        int centerX = x + (VERTEX_RADIUS / 2) - (g.getFontMetrics().charWidth(name) / 2);
        int centerY = y + (VERTEX_RADIUS / 2) + (g.getFontMetrics().getHeight() / 2) - 5;
        
        g.drawString(String.valueOf(name), centerX, centerY);
    }
}
