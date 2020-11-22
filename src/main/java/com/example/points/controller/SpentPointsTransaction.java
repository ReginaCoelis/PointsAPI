package com.example.points.controller;

import java.util.Date;

public class SpentPointsTransaction {
    private String payerName;
    private long points;
    private Date transactionDate;

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = -points;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

}
