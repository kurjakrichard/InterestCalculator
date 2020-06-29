/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.domain;

/**
 *
 * @author balza
 */
public class InterestRate {

    private int id;
    private String rateDate;
    private double rate;

    public InterestRate() {
        this.id = 0;
        this.rateDate = "";
        this.rate = 0;
    }

    public InterestRate(String rateDate, double rate) {
        this.id = 0;
        this.rateDate = rateDate;
        this.rate = rate;
    }

    public InterestRate(int id, String rateDate, double rate) {
        this.id = id;
        this.rateDate = rateDate;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRateDate() {
        return rateDate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
