package com.example.weather.ui.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.weather.Constants;
import com.example.weather.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment implements Constants {
    private CheckBox humidityContainer;
    private CheckBox windContainer;
    private RadioButton darkTheme;
    private RadioButton lightTheme;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        humidityContainer = view.findViewById(R.id.settings_humidity);
        windContainer = view.findViewById(R.id.settings_wind);
        darkTheme = view.findViewById(R.id.settings_theme_dark);
        lightTheme = view.findViewById(R.id.settings_theme_light);

        checkState();

        humidityContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(HUMIDITY_CONTAINER, humidityContainer.isChecked());
                showDialog(getResources().getString(R.string.alert_message_reboot));
            }
        });

        windContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(WIND_CONTAINER, windContainer.isChecked());
                showDialog(getResources().getString(R.string.alert_message_reboot));
            }
        });

        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(IS_DARK_THEME, true);
                Activity activity = getActivity();
                if (activity != null) activity.recreate();
            }
        });

        lightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(IS_DARK_THEME, false);
                Activity activity = getActivity();
                if (activity != null) activity.recreate();
            }
        });
    }

    private void showDialog(final String message) {
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.alert_header_warning)
                    .setMessage(message)
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_button_understand,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            Toast.makeText(activity, getResources().getString(R.string.alert_header_warning), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSettings(String name, Boolean value) {
        Activity activity = getActivity();

        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putBoolean(name, value);

            if (name.equals(IS_DARK_THEME)) {
                ed.putBoolean(THEME_CHANGED, true);
            }

            if (name.equals(WIND_CONTAINER) || name.equals(HUMIDITY_CONTAINER)) {
                ed.putBoolean(VISIBILITY_CHANGED, true);
            }

            ed.apply();
            Toast.makeText(getActivity(), name + " - " + value, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), VISIBILITY_CHANGED + " - " + true, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkState() {
        Activity activity = getActivity();

        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            humidityContainer.setChecked(sharedPreferences.getBoolean(HUMIDITY_CONTAINER, true));
            windContainer.setChecked(sharedPreferences.getBoolean(WIND_CONTAINER, true));

            if (sharedPreferences.getBoolean(IS_DARK_THEME, true)) {
                darkTheme.setChecked(true);
                lightTheme.setChecked(false);
            } else {
                darkTheme.setChecked(false);
                lightTheme.setChecked(true);
            }
        }
    }
}