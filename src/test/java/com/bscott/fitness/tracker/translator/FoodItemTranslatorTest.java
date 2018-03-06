package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.dto.FoodItemDto;
import com.bscott.fitness.tracker.model.FoodItem;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class FoodItemTranslatorTest {

    private FoodItemTranslator foodItemTranslator = new FoodItemTranslator();
    private FoodItem foodItem, foodItem2;
    private FoodItemDto foodItemDto;
    private List<FoodItem> foodItems;

    @Before
    public void setup(){

        foodItem = new FoodItem();
        foodItem.setName("Apple");
        foodItem.setCalories(100);
        foodItem.setCarbGrams(new BigDecimal("12.5"));
        foodItem.setFatGrams(new BigDecimal("4.0"));
        foodItem.setProteinGrams(new BigDecimal("11.0"));

        foodItem2 = new FoodItem();
        foodItem2.setName("Banana");
        foodItem2.setCalories(300);
        foodItem2.setCarbGrams(new BigDecimal("2.0"));
        foodItem2.setFatGrams(new BigDecimal("14.0"));
        foodItem2.setProteinGrams(new BigDecimal("1.0"));

        foodItems = new ArrayList<>();
        foodItems.add(foodItem);
        foodItems.add(foodItem2);

        foodItemDto = new FoodItemDto();
        foodItemDto.setCalories(100);
        foodItemDto.setCarbGrams(new BigDecimal("12.5"));
        foodItemDto.setFatGrams(new BigDecimal("4.0"));
        foodItemDto.setProteinGrams(new BigDecimal("11.0"));
    }

    @Test
    public void testToDto(){

        FoodItemDto foodItemDto = foodItemTranslator.toDto(foodItem);
        assertNotNull(foodItemDto);
        assertEquals(foodItem.getCalories(), foodItemDto.getCalories());
        assertEquals(foodItem.getCarbGrams(), foodItemDto.getCarbGrams());
        assertEquals(foodItem.getFatGrams(), foodItemDto.getFatGrams());
        assertEquals(foodItem.getProteinGrams(), foodItemDto.getProteinGrams());
    }

    @Test
    public void testToDto_NullEntity(){

        FoodItemDto foodItemDto = foodItemTranslator.toDto(null);
        assertNull(foodItemDto);
    }

    @Test
    public void testToDtos(){

        List<FoodItemDto> foodItemDtos = foodItemTranslator.toDtos(foodItems);
        assertEquals(2, foodItemDtos.size());
        assertThat(foodItemDtos, Matchers.contains(
                HasPropertyWithValue.hasProperty("name", CoreMatchers.is("Apple")),
                HasPropertyWithValue.hasProperty("name", CoreMatchers.is("Banana")))
        );
    }

    @Test
    public void testToDtos_NullEntityList(){

        List<FoodItemDto> foodItemDtos = foodItemTranslator.toDtos(null);
        assertEquals(0, foodItemDtos.size());
    }

    @Test
    public void testToEntity(){

        FoodItem foodItem = foodItemTranslator.toEntity(foodItemDto);
        assertNotNull(foodItem);
        assertEquals(foodItemDto.getCalories(), foodItem.getCalories());
        assertEquals(foodItemDto.getCarbGrams(), foodItem.getCarbGrams());
        assertEquals(foodItemDto.getFatGrams(), foodItem.getFatGrams());
        assertEquals(foodItemDto.getProteinGrams(), foodItem.getProteinGrams());
    }

    @Test
    public void testToEntity_NullDto(){

        FoodItem foodItem = foodItemTranslator.toEntity(null);
        assertNull(foodItem);
    }
}
