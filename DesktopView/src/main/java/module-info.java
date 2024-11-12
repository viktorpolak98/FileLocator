module com.DesktopView {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.FileLocator.Logic;

    opens DesktopView to javafx.fxml;
    exports DesktopView;
    exports Controller;
    opens Controller to javafx.fxml;
}