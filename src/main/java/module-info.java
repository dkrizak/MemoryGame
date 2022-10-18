module com.example.memorygame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.memorygame to javafx.fxml;
    exports com.example.memorygame;
}