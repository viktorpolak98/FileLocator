package View;

import Controller.StartupController;
import Model.AvailableStructures;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

//TODO: Implement chooser for structure, call controller.setStructure after choice
//TODO: Change JList to panels with JRadioButtons
public class StructureChooserGUI extends JFrame {
    StartupController controller;

    public StructureChooserGUI(StartupController controller){
        this.controller = controller;
        setTitle("Choose Structure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setMinimumSize(new Dimension(300, 400));
        add(new JScrollPane(createPanel()));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        DefaultListModel<AvailableStructures> model = new DefaultListModel<>();
        populateJList(model);
        JList<AvailableStructures> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return panel;
    }

    private void populateJList(DefaultListModel<AvailableStructures> model){
        for (AvailableStructures structure : AvailableStructures.values()){
            model.addElement(structure);
            System.out.println(model.getSize());
        }
    }

}
