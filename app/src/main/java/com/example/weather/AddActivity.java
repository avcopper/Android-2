package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;

public class AddActivity extends BaseActivity implements Constants {
    Intent intent;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.menu_add));
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
        switch (item.getItemId()) {

            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_author:
                Snackbar.make(toolbar, "Автор: @andrew", Snackbar.LENGTH_LONG)
                        .setAction("Закрыть", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AddActivity.this.getApplicationContext(), "Snackbar закрыт", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;

//            case R.id.action_history:
//                intent = new Intent(this, HistoryActivity.class);
//                startActivity(intent);
//                break;

            case android.R.id.home:
            case R.id.action_exit:
                finish();
                break;

            default:
                Toast.makeText(getApplicationContext(), getString(R.string.action_not_found), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}