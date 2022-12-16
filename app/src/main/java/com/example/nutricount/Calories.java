package com.example.nutricount;

public class Calories {
    private String foodName;
    private String category;
    private String description;

    Calories(String foodName, String category, String description) {
        this.foodName = foodName;
        this.category = category;
        this.description = description;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
