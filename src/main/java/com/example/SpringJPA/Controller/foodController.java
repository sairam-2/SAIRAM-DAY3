package com.example.SpringJPA.Controller;

import com.example.SpringJPA.Model.Food;
import com.example.SpringJPA.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class foodController {
    @Autowired

    private FoodService foodService;
//    @RequestMapping("/test")
//    public String test()
//    {
//        return "Server is Running";
//    }

    // Adding new entry to DB
    @PostMapping("/addFood")
    public ResponseEntity<Food> addFood(@RequestBody Food food)
    {
        return foodService.createFood(food);
    }

    // Getting Foods from Db
    @GetMapping("/getFood")
    public ResponseEntity<List<Food>> getFood()
    {
        return ResponseEntity.ok(foodService.getAllFood().getBody());
    }

    // Getting Food by ID
    @GetMapping("/getFood/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id)
    {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    // Updating Food by ID
    @PutMapping("/updateFood/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food food)
    {
        return ResponseEntity.ok(foodService.updateFood(id, food));
    }

    // Deleting Food by ID
    @DeleteMapping("/deleteFood/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id)
    {
        foodService.deleteFood(id);
        return ResponseEntity.ok("Food with ID " + id + " has been deleted.");
    }
}