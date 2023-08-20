package View;

import Controller.MainController;
import Interfaces.RecentSearchesCallback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class RecentSearchesGUI extends JFrame implements RecentSearchesCallback {
    private final DefaultTableModel tableModel;
    public RecentSearchesGUI(MainController controller){
        setTitle("Recent searches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"File Name", "Path"}, 0);
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);

        recentSearches(controller.getRecentSearches());

        mainPanel.add(scrollPane);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void recentSearches(List<File> files) {
        for(File file : files){
            Object[] row = new Object[]{file.getName(), file.getAbsolutePath()};
            tableModel.addRow(row);
        }
    }
}
