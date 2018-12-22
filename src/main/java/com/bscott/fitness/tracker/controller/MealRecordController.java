package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.dto.DailyMealRecordDto;
import com.bscott.fitness.tracker.model.DailyMealRecord;
import com.bscott.fitness.tracker.service.MealRecordService;
import com.bscott.fitness.tracker.translator.MealRecordTranslator;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Api(value = "Meal Record Services")
@RestController
@RequestMapping("/meals")
public class MealRecordController {

    private MealRecordService mealRecordService;
    private MealRecordTranslator mealRecordTranslator;

    @Autowired
    public MealRecordController(MealRecordService mealRecordService, MealRecordTranslator mealRecordTranslator) {
        this.mealRecordService = mealRecordService;
        this.mealRecordTranslator = mealRecordTranslator;
    }

    @ApiOperation(value = "Get Daily Meal Record")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Meal Record was retrieved successfully"),
            @ApiResponse(code = 404, message = "Meal Record was not found")
    })
    @GetMapping("/daily")
    public ResponseEntity findMealRecord(@ApiParam(value = "The userId of the meal record to find", required = true) @RequestParam("userId") String userId,
                                  @ApiParam(value = "The date of the meal record to find") @RequestParam("date") String date) {

        LocalDate searchDate = new LocalDate(date);

        mealRecordService.findMealRecordBySearchCriteria(userId, searchDate);

        return ResponseEntity.ok().build();
    }

//    @ApiOperation(value = "Search Food Items")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Foods were retrieved successfully")
//    })
//    @Secured
//    @GET
//    @Path("/")
//    Response findFoods(@QueryParam(value = "name") String name);
//
    @ApiOperation(value = "Add new meal record")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Meal Record was added successfully")
    })
    @PostMapping
    ResponseEntity addMealRecord(@ApiParam(value = "The meal record to add", required = true)
                                 @RequestBody DailyMealRecordDto dailyMealRecordDto) {

        DailyMealRecord newDailyMealRecord = mealRecordService.addDailyMealRecord(mealRecordTranslator.toEntity(dailyMealRecordDto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDailyMealRecord.getId()).toUri();

        return ResponseEntity.created(location).body(mealRecordTranslator.toDto(newDailyMealRecord));
    }

//    @ApiOperation(value = "Update food item")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Food Item was updated successfully")
//    })
//    @Secured
//    @PUT
//    @Path("/")
//    Response updateFoodItem(@ApiParam(value = "The food item to update", required = true) FoodItemDto foodItemDto);
//
//    @ApiOperation(value = "Delete Food Item")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "Food Item was deleted successfully")
//    })
//    @Secured
//    @DELETE
//    @Path("/{id}")
//    Response deleteFoodItem(@ApiParam(value = "The id of the food item to delete", required = true) @PathParam("id") String id);
}
