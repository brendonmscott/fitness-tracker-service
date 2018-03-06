package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.model.DailyMealRecord;
import com.bscott.fitness.tracker.repository.MealRecordRepository;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealRecordService {

    private MealRecordRepository mealRecordRepository;

    public MealRecordService(MealRecordRepository mealRecordRepository){
        this.mealRecordRepository = mealRecordRepository;
    }

    public List<DailyMealRecord> findMealRecordBySearchCriteria(String userId, LocalDate date){

        LocalDate searchDate = date;

        if(searchDate == null){
            searchDate = new LocalDate("2018-01-01");
        }

        return mealRecordRepository.findDailyMealRecordsByUserIdAndDate(userId, searchDate);
    }
}
