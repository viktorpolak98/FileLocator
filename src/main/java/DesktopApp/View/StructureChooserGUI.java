package main.java.DesktopApp.View;

import main.java.Controller.StartupController;
import main.java.Model.AvailableStructures;

import javax.swing.*;
import java.awt.*;

public class StructureChooserGUI extends JFrame {
    private StartupController controller;
    private ButtonGroup buttonGroup;

    public StructureChooserGUI(StartupController controller){
        this.controller = controller;
        setTitle("Choose Structure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 400);
        setMinimumSize(new Dimension(250, 400));
        add(new JScrollPane(createMainPanel()), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel(){
        JLabel label = new JLabel("Choose a structure");
        JPanel panel = new JPanel();

        panel.add(label);

        buttonGroup = new ButtonGroup();
        populateJList(panel, buttonGroup);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        return panel;
    }

    private JPanel createBottomPanel(){
        JPanel panel = new JPanel();
        JButton btn = new JButton("Create");
        btn.addActionListener(e -> {
            if (buttonGroup.getSelection() != null) {
                controller.createDataStructure(buttonGroup.getSelection().getActionCommand());
            }
        });
        panel.add(btn);

        return panel;
    }

    private void populateJList(JPanel panel, ButtonGroup buttonGroup){
        for (AvailableStructures structure : AvailableStructures.values()){
            JRadioButton jRadioButton = new JRadioButton(structure.name(), false);
            jRadioButton.setActionCommand(structure.name());
            panel.add(jRadioButton);
            buttonGroup.add(jRadioButton);
        }
    }

}
