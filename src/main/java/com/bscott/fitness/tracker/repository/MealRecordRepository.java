package com.bscott.fitness.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bscott.fitness.tracker.model.DailyMealRecord;
import org.joda.time.LocalDate;

import java.util.List;

public interface MealRecordRepository extends MongoRepository<DailyMealRecord, String> {

    List<DailyMealRecord> findDailyMealRecordsByUserIdAndDate(String userId, LocalDate date);
}
