package main.java.DesktopApp.View;

import main.java.Interfaces.IVisualizerGUI;
import main.java.Model.Node;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class TreeStructureGUI extends JFrame implements IVisualizerGUI {

    private final Node root;
    public TreeStructureGUI(Node root) {
        this.root = root;
    }

    @Override
    public void startGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        TreeVisualization treeVisualization = new TreeVisualization(root);
        add(treeVisualization);
        setVisible(true);
    }

    private class TreeVisualization extends JPanel{
        private final Node root;

        public TreeVisualization(Node root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, getWidth() / 2, 30, root);
        }

        private void drawTree(Graphics g, int x, int y, Node node) {
            if (node != null) {
                // Draw the node's name
                g.setColor(Color.BLACK);
                int nodeWidth = 50;
                int nodeHeight = 30;
                g.drawRect(x - nodeWidth / 2, y, nodeWidth, nodeHeight);
                g.drawString(node.getFile().getName(), x - nodeWidth / 2 + 5, y + nodeHeight / 2);

                List<Node> children = node.getChildren();
                if (!children.isEmpty()) {
                    // Draw lines connecting the node to its children
                    int startX = x;
                    int startY = y + nodeHeight;
                    for (Node child : children) {
                        int horizontalSpacing = 80;
                        int endX = startX + horizontalSpacing;
                        int verticalSpacing = 50;
                        int endY = startY + verticalSpacing;
                        g.drawLine(startX, startY, endX, endY);
                        drawTree(g, endX, endY, child);
                        startX += horizontalSpacing;
                    }
                }
            }
        }
    }
}
