// FIXME
//package com.bscott.fitness.tracker.service;
//
//import com.bscott.fitness.tracker.Constants;
//import com.bscott.fitness.tracker.FitnessTrackerApplication;
//import com.bscott.fitness.tracker.model.FoodItem;
//import com.bscott.fitness.tracker.model.User;
//import com.bscott.fitness.tracker.exception.BusinessLogicException;
//import org.bson.types.ObjectId;
//import org.hamcrest.Matchers;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = FitnessTrackerApplication.class)
//public class FavoriteFoodServiceTest {
//
//    @Mock
//    private FoodService foodService;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private FavoriteFoodsService favoriteFoodsService = new FavoriteFoodsService(foodService, userService);
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    private static final String FOODITEM_ID = "583a9be33004dfd16b956697";
//    private static final String USER_ID = "5a47328424aa9a0047a31638";
//    private User user;
//    private FoodItem foodItem;
//
//    @Before
//    public void setup(){
//
//        user = new User();
//        user.setId(USER_ID);
//        user.setFirstName("Homer");
//        user.setLastName("Simpson");
//        user.setBirthDate(new LocalDate("1975-12-19"));
//        user.getRoles().add(Constants.ADMIN_USER);
//
//        foodItem = new FoodItem();
//        foodItem.setId(FOODITEM_ID);
//        foodItem.setName("Banana");
//        foodItem.setCalories(90);
//        foodItem.setFatGrams(new BigDecimal("2.0"));
//        foodItem.setCarbGrams(new BigDecimal("3.4"));
//        foodItem.setProteinGrams(new BigDecimal("1.2"));
//    }
//
//    @Test
//    public void testAddFavoriteFoodItem(){
//
//        when(userService.findUserById(USER_ID)).thenReturn(user);
//        when(userService.updateUser(user)).thenReturn(user);
//
//        assertTrue(user.getFavoriteFoodItems().isEmpty());
//
//        User user = favoriteFoodsService.addFavoriteFoodItem(FOODITEM_ID, USER_ID);
//        assertNotNull(user);
//        assertThat(user.getFavoriteFoodItems(), Matchers.contains(new ObjectId(FOODITEM_ID)));
//    }
//
//    @Test
//    public void testAddFavoriteFoodItem_ItemAlreadyExists(){
//
//        when(userService.findUserById(USER_ID)).thenReturn(user);
//
//        user.getFavoriteFoodItems().add(new ObjectId(FOODITEM_ID));
//        assertThat(user.getFavoriteFoodItems(), Matchers.contains(new ObjectId(FOODITEM_ID)));
//
//        User user = favoriteFoodsService.addFavoriteFoodItem(FOODITEM_ID, USER_ID);
//        assertNotNull(user);
//        assertThat(user.getFavoriteFoodItems(), Matchers.contains(new ObjectId(FOODITEM_ID)));
//    }
//
//    @Test
//    public void testDeleteFavoriteFoodItem(){
//
//        when(userService.findUserById(anyString())).thenReturn(user);
//        when(userService.updateUser(user)).thenReturn(user);
//
//        user.getFavoriteFoodItems().add(new ObjectId(FOODITEM_ID));
//        assertThat(user.getFavoriteFoodItems(), Matchers.contains(new ObjectId(FOODITEM_ID)));
//
//        User user = favoriteFoodsService.deleteFavoriteFoodItem(FOODITEM_ID, USER_ID);
//        assertNotNull(user);
//        assertTrue(user.getFavoriteFoodItems().isEmpty());
//    }
//
//    @Test
//    public void testDeleteFavoriteFoodItem_ItemDoesNotExist(){
//
//        when(userService.findUserById(USER_ID)).thenReturn(user);
//        when(userService.updateUser(user)).thenReturn(user);
//
//        assertTrue(user.getFavoriteFoodItems().isEmpty());
//
//        User user = favoriteFoodsService.deleteFavoriteFoodItem(FOODITEM_ID, USER_ID);
//        assertNotNull(user);
//        assertTrue(user.getFavoriteFoodItems().isEmpty());
//    }
//
//    @Test
//    public void testAddCustomizedFoodItem() throws BusinessLogicException{
//
//        when(foodService.addFoodItem(foodItem)).thenReturn(foodItem);
//        when(userService.findUserById(USER_ID)).thenReturn(user);
//        when(userService.updateUser(user)).thenReturn(user);
//
//        assertTrue(user.getCustomizedFoodItems().isEmpty());
//
//        User user = favoriteFoodsService.addCustomizedFoodItem(foodItem, USER_ID);
//        assertNotNull(user);
//        assertThat(user.getCustomizedFoodItems(), Matchers.contains(new ObjectId(FOODITEM_ID)));
//    }
//
//    @Test
//    public void testDeleteCustomizedFoodItem(){
//
//        doNothing().when(foodService).deleteFoodItem(FOODITEM_ID);
//        when(userService.findUserById(USER_ID)).thenReturn(user);
//        when(userService.updateUser(user)).thenReturn(user);
//
//        user.getCustomizedFoodItems().add(new ObjectId(FOODITEM_ID));
//        assertThat(user.getCustomizedFoodItems(), Matchers.contains(new ObjectId(FOODITEM_ID)));
//
//        User user = favoriteFoodsService.deleteCustomizedFoodItem(FOODITEM_ID, USER_ID);
//        assertNotNull(user);
//        assertTrue(user.getCustomizedFoodItems().isEmpty());
//    }
//}
