package com.example.travelguide;

public class WeatherData {
    private double temperature;
    private int humidity;
    private double windSpeed;
    private String condition;

    public WeatherData() {
        // Empty constructor needed for Firestore
    }

    public WeatherData(double temperature, int humidity, double windSpeed, String condition) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCondition() {
        return condition;
    }
}
