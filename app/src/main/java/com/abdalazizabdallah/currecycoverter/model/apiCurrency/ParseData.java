package com.abdalazizabdallah.currecycoverter.model.apiCurrency;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseData {


    public static Currancy parseData(String response, String target) {

        Currancy currency = null;
        try {
            JSONObject jsonObject = new JSONObject(response);

            String disclaimer = jsonObject.getString("disclaimer");

            String license = jsonObject.getString("license");

            Integer timestamp = jsonObject.getInt("timestamp");

            String base = jsonObject.getString("base");

            JSONObject rate = jsonObject.getJSONObject("rates");
            double currenyTarget = rate.getDouble(target);

            currency = new Currancy(disclaimer, license, timestamp, base, new Rates(currenyTarget));
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return currency;
    }
}
