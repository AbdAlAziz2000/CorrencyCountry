package com.abdalazizabdallah.currecycoverter.model;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.abdalazizabdallah.currecycoverter.ConstantHelper.Constants;
import com.abdalazizabdallah.currecycoverter.R;
import com.abdalazizabdallah.currecycoverter.model.apiCurrency.Currancy;
import com.abdalazizabdallah.currecycoverter.model.apiCurrency.NetworkUtils;
import com.abdalazizabdallah.currecycoverter.model.apiCurrency.ParseData;
import com.abdalazizabdallah.currecycoverter.model.parserApi.Country;
import com.abdalazizabdallah.currecycoverter.model.parserApi.Currency;
import com.abdalazizabdallah.currecycoverter.model.parserApi.NetworkUtilsCountry;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRepository {

    private static final String TAG = "CurrencyRepository";
    private static CurrencyRepository instance;
    private final NetworkUtils networkUtils;
    private final NetworkUtilsCountry networkUtilsCountry;
    private final Gson gson;
    private final MutableLiveData<Result> dataWrapper;
    private final Application application;

    public CurrencyRepository(Application application) {
        this.application = application;
        gson = new Gson();
        networkUtils = NetworkUtils.getInstance(application);
        networkUtilsCountry = NetworkUtilsCountry.getInstance(application);
        dataWrapper = new MutableLiveData<Result>();
    }

    public static CurrencyRepository getInstance(Application application) {
        if (instance == null) {
            instance = new CurrencyRepository(application);
        }
        return instance;
    }

    public void requestGetCurrency(String textNumber, Currency currencySource, Currency currencyTarget) {
        if (TextUtils.isEmpty(textNumber)) {
            //TODO : show messsage error
            Log.e(TAG, " CurrencyRepository : requestGetCurrency : " + textNumber + " TextUtils.isEmpty(textNumber) ", null);

            dataWrapper.setValue(Result.error(R.string.empty_filed));
        } else {

            Log.e(TAG, " CurrencyRepository : requestGetCurrency : else", null);


            Call<ResponseBody> data = networkUtils.getCurrencyApiInterface().getData(Constants.API_KEY, currencySource.getCode(), currencyTarget.getCode());
            data.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String stringRespone = response.body().string();
                            Currancy currancy = ParseData.parseData(stringRespone, currencyTarget.getCode());
                            double result = calculateCurrency(Double.parseDouble(textNumber), currancy.getRates().getTargetCurrency());

                            Log.e(TAG, " CurrencyRepository : requestGetCurrency :  else : onResponse : " + result, null);


                            dataWrapper.setValue(Result.success(new BackResult(result, currencySource.getSymbol(), currencyTarget.getSymbol())));
                            //textView.setText(String.valueOf(result));
                            // TODO : retrun data to Ui
                        } catch (IOException e) {
                            Log.e(TAG, " CurrencyRepository : requestGetCurrency : else : onResponse : catch " + response.body().toString(), null);

                            dataWrapper.setValue(Result.error(response.body().toString()));
                            e.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, " CurrencyRepository : requestGetCurrency : else : onResponse :  not Successful " + "Symbols  '" + currencyTarget.getCode() + "'  are invalid ", null);

                        dataWrapper.setValue(Result.error("Symbols  '" + currencyTarget.getCode() + "'  are invalid "));
                        // TODO : show message Error
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, " CurrencyRepository : requestGetCurrency : onFailure " + t.getMessage(), null);

                    dataWrapper.setValue(Result.error(t.getMessage()));
                    // TODO : show message Error
                }
            });
        }
    }

    public void getCurrencyCountryCode(String codeNameCountry, String codeSource, String textNumber) {

        String quray = codeNameCountry + ";" + codeSource;
        Log.e(TAG, " CurrencyRepository : getCurrencyCountryCode onResponse " + quray, null);
        Call<List<Country>> nameCountry = networkUtilsCountry.getCountryApiInterface().getNameCountry(quray);
        nameCountry.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.body() != null && response.body().get(0) != null) {
                    Currency currencyTarget = response.body().get(0).getCurrencies().get(0);
                    Currency currencySourse = response.body().get(1).getCurrencies().get(0);
                    if (currencySourse.getCode() == null) {
                        currencySourse.setCode("USD");
                    }
                    if (currencyTarget.getCode() == null) {
                        currencyTarget.setCode("USD");
                    }

                    requestGetCurrency(textNumber, currencySourse, currencyTarget);

                    Log.e(TAG, " getCurrencyCountryCode :getCurrencyCountryCode  onResponse : Base : " + currencySourse.getCode() + " and Target :" + currencyTarget.getCode(), null);

                } else {
                    dataWrapper.setValue(Result.error("error"));
                    Log.e(TAG, " CurrencyRepository : getCurrencyCountryCode onResponse " + ".error(\"error\")", null);

                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                dataWrapper.setValue(Result.error(t.getMessage()));

                Log.e(TAG, " CurrencyRepository : getCurrencyCountryCode onFailure " + t.getMessage(), null);

            }
        });
    }


    public double calculateCurrency(Double number, double targetCurreny) {
        return ((int) ((number * targetCurreny) * 100)) / 100.0;
    }


    public MutableLiveData<Result> getDataWrapper() {
        return dataWrapper;
    }

}
