package com.example.pogoda;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pogoda extends Application {

    private static final String API_KEY = "bd3713822953ad4da2d6354c464b3a7f";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("API Pogody");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Text header = new Text("API Pogody");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField cityField = new TextField();
        cityField.setPromptText("Wpisz nazwę miasta");
        cityField.setPrefWidth(250);

        Button searchButton = new Button("Szukaj");

        Button closeButton = new Button("Zamknij");
        closeButton.setOnAction(e -> primaryStage.close());

        TextArea weatherData = new TextArea();
        weatherData.setEditable(false);
        weatherData.setWrapText(true);
        weatherData.setPrefHeight(200);

        ImageView weatherIcon = new ImageView();
        weatherIcon.setFitWidth(100);
        weatherIcon.setFitHeight(100);

        searchButton.setOnAction(e -> {
            String city = cityField.getText();
            if (city != null && !city.isEmpty()) {
                getWeatherData(city, weatherData, weatherIcon);
            } else {
                weatherData.setText("Proszę wprowadzić nazwę miasta.");
            }
        });

        root.getChildren().addAll(header, cityField, searchButton, weatherIcon, weatherData, closeButton);

        Scene scene = new Scene(root, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void getWeatherData(String city, TextArea weatherData, ImageView weatherIcon) {
        try {
            String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&lang=pl";
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            StringBuilder weatherInfo = new StringBuilder();

            if (jsonResponse.getInt("cod") == 200) {
                JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                String weatherDescription = weatherArray.getJSONObject(0).getString("description");
                String weatherIconCode = weatherArray.getJSONObject(0).getString("icon");

                String iconUrl = "https://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png";
                Image icon = new Image(iconUrl);
                weatherIcon.setImage(icon);

                JSONObject main = jsonResponse.getJSONObject("main");
                double temperature = main.getDouble("temp") - 273.15;

                JSONObject wind = jsonResponse.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");

                JSONObject clouds = jsonResponse.getJSONObject("clouds");
                int cloudiness = clouds.getInt("all");

                int visibility = jsonResponse.getInt("visibility");

                String cityName = jsonResponse.getString("name");

                // deszcz jesli dostepny
                String rainInfo = "Brak danych o opadach";
                if (jsonResponse.has("rain")) {
                    JSONObject rain = jsonResponse.getJSONObject("rain");
                    if (rain.has("1h")) {
                        rainInfo = "Opady w ciągu ostatniej godziny: " + rain.getDouble("1h") + " mm";
                    } else if (rain.has("3h")) {
                        rainInfo = "Opady w ciągu ostatnich 3 godzin: " + rain.getDouble("3h") + " mm";
                    }
                }

                // snieg jesli dostepny
                String snowInfo = "Brak danych o opadach śniegu";
                if (jsonResponse.has("snow")) {
                    JSONObject snow = jsonResponse.getJSONObject("snow");
                    if (snow.has("1h")) {
                        snowInfo = "Opady śniegu w ciągu ostatniej godziny: " + snow.getDouble("1h") + " mm";
                    } else if (snow.has("3h")) {
                        snowInfo = "Opady śniegu w ciągu ostatnich 3 godzin: " + snow.getDouble("3h") + " mm";
                    }
                }

                weatherInfo.append("Miasto: ").append(cityName).append("\n");
                weatherInfo.append("Pogoda: ").append(weatherDescription).append("\n");
                weatherInfo.append("Temperatura: ").append(String.format("%.2f", temperature)).append(" °C\n");
                weatherInfo.append("Wiatr: ").append(windSpeed).append(" m/s\n");
                weatherInfo.append("Zachmurzenie: ").append(cloudiness).append("%\n");
                weatherInfo.append("Widoczność: ").append(visibility / 1000).append(" km\n");
                weatherInfo.append("Opady: ").append(rainInfo).append("\n");
                weatherInfo.append("Opady śniegu: ").append(snowInfo).append("\n");

            } else {
                weatherInfo.append("Nie znaleziono danych dla podanego miasta.");
            }
            weatherData.setText(weatherInfo.toString());
        } catch (Exception e) {
            weatherData.setText("Błąd podczas pobierania danych: " + e.getMessage());
        }
    }
}
