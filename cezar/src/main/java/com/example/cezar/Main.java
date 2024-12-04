package com.example.cezar;

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
    public static String cezarRoz(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char i : text.toCharArray()) {
            if (Character.isLetter(i)) {
                char base = (Character.isLowerCase(i)) ? 'a' : 'A';
                result.append((char) ((i - base + shift) % 26 + base));
            } else {
                result.append(i);
            }
        }
        return result.toString();
    }
    public static String caesarDecipher(String text, int shift) {
        return cezarRoz(text, 26 - (shift % 26));
    }
    @Override
    public void start(Stage primaryStage) {
        TextField fileNameField = new TextField();
        TextArea textArea = new TextArea();
        TextField keyField = new TextField();
        Button loadButton = new Button("Otwórz plik");
        Button saveButton = new Button("Zapisz plik");
        Button encryptButton = new Button("Zaszyfruj");
        Button decryptButton = new Button("Deszyfruj");
        Button exitButton = new Button("Wyłącz aplikację");

        HBox fileBox = new HBox(10, new Label("Plik:"), fileNameField);
        HBox keyBox = new HBox(10, new Label("Klucz (liczba całkowita):"), keyField);
        HBox buttonBox = new HBox(10, loadButton, saveButton, encryptButton, decryptButton, exitButton);
        VBox root = new VBox(10, fileBox, keyBox, textArea, buttonBox);
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
                ex.printStackTrace();
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
                ex.printStackTrace();
            }
        });
        encryptButton.setOnAction(e -> {
            String text = textArea.getText();
            String keyText = keyField.getText();
            if (keyText.isEmpty()) {
                return;
            }
            try {
                int key = Integer.parseInt(keyText);
                String encryptedText = cezarRoz(text, key);
                textArea.setText(encryptedText);
            } catch (NumberFormatException ex) {
                showAlert("Błąd", "Wprowadź poprawny klucz (liczba całkowita).");
            }
        });
        decryptButton.setOnAction(e -> {
            String text = textArea.getText();
            String keyText = keyField.getText();
            if (keyText.isEmpty()) {
                return;
            }
            try {
                int key = Integer.parseInt(keyText);
                String decryptedText = caesarDecipher(text, key);
                textArea.setText(decryptedText);
            } catch (NumberFormatException ex) {
                showAlert("Błąd", "Wprowadź poprawny klucz (liczba całkowita).");
            }
        });
        exitButton.setOnAction(e -> primaryStage.close());
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Edytor Szyfrów");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
