package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.dto.FoodItemDto;
import com.bscott.fitness.tracker.repository.FoodsRepository;
import com.bscott.fitness.tracker.model.FoodItem;
import com.bscott.fitness.tracker.exception.BusinessLogicException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodServiceTest {

    @Mock
    private FoodsRepository foodsRepository;

    @InjectMocks
    private FoodService foodService = new FoodService(foodsRepository);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String FOODITEM_ID = "583a9be33004dfd16b956697";
    private ExecutableValidator executableValidator;
    private FoodItem foodItem;
    private FoodItemDto foodItemDto;
    private List<FoodItemDto> foodItemDtoList;
    private List<FoodItem> foodItemList;

    @Before
    public void setup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        executableValidator = factory.getValidator().forExecutables();

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
    public void testFindFoodItemById(){

        when(foodsRepository.findOne(FOODITEM_ID)).thenReturn(foodItem);
        FoodItem foundFoodItem = foodService.findFoodItemById(FOODITEM_ID);

        assertNotNull(foundFoodItem);
        assertEquals(foodItem.getName(), foundFoodItem.getName());
        assertEquals(foodItem.getCalories(), foundFoodItem.getCalories());
        assertEquals(foodItem.getCarbGrams(), foundFoodItem.getCarbGrams());
        assertEquals(foodItem.getFatGrams(), foundFoodItem.getFatGrams());
        assertEquals(foodItem.getProteinGrams(), foundFoodItem.getProteinGrams());
        assertEquals(foodItem.getId(), foundFoodItem.getId());
        assertEquals(FOODITEM_ID, foundFoodItem.getId());
    }

    @Test
    public void testFindFoodItemById_NullId() throws Exception{

        Method method = FoodService.class.getMethod( "findFoodItemById", String.class);
        Object[] parameterValues = { null };
        Set<ConstraintViolation<FoodService>> violations = executableValidator.validateParameters(
                foodService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindFoodItemById_BlankId() throws Exception{

        Method method = FoodService.class.getMethod( "findFoodItemById", String.class);
        Object[] parameterValues = { "" };
        Set<ConstraintViolation<FoodService>> violations = executableValidator.validateParameters(
                foodService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindFoodItemByName(){

        when(foodsRepository.findFoodItemsByNameLike("Banana")).thenReturn(foodItemList);
        List<FoodItem> foodItems = foodService.findFoodItemsBySearchCriteria("Banana");

        assertNotNull(foodItems);
        assertThat(foodItems, Matchers.contains(Matchers.allOf(
                HasPropertyWithValue.hasProperty("name", CoreMatchers.is("Banana")))
        ));
    }

    @Test
    public void testFindFoodItemByName_NullName() {

        when(foodsRepository.findFoodItemsByNameLike(null)).thenReturn(new ArrayList<>());
        List<FoodItem> foodItems = foodService.findFoodItemsBySearchCriteria(null);

        assertNotNull(foodItems);
        assertTrue(foodItems.isEmpty());
    }

    @Test
    public void testFindFoodItemByName_BlankName() {

        when(foodsRepository.findFoodItemsByNameLike("")).thenReturn(new ArrayList<>());
        List<FoodItem> foodItems = foodService.findFoodItemsBySearchCriteria("");

        assertNotNull(foodItems);
        assertTrue(foodItems.isEmpty());
    }

    @Test
    public void testAddFoodItem() throws BusinessLogicException{

        when(foodsRepository.save(foodItem)).thenReturn(foodItem);

        FoodItem addedFoodItem = foodService.addFoodItem(foodItem);

        assertEquals(foodItem, addedFoodItem);
    }

    @Test
    public void testAddFoodItemFoodAlreadyExists() throws BusinessLogicException{

        thrown.expect(BusinessLogicException.class);
        thrown.expectMessage("Food Item Banana already exists");

        when(foodsRepository.findFoodItemsByName(anyString())).thenReturn(foodItem);

        FoodItem addedFoodItem = foodService.addFoodItem(foodItem);
        assertNull(addedFoodItem);
    }

    @Test
    public void test_UpdateFoodItem() {

        when(foodsRepository.save(foodItem)).thenReturn(foodItem);

        FoodItem updatedFoodItem = foodService.updateFoodItem(foodItem);

        assertEquals(foodItem, updatedFoodItem);
    }

    @Test
    public void test_UpdateFoodItem_NullFoodItem() throws Exception{
        Method method = FoodService.class.getMethod( "updateFoodItem", FoodItem.class);
        Object[] parameterValues = { null };
        Set<ConstraintViolation<FoodService>> violations = executableValidator.validateParameters(
                foodService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void test_UpdateFoodItem_InvalidFoodItem() throws Exception{

        foodItem.setName(null);

        Method method = FoodService.class.getMethod( "updateFoodItem", FoodItem.class);
        Object[] parameterValues = { foodItem };
        Set<ConstraintViolation<FoodService>> violations = executableValidator.validateParameters(
                foodService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void test_DeleteFoodItem() {

        doNothing().when(foodsRepository).delete(FOODITEM_ID);
        foodService.deleteFoodItem(FOODITEM_ID);

        verify(foodsRepository, times(1)).delete(FOODITEM_ID);
    }

    @Test
    public void test_DeleteFoodItem_NullId() throws Exception{
        Method method = FoodService.class.getMethod( "deleteFoodItem", String.class);
        Object[] parameterValues = { null };
        Set<ConstraintViolation<FoodService>> violations = executableValidator.validateParameters(
                foodService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void test_DeleteFoodItem_BlankId() throws Exception{
        Method method = FoodService.class.getMethod( "deleteFoodItem", String.class);
        Object[] parameterValues = { "" };
        Set<ConstraintViolation<FoodService>> violations = executableValidator.validateParameters(
                foodService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }
}
