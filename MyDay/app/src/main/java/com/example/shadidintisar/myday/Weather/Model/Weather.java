package com.example.shadidintisar.myday.Weather.Model;

public class Weather {
    private int id;
    private String main;
    private String descriptionn;
    private String icon;

    public Weather(int id, String main, String descriptionn, String icon) {
        this.id = id;
        this.main = main;
        this.descriptionn = descriptionn;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescriptionn() {
        return descriptionn;
    }

    public String getIcon() {
        return icon;
    }
}
