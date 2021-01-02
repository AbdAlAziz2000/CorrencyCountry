package com.abdalazizabdallah.currecycoverter.model.apiCurrency;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String RES_PATH = " latest.json";
    String BASE_PARAM = "base";
    String SYMBOLS_PARAM = "symbols";
    String APP_ID_PARAM = "app_id";


    @GET(RES_PATH)
    Call<ResponseBody> getData(@Query(APP_ID_PARAM) String app_id, @Query(BASE_PARAM) String baseCurr, @Query(SYMBOLS_PARAM) String targetCurr);


}
