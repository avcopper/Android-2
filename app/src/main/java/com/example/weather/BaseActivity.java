package com.example.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPref = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        return sharedPref.getBoolean(IS_DARK_THEME, true);
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_DARK_THEME , isDarkTheme);
        editor.apply();
    }
}
