package com.example.points.repository;

import com.example.points.controller.NewPointsTransaction;
import com.example.points.controller.SpentPointsTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemPointsRepo implements PointsRepo {
    private static final ConcurrentHashMap<Long, UserPoints> userPointsStore = new ConcurrentHashMap<>();


    @Override
    public Optional<UserPoints> findById(Long userId) {
        return Optional.ofNullable(userPointsStore.get(userId));
    }

    @Override
    public void addNewPoints(Long userId, NewPointsTransaction newPointsTransaction) {
        UserPoints userPoints = userPointsStore.getOrDefault(userId, new UserPoints());
        userPoints.getUnSpentOldestTransactions().add(newPointsTransaction);
        userPointsStore.put(userId, userPoints);
    }

    @Override
    public long getPayerTotalPointsByName(Long userId, String payerName) {
        return userPointsStore.get(userId).getPayers().getOrDefault(payerName, 0L);
    }

    @Override
    public void savePayerTotalPoints(Long userId, String payerName, Long newPoints) {
        Map<String, Long> payerTotalPoints = userPointsStore.get(userId).getPayers();
        payerTotalPoints.put(payerName, newPoints);
    }

    @Override
    public boolean exist(Long userId) {
        return findById(userId).isPresent();
    }

    @Override
    public Map<String, Long> getPayers(Long userId) {
        return userPointsStore.getOrDefault(userId, new UserPoints()).getPayers();
    }

    @Override
    public PriorityQueue<NewPointsTransaction> getUnspentPoints(long userId) {
        return userPointsStore.getOrDefault(userId, new UserPoints()).getUnSpentOldestTransactions();
    }

    @Override
    public void saveSpentPoints(long userId, List<SpentPointsTransaction> spentPointsTransactions) {
        userPointsStore.getOrDefault(userId, new UserPoints()).getSpentPointsTransactions().addAll(spentPointsTransactions);
    }

    private void checkUserPointsEnough(UserPoints userPoints, long points) {
        if (userPoints == null) throw new RuntimeException("No user with given id");
        long userTotalPoints = userPoints.getPayers().values().stream().reduce(0L, Long::sum);
        if (userTotalPoints < points) throw new RuntimeException("No enough points to deduct");
    }
}
