package com.example.points.repository;

import com.example.points.controller.SpentPointsTransaction;
import com.example.points.controller.TransactionDto;
import com.example.points.controller.NewPointsTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

@Repository
public interface PointsRepo {
    Optional<UserPoints> findById(Long userId);
    void addNewPoints(Long userId, NewPointsTransaction newPointsTransaction);
    long getPayerTotalPointsByName(Long userId, String payerName);
    void savePayerTotalPoints(Long userId, String payerName, Long newPoints);
    boolean exist(Long userId);
    Map<String, Long> getPayers(Long userId);
    PriorityQueue<NewPointsTransaction> getUnspentPoints(long userId);
    void saveSpentPoints(long userId, List<SpentPointsTransaction> spentPointsTransactions);
}
