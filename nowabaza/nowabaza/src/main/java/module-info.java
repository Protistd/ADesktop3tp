module org.example.nowabaza {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.nowabaza to javafx.fxml;
    exports org.example.nowabaza;
}
