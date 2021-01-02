package com.abdalazizabdallah.currecycoverter.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abdalazizabdallah.currecycoverter.model.CurrencyRepository;
import com.abdalazizabdallah.currecycoverter.model.Result;


public class MainViewModel extends AndroidViewModel {

    private final LiveData<Result> dataWrapper;

    private final CurrencyRepository currencyRepository;


    public MainViewModel(Application application) {
        super(application);
        currencyRepository = CurrencyRepository.getInstance(application);
        dataWrapper = currencyRepository.getDataWrapper();// get repo
    }

    public void requestCurrencyCode(String codeNameCountry, String codeSource, String textNumber) {
        //TODO : request code from repository
        currencyRepository.getCurrencyCountryCode(codeNameCountry, codeSource, textNumber);
    }

    public LiveData<Result> getDataWrapper() {
        return dataWrapper;
    }
}
