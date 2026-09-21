package com.example.SpringJPA.Controller;

import com.example.SpringJPA.Model.Food;
import com.example.SpringJPA.Service.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    private final FoodService foodService;

    public WebController(FoodService foodService) {
        this.foodService = foodService;
    }

    // Home / dashboard
    @GetMapping({"/", "/food"})
    public String home(Model model) {
        List<Food> foods = foodService.getAllFood().getBody();
        if (foods == null) {
            foods = List.of();
        }

        long availableFoods = foods.stream()
                .filter(food -> Boolean.TRUE.equals(food.getIsAvailable()))
                .count();

        double averagePrice = foods.stream()
                .filter(food -> food.getPrice() != null)
                .mapToDouble(Food::getPrice)
                .average()
                .orElse(0.0);

        model.addAttribute("totalFoods", foods.size());
        model.addAttribute("availableFoods", availableFoods);
        model.addAttribute("averagePrice", averagePrice);
        model.addAttribute("foods", foods);

        return "food";
    }

    // CREATE page
    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("food", new Food());
        return "create";
    }

    @PostMapping("/create")
    public String createFood(Food food) {
        food.setId(null);
        foodService.createFood(food);
        return "redirect:/read?created=true";
    }

    // READ page
    @GetMapping("/read")
    public String readPage(
            @RequestParam(required = false) Boolean created,
            @RequestParam(required = false) Boolean updated,
            @RequestParam(required = false) Boolean deleted,
            Model model) {

        model.addAttribute("foods", foodService.getAllFood().getBody());
        model.addAttribute("created", Boolean.TRUE.equals(created));
        model.addAttribute("updated", Boolean.TRUE.equals(updated));
        model.addAttribute("deleted", Boolean.TRUE.equals(deleted));

        return "read";
    }

    // UPDATE page
    @GetMapping("/update")
    public String updatePage(Model model) {
        model.addAttribute("foods", foodService.getAllFood().getBody());
        return "update";
    }

    @PostMapping("/update")
    public String updateFood(
            @RequestParam Long id,
            @RequestParam String foodName,
            @RequestParam Double price,
            @RequestParam Boolean isAvailable) {

        Food food = new Food();
        food.setFoodName(foodName);
        food.setPrice(price);
        food.setAvailable(isAvailable);
        foodService.updateFood(id, food);

        return "redirect:/read?updated=true";
    }

    // DELETE page
    @GetMapping("/delete")
    public String deletePage(Model model) {
        model.addAttribute("foods", foodService.getAllFood().getBody());
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteFood(@RequestParam Long id) {
        foodService.deleteFood(id);
        return "redirect:/read?deleted=true";
    }

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }
}
