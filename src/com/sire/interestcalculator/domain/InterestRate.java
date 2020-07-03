/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.domain;

import java.time.LocalDate;

/**
 *
 * @author balza
 */
public class InterestRate {

    private LocalDate rateDate;
    private double rate;

    public InterestRate() {
    }

    public InterestRate(LocalDate rateDate, double rate) {
        this.rateDate = rateDate;
        this.rate = rate;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
