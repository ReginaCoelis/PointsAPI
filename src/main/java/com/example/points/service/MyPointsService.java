package com.example.points.service;

import com.example.points.controller.SpentPointsTransaction;
import com.example.points.controller.TransactionDto;
import com.example.points.controller.NewPointsTransaction;
import com.example.points.repository.PointsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyPointsService implements PointsService {

    @Autowired
    private PointsRepo repo;


    @Override
    public void addPoints(long userId, TransactionDto transactionDto) {
        if(transactionDto.getPoints() > 0) {
            NewPointsTransaction newPointsTransaction = new NewPointsTransaction();
            newPointsTransaction.setPoints(transactionDto.getPoints());
            newPointsTransaction.setPayerName(transactionDto.getPayerName());
            newPointsTransaction.setTransactionDate(transactionDto.getTransactionDate());
            newPointsTransaction.setCreatedAt(new Date());
            repo.addNewPoints(userId, newPointsTransaction);
            String payerName = transactionDto.getPayerName();
            long newPayerPoints = repo.getPayerTotalPointsByName(userId, payerName) + transactionDto.getPoints();
            repo.savePayerTotalPoints(userId, payerName, newPayerPoints);
        }else {
            deductSinglePlayerPoints(userId, transactionDto);
        }
    }

    private void deductSinglePlayerPoints(long userId, TransactionDto transactionDto) {
        long points = -transactionDto.getPoints();
        String payerName = transactionDto.getPayerName();
        checkUserSinglePlayerPointsEnough(userId, points, payerName);

        Map<String, Long > payerSpentPoints = new HashMap<>();
        PriorityQueue<NewPointsTransaction> oldestFirstNewPointsTransactions = repo.getPayerUnspentPoints(userId, payerName);
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

            long totalPoints = repo.getPayerTotalPointsByName(userId, payerName);
            repo.savePayerTotalPoints(userId, payerName, totalPoints - newSpentPoints );
            payerSpentPoints.put(payerName, payerSpentPoints.getOrDefault(payerName, 0L)+newSpentPoints);

            points = newPoints;
        }


        List<SpentPointsTransaction> spentPointsTransactions = buildSpentPointsTransaction(payerSpentPoints);
        repo.saveSpentPoints(userId, spentPointsTransactions);

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

    @Override
    public List<TransactionDto> getPointBal(long userId) {
        return repo.getPayers(userId).entrySet().stream().map(e->{
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setPayerName(e.getKey());
            transactionDto.setPoints(e.getValue());
            transactionDto.setTransactionDate(new Date());
            return transactionDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SpentPointsTransaction> getSpentPoints(long userId) {
        return repo.getSpentPoints(userId);
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

    private void checkUserSinglePlayerPointsEnough(Long userId, long points, String payerName ) {
        if(!repo.exist(userId)) throw new RuntimeException("No user with given id");
        long payerTotalPoints = repo.getPayerTotalPointsByName(userId, payerName);
        if(payerTotalPoints < points) throw new RuntimeException("No enough points to deduct");
    }


}
