package Controller;

import Factories.DataStructureFactory;
import Interfaces.IDataStructure;
import Model.AvailableStructures;
import View.StructureChooserGUI;

public class StartupController {
    private final int cacheSize;
    private IDataStructure dataStructure;
    public StartupController(int cacheSize){
        new StructureChooserGUI(this);
        this.cacheSize = cacheSize;
    }

    public void createDataStructure(AvailableStructures structure){
        dataStructure = DataStructureFactory.build(structure);
    }

    public boolean startMainGUI(){
        if (dataStructure == null) {
            return false;
        }
        new MainController(cacheSize, dataStructure);
        return true;
    }
}
