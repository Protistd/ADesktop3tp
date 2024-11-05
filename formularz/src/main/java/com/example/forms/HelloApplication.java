package com.example.forms;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        Text nameLabel = new Text("Name");
        TextField nameText = new TextField();
        Text dobLabel = new Text("Date of birth");
        DatePicker datePicker = new DatePicker();
        Text genderLabel = new Text("Gender");
        ToggleGroup groupGender = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("Male");
        maleRadio.setToggleGroup(groupGender);
        RadioButton femaleRadio = new RadioButton("Female");
        femaleRadio.setToggleGroup(groupGender);
        Text reservationLabel = new Text("Reservation");
        ToggleButton yes = new ToggleButton("Yes");
        ToggleButton no = new ToggleButton("No");
        ToggleGroup groupReservation = new ToggleGroup();
        yes.setToggleGroup(groupReservation);
        no.setToggleGroup(groupReservation);
        Text technologiesLabel = new Text("Technologies Known");
        CheckBox javaCheckBox = new CheckBox("Java");
        CheckBox dotnetCheckBox = new CheckBox("DotNet");
        Text educationLabel = new Text("Educational Qualification");
        ObservableList<String> names = FXCollections.observableArrayList(
                "Engineering", "MCA", "MBA");
        ListView<String> educationListView = new ListView<>(names);
        Text locationLabel = new Text("Location");
        ChoiceBox<String> locationChoiceBox = new ChoiceBox<>();
        locationChoiceBox.getItems().addAll("Warszawa", "Krakow", "Poznań", "Mumbai", "Wrocław");
        Button buttonRegister = new Button("Register");

        buttonRegister.setOnAction(e -> {
            String imie = nameText.getText();
            String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "Not selected";
            String plec = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "Not selected";
            String rezer = yes.isSelected() ? "Yes" : no.isSelected() ? "No" : "Not selected";
            String techno = (javaCheckBox.isSelected() ? "Java " : "") + (dotnetCheckBox.isSelected() ? "DotNet" : "").trim();
            String edukacja = educationListView.getSelectionModel().getSelectedItem() != null ? educationListView.getSelectionModel().getSelectedItem() : "Not selected";
            String location = locationChoiceBox.getValue() != null ? locationChoiceBox.getValue() : "Not selected";

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy jesteś pewien?");
            alert.setContentText("Zebrane dane:\n" +
                    "Name: " + imie + "\n" +
                    "Date of Birth: " + date + "\n" +
                    "Gender: " + plec + "\n" +
                    "Reservation: " + rezer + "\n" +
                    "Technologies Known: " + techno + "\n" +
                    "Educational Qualification: " + edukacja + "\n" +
                    "Location: " + location);
            alert.showAndWait();
        });
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(500,260);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameText, 1, 0);
        gridPane.add(dobLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);
        gridPane.add(genderLabel, 0, 2);
        gridPane.add(maleRadio, 1, 2);
        gridPane.add(femaleRadio, 2, 2);
        gridPane.add(reservationLabel, 0, 3);
        gridPane.add(yes, 1, 3);
        gridPane.add(no, 2, 3);
        gridPane.add(technologiesLabel, 0, 4);
        gridPane.add(javaCheckBox, 1, 4);
        gridPane.add(dotnetCheckBox, 2, 4);
        gridPane.add(educationLabel, 0, 5);
        gridPane.add(educationListView, 1, 5);
        gridPane.add(locationLabel, 0, 6);
        gridPane.add(locationChoiceBox, 1, 6);
        gridPane.add(buttonRegister, 2, 8);

        gridPane.setStyle("-fx-background-color: BEIGE;");
        Scene scene = new Scene(gridPane);
        stage.setTitle("Formularz");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
