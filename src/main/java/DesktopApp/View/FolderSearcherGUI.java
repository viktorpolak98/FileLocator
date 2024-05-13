package main.java.DesktopApp.View;

import main.java.Controller.MainController;
import main.java.Interfaces.BuildingCallback;
import main.java.Interfaces.FileFoundCallback;
import main.java.Model.TableModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FolderSearcherGUI extends JFrame implements FileFoundCallback, BuildingCallback {
    private JTextField folderField;
    private TableModel tableModel;
    private final MainController controller;

    public FolderSearcherGUI(MainController controller) {
        this.controller = controller;
        Runtime.getRuntime().addShutdownHook(new Thread(controller::saveCache));

        setTitle("Folder Searcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = createMainPanel();
        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new TableModel(new String[]{"File Name", "Path"}, 0);
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);

        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBottomPanel(){
        JPanel returnPanel = new JPanel(new BorderLayout());

        JLabel queryLabel = new JLabel("Search Query:");
        JTextField queryField = new JTextField();

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String folderPath = folderField.getText();
            String query = queryField.getText();
            if (folderPath.isEmpty() || query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a folder path and search query");
                return;
            }

            tableModel.setRowCount(0);
            controller.search(query, this);
        });

        returnPanel.add(queryLabel, BorderLayout.WEST);
        returnPanel.add(queryField, BorderLayout.CENTER);
        returnPanel.add(searchButton, BorderLayout.EAST);

        return returnPanel;
    }

    private JPanel createTopPanel(){
        JPanel returnPanel = new JPanel(new BorderLayout());

        JLabel folderLabel = new JLabel("Select a folder: ");
        folderField = new JTextField();
        folderField.setEditable(false);
        folderField.setBorder(null);

        JButton folderSelectButton = new JButton("Folder");
        folderSelectButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION){
                File rootFile = chooser.getSelectedFile();
                if (rootFile.isDirectory()){
                    controller.createDataStructure(rootFile, this);
                }
            }

        });

        returnPanel.add(folderLabel, BorderLayout.WEST);
        returnPanel.add(folderField, BorderLayout.CENTER);
        returnPanel.add(folderSelectButton, BorderLayout.EAST);

        return returnPanel;
    }

    @Override
    public void onFileFound(File file) {
        SwingUtilities.invokeLater(() -> {
            Object[] row = new Object[]{file.getName(), file.getAbsolutePath()};
            tableModel.addRow(row);
        });
    }

    @Override
    public synchronized void onBuilding(String name){
        SwingUtilities.invokeLater(() -> folderField.setText(name));
    }
}