package com.example.shadidintisar.myday.Weather.Model;

public class OpenWeatherMap {

    private Coord coord;
    private Weather weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private String name;

    public OpenWeatherMap(Coord coord, Weather weather, Main main, Wind wind, Clouds clouds, Sys sys, String name) {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.sys = sys;
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public Weather getWeather() {
        return weather;
    }


    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }
}
