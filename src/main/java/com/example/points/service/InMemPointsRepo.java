package com.example.points.service;

import com.example.points.controller.Transaction;
import com.example.points.controller.TransactionDto;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class InMemPointsRepo implements PointsRepo {
    private static final ConcurrentHashMap<Integer, UserPoints> userPointStore = new ConcurrentHashMap<>();
    @Override
    public void addPoints(int userId, TransactionDto transactionDto) {
        UserPoints userPoints = userPointStore.getOrDefault(userId, new UserPoints());
        userPoints.getUnUsedOldestTransactions().add(transactionDto);

        String payerName = transactionDto.getPayerName();
        Map<String, Integer> payerTotalPoints = userPoints.getPayerTotalPoints();
        int newPayerPoints = payerTotalPoints.getOrDefault(payerName, 0) + transactionDto.getPoints();
        payerTotalPoints.put(payerName, newPayerPoints);
    }

    @Override
    public List<TransactionDto> deduct(int userId, long points) {
        UserPoints userPoints = userPointStore.getOrDefault(userId, new UserPoints());
        PriorityQueue<Transaction> oldestFirstTransactions = userPoints.getUnUsedOldestTransactions();
        while ( points > 0 && !oldestFirstTransactions.isEmpty()){
            Transaction oldestTransaction = oldestFirstTransactions.peek();
            Map<String, Integer> payerTotalPoints = userPoints.getPayerTotalPoints();
            long totalPoints = payerTotalPoints.get(oldestTransaction.getPayerName());
            long newPoints = points - totalPoints;
            if(newPoints > 0){
                oldestFirstTransactions.poll();
            }
            oldestTransaction.setSpentPoints();
            points = newPoints;


        }
        TransactionDto oldestTransactionDto = ;

        return null;
    }
}
