package com.example.weather.ui.settings;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;
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
                saveSettings(HUMIDITY, humidityContainer.isChecked());
            }
        });

        windContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(WIND, windContainer.isChecked());
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

    private void saveSettings(String name, Boolean value) {
        Activity activity = getActivity();

        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putBoolean(name, value);
            if (name.equals(IS_DARK_THEME)) {
                ed.putBoolean(THEME_CHANGED, true);
            }
            ed.apply();
            Toast.makeText(getActivity(), name + " - " + value, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkState() {
        Activity activity = getActivity();

        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            humidityContainer.setChecked(sharedPreferences.getBoolean(HUMIDITY, true));
            windContainer.setChecked(sharedPreferences.getBoolean(WIND, true));

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