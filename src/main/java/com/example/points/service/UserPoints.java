package com.example.points.service;

import com.example.points.controller.Transaction;
import com.example.points.controller.TransactionDto;

import java.util.*;

public class UserPoints {
    private final Map<String, Integer> payerTotalPoints;
    private final PriorityQueue<Transaction> unUsedOldestTransaction;
    private final LinkedList<Transaction> usedPointsTransaction;

    public UserPoints(){
        payerTotalPoints = new HashMap<>();
        unUsedOldestTransaction =
                new PriorityQueue<>(Comparator.comparing(Transaction::getTransactiondate).reversed());
        usedPointsTransaction = new LinkedList<>();
    }

    public Map<String, Integer> getPayerTotalPoints() {
        return payerTotalPoints;
    }

    public PriorityQueue<Transaction> getUnUsedOldestTransactions() {
        return unUsedOldestTransaction;
    }
}
