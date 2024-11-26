package com.example.pliki;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main extends Application {

    public static List<String> readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

    public static void writeFile(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, lines);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField fileNameField = new TextField();
        TextArea textArea = new TextArea();
        Button loadButton = new Button("Otwórz plik");
        Button saveButton = new Button("Zapisz plik");
        Button exitButton = new Button("Wyłącz aplikacje");

        HBox fileBox = new HBox(10, new Label("Plik:"), fileNameField);
        HBox buttonBox = new HBox(10, loadButton, saveButton, exitButton);
        VBox root = new VBox(10, fileBox, textArea, buttonBox);
        root.setSpacing(10);
        root.setPadding(new javafx.geometry.Insets(10));

        loadButton.setOnAction(e -> {
            String fileName = fileNameField.getText().trim();
            if (fileName.isEmpty()) {
                return;
            }
            try {
                List<String> fileContent = readFile(fileName);
                textArea.setText(String.join("\n", fileContent));
            } catch (IOException ex) {
            }
        });

        saveButton.setOnAction(e -> {
            String fileName = fileNameField.getText().trim();
            if (fileName.isEmpty()) {
                return;
            }
            try {
                List<String> fileContent = List.of(textArea.getText().split("\n"));
                writeFile(fileName, fileContent);
            } catch (IOException ex) {
            }
        });

        exitButton.setOnAction(e ->{primaryStage.close();});

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Edytor Plików");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
