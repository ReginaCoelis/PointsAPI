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
    //pathVariable- for identifying a particular resource
    @PostMapping("/add/{userId}")
    public String addPoints(@PathVariable int userId, @RequestBody Transaction transaction){
        return "";
    }
    //RequestParameter
    @PutMapping(value = "/deduct/{userId}", params = "points")
    public String deductPoints(@PathVariable int userId, @RequestParam int points){
        return userId+ " " + points;
    }

}
