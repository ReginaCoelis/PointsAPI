package com.example.points.controller;

import java.util.Date;

public class NewPointsTransaction implements Comparable< NewPointsTransaction> {
    private String payerName;
    private long points;
    private long spentPoints;
    private Date transactionDate;
    private Date createdAt;

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getSpentPoints() {
        return spentPoints;
    }

    public void setSpentPoints(long spentPoints) {
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

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getBalance() {
        return points - spentPoints;
    }

    @Override
    public int compareTo(NewPointsTransaction t) {
        return this.transactionDate.compareTo(t.getTransactionDate());
    }
}
