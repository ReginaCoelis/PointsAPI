package com.example.points.service;

import com.example.points.controller.TransactionDto;

import java.util.List;

public interface PointsRepo {
    void addPoints(int userId, TransactionDto transactionDto);
    List<TransactionDto> deduct(int userId, long points);
}
