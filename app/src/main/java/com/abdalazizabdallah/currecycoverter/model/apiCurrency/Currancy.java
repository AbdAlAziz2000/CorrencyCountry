package com.abdalazizabdallah.currecycoverter.model.apiCurrency;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currancy {

    @SerializedName("disclaimer")
    @Expose
    private String disclaimer;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("rates")
    @Expose
    private Rates rates;

    /**
     * No args constructor for use in serialization
     */
    public Currancy() {
    }

    /**
     * @param license
     * @param rates
     * @param disclaimer
     * @param timestamp
     * @param base
     */
    public Currancy(String disclaimer, String license, Integer timestamp, String base, Rates rates) {
        super();
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}