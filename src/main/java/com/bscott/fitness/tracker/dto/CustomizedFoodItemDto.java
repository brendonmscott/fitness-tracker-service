package com.bscott.fitness.tracker.dto;

public class CustomizedFoodItemDto {

    private String userId;
    private FoodItemDto foodItem;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FoodItemDto getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemDto foodItem) {
        this.foodItem = foodItem;
    }
}
