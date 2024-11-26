package Controller;

import LogicInterfaces.BuildingCallback;
import LogicInterfaces.FileFoundCallback;
import LogicInterfaces.IDataStructure;
import ViewModel.TableViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainViewController implements FileFoundCallback, BuildingCallback {
    private IDataStructure dataStructure;
    private Stage stage;
    @FXML
    private TextField fileSearchField;
    @FXML
    private TableView<TableViewModel> resultView;
    @FXML
    private TableColumn<TableViewModel, String> fileNameColumn;
    @FXML
    private TableColumn<TableViewModel, String> fileLocationColumn;
    private final ObservableList<TableViewModel> tableData = FXCollections.observableArrayList();
    @FXML
    private Label rootFolderLabel;

    @FXML
    public void initialize() {
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileLocationColumn.setCellValueFactory(new PropertyValueFactory<>("fileLocation"));
        resultView.setItems(tableData);
    }

    public void setDataStructure(IDataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    public void onRootSelectionAction() {
        DirectoryChooser chooser = new DirectoryChooser();
        File root = chooser.showDialog(stage);

        if (root == null){
            return;
        }

        dataStructure.build(root, this);
    }

    public void onSearchFileAction() {
        if(dataStructure.getRoot() == null){
            return;
        }
        tableData.clear();
        dataStructure.search(fileSearchField.getText(), this);
    }

    @Override
    public synchronized void onFileFound(File file) {
        Platform.runLater(() -> tableData.add(new TableViewModel(file.getName(), file.getAbsolutePath())));
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @Override
    public void onBuilding(String name) {
        rootFolderLabel.setText(name);
    }
}
