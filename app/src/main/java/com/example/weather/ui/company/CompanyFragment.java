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

    private CompanyViewModel companyViewModel;

//    public static CompanyFragment create(Parcel parcel) {
//        CompanyFragment f = new CompanyFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(PARCEL, parcel);
//        f.setArguments(args);
//        return f;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        companyViewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);

        View root = inflater.inflate(R.layout.fragment_company, container, false);

//        final TextView textView = root.findViewById(R.id.text_cities);
//        companyViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}