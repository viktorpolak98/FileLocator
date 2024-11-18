package Controller;

import LogicInterfaces.FileFoundCallback;
import LogicInterfaces.IDataStructure;
import ViewModel.TableViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;

public class MainViewController implements FileFoundCallback {
    private IDataStructure dataStructure;
    @FXML
    TextField fileSearchField;
    @FXML
    TableView<TableViewModel> resultView;

    public void setDataStructure(IDataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    public void onRootSelectionAction(ActionEvent actionEvent) {
    }

    public void onSearchFileAction(ActionEvent actionEvent) {
    }

    @Override
    public void onFileFound(File file) {

    }
}
