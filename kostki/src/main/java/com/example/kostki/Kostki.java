package com.example.kostki;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class Kostki extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("losowanie kostek");

        // UI Components
        Label promptLabel = new Label("ile kostek chcesz rzucic (3-10):");
        TextField diceInput = new TextField();
        Button rollButton = new Button("rzuc");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Event handler for roll button
        rollButton.setOnAction(e -> {
            try {
                int diceCount = Integer.parseInt(diceInput.getText());
                if (diceCount < 3 || diceCount > 10) {
                    resultArea.setText("wprowadz liczbe od 3 do 10");
                } else {
                    int totalScore = 0;
                    StringBuilder results = new StringBuilder("Wyniki:\n");
                    Random random = new Random();
                    for (int i = 1; i <= diceCount; i++) {
                        int roll = random.nextInt(6) + 1;
                        results.append("kostka ").append(i).append(": ").append(roll).append("\n");
                        totalScore += roll;
                    }
                    results.append("wynik: ").append(totalScore);
                    resultArea.setText(results.toString());
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Error, wprowadz liczbe od 3-10");
            }
        });

        // Layout
        VBox layout = new VBox(10, promptLabel, diceInput, rollButton, resultArea);
        Scene scene = new Scene(layout, 300, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}