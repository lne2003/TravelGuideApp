package com.example.travelguide;

public class WeatherstackResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public class Current {
        private double temperature;
        private int humidity;
        private double wind_speed;
        private String weather_descriptions[];

        public double getTemperature() {
            return temperature;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getWindSpeed() {
            return wind_speed;
        }

        public String getWeatherDescription() {
            return weather_descriptions != null && weather_descriptions.length > 0 ? weather_descriptions[0] : "";
        }
    }
}
