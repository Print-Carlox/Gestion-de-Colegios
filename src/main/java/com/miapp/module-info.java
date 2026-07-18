module com.miapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.miapp to javafx.fxml;
    exports com.miapp;
}
