package View;

import Controller.FolderController;
import Interfaces.RecentSearchesCallback;
import Model.Node;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecentSearchesGUI extends JFrame implements RecentSearchesCallback {
    private FolderController controller;
    private DefaultTableModel tableModel;
    public RecentSearchesGUI(FolderController controller){
        this.controller = controller;
        setTitle("Recent searches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"File Name", "Path"}, 0);
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);

        populateTable();

        mainPanel.add(scrollPane);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateTable(){
        for (Node node : controller.getRecentSearches()) {
            Object[] row = new Object[]{node.getFile().getName(), node.getFile().getAbsolutePath()};
            tableModel.addRow(row);
        }
    }

    @Override
    public void recentSearches(List<Node> list) {
        populateTable();
    }
}
