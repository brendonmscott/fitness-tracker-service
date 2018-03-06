package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.service.MealRecordService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Meal Record Services")
@RestController
@RequestMapping("/meals")
public class MealRecordController {

    @Autowired
    private MealRecordService mealRecordService;

    @ApiOperation(value = "Get Daily Meal Record")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Meal Record was retrieved successfully"),
            @ApiResponse(code = 404, message = "Meal Record was not found")
    })
    @GetMapping("/daily")
    public ResponseEntity findMealRecord(@ApiParam(value = "The userId of the meal record to find", required = true) @RequestParam("userId") String userId,
                                  @ApiParam(value = "The date of the meal record to find") @RequestParam("date") String date){

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
//    @ApiOperation(value = "Add new food item")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Food Item was added successfully")
//    })
//    @Secured
//    @POST
//    @Path("/")
//    Response addFoodItem(@ApiParam(value = "The food item to add", required = true) FoodItemDto foodItemDto,
//                         @Context UriInfo uriInfo) throws BusinessLogicException;
//
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
