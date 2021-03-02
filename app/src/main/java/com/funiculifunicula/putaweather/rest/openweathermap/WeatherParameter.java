package com.funiculifunicula.putaweather.rest.openweathermap;

public class WeatherParameter {
    private String name;
    private String value;

    public WeatherParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}