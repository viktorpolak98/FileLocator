package Controller;

import Factories.DataStructureFactory;
import Interfaces.IDataStructure;
import View.StructureChooserGUI;

public class StartupController {
    private final int cacheSize;
    private IDataStructure dataStructure;
    private StructureChooserGUI structureChooserGUI;
    public StartupController(int cacheSize){
        structureChooserGUI = new StructureChooserGUI(this);
        this.cacheSize = cacheSize;
    }

    public void createDataStructure(String structure){
        dataStructure = DataStructureFactory.build(structure);
        startMainGUI();
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
