package com.example.points.repository;

import com.example.points.controller.SpentPointsTransaction;
import com.example.points.controller.NewPointsTransaction;

import java.util.*;

public class UserPoints {
    private final Map<String, Long> payers;
    private final PriorityQueue<NewPointsTransaction> unSpentOldestNewPointsTransactions;
    private final LinkedList<SpentPointsTransaction> spentPointsUnSpentPointsTransactions;

    public UserPoints() {
        payers = new HashMap<>();
        unSpentOldestNewPointsTransactions = new PriorityQueue<>();
        spentPointsUnSpentPointsTransactions = new LinkedList<>();
    }

    Map<String, Long> getPayers() {
        return payers;
    }

    PriorityQueue<NewPointsTransaction> getUnSpentOldestTransactions() {
        return unSpentOldestNewPointsTransactions;
    }

    LinkedList<SpentPointsTransaction> getSpentPointsTransactions() {
        return spentPointsUnSpentPointsTransactions;
    }
}
