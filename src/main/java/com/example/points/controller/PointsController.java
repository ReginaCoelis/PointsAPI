package com.example.points.controller;

import com.example.points.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// for the end points
@RequestMapping( "/points" )
@RestController
public class PointsController {

    @Autowired
    private PointsService service;

    @GetMapping("/balance/{userId}")
    public List<TransactionDto> pointBal(@PathVariable long userId){
        return service.getPointBal(userId);
    }

    //pathVariable- for identifying a particular resource
    //adding points
    @PostMapping("/add/{userId}")
    public void addPoints(@PathVariable long userId, @RequestBody TransactionDto transactionDto){
        service.addPoints(userId, transactionDto);
    }

    //RequestParameter
    //deducting points from a user's balance
    @PutMapping(value = "/deduct/{userId}", params = "points")
    public List<SpentPointsTransaction> deductPoints(@PathVariable long userId, @RequestParam long points){
        return service.deductPoints(userId, points);
    }

}
