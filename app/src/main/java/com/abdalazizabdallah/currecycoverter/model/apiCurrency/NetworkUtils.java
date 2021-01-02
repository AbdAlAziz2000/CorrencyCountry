package com.abdalazizabdallah.currecycoverter.model.apiCurrency;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static NetworkUtils networkUtils;

    private final String BASE_URL = " https://openexchangerates.org/api/";
    private final ApiInterface apiInterface;
    private final Context context;


    private NetworkUtils(Context context) {
        this.context = context;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static NetworkUtils getInstance(Context context) {
        if (networkUtils == null) {
            networkUtils = new NetworkUtils(context.getApplicationContext());
        }
        return networkUtils;
    }

    public ApiInterface getCurrencyApiInterface() {
        return apiInterface;
    }

}
