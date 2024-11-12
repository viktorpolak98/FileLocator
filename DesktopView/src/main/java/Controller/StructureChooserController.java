package Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StructureChooserController {
    private Stage stage;
    private Application application;
    @FXML
    public void onSelectButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(application.getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);
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
