package com.example.weather.ui.feedback;

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
import com.example.weather.ui.company.CompanyViewModel;


public class FeedbackFragment extends Fragment implements Constants {

    private FeedbackViewModel feedbackViewModel;

//    public static FeedbackFragment create(Parcel parcel) {
//        FeedbackFragment f = new FeedbackFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(PARCEL, parcel);
//        f.setArguments(args);
//        return f;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

//        final TextView textView = root.findViewById(R.id.text_cities);
//        feedbackViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}