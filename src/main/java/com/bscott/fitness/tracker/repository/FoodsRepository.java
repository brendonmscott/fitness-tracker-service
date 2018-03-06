package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.model.FoodItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodsRepository extends MongoRepository<FoodItem, String>, FoodsRepositoryCustom {

    FoodItem findFoodItemsByName(String name);
    List<FoodItem> findFoodItemsByNameLike(String name);
}
