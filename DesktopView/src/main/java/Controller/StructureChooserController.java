package Controller;

import LogicFactories.DataStructureFactory;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class StructureChooserController {
    private Stage stage;
    private Application application;
    @FXML
    ToggleGroup structureChooserGroup;

    @FXML
    public void onSelectButtonAction() throws IOException {
        RadioButton rBtn = (RadioButton)structureChooserGroup.getSelectedToggle();
        if (rBtn == null){
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(stage);
            VBox box = new VBox();
            box.setMinSize(200, 50);
            Text text = new Text("Please make a selection");
            text.setFont(Font.font(15));
            box.getChildren().add(text);
            box.setAlignment(Pos.CENTER);
            Scene scene = new Scene(box);
            popupStage.setScene(scene);
            popupStage.show();

            return;
        }

        String selection = rBtn.getText();
        selection = selection.substring(0, selection.indexOf(" ")).toLowerCase();

        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);

        MainViewController controller = loader.getController();
        controller.setDataStructure(DataStructureFactory.buildEmptyStructure(selection));

        stage.setTitle("FileLocator");
        stage.setScene(scene);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
