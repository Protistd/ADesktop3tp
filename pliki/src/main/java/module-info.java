module com.example.pliki {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pliki to javafx.fxml;
    exports com.example.pliki;
}