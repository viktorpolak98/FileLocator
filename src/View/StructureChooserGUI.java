package View;

import Controller.StartupController;
import Model.AvailableStructures;

import javax.swing.*;
import java.awt.*;

//TODO: Implement chooser for structure, call controller.setStructure after choice
public class StructureChooserGUI extends JFrame {
    StartupController controller;

    public StructureChooserGUI(StartupController controller){
        this.controller = controller;
        setTitle("Choose Structure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 400);
        setMinimumSize(new Dimension(250, 400));
        add(new JScrollPane(createPanel()));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createPanel(){
        JLabel label = new JLabel("Choose a structure");
        JPanel panel = new JPanel();

        panel.add(label);

        ButtonGroup buttonGroup = new ButtonGroup();
        populateJList(panel, buttonGroup);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        return panel;
    }

    private void populateJList(JPanel panel, ButtonGroup buttonGroup){
        for (AvailableStructures structure : AvailableStructures.values()){
            JRadioButton jRadioButton = new JRadioButton(structure.name(), false);
            panel.add(jRadioButton);
            buttonGroup.add(jRadioButton);
        }
    }

}
