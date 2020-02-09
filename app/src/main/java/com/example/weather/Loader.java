package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.weather.model.WeatherRequest;
import com.example.weather.ui.add.AddFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

public class Loader implements Constants {
//    private static final String WEATHER_API_KEY = "50827a7213ada81bde07134eec5501f3";
    private static final String WEATHER_API_KEY = "0240835eed185923190f675bf4e672cc";
    private static final String WEATHER_URL_CITY = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=ru&appid=" + WEATHER_API_KEY + "&q=";
    private static final String WEATHER_URL_CITY_HOURLY = "https://samples.openweathermap.org/data/2.5/forecast/hourly?London,us&appid=" + WEATHER_API_KEY + "&q=";

    private DataHandler dataHandler;

    public void getData(final Activity context, final View view, final String currentCity)
    {
        dataHandler = new DataHandler();

        try {
            final URL uri = new URL(WEATHER_URL_CITY + currentCity);
            final Handler handler = new Handler();

            Log.d("reload: ", "1. Start reload procedure");
            new Thread(new Runnable() {
                public void run() {
                    Looper.prepare();

                    Log.d("reload: ", "2. Start thread");
                    HttpsURLConnection urlConnection = null;
                    try {
                        Log.d("reload: ", "3. Start connection");
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);

                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        final WeatherRequest weatherRequest = dataHandler.getData(in);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                dataHandler.setData(context, view, weatherRequest);

                                Log.d("test: ", String.valueOf(weatherRequest.getMain().getTemp()));
                            }
                        });
                    } catch (FileNotFoundException e) {
                        if (context != null) showDialog(context, context.getResources().getString(R.string.alert_error_city));

                        Log.d("error: ", "City not found", e);
//                        e.printStackTrace();
                    } catch (Exception e) {
                        if (context != null) showDialog(context, context.getResources().getString(R.string.alert_error_connection));
                        Log.d("error: ", "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        Log.d("reload: ", "6. Close connection");
                        if (null != urlConnection) urlConnection.disconnect();
                    }

                    Looper.loop();
                }
            }).start();
        } catch (MalformedURLException e) {
            if (context != null) showDialog(context, context.getResources().getString(R.string.alert_error_uri));
            Log.d("error: ","Fail URI");
        }
    }

    private void showDialog(final Context context, final String message) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.alert_header_error)
                    .setMessage(message)
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_button_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            Toast.makeText(context, context.getResources().getString(R.string.alert_header_error), Toast.LENGTH_SHORT).show();
        }
    }
}
