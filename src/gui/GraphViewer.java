package gui;

import graph.Graph;

import javax.swing.*;
import java.awt.*;

public class GraphViewer extends JFrame implements Runnable {
    public GraphViewer(Graph graph, int[][] coords) {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle(graph.getTitle());
        
        GraphPanel g = new GraphPanel(graph, coords);
        this.add(g);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    public GraphViewer(Graph graph) {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle(graph.getTitle());
        
        GraphPanel g = new GraphPanel(graph);
        this.add(g);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    @Override
    public void run() {
        this.repaint();
    }
    
    public static GraphViewer newWindow(Graph graph)
    {
        GraphViewer viewer = new GraphViewer(graph);
        
        SwingUtilities.invokeLater(viewer);
        
        return viewer;
    }
    
    public static GraphViewer newWindow(Graph graph, int[][] coords)
    {
        GraphViewer viewer = new GraphViewer(graph, coords);
        
        SwingUtilities.invokeLater(viewer);
        
        return viewer;
    }
}
