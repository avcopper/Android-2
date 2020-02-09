package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.weather.recycler.RecyclerAdapterList;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Arrays;

public class HistoryActivity extends BaseActivity implements Constants {
    private final static int REQUEST_CODE = 3;
    private Toolbar toolbar;
    private RecyclerAdapterList adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.menu_history));
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {Log.d("test: ", "click");
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onContextItemSelected(item);
    }

    private void initList() {
        String[] cities = getResources().getStringArray(R.array.cities_collection);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(cities));

        adapter = new RecyclerAdapterList(data, this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_add:
                adapter.addItem();
                break;

            case R.id.menu_edit:
                adapter.editItem(" (ред.)");
                break;

            case R.id.menu_remove:
                adapter.removeElement();
                break;

            case R.id.menu_clear:
                adapter.clearList();
                break;

            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.action_author:
                Snackbar.make(toolbar, "Автор: @andrew", Snackbar.LENGTH_LONG)
                        .setAction("Закрыть", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(HistoryActivity.this.getApplicationContext(), "Snackbar закрыт", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;

            case R.id.action_history:
                break;

            case android.R.id.home:
            case R.id.action_exit:
                finish();
                break;

            default:
                Toast.makeText(getApplicationContext(), getString(R.string.action_not_found), Toast.LENGTH_SHORT).show();
        }
    }
}
