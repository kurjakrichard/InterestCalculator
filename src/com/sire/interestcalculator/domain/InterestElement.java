/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.domain;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author balza
 */
public class InterestElement {
    
    private ArrayList<InterestRate> interestRates;
    private LocalDate actualDate;
    private LocalDate paymentDate;
    private double amount;

    public InterestElement(LocalDate actualDate, LocalDate paymentDate, double amount) {
        this.actualDate = actualDate;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public ArrayList<InterestRate> getInterestRates() {
        return interestRates;
    }

    public void setInterestRates(ArrayList<InterestRate> interestRates) {
        this.interestRates = interestRates;
    }

    public LocalDate getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDate actualDate) {
        this.actualDate = actualDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
