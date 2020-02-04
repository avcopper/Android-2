package com.example.weather.ui.cities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CitiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CitiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cities fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}