package com.example.weather.ui.cities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weather.AddActivity;
import com.example.weather.Constants;
import com.example.weather.Parcel;
import com.example.weather.R;
import com.example.weather.recycler.RecyclerAdapterCity;
import java.util.Arrays;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class CitiesFragment extends Fragment implements Constants {
    private final static int REQUEST_CODE = 2;

    public CitiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView addCity = view.findViewById(R.id.add_city_start);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityAddition();
            }
        });

        Parcel parcel = new Parcel();
        parcel.cities = getResources().getStringArray(R.array.cities_collection);
        parcel.tempCities = getResources().getStringArray(R.array.temperature_collection);
        parcel.weather_collection = getResources().getStringArray(R.array.weather_collection);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_city);
        recyclerView.setHasFixedSize(true);
        RecyclerAdapterCity adapter = new RecyclerAdapterCity(parcel);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new RecyclerAdapterCity.RecyclerItemClickListener() {
            @Override
            public void onItemClick(Parcel parcel) {
                Intent intent = new Intent();
                intent.putExtra(CITY, parcel);

                FragmentActivity activity = getActivity();
                if (activity != null) {
                    SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPreferences.edit();
                    ed.putString(CITY, parcel.city);
                    ed.apply();

                    activity.setResult(RESULT_OK, intent);
                    activity.finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1 && requestCode == 2) {
            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);

            if (parcel != null) {
                if (parcel.city != null) {
                    String[] cities = getResources().getStringArray(R.array.cities_collection);
                    parcel.cities = Arrays.copyOf(cities, cities.length + 1);
                    parcel.cities[cities.length] = parcel.city;
                    parcel.tempCities = getResources().getStringArray(R.array.temperature_collection);
                    parcel.weather_collection = getResources().getStringArray(R.array.weather_collection);

                    RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_city);
                    recyclerView.setHasFixedSize(true);
                    RecyclerAdapterCity adapter = new RecyclerAdapterCity(parcel);
                    recyclerView.setAdapter(adapter);

                    adapter.setClickListener(new RecyclerAdapterCity.RecyclerItemClickListener() {
                        @Override
                        public void onItemClick(Parcel parcel) {
                            Intent intent = new Intent();
                            intent.putExtra(CITY, parcel);
                            getActivity().setResult(RESULT_OK, intent);
                            getActivity().finish();
                        }
                    });
                }
            }
        }
    }

    private void showCityAddition() {
        Intent intent = new Intent(getActivity(), AddActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}