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
public class InterestElementString {

    private String period;
    private String days;
    private String rate;
    private String amount;
    private String interest;

    public InterestElementString() {
    }

    public InterestElementString(String period, String days, String rate, String amount, String interest) {
        this.period = period;
        this.days = days;
        this.rate = rate;
        this.amount = amount;
        this.interest = interest;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    
}
