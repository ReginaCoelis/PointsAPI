package com.example.points.controller;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TransactionDto {
    private String payerName;
    private long points;
    private Date transactionDate;

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactiondate) {
        this.transactionDate = transactiondate;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }
}
