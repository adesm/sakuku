package com.example.android.iak3_bproject.entity;

/**
 * Created by adesm on 04/02/18.
 */

public class Cuaca2 {

    private String date;
    private String temp;
    private String weather;

    public Cuaca2(String date, String temp, String weather) {
        this.date = date;
        this.temp = temp;
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
