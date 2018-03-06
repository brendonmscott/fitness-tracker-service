package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.FitnessTrackerApplication;
import com.bscott.fitness.tracker.TestUtils;
import com.bscott.fitness.tracker.model.LoginCredentials;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataMongoTest
@SpringBootTest(classes = FitnessTrackerApplication.class)
@ActiveProfiles("test")
public class LoginRepositoryTest {

    @Autowired
    private LoginRepository loginRepository;
    private TestUtils testUtils = new TestUtils();

    private String loginId = "59f5344e8d6aa4686a59e128";
    private String userId = "59e2cff8e4b06b700839b5fa";
    private String email = "bartsimpson@springfield.gov";
    private String password = "krustyrulz";

    @After
    public void teardown(){ testUtils.dropCollection(Constants.LOGIN_COLLECTION); }

    @Test
    public void testFindById() throws Exception{

        testUtils.importTestData("src/test/resources/users/loginCollection.json", LoginCredentials.class);
        LoginCredentials loginCredentials = loginRepository.findOne(loginId);
        assertNotNull(loginCredentials);
    }

    @Test
    public void testFindById_InvalidReturnsNull() throws Exception{

        String invalidLoginId = "59f5344e8d6aa4686a59e127";
        testUtils.importTestData("src/test/resources/users/loginCollection.json", LoginCredentials.class);
        LoginCredentials loginCredentials = loginRepository.findOne(invalidLoginId);
        assertNull(loginCredentials);
    }

    @Test
    public void testFindLoginCredentials() throws Exception{

        testUtils.importTestData("src/test/resources/users/loginCollection.json", LoginCredentials.class);
        LoginCredentials loginCredentials = loginRepository.findLoginCredentialsByUserNameAndPassword(email, password);
        assertNotNull(loginCredentials);
        assertEquals(userId, loginCredentials.getUserId());
        assertEquals(email, loginCredentials.getUserName());
        assertEquals(password, loginCredentials.getPassword());
    }

    @Test
    public void testFindLoginCredentials_InvalidReturnsNull() throws Exception{

        testUtils.importTestData("src/test/resources/users/loginCollection.json", LoginCredentials.class);
        LoginCredentials loginCredentials = loginRepository.findLoginCredentialsByUserNameAndPassword(email, "badpassword");
        assertNull(loginCredentials);
    }

    @Test
    public void testAddLoginCredentials(){

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUserName(email);
        loginCredentials.setPassword(password);
        loginCredentials.setUserId(userId);

        assertNull(loginCredentials.getId());

        loginCredentials = loginRepository.save(loginCredentials);
        assertNotNull(loginCredentials.getId());
    }

    @Test
    public void testUpdateLoginCredentials() throws Exception{

        testUtils.importTestData("src/test/resources/users/loginCollection.json", LoginCredentials.class);
        LoginCredentials loginCredentials = loginRepository.findOne(loginId);
        loginCredentials.setUserName("bartsimpson@krustyland.edu");

        loginRepository.save(loginCredentials);
        LoginCredentials updatedLoginCredentials = loginRepository.findOne(loginId);

        assertEquals("bartsimpson@krustyland.edu", updatedLoginCredentials.getUserName());
    }

    @Test
    public void testDeleteLoginCredentials() throws Exception{

        testUtils.importTestData("src/test/resources/users/loginCollection.json", LoginCredentials.class);

        LoginCredentials loginCredentials = loginRepository.findOne(loginId);
        assertNotNull(loginCredentials);

        loginRepository.delete(loginId);

        loginCredentials = loginRepository.findOne(loginId);
        assertNull(loginCredentials);
    }
}
