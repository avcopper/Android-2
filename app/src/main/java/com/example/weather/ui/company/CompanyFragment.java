package com.example.weather.ui.company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.weather.Constants;
import com.example.weather.Parcel;
import com.example.weather.R;
import com.example.weather.recycler.RecyclerAdapterList;
import com.example.weather.ui.cities.CitiesViewModel;

public class CompanyFragment extends Fragment implements Constants {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company, container, false);
    }
}