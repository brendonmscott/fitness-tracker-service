package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.dto.FoodItemDto;
import com.bscott.fitness.tracker.model.FoodItem;
import com.bscott.fitness.tracker.service.FoodService;
import com.bscott.fitness.tracker.translator.FoodItemTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bscott.fitness.tracker.exception.BusinessLogicException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api(value = "Food Services")
@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private FoodItemTranslator foodItemTranslator;

    @ApiOperation(value = "Get Food by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Food was retrieved successfully"),
            @ApiResponse(code = 404, message = "Food was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity findFoodById(@ApiParam(value = "The id of the food to find", required = true) @PathVariable("id") String id){

        FoodItemDto foodItem = foodItemTranslator.toDto(foodService.findFoodItemById(id));

        if(foodItem == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foodItem);
    }

    @ApiOperation(value = "Search Food Items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foods were retrieved successfully")
    })
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @GetMapping("/")
    public ResponseEntity findFoods(@RequestParam(value = "name") String name){

        List<FoodItemDto> foodItems = foodItemTranslator.toDtos(foodService.findFoodItemsBySearchCriteria(name));

        return ResponseEntity.ok(foodItems);
    }

    @ApiOperation(value = "Add new food item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Food Item was added successfully")
    })
    @PostMapping("/")
    public ResponseEntity addFoodItem(@ApiParam(value = "The food item to add", required = true) FoodItemDto foodItemDto) throws BusinessLogicException{

        FoodItem newFoodItem = foodService.addFoodItem(foodItemTranslator.toEntity(foodItemDto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newFoodItem.getId()).toUri();

        return ResponseEntity.created(location).body(foodItemTranslator.toDto(newFoodItem));

    }

    @ApiOperation(value = "Update food item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Food Item was updated successfully")
    })
    @PutMapping("/")
    public ResponseEntity updateFoodItem(@ApiParam(value = "The food item to update", required = true) FoodItemDto foodItemDto){

        FoodItem updatedFoodItem = foodService.updateFoodItem(foodItemTranslator.toEntity(foodItemDto));

        return ResponseEntity.ok(foodItemTranslator.toDto(updatedFoodItem));
    }

    @ApiOperation(value = "Delete Food Item")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Food Item was deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFoodItem(@ApiParam(value = "The id of the food item to delete", required = true) @PathVariable("id") String id){

        foodService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}
