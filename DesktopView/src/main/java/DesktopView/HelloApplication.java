package DesktopView;

import Controller.StructureChooserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("structure-chooser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);

        StructureChooserController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setApplication(this);

        stage.setTitle("Choose a structure");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}