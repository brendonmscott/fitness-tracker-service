package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.dto.CustomizedFoodItemDto;
import com.bscott.fitness.tracker.dto.FavoriteFoodItemDto;
import com.bscott.fitness.tracker.dto.FoodItemDto;
import com.bscott.fitness.tracker.dto.UserDto;
import com.bscott.fitness.tracker.model.FoodItem;
import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.exception.BusinessLogicException;
import com.bscott.fitness.tracker.service.FavoriteFoodsService;
import com.bscott.fitness.tracker.translator.FoodItemTranslator;
import com.bscott.fitness.tracker.translator.UserTranslator;
import org.joda.time.LocalDate;
import org.junit.Assert;import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteFoodControllerTest {

    @Mock
    private FavoriteFoodsService favoriteFoodsService;
    @Mock
    private FoodItemTranslator foodItemTranslator;
    @Mock
    private UserTranslator userTranslator;

    @InjectMocks
    private FavoriteFoodsController favoriteFoodsController;

    private static final String FOODITEM_ID = "583a9be33004dfd16b956697";
    private static final String USER_ID = "5a47328424aa9a0047a31638";
    private FavoriteFoodItemDto favoriteFoodItemDto;
    private CustomizedFoodItemDto customizedFoodItemDto;
    private User user;
    private UserDto userDto;
    private FoodItem foodItem;
    private FoodItemDto foodItemDto;

    @Before
    public void setup(){

        favoriteFoodItemDto = new FavoriteFoodItemDto();
        favoriteFoodItemDto.setFoodItemId(FOODITEM_ID);
        favoriteFoodItemDto.setUserId(USER_ID);

        foodItem = new FoodItem();
        foodItem.setId(FOODITEM_ID);
        foodItem.setName("Banana");
        foodItem.setCalories(90);
        foodItem.setFatGrams(new BigDecimal("2.0"));
        foodItem.setCarbGrams(new BigDecimal("3.4"));
        foodItem.setProteinGrams(new BigDecimal("1.2"));

        user = new User();
        user.setId(USER_ID);
        user.setFirstName("Homer");
        user.setLastName("Simpson");
        user.setBirthDate(new LocalDate("1975-12-19"));
        user.getRoles().add(Constants.ADMIN_ROLE);

        userDto = new UserDto();

        foodItemDto = new FoodItemDto();
        foodItemDto.setName("Banana");
        foodItemDto.setCalories(90);
        foodItemDto.setFatGrams(new BigDecimal("2.0"));
        foodItemDto.setCarbGrams(new BigDecimal("3.4"));
        foodItemDto.setProteinGrams(new BigDecimal("1.2"));

        customizedFoodItemDto = new CustomizedFoodItemDto();
        customizedFoodItemDto.setUserId(USER_ID);
        customizedFoodItemDto.setFoodItem(foodItemDto);
    }

    @Test
    public void testAddFavoriteFood() {

        when(favoriteFoodsService.addFavoriteFoodItem(FOODITEM_ID, USER_ID)).thenReturn(user);
        when(userTranslator.toDto(user)).thenReturn(userDto);

        ResponseEntity response = favoriteFoodsController.addFavoriteFood(favoriteFoodItemDto);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void testDeleteFavoriteFood() {

        when(favoriteFoodsService.deleteFavoriteFoodItem(FOODITEM_ID, USER_ID)).thenReturn(user);
        when(userTranslator.toDto(user)).thenReturn(userDto);

        ResponseEntity response = favoriteFoodsController.deleteFavoriteFood(FOODITEM_ID, USER_ID);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void testAddCustomizedFood() throws BusinessLogicException {

        when(favoriteFoodsService.addCustomizedFoodItem(foodItem, USER_ID)).thenReturn(user);
        when(foodItemTranslator.toEntity(foodItemDto)).thenReturn(foodItem);
        when(userTranslator.toDto(user)).thenReturn(userDto);

        ResponseEntity response = favoriteFoodsController.addCustomizedFood(customizedFoodItemDto);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void testDeleteCustomizedFood(){

        when(favoriteFoodsService.deleteCustomizedFoodItem(FOODITEM_ID, USER_ID)).thenReturn(user);
        when(userTranslator.toDto(user)).thenReturn(userDto);

        ResponseEntity response = favoriteFoodsController.deleteCustomizedFood(FOODITEM_ID, USER_ID);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }
}
