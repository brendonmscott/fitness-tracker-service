package com.bscott.fitness.tracker.dto;

import com.bscott.fitness.tracker.Constants;
import lombok.Data;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = Constants.MEAL_RECORD_COLLECTION)
public class DailyMealRecordDto {

    private String userId;
    private LocalDate date;
    private List<FoodItemDto> breakfastItems = new ArrayList<>();
    private List<FoodItemDto> lunchItems = new ArrayList<>();
    private List<FoodItemDto> dinnerItems = new ArrayList<>();
    private List<FoodItemDto> snacks = new ArrayList<>();
}
