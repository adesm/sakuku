package com.example.android.iak3_bproject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adesm on 09/12/17.
 */

public class Person implements Serializable {
    private String name;
    private int numberOfPayment;
    private boolean isComplete;
    private Calendar paymentDate;

    public Person(String name, int numberOfPayment) {
        this.name = name;
        this.numberOfPayment = numberOfPayment;
        this.paymentDate = Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPayment() {
        return numberOfPayment;
    }

    public void setNumberOfPayment(int numberOfPayment) {
        this.numberOfPayment = numberOfPayment;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Calendar getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Calendar paymentDate) {
        this.paymentDate = paymentDate;
    }
}
