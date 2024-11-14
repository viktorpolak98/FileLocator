package Controller;

import LogicInterfaces.IDataStructure;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainViewController {
    private IDataStructure dataStructure;
    @FXML
    TextField fileSearchField;

    public void setDataStructure(IDataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    public void onRootSelectionAction(ActionEvent actionEvent) {
    }

    public void onSearchFileAction(ActionEvent actionEvent) {
    }
}
