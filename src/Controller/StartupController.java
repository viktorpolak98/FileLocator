package Controller;

import Factories.DataStructureFactory;
import Interfaces.IDataStructure;
import DesktopApp.View.StructureChooserGUI;

import javax.swing.*;

public class StartupController {
    private final int cacheSize;
    private IDataStructure dataStructure;
    private final StructureChooserGUI structureChooserGUI;
    public StartupController(int cacheSize){
        structureChooserGUI = new StructureChooserGUI(this);
        this.cacheSize = cacheSize;
    }

    public void createDataStructure(String structure){
        dataStructure = DataStructureFactory.build(structure);
        if (!startMainGUI()){
            JOptionPane.showMessageDialog(new JFrame(), "Please select an option");
        }
    }

    public boolean startMainGUI(){
        if (dataStructure == null) {
            return false;
        }
        new MainController(cacheSize, dataStructure);
        structureChooserGUI.dispose();
        return true;
    }
}
