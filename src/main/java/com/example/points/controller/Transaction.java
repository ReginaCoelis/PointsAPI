package com.example.points.controller;

import java.util.Date;

public class Transaction {
    private String payerName;
    private int points;
    private Date transactionDate;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getTransactiondate() {
        return transactionDate;
    }

    public void setTransactiondate(Date transactiondate) {
        this.transactionDate = transactiondate;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }
}
