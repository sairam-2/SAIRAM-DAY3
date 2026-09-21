package com.example.SpringJPA.Service;

import com.example.SpringJPA.Model.Food;
import com.example.SpringJPA.Repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public ResponseEntity<Food> createFood(Food food)
    {
       foodRepository.save(food);
       return ResponseEntity.ok(food);
    }

    public ResponseEntity<List<Food>> getAllFood()
    {
        List<Food> foodList = foodRepository.findAll();
        return ResponseEntity.ok(foodList);
    }

    public Food getFoodById(Long id)
    {
        return foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found with ID: " + id));
    }

    public Food updateFood(Long id, Food foodDetails)
    {
        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found with ID: " + id));
        food.setFoodName(foodDetails.getFoodName());
        food.setPrice(foodDetails.getPrice());
        food.setAvailable(foodDetails.isAvailable());
        return foodRepository.save(food);
    }

    public void deleteFood(Long id)
    {
        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found with ID: " + id));
        foodRepository.delete(food);
    }

}