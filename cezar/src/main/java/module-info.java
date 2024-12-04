module com.example.cezar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cezar to javafx.fxml;
    exports com.example.cezar;
}