package main.java.DesktopApp.View;

import main.java.Controller.MainController;
import main.java.Interfaces.RecentSearchesCallback;
import main.java.Model.TableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class RecentSearchesGUI extends JFrame implements RecentSearchesCallback {
    private final DefaultTableModel tableModel;
    public RecentSearchesGUI(MainController controller){
        setTitle("Recent searches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        tableModel = new TableModel(new String[]{"File Name", "Path"}, 0);

        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);


        mainPanel.add(scrollPane);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        recentSearches(controller.getRecentSearches());
    }

    @Override
    public void recentSearches(Vector<File> files) {
        for(File file : files){
            Object[] row = new Object[]{file.getName(), file.getAbsolutePath()};
            tableModel.addRow(row);
        }
    }
}
