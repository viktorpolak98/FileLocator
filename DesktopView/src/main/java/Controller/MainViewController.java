package Controller;

import LogicInterfaces.IDataStructure;

public class MainViewController {
    private IDataStructure dataStructure;

    public void setDataStructure(IDataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }
}
