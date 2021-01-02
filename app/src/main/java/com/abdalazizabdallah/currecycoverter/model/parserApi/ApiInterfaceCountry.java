package com.abdalazizabdallah.currecycoverter.model.parserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceCountry {

    String RES_PATH = "rest/v2/alpha";

    @GET(RES_PATH)
    Call<List<Country>> getNameCountry(@Query("codes") String code);


}
