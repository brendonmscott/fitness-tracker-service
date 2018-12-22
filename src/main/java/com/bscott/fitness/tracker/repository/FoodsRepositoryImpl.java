package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.model.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class FoodsRepositoryImpl implements FoodsRepositoryCustom {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<FoodItem> findFoods(String name) {

        Query query = new Query();
        query.limit(PAGE_SIZE);
        query.addCriteria(Criteria.where("name").regex(name));

        return mongoTemplate.find(query, FoodItem.class);
    }
}
