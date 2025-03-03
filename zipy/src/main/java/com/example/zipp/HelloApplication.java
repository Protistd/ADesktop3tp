package com.example.zipp;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button compressButton = new Button("Kompresuj pliki");
        Button decompressButton = new Button("Dekompresuj plik ZIP");

        compressButton.setOnAction(e -> compressFiles(primaryStage));
        decompressButton.setOnAction(e -> decompressFile(primaryStage));

        VBox root = new VBox(10, compressButton, decompressButton);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 150);
        primaryStage.setTitle("Narzędzie ZIP");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void compressFiles(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz pliki do kompresji");
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if (files != null) {
            FileChooser saveDialog = new FileChooser();
            saveDialog.setTitle("Zapisz plik ZIP");
            saveDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archiwum ZIP", "*.zip"));
            File zipFile = saveDialog.showSaveDialog(stage);

            if (zipFile != null) {
                try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
                    for (File file : files) {
                        FileInputStream fis = new FileInputStream(file);
                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        zipOut.putNextEntry(zipEntry);

                        byte[] bytes = new byte[1024];
                        int length;
                        while ((length = fis.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                        fis.close();
                    }
                    showAlert(AlertType.INFORMATION, "Sukces", "Pliki zostały skompresowane pomyślnie!");
                } catch (IOException ex) {
                    showAlert(AlertType.ERROR, "Błąd", "Wystąpił błąd podczas kompresji plików.");
                    ex.printStackTrace();
                }
            }
        }
    }

    private void decompressFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik ZIP do dekompresji");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archiwum ZIP", "*.zip"));
        File zipFile = fileChooser.showOpenDialog(stage);

        if (zipFile != null) {
            FileChooser dirChooser = new FileChooser();
            dirChooser.setTitle("Wybierz folder docelowy");
            File outputDir = dirChooser.showSaveDialog(stage);

            if (outputDir != null) {
                try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
                    ZipEntry zipEntry;
                    byte[] buffer = new byte[1024];
                    while ((zipEntry = zis.getNextEntry()) != null) {
                        File newFile = new File(outputDir.getParent(), zipEntry.getName());
                        new File(newFile.getParent()).mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                    showAlert(AlertType.INFORMATION, "Sukces", "Plik ZIP został pomyślnie zdekompresowany!");
                } catch (IOException ex) {
                    showAlert(AlertType.ERROR, "Błąd", "Wystąpił błąd podczas dekompresji pliku.");
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
