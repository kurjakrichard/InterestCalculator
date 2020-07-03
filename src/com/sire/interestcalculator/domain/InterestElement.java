/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 *
 * @author balza
 */
public class InterestElement {

    private LocalDate startDate;
    private LocalDate endDate;
    private long days;
    private double rate;
    private long amount;
    private double interest;

    public InterestElement() {
    }
    
    

    public InterestElement(LocalDate startDate, LocalDate endDate, double rate, long amount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
    
    public double interest() {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        interest = amount * rate / 100 * days/startDate.lengthOfYear();

        return interest;
    }

}
