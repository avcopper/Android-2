package com.example.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weather.model.WeatherRequest;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.stream.Collectors;

public class DataHandler extends AppCompatActivity implements Constants {
    public int generateNumber(int min, int max) {
        Random rnd = new Random(System.currentTimeMillis());
        return (min + rnd.nextInt(max - min + 1));
    }

    WeatherRequest getData(BufferedReader in)
    {
        String result = (new DataHandler()).getLines(in);
        Gson gson = new Gson();
        return gson.fromJson(result, WeatherRequest.class);
    }

    String getLines(BufferedReader in)
    {
        Log.d("reload: ", "4. BufferedReader");
        return in.lines().collect(Collectors.joining("\n"));
    }

    void setData(Context context, View view, WeatherRequest weatherRequest)
    {
        TextView cityContainer        = view.findViewById(R.id.fragment_main_city);
        TextView weatherContainer     = view.findViewById(R.id.fragment_main_weather);
        TextView tempCurrentContainer = view.findViewById(R.id.fragment_main_temp_curr);
        TextView tempRangeContainer   = view.findViewById(R.id.fragment_main_temp_range);
        TextView humidityValue        = view.findViewById(R.id.main_humidity_value);
        TextView windValue            = view.findViewById(R.id.main_speed_value);
        TextView windDirection        = view.findViewById(R.id.main_direction);
        TextView updated              = view.findViewById(R.id.updated);

        String currentCity = weatherRequest.getName();

        Log.d("reload: ", "5. setData");
        int temp = Math.round(weatherRequest.getMain().getTemp());
        String tempData = (temp > 0 ? "+" + temp : temp) + context.getResources().getString(R.string.unit_deg);

        int tempMin = Math.round(weatherRequest.getMain().getTemp_min());
        String temperatureMin = (tempMin > 0 ? "+" + tempMin : tempMin) + context.getResources().getString(R.string.unit_deg);
        int tempMax = Math.round(weatherRequest.getMain().getTemp_max());
        String temperatureMax = (tempMax > 0 ? "+" + tempMax : tempMax) + context.getResources().getString(R.string.unit_deg);
        String tempRange = temperatureMin + " / " + temperatureMax;

        int humidity = Math.round(weatherRequest.getMain().getHumidity());
        String humidityData = humidity + context.getResources().getString(R.string.unit_percent);
        String speedData = Math.round(weatherRequest.getWind().getSpeed()) + " " + context.getResources().getString(R.string.unit_speed);

        int degrees = weatherRequest.getWind().getDeg();
        String direction = getWindDirection(context, degrees);

        String weather = getCloudy(context, weatherRequest.getClouds().getAll(), humidity, temp);
        String date = MessageFormat.format("{0}{1}{2}", context.getResources().getString(R.string.updated), " ", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));

        Parcel parcel = new Parcel();
        parcel.tempCurrent = tempData;
        parcel.tempRange = tempRange;
        parcel.humidity = humidityData;
        parcel.wind = speedData;
        parcel.wind_direction = direction;
        parcel.weather = weather;
        parcel.updated = date;

        saveSettings(context, parcel);

        cityContainer.setText(currentCity);
        tempCurrentContainer.setText(tempData);
        tempRangeContainer.setText(tempRange);
        humidityValue.setText(humidityData);
        windValue.setText(speedData);
        windDirection.setText(direction);
        weatherContainer.setText(weather);
        updated.setText(date);
    }

    private String getWindDirection(Context context, float degrees)
    {
        String wind = "";
        if (degrees > 337.5 && degrees <= 22.5) {
            wind = context.getResources().getString(R.string.north);
        } else if (degrees > 22.5 && degrees <= 67.5) {
            wind = context.getResources().getString(R.string.north_east);
        } else if (degrees > 67.5 && degrees <= 112.5) {
            wind = context.getResources().getString(R.string.east);
        } else if (degrees > 112.5 && degrees <= 157.5) {
            wind = context.getResources().getString(R.string.south_east);
        } else if (degrees > 157.5 && degrees <= 202.5) {
            wind = context.getResources().getString(R.string.south);
        } else if (degrees > 202.5 && degrees <= 247.5) {
            wind = context.getResources().getString(R.string.south_west);
        } else if (degrees > 247.5 && degrees <= 292.5) {
            wind = context.getResources().getString(R.string.west);
        } else if (degrees > 292.5 && degrees <= 337.5) {
            wind = context.getResources().getString(R.string.south_west);
        }
        return wind;
    }

    private String getCloudy(Context context, int clouds, int humidity, int temp)
    {
        String weather = "";
        if (humidity > 90) {
            if (temp > 0) {
                weather = context.getResources().getString(R.string.rain);
            } else {
                weather = context.getResources().getString(R.string.snow);
            }
        } else if (clouds >= 60) {
            weather = context.getResources().getString(R.string.cloudy);
        } else if (clouds < 60 && clouds >= 30) {
            weather = context.getResources().getString(R.string.party_cloudy);
        } else if (clouds < 30) {
            weather = context.getResources().getString(R.string.sunny);
        }
        return weather;
    }

    void saveSettings(Context context, Parcel parcel) {
        if (context != null && parcel != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();

            if (parcel.city != null) ed.putString(CITY, parcel.city);
            if (parcel.tempCurrent != null) ed.putString(TEMP_CURRENT, parcel.tempCurrent);
            if (parcel.tempRange != null) ed.putString(TEMP_RANGE, parcel.tempRange);
            if (parcel.humidity != null) ed.putString(HUMIDITY, parcel.humidity);
            if (parcel.wind != null) ed.putString(WIND, parcel.wind);
            if (parcel.wind_direction != null) ed.putString(WIND_DIRECTION, parcel.wind_direction);
            if (parcel.weather != null) ed.putString(WEATHER, parcel.weather);
            if (parcel.updated != null) ed.putString(UPDATED, parcel.updated);

            ed.apply();
        }
    }
}
