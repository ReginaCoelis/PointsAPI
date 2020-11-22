package com.example.points.service;

import com.example.points.controller.SpentPointsTransaction;
import com.example.points.controller.TransactionDto;
import com.example.points.controller.NewPointsTransaction;
import com.example.points.repository.PointsRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyPointsService implements PointsService {
    private final PointsRepo repo;
    public MyPointsService(PointsRepo repo) {
        this.repo = repo;
    }

    @Override
    public void addPoints(long userId, TransactionDto transactionDto) {
        NewPointsTransaction newPointsTransaction = new NewPointsTransaction();
        newPointsTransaction.setPoints(transactionDto.getPoints());
        newPointsTransaction.setPayerName(transactionDto.getPayerName());
        newPointsTransaction.setCreatedAt(new Date());
        repo.addNewPoints(userId, newPointsTransaction);
        String payerName = transactionDto.getPayerName();
        long newPayerPoints = repo.getPayerTotalPointsByName(userId, payerName) + transactionDto.getPoints();
        repo.savePayerTotalPoints(userId, payerName, newPayerPoints);
    }

    @Override
    public List<SpentPointsTransaction> deductPoints(long userId, long points) {
        checkUserPointsEnough(userId, points);

        Map<String, Long > payerSpentPoints = new HashMap<>();
        PriorityQueue<NewPointsTransaction> oldestFirstNewPointsTransactions = repo.getUnspentPoints(userId);
        while (points > 0 && !oldestFirstNewPointsTransactions.isEmpty()) {
            NewPointsTransaction oldestNewPointsTransaction = oldestFirstNewPointsTransactions.peek();

            long balancePoints = oldestNewPointsTransaction.getBalance();
            long newPoints = points - balancePoints;

            long newSpentPoints;
            if (newPoints < 0) {
                newSpentPoints = points;
            } else {
                newSpentPoints = balancePoints;
            }

            oldestNewPointsTransaction.setSpentPoints(oldestNewPointsTransaction.getSpentPoints() + newSpentPoints);

            if (oldestNewPointsTransaction.getBalance() == 0){
                oldestFirstNewPointsTransactions.poll();
            }


            String payerName = oldestNewPointsTransaction.getPayerName();
            long totalPoints = repo.getPayerTotalPointsByName(userId, payerName);
            repo.savePayerTotalPoints(userId, payerName, totalPoints - newSpentPoints );
            payerSpentPoints.put(payerName, payerSpentPoints.getOrDefault(payerName, 0L)+newSpentPoints);

            points = newPoints;
        }


        List<SpentPointsTransaction> spentPointsTransactions = buildSpentPointsTransaction(payerSpentPoints);
        repo.saveSpentPoints(userId, spentPointsTransactions);
        return spentPointsTransactions;
    }

    private List<SpentPointsTransaction> buildSpentPointsTransaction(Map<String, Long> payerSpentPoints) {
        return payerSpentPoints.entrySet().stream().map(e->{
            SpentPointsTransaction spentPointsTransaction = new SpentPointsTransaction();
            spentPointsTransaction.setPoints(e.getValue());
            spentPointsTransaction.setPayerName(e.getKey());
            spentPointsTransaction.setTransactionDate(new Date());
            return spentPointsTransaction;
        }).collect(Collectors.toList());
    }

    private void checkUserPointsEnough(Long userId, long points) {
        if(!repo.exist(userId)) throw new RuntimeException("No user with given id");
        long userTotalPoints = repo.getPayers(userId).values().stream().reduce(0L, Long::sum);
        if(userTotalPoints < points) throw new RuntimeException("No enough points to deduct");
    }


}
