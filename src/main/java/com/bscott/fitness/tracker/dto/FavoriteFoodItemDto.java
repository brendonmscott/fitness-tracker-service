package com.bscott.fitness.tracker.dto;

public class FavoriteFoodItemDto {

    private String userId;
    private String foodItemId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(String foodItemId) {
        this.foodItemId = foodItemId;
    }
}
