module com.example.zipp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zipp to javafx.fxml;
    exports com.example.zipp;
}