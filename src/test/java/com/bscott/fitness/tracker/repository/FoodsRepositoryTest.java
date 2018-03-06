package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.FitnessTrackerApplication;
import com.bscott.fitness.tracker.TestUtils;
import com.bscott.fitness.tracker.model.FoodItem;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
@SpringBootTest(classes = FitnessTrackerApplication.class)
@ActiveProfiles("test")
public class FoodsRepositoryTest {

    @Autowired
    private FoodsRepository foodsRepository;
    private TestUtils testUtils = new TestUtils();

    private String peanutButterId = "59e2cff8e4b06b700839b5fa";
    private String bananaId = "59e2cff8e4b06b700839b5fb";
    private String invalidId = "59e2cff8e4b06b700839b5fc";

    @After
    public void teardown(){
        testUtils.dropCollection(Constants.FOODS_COLLECTION);
    }

    @Test
    public void testFindById() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        FoodItem foodItem = foodsRepository.findOne(peanutButterId);
        assertNotNull(foodItem);
    }

    @Test
    public void testFindById_InvalidReturnsNull() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        FoodItem food = foodsRepository.findOne(invalidId);
        assertNull(food);
    }

    @Test
    public void testFindByName() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        FoodItem foodItem = foodsRepository.findFoodItemsByName("Banana");
        assertNotNull(foodItem);
        assertEquals("Banana", foodItem.getName());
    }

    @Test
    public void testFindByName_InvalidReturnsNull() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        FoodItem foodItem = foodsRepository.findFoodItemsByName("Taco");
        assertNull(foodItem);
    }

    @Test
    public void testFindAll() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        List<FoodItem> foodItems = foodsRepository.findAll();
        assertNotNull(foodItems);
        assertEquals(2, foodItems.size());
        assertThat(foodItems, Matchers.contains(
                HasPropertyWithValue.hasProperty("name", CoreMatchers.is("Peanut Butter")),
                HasPropertyWithValue.hasProperty("name", CoreMatchers.is("Banana"))
        ));
    }

    @Test
    public void testFindBySearchCriteria() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        List<FoodItem> foodItems = foodsRepository.findFoodItemsByNameLike("Banana");
        assertNotNull(foodItems);
        assertEquals(1, foodItems.size());

        assertThat(foodItems, Matchers.contains(
                HasPropertyWithValue.hasProperty("name", CoreMatchers.is("Banana"))
        ));
    }

    @Test
    public void testAddFoodItem(){

        FoodItem foodItem = new FoodItem();
        foodItem.setName("Apple");
        foodItem.setCalories(95);
        foodItem.setProteinGrams(new BigDecimal("0.5"));
        foodItem.setCarbGrams(new BigDecimal("25.0"));
        foodItem.setFatGrams(new BigDecimal("0.3"));

        assertNull(foodItem.getId());
        foodItem = foodsRepository.save(foodItem);
        assertNotNull(foodItem.getId());
    }

    @Test
    public void testUpdateFoodItem() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);
        FoodItem foodItem = foodsRepository.findOne(peanutButterId);
        foodItem.setName("PB");

        foodsRepository.save(foodItem);
        FoodItem updatedFoodItem = foodsRepository.findOne(peanutButterId);

        assertEquals("PB", updatedFoodItem.getName());
    }

    @Test
    public void testDeleteFoodItem() throws Exception{

        testUtils.importTestData("src/test/resources/foods/foodsCollection.json", FoodItem.class);

        FoodItem foodItem = foodsRepository.findOne(peanutButterId);
        assertNotNull(foodItem);

        foodsRepository.delete(peanutButterId);

        foodItem = foodsRepository.findOne(peanutButterId);
        assertNull(foodItem);
    }
}
