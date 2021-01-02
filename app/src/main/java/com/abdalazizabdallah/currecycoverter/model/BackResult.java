package com.abdalazizabdallah.currecycoverter.model;

public class BackResult {

    private double result;
    private String symbolSource;
    private String symbolTaget;

    public BackResult(double result, String symbolSource, String symbolTaget) {
        this.result = result;
        this.symbolSource = symbolSource;
        this.symbolTaget = symbolTaget;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getSymbolSource() {
        return symbolSource;
    }

    public void setSymbolSource(String symbolSource) {
        this.symbolSource = symbolSource;
    }

    public String getSymbolTaget() {
        return symbolTaget;
    }

    public void setSymbolTaget(String symbolTaget) {
        this.symbolTaget = symbolTaget;
    }
}
