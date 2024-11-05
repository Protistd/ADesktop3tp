package com.example.kalkulator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kalkulator");
        ToggleGroup methodGroup = new ToggleGroup();
        RadioButton Nettobrutto = new RadioButton("Od netto do brutto");
        Nettobrutto.setToggleGroup(methodGroup);
        Nettobrutto.setSelected(true);
        RadioButton Bruttonetto = new RadioButton("Od brutto do netto");
        Bruttonetto.setToggleGroup(methodGroup);
        RadioButton Vat = new RadioButton("Dopasuj do kwoty VAT");
        Vat.setToggleGroup(methodGroup);
        VBox methodBox = new VBox(10);
        methodBox.getChildren().addAll(Nettobrutto, Bruttonetto, Vat);
        methodBox.setPadding(new Insets(10));

        Label baseValueLabel = new Label("Wartość bazowa:");
        TextField baseValueField = new TextField();
        Label vatRateLabel = new Label("Stawka VAT:");
        ComboBox<String> vatRateCombo = new ComboBox<>();
        vatRateCombo.getItems().addAll("23%", "8%", "5%");
        vatRateCombo.setValue("23%");

        GridPane dataGrid = new GridPane();
        dataGrid.setHgap(10);
        dataGrid.setVgap(10);
        dataGrid.add(baseValueLabel, 0, 0);
        dataGrid.add(baseValueField, 1, 0);
        dataGrid.add(vatRateLabel, 0, 1);
        dataGrid.add(vatRateCombo, 1, 1);
        dataGrid.setPadding(new Insets(10));
        Button calculateButton = new Button("OBLICZ");
        Button closeButton = new Button("Zamknij");
        HBox buttonBox = new HBox(10, calculateButton, closeButton);
        buttonBox.setPadding(new Insets(10));
        Label nettoLabel = new Label("Netto:");
        Label nettoValue = new Label();
        Label vatLabel = new Label("VAT:");
        Label vatValue = new Label();
        Label bruttoLabel = new Label("Brutto:");
        Label bruttoValue = new Label();

        GridPane resultGrid = new GridPane();
        resultGrid.setHgap(10);
        resultGrid.setVgap(10);
        resultGrid.add(nettoLabel, 0, 0);
        resultGrid.add(nettoValue, 1, 0);
        resultGrid.add(vatLabel, 0, 1);
        resultGrid.add(vatValue, 1, 1);
        resultGrid.add(bruttoLabel, 0, 2);
        resultGrid.add(bruttoValue, 1, 2);
        resultGrid.setPadding(new Insets(10));

        calculateButton.setOnAction(e -> {
            try {
                double baseValue = Double.parseDouble(baseValueField.getText());
                String vatRateText = vatRateCombo.getValue().replace("%", "");
                double vatRate = Double.parseDouble(vatRateText) / 100.0;

                if (Nettobrutto.isSelected()) {
                    double vatAmount = baseValue * vatRate;
                    double bruttoValueAmount = baseValue + vatAmount;
                    nettoValue.setText(String.format("%.2f", baseValue));
                    vatValue.setText(String.format("%.2f @ %s", vatAmount, vatRateCombo.getValue()));
                    bruttoValue.setText(String.format("%.2f", bruttoValueAmount));
                } else if (Bruttonetto.isSelected()) {
                    double nettoValueAmount = baseValue / (1 + vatRate);
                    double vatAmount = baseValue - nettoValueAmount;
                    nettoValue.setText(String.format("%.2f", nettoValueAmount));
                    vatValue.setText(String.format("%.2f @ %s", vatAmount, vatRateCombo.getValue()));
                    bruttoValue.setText(String.format("%.2f", baseValue));
                } else if (Vat.isSelected()) {
                    double vatAmount = baseValue;
                    double nettoValueAmount = vatAmount / vatRate;
                    double bruttoValueAmount = nettoValueAmount + vatAmount;
                    nettoValue.setText(String.format("%.2f", nettoValueAmount));
                    vatValue.setText(String.format("%.2f @ %s", vatAmount, vatRateCombo.getValue()));
                    bruttoValue.setText(String.format("%.2f", bruttoValueAmount));
                }
            } catch (NumberFormatException ex) {
                nettoValue.setText("Błąd");
                vatValue.setText("Błąd");
                bruttoValue.setText("Błąd");
            }
        });
        closeButton.setOnAction(e -> primaryStage.close());
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(methodBox, dataGrid, buttonBox, resultGrid);
        mainLayout.setPadding(new Insets(10));
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}