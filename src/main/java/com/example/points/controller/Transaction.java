package com.example.points.controller;

import java.util.Date;

public class Transaction {
    private String payerName;
    private int points;
    private int spentPoints;
    private Date transactionDate;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getSpentPoints() {
        return spentPoints;
    }

    public void setSpentPoints(int spentPoints) {
        this.spentPoints = spentPoints;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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
