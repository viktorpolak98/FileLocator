module com.DesktopView {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.FileLocator.Logic;

    opens DesktopView to javafx.fxml;
    opens Controller to javafx.fxml;
    opens ViewModel to javafx.base;

    exports DesktopView;
    exports Controller;
}