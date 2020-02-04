package com.example.weather.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Constants;
import com.example.weather.Parcel;
import com.example.weather.R;
import com.example.weather.recycler.RecyclerAdapterCity;
import com.example.weather.recycler.RecyclerAdapterList;
import com.example.weather.ui.cities.CitiesViewModel;
import com.example.weather.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;


public class HistoryFragment extends Fragment implements Constants {

    private CitiesViewModel citiesViewModel;
    private RecyclerAdapterList adapter = null;

    public static HistoryFragment create(Parcel parcel) {
        HistoryFragment f = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        citiesViewModel = ViewModelProviders.of(this).get(CitiesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_history, container, false);

//        final TextView textView = root.findViewById(R.id.text_cities);
//        citiesViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        ArrayList<Integer> data = new ArrayList<>();
//        data.add(1);
//        data.add(2);
//        data.add(3);
//        data.add(4);
//
//        adapter = new RecyclerAdapterList(data, this);
//        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
//
//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_history);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        v.getMenuInflater().inflate(R.menu.main, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        handleMenuItemClick(item);
//        return super.onContextItemSelected(item);
//    }
}