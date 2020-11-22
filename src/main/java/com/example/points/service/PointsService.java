package com.example.points.service;

import com.example.points.controller.SpentPointsTransaction;
import com.example.points.controller.TransactionDto;

import java.util.List;

public interface PointsService {
    void addPoints(long userId, TransactionDto dto);
    List<SpentPointsTransaction> deductPoints(long userId, long points);
    List<TransactionDto> getPointBal(long userId);
}
