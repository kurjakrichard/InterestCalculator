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

    private int id;
    private LocalDate rateDate;
    private double rate;

    public InterestRate() {
        this.id = 0;
        this.rateDate = null;
        this.rate = 0;
    }

    public InterestRate(LocalDate rateDate, double rate) {
        this.id = 0;
        this.rateDate = rateDate;
        this.rate = rate;
    }

    public InterestRate(int id, LocalDate rateDate, double rate) {
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
