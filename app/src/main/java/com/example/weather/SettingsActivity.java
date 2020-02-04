package com.example.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends BaseActivity implements Constants {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.menu_settings));
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_exit:
                finish();
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_author:
                Snackbar.make(toolbar, "Автор: @andrew", Snackbar.LENGTH_LONG)
                        .setAction("Закрыть", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SettingsActivity.this.getApplicationContext(), "Snackbar закрыт", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Parcel parcel = new Parcel();
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);

        parcel.humidityVisibility = sharedPreferences.getBoolean(HUMIDITY, true);
        parcel.windVisibility = sharedPreferences.getBoolean(WIND, true);
        showMain(parcel);
    }

    private void showMain(Parcel parcel) {
        Intent intent = new Intent();
//        intent.putExtra(CITY, inputAddCity.getText().toString());
        intent.putExtra(CITY, parcel);

        setResult(RESULT_OK, intent);
        finish();
    }
}