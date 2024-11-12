module com.example.kostki {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kostki to javafx.fxml;
    exports com.example.kostki;
}