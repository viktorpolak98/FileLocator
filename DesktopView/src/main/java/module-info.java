module com.DesktopView {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.FileLocator.Logic;

    opens com.view.view to javafx.fxml;
    exports com.view.view;
}