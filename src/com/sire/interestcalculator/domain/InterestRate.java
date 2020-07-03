/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author balza
 */
public class InterestRate {

    private LocalDate startDate;
    private LocalDate endDate;
    private double rate;
    private int amount;

    public InterestRate() {
    }

    public InterestRate(LocalDate startDate, LocalDate endDate, double rate, int amount) {
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public double interest(LocalDate startDate, LocalDate endDate, double rate, int amount){
        rate = 10;
        long days = ChronoUnit.DAYS.between(endDate, startDate);
        double interest = amount * rate/100 * days;
                
        return interest;
    }
}
