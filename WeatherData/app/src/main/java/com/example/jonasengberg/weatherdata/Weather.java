package com.example.jonasengberg.weatherdata;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather
{
    private double temp;
    private double wind;
    private Date sunset;
    private Date sunrise;

    public Weather(double temp, double wind, Date sunset, Date sunrise){
        this.temp = temp;
        this.wind = wind;
        this.sunset = sunset;
        this.sunrise = sunrise;
    }

    public String[] getAsArray()
    {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        String[] values = {"Temperature: "  + String.valueOf(temp), "Windspeed: " + String.valueOf(wind) + " m/s", "Sunset: " + format.format(sunrise), "Sunrise: " + format.format(sunset)};

        return values;
    }

}
