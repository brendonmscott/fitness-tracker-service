package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import lombok.Data;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = Constants.MEAL_RECORD_COLLECTION)
public class DailyMealRecord {

    @Id
    private String id;
    private String userId;
    private LocalDate date;
    private List<FoodItem> breakfastItems;
    private List<FoodItem> lunchItems;
    private List<FoodItem> dinnerItems;
    private List<FoodItem> snacks;
}
