package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.dto.CustomizedFoodItemDto;
import com.bscott.fitness.tracker.dto.FavoriteFoodItemDto;
import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.service.FavoriteFoodsService;
import com.bscott.fitness.tracker.translator.FoodItemTranslator;
import com.bscott.fitness.tracker.translator.UserTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bscott.fitness.tracker.exception.BusinessLogicException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Favorite Food Services")
@RequestMapping("/foods")
@RestController
public class FavoriteFoodsController {

    @Autowired
    private FavoriteFoodsService favoriteFoodsService;
    @Autowired
    private FoodItemTranslator foodItemTranslator;
    @Autowired
    private UserTranslator userTranslator;

    @ApiOperation(value = "Add a favorite food item for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Favorite Food Item was added successfully")
    })
    @PutMapping("/favorites")
    public ResponseEntity addFavoriteFood(@ApiParam(value = "The food item to add", required = true) @RequestBody FavoriteFoodItemDto favoriteFoodItemDto) {

        User user = favoriteFoodsService.addFavoriteFoodItem(
                favoriteFoodItemDto.getFoodItemId(), favoriteFoodItemDto.getUserId());

        return ResponseEntity.ok(userTranslator.toDto(user));
    }

    @ApiOperation(value = "Delete a favorite food item for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Favorite Food Item was deleted successfully")
    })
    @DeleteMapping("/favorites/{foodItemId}/user/{userId}")
    public ResponseEntity deleteFavoriteFood(@ApiParam(value = "The food item to delete", required = true) @PathVariable("foodItemId") String foodItemId,
                                @ApiParam(value = "The user to delete the item for", required = true) @PathVariable("userId") String userId) {

        User user = favoriteFoodsService.deleteFavoriteFoodItem(foodItemId, userId);

        return ResponseEntity.ok(userTranslator.toDto(user));
    }

    @ApiOperation(value = "Add a customized food item for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customized Food Item was added successfully")
    })
    @PostMapping("/customized")
    public ResponseEntity addCustomizedFood(@ApiParam(value = "The food item to add", required = true) @RequestBody CustomizedFoodItemDto customizedFoodItemDto)
            throws BusinessLogicException {

        User user = favoriteFoodsService.addCustomizedFoodItem(
                foodItemTranslator.toEntity(customizedFoodItemDto.getFoodItem()), customizedFoodItemDto.getUserId());

        return ResponseEntity.ok(userTranslator.toDto(user));
    }

    @ApiOperation(value = "Delete a customized food item for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Customized Food Item was deleted successfully")
    })
    @DeleteMapping("/customized/{foodItemId}/user/{userId}")
    public ResponseEntity deleteCustomizedFood(@ApiParam(value = "The food item to delete", required = true) @PathVariable("foodItemId") String foodItemId,
                                  @ApiParam(value = "The user to delete the item for", required = true) @PathVariable("userId") String userId) {

        User user = favoriteFoodsService.deleteCustomizedFoodItem(foodItemId, userId);

        return ResponseEntity.ok(userTranslator.toDto(user));
    }

}
