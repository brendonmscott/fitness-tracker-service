package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.model.FoodItem;

import java.util.List;

// TODO: Can this be removed?
public interface FoodsRepositoryCustom {

    List<FoodItem> findFoods(String name);
}
