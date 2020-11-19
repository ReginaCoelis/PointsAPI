package com.example.points.controller;

import org.springframework.web.bind.annotation.*;

// for the end points
@RequestMapping( "/points" )
@RestController
public class PointsController {

    @GetMapping("/balance/{userId}")
    public String pointBal(@PathVariable int userId){
        return " hello world" + userId;
    }
    @PostMapping("/add/{userId}")
    public String addPoints(@PathVariable int userId, @RequestBody Transaction transaction){
        return "";
    }

}
