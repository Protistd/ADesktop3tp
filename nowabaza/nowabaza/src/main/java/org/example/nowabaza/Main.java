package org.example.nowabaza;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    private static final String URL = "jdbc:mysql://localhost:3306/school";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private ListView<String> studentsList = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Imię Nazwisko");
        TextField ageField = new TextField();
        ageField.setPromptText("Wiek");
        TextField gradeField = new TextField();
        gradeField.setPromptText("Ocena");

        Button addButton = new Button("dodaj");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String grade = gradeField.getText();
            addStudent(name, age, grade);
            nameField.clear();
            ageField.clear();
            gradeField.clear();
            refreshStudents();
        });

        Button updateButton = new Button("aktualizuj");
        updateButton.setOnAction(e -> {
            String selectedStudent = studentsList.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                int id = Integer.parseInt(selectedStudent.split(",")[0].split(":")[1].trim());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String grade = gradeField.getText();
                updateStudent(id, name, age, grade);
                refreshStudents();
            }
        });

        Button deleteButton = new Button("usun");
        deleteButton.setOnAction(e -> {
            String selectedStudent = studentsList.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                int id = Integer.parseInt(selectedStudent.split(",")[0].split(":")[1].trim());
                deleteStudent(id);
                refreshStudents();
            }
        });

        Button refreshButton = new Button("odswiez");
        refreshButton.setOnAction(e -> refreshStudents());

        TextField tableNameField = new TextField();
        tableNameField.setPromptText("Nazwa tabeli");
        TextField columnField = new TextField();
        columnField.setPromptText("Nazwa kolumny i typ");

        Button createTableButton = new Button("stwórz tabelę");
        createTableButton.setOnAction(e -> {
            String tableName = tableNameField.getText();
            String columnDefinition = columnField.getText();
            createTable(tableName, columnDefinition);
            tableNameField.clear();
            columnField.clear();
        });

        vbox.getChildren().addAll(
                nameField, ageField, gradeField, addButton, updateButton, deleteButton, refreshButton, studentsList,
                tableNameField, columnField, createTableButton
        );

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setTitle("zagorowski");
        primaryStage.setScene(scene);
        primaryStage.show();

        refreshStudents();
    }

    private void addStudent(String name, int age, String grade) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String insertSQL = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.setString(3, grade);
                statement.executeUpdate();
                System.out.println("Dodano studenta: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent(int id, String name, int age, String grade) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String updateSQL = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.setString(3, grade);
                statement.setInt(4, id);
                statement.executeUpdate();
                System.out.println("Zaktualizowano studenta o ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String deleteSQL = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
                statement.setInt(1, id);
                statement.executeUpdate();
                System.out.println("Usunięto studenta o ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshStudents() {
        studentsList.getItems().clear();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String grade = resultSet.getString("grade");
                studentsList.getItems().add("ID: " + id + ", Imię: " + name + ", Wiek: " + age + ", Ocena: " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(String tableName, String columnDefinition) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String createSQL = "CREATE TABLE " + tableName + " (" + columnDefinition + ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createSQL);
                System.out.println("Stworzono tabelę: " + tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
