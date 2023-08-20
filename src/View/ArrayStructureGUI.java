package View;

import Interfaces.IVisualizerGUI;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class ArrayStructureGUI extends JFrame implements IVisualizerGUI {

    public ArrayStructureGUI(List<File> rootList){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        ArrayVisualizer arrayVisualizer = new ArrayVisualizer(rootList);
        add(arrayVisualizer);
        setVisible(true);
    }

    private class ArrayVisualizer extends JPanel{
        public ArrayVisualizer(List<File> rootList){

        }

    }
}
