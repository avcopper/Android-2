package com.example.weather.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weather.CitiesActivity;
import com.example.weather.Constants;
import com.example.weather.Loader;
import com.example.weather.Parcel;
import com.example.weather.R;
import com.example.weather.recycler.RecyclerAdapterDay;
import com.example.weather.recycler.RecyclerAdapterTime;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements Constants {
    private final static int REQUEST_CODE = 1;

    private Loader loader;
    private ImageView reload;
    private String currentCity;
    private TextView cityContainer;
    private TextView tempCurrentContainer;
    private TextView tempRangeContainer;
    private TextView weatherContainer;
    private TextView humidityValue;
    private TextView windValue;
    private TextView windDirection;
    private TextView updated;
    private LinearLayout humidityContainer;
    private LinearLayout windContainer;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader               = new Loader();
        ImageView changeCity = view.findViewById(R.id.changeCity);
        reload               = view.findViewById(R.id.reload);
        cityContainer        = view.findViewById(R.id.fragment_main_city);
        weatherContainer     = view.findViewById(R.id.fragment_main_weather);
        tempCurrentContainer = view.findViewById(R.id.fragment_main_temp_curr);
        tempRangeContainer   = view.findViewById(R.id.fragment_main_temp_range);
        humidityValue        = view.findViewById(R.id.main_humidity_value);
        windValue            = view.findViewById(R.id.main_speed_value);
        humidityContainer    = view.findViewById(R.id.fragment_main_humidity);
        windContainer        = view.findViewById(R.id.fragment_main_wind);
        windDirection        = view.findViewById(R.id.main_direction);
        updated              = view.findViewById(R.id.updated);

        Parcel parcel = getParcel();

        if (savedInstanceState != null ) {
            checkState(savedInstanceState);
        } else if (parcel != null) {
            checkParcel(parcel);
        } else {
            Activity activity = getActivity();

            if (activity != null) {
                SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
                currentCity = sharedPreferences.getString(CITY, getResources().getString(R.string.city_default));

                humidityContainer.setVisibility(sharedPreferences.getBoolean(HUMIDITY_CONTAINER, true) ? View.VISIBLE : View.GONE);
                windContainer.setVisibility(sharedPreferences.getBoolean(WIND_CONTAINER, true) ? View.VISIBLE : View.GONE);

                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putBoolean(VISIBILITY_CHANGED, false);
                ed.apply();
                loader.getData(activity, view, currentCity);
            } else {
                currentCity = getResources().getString(R.string.city_default);
            }
        }

        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCities(new Parcel());
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCity = cityContainer.getText().toString();
                startAnimate();
                loader.getData(getActivity(), view, currentCity);
            }
        });

        Parcel parcelTime = new Parcel();
        parcelTime.time = getResources().getStringArray(R.array.time_collection);

        Parcel parcelDay = new Parcel();
        parcelDay.day = getResources().getStringArray(R.array.days_collection);
        parcelDay.temperature_collection = getResources().getIntArray(R.array.temperature_collection);
        parcelDay.weather_image_collection = parcelTime.weather_image_collection = getImageArray();


        RecyclerView recyclerViewTime = view.findViewById(R.id.recycler_view_time);
        recyclerViewTime.setHasFixedSize(true);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTime.setAdapter(new RecyclerAdapterTime(parcelTime));

        RecyclerView recyclerViewDay = view.findViewById(R.id.recycler_view_day);
        recyclerViewDay.setHasFixedSize(true);
        recyclerViewDay.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDay.setAdapter(new RecyclerAdapterDay(parcelDay));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1 && requestCode == 1) {
            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);
            FragmentActivity activity = getActivity();
            View view = getView();

            if (activity != null && view != null && parcel != null) {
                if (parcel.city != null) loader.getData(activity, view, parcel.city);
            } else {
                checkSettings();
            }
        }
    }

    private void checkSettings() {
        Activity activity = getActivity();
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SETTINGS, MODE_PRIVATE);
            cityContainer.setText(sharedPreferences.getString(CITY, getResources().getString(R.string.city_default)));
            tempCurrentContainer.setText(sharedPreferences.getString(TEMP_CURRENT, ""));
            tempRangeContainer.setText(sharedPreferences.getString(TEMP_RANGE, ""));
            humidityValue.setText(sharedPreferences.getString(HUMIDITY, ""));
            windValue.setText(sharedPreferences.getString(WIND, ""));
            windDirection.setText(sharedPreferences.getString(WIND_DIRECTION, ""));
            weatherContainer.setText(sharedPreferences.getString(WEATHER, ""));
            updated.setText(sharedPreferences.getString(UPDATED, ""));
        }
    }

    private void showCities(Parcel parcel) {
        Intent intent = new Intent(getActivity(), CitiesActivity.class);
        intent.putExtra(PARCEL, parcel);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private Parcel getParcel() {
        return (Parcel)((getArguments() != null) ? getArguments().getSerializable(PARCEL) : null);
    }

    private void checkState(Bundle savedInstanceState) {
        String city           = savedInstanceState.getString(CITY);
        String weather        = savedInstanceState.getString(WEATHER);
        String tempCurrent    = savedInstanceState.getString(TEMP_CURRENT);
        String tempRange      = savedInstanceState.getString(TEMP_RANGE);
        String humidity       = savedInstanceState.getString(HUMIDITY);
        String wind           = savedInstanceState.getString(WIND);
        String windDeg        = savedInstanceState.getString(WIND_DIRECTION);
        String humiVisibility = savedInstanceState.getString(HUMIDITY_CONTAINER);
        String windVisibility = savedInstanceState.getString(WIND_CONTAINER);
        String updatedValue   = savedInstanceState.getString(UPDATED);

        cityContainer.setText(city);
        weatherContainer.setText(weather);
        tempCurrentContainer.setText(tempCurrent);
        tempRangeContainer.setText(tempRange);
        humidityValue.setText(humidity);
        windValue.setText(wind);
        windDirection.setText(windDeg);
        updated.setText(updatedValue);

        if (humiVisibility != null) {
            if (humiVisibility.equals("-1")) {
                humidityContainer.setVisibility(View.GONE);
            } else if (humiVisibility.equals("1")) {
                humidityContainer.setVisibility(View.VISIBLE);
            }
        }

        if (windVisibility != null) {
            if (windVisibility.equals("-1")) {
                windContainer.setVisibility(View.GONE);
            } else if (windVisibility.equals("1")) {
                windContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkParcel(Parcel parcel) {
        if (parcel.city != null) {
            cityContainer.setText(parcel.city);
            new Loader().getData(getActivity(), getView(), parcel.city);
        }
        if (parcel.tempCurrent != null) tempCurrentContainer.setText(parcel.tempCurrent);
        if (parcel.tempRange != null) tempRangeContainer.setText(parcel.tempRange);
        if (parcel.weather != null) weatherContainer.setText(parcel.weather);

        if (parcel.humidityVisibility) {
            humidityContainer.setVisibility(View.VISIBLE);
        } else {
            humidityContainer.setVisibility(View.GONE);
        }

        if (parcel.windVisibility) {
            windContainer.setVisibility(View.VISIBLE);
        } else {
            windContainer.setVisibility(View.GONE);
        }
    }

    private int[] getImageArray(){
        TypedArray pictures = getResources().obtainTypedArray(R.array.weather_image_collection);
        int length = pictures.length();
        int[] answer = new int [length];
        for ( int i = 0 ; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    private void startAnimate()
    {
        final Animation animationRotateCenter = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        reload.startAnimation(animationRotateCenter);
    }
}
