package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.FitnessTrackerApplication;
import com.bscott.fitness.tracker.TestUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import com.bscott.fitness.tracker.model.User;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataMongoTest
@SpringBootTest(classes = FitnessTrackerApplication.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private TestUtils testUtils = new TestUtils();

    private String userId = "59e2cff8e4b06b700839b5fa";
    private String invalidUserId = "59e2cff8e4b06b700839b5fb";

    @After
    public void teardown(){
        testUtils.dropCollection(Constants.USER_COLLECTION);
    }

    @Test
    public void testFindById() throws Exception{

        testUtils.importTestData("src/test/resources/users/userCollection.json", User.class);
        User user = userRepository.findOne(userId);
        assertNotNull(user);
    }

    @Test
    public void testFindById_InvalidReturnsNull() throws Exception{

        testUtils.importTestData("src/test/resources/users/userCollection.json", User.class);
        User user = userRepository.findOne(invalidUserId);
        assertNull(user);
    }

    @Test
    public void testFindByName() throws Exception{

        testUtils.importTestData("src/test/resources/users/userCollection.json", User.class);
        List<User> users = userRepository.findUsersByFirstNameAndLastName("Bart", "Simpson");
        assertNotNull(users);
        assertThat(users, Matchers.contains(Matchers.allOf(
                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Bart")),
                HasPropertyWithValue.hasProperty("lastName", CoreMatchers.is("Simpson"))
        )));
    }

    @Test
    public void testFindByName_InvalidReturnsNull() throws Exception{

        testUtils.importTestData("src/test/resources/users/userCollection.json", User.class);
        List<User> users = userRepository.findUsersByFirstNameAndLastName("Bartholomew", "Simpson");
        assertTrue(users.isEmpty());
    }

    // FIXME
//    @Test
//    public void testAddUser(){
//
//        User user = new User();
//        user.setFirstName("Homer");
//        user.setLastName("Simpson");
//        user.setBirthDate(new LocalDate("1975-12-19"));
//        user.getRoles().add(Constants.ADMIN_USER);
//
//        assertNull(user.getId());
//        user = userRepository.save(user);
//        assertNotNull(user.getId());
//    }

    @Test
    public void testUpdateUser() throws Exception{

        testUtils.importTestData("src/test/resources/users/userCollection.json", User.class);
        User user = userRepository.findOne(userId);
        user.setFirstName("Bartholomew");

        userRepository.save(user);
        User updatedUser = userRepository.findOne(userId);

        assertEquals("Bartholomew", updatedUser.getFirstName());
    }

    @Test
    public void testDeleteUser() throws Exception{

        testUtils.importTestData("src/test/resources/users/userCollection.json", User.class);

        User user = userRepository.findOne(userId);
        assertNotNull(user);

        userRepository.delete(userId);

        user = userRepository.findOne(userId);
        assertNull(user);
    }
}
