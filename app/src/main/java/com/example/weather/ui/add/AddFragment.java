package com.example.weather.ui.add;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.example.weather.Constants;
import com.example.weather.Loader;
import com.example.weather.Parcel;
import com.example.weather.R;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AddFragment extends Fragment implements Constants {
//    private static final String WEATHER_API_KEY = "50827a7213ada81bde07134eec5501f3";
    private static final String WEATHER_API_KEY = "0240835eed185923190f675bf4e672cc";
    private static final String WEATHER_URL_CITY = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=ru&appid=" + WEATHER_API_KEY + "&q=";

    private Loader loader;
    private TextInputEditText inputAddCity;
    private Pattern checkCity = Pattern.compile("^[a-zA-Zа-яё]{2,}$");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader       = new Loader();
        inputAddCity = view.findViewById(R.id.input_add_city);
        Button buttonCityAdd = view.findViewById(R.id.button_add_city);
        Button cancel = view.findViewById(R.id.button_cancel_city);

        buttonCityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Editable c = inputAddCity.getText();

            if (c != null) {
                String city = c.toString();
                if (!city.equals("")) {
                    city = firstUpperCase(city);
                    Boolean check = validate(inputAddCity, checkCity, "Русские/английские буквы, не менее 2-х!");
                    if (check) {
                        getWeatherByCityName(city, inputAddCity);
                    }
                }
            }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        });
    }

    private void getWeatherByCityName(final String currentCity, final TextInputEditText inputAddCity)
    {
        try {
            final URL uri = new URL(WEATHER_URL_CITY + currentCity);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        getLines(in);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                saveSettings(currentCity);
                                Parcel parcel = new Parcel();
                                parcel.city = currentCity;
                                showCities(parcel);
                            }
                        });
                    } catch (FileNotFoundException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showError(inputAddCity, getResources().getString(R.string.alert_error_city));
                            }
                        });
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void showCities(Parcel parcel) {
        Intent intent = new Intent();
        intent.putExtra(CITY, parcel);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
    }

    private void saveSettings(String value) {
        Activity activity = getActivity();

        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putString(CITY, value);
            ed.apply();
            Toast.makeText(getActivity(), CITY + " - " + value, Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean validate(TextView tv, Pattern check, String message){
        String value = tv.getText().toString();
        if (check.matcher(value).matches()){
            hideError(tv);
            return true;
        } else {
            showError(tv, message);
            return false;
        }
    }

    private void showError(final TextView view, String message) {
        view.setError(message);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            showDialog(activity, view);
        }
    }

    private void hideError(TextView view) {
        view.setError(null);
    }

    private void showDialog(FragmentActivity activity, final TextView view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.alert_header_error)
                .setMessage(R.string.alert_message_city)
                .setIcon(R.mipmap.ic_launcher_round)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_button_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hideError(view);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        Toast.makeText(activity, getResources().getString(R.string.alert_error_city), Toast.LENGTH_SHORT).show();
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private String firstUpperCase(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
