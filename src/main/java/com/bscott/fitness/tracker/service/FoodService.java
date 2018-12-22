package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.exception.BusinessLogicException;
import com.bscott.fitness.tracker.model.FoodItem;
import com.bscott.fitness.tracker.repository.FoodsRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class FoodService {

    private FoodsRepository foodsRepository;

    @Autowired
    public FoodService(FoodsRepository foodsRepository) {
        this.foodsRepository = foodsRepository;
    }

    public FoodItem findFoodItemById(@NotEmpty String id) {
        return foodsRepository.findOne(id);
    }

    public List<FoodItem> findFoodItemsBySearchCriteria(String name) {
        return foodsRepository.findFoodItemsByNameLike(name);
    }

    public FoodItem addFoodItem(@Valid FoodItem foodItem) throws BusinessLogicException {

        // Verify a food item with the same name does not exist
        FoodItem existingFoodItem = foodsRepository.findFoodItemsByName(foodItem.getName());

        if (existingFoodItem != null) {
            throw new BusinessLogicException("Food Item " + foodItem.getName() + " already exists");
        }

        return foodsRepository.save(foodItem);
    }

    public FoodItem updateFoodItem(@Valid @NotNull FoodItem foodItem) {

        foodsRepository.save(foodItem);
        return foodItem;
    }

    public void deleteFoodItem(@NotEmpty String id) {
        foodsRepository.delete(id);
    }
}
