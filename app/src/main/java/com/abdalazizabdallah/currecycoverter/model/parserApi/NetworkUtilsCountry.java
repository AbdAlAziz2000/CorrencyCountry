package com.abdalazizabdallah.currecycoverter.model.parserApi;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtilsCountry {

    private static NetworkUtilsCountry networkUtils;

    private final String BASE_URL = "  https://restcountries.eu/";

    private final ApiInterfaceCountry apiInterfaceCountry;
    private final Context context;

    private NetworkUtilsCountry(Context context) {
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
        apiInterfaceCountry = retrofit.create(ApiInterfaceCountry.class);
    }

    public static NetworkUtilsCountry getInstance(Context context) {
        if (networkUtils == null) {
            networkUtils = new NetworkUtilsCountry(context.getApplicationContext());
        }
        return networkUtils;
    }

    public ApiInterfaceCountry getCountryApiInterface() {
        return apiInterfaceCountry;
    }

}
