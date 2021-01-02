package com.abdalazizabdallah.currecycoverter.model.apiCurrency;

public class Rates {

    private Double targetCurrency;

    /**
     * No args constructor for use in serialization
     */
    public Rates() {
    }

    /**
     * @param targetCurrency
     */
    public Rates(Double targetCurrency) {
        super();
        this.targetCurrency = targetCurrency;
    }

    public Double getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Double targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

}
