package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.dto.FoodItemDto;
import com.bscott.fitness.tracker.model.FoodItem;
import com.bscott.fitness.tracker.exception.BusinessLogicException;
import com.bscott.fitness.tracker.service.FoodService;
import com.bscott.fitness.tracker.translator.FoodItemTranslator;
import org.junit.Assert;import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodControllerTest {

    @Mock
    private FoodService foodService;

    @Mock
    private FoodItemTranslator foodItemTranslator;

    @Mock
    private ServletUriComponentsBuilder mockServletUriComponentsBuilder;

    @Mock
    private UriComponentsBuilder mockUriComponentsBuilder;

    @Mock
    private UriComponents mockUriComponents;

    @InjectMocks
    private FoodController foodController;

    private static final String FOODITEM_ID = "583a9be33004dfd16b956697";
    private FoodItem foodItem;
    private FoodItemDto foodItemDto;
    private List<FoodItem> foodItemList;
    private List<FoodItemDto> foodItemDtoList;

    @Before
    public void setup(){

        foodItem = new FoodItem();
        foodItem.setId(FOODITEM_ID);
        foodItem.setName("Banana");
        foodItem.setCalories(90);
        foodItem.setFatGrams(new BigDecimal("2.0"));
        foodItem.setCarbGrams(new BigDecimal("3.4"));
        foodItem.setProteinGrams(new BigDecimal("1.2"));

        foodItemList = new ArrayList<>();
        foodItemList.add(foodItem);

        foodItemDto = new FoodItemDto();
        foodItemDto.setName("Banana");
        foodItemDto.setCalories(90);
        foodItemDto.setFatGrams(new BigDecimal("2.0"));
        foodItemDto.setCarbGrams(new BigDecimal("3.4"));
        foodItemDto.setProteinGrams(new BigDecimal("1.2"));

        foodItemDtoList = new ArrayList<>();
        foodItemDtoList.add(foodItemDto);
    }

    @Test
    public void testFindFoodById(){

        when(foodService.findFoodItemById(anyString())).thenReturn(foodItem);
        when(foodItemTranslator.toDto(foodItem)).thenReturn(foodItemDto);

        ResponseEntity response = foodController.findFoodById(FOODITEM_ID);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(foodItemDto, response.getBody());
    }

    @Test
    public void testFindFoodById_NotFound(){

        when(foodService.findFoodItemById(anyString())).thenReturn(null);

        ResponseEntity response = foodController.findFoodById(FOODITEM_ID);

        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        Assert.assertEquals(null, response.getBody());
    }

    @Test
    public void testFindFoodsBySearchCriteria(){

        when(foodService.findFoodItemsBySearchCriteria(anyString())).thenReturn(foodItemList);
        when(foodItemTranslator.toDtos(foodItemList)).thenReturn(foodItemDtoList);

        ResponseEntity response = foodController.findFoods("Banana");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals(foodItemDtoList, response.getBody());
    }

    @Test
    public void testFindFoodsBySearchCriteria_NotFound(){

        when(foodService.findFoodItemsBySearchCriteria(anyString())).thenReturn(null);

        ResponseEntity response = foodController.findFoods("Banana");

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Assert.assertEquals(new ArrayList<FoodItem>(), response.getBody());
    }

    @Test
    public void testAddFoodItem() throws BusinessLogicException{

        when(foodService.addFoodItem(foodItem)).thenReturn(foodItem);
        when(foodItemTranslator.toEntity(foodItemDto)).thenReturn(foodItem);
        when(foodItemTranslator.toDto(foodItem)).thenReturn(foodItemDto);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity response = foodController.addFoodItem(foodItemDto);

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(foodItemDto, response.getBody());
    }

    @Test
    public void testUpdateFoodItem(){

        when(foodService.updateFoodItem(foodItem)).thenReturn(foodItem);
        when(foodItemTranslator.toEntity(foodItemDto)).thenReturn(foodItem);
        when(foodItemTranslator.toDto(foodItem)).thenReturn(foodItemDto);

        ResponseEntity response = foodController.updateFoodItem(foodItemDto);

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(foodItemDto, response.getBody());
    }

    @Test
    public void testDeleteFoodItem(){

        doNothing().when(foodService).deleteFoodItem(FOODITEM_ID);

        ResponseEntity response = foodController.deleteFoodItem(FOODITEM_ID);

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
    }
}
