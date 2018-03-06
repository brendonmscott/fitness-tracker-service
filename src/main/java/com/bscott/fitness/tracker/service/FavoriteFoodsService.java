package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.exception.BusinessLogicException;
import com.bscott.fitness.tracker.model.FoodItem;
import com.bscott.fitness.tracker.model.User;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteFoodsService {

    private Logger logger = LoggerFactory.getLogger(FavoriteFoodsService.class);

    private FoodService foodService;
    private UserService userService;
    private Gson gson = new Gson();

    @Autowired
    public FavoriteFoodsService(FoodService foodService, UserService userService){
        this.foodService = foodService;
        this.userService = userService;
    }

    public User addFavoriteFoodItem(String foodItemId, String userId){

        logger.info("Adding favorite food item {} to user {}", foodItemId, userId);
        User user = userService.findUserById(userId);

        if(!user.getFavoriteFoodItems().contains(new ObjectId(foodItemId))){
            user.getFavoriteFoodItems().add(new ObjectId(foodItemId));
            user = userService.updateUser(user);
        }

        return user;
    }

    public User deleteFavoriteFoodItem(String foodItemId, String userId) {

        logger.info("Deleting favorite food item {} for user {}", foodItemId, userId);
        User user = userService.findUserById(userId);
        user.getFavoriteFoodItems().remove(new ObjectId(foodItemId));
        return userService.updateUser(user);
    }

    public User addCustomizedFoodItem(FoodItem foodItem, String userId) throws BusinessLogicException{

        String foodItemJson = gson.toJson(foodItem);

        logger.info("Adding favorite food item {}", foodItemJson);
        FoodItem newFoodItem = foodService.addFoodItem(foodItem);

        logger.info("Adding favorite food item {} to user {}", newFoodItem.getId(), userId);
        User user = userService.findUserById(userId);
        user.getCustomizedFoodItems().add(new ObjectId(newFoodItem.getId()));

        return userService.updateUser(user);
    }

    public User deleteCustomizedFoodItem(String foodItemId, String userId) {

        logger.info("Deleting favorite food item {}", foodItemId);
        foodService.deleteFoodItem(foodItemId);

        logger.info("Deleting favorite food item {} for user {}", foodItemId, userId);
        User user = userService.findUserById(userId);
        user.getCustomizedFoodItems().remove(new ObjectId(foodItemId));

        return userService.updateUser(user);
    }
}
