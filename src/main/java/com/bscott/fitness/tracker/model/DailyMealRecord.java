package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = Constants.MEAL_RECORD_COLLECTION)
public class DailyMealRecord {

    private String userId;
    private LocalDate date;
    private List<FoodItem> breakfastItems;
    private List<FoodItem> lunchItems;
    private List<FoodItem> dinnerItems;
    private List<FoodItem> snacks;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<FoodItem> getBreakfastItems() {

        if(breakfastItems == null){
            breakfastItems = new ArrayList<>();
        }

        return breakfastItems;
    }

    public void setBreakfastItems(List<FoodItem> breakfastItems) {
        this.breakfastItems = breakfastItems;
    }

    public List<FoodItem> getLunchItems() {

        if(lunchItems == null){
            lunchItems = new ArrayList<>();
        }

        return lunchItems;
    }

    public void setLunchItems(List<FoodItem> lunchItems) {
        this.lunchItems = lunchItems;
    }

    public List<FoodItem> getDinnerItems() {

        if(dinnerItems == null){
            dinnerItems = new ArrayList<>();
        }

        return dinnerItems;
    }

    public void setDinnerItems(List<FoodItem> dinnerItems) {
        this.dinnerItems = dinnerItems;
    }

    public List<FoodItem> getSnacks() {

        if(snacks == null){
            snacks = new ArrayList<>();
        }

        return snacks;
    }

    public void setSnacks(List<FoodItem> snacks) {
        this.snacks = snacks;
    }
}
