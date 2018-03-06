package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.FitnessTrackerApplication;
import com.bscott.fitness.tracker.TestUtils;
import com.bscott.fitness.tracker.model.Account;
import com.bscott.fitness.tracker.model.User;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataMongoTest
@SpringBootTest(classes = FitnessTrackerApplication.class)
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    private TestUtils testUtils = new TestUtils();

    private String accountId = "59f5344e8d6aa4686a59e128";
    private String userId = "59e2cff8e4b06b700839b5fa";
    private String email = "bartsimpson@springfield.gov";
    private User accountOwner;
    private User friendOne;
    private User friendTwo;

    @Before
    public void setup(){

        String friendOneId = "59e2cff8e4b06b700839b5fb";
        String friendTwoId = "59e2cff8e4b06b700839b5fc";

        accountOwner = new User();
        accountOwner.setId(userId);
        accountOwner.setFirstName("Bart");
        accountOwner.setLastName("Simpson");
        accountOwner.setBirthDate(new LocalDate("1994-09-01"));

        friendOne = new User();
        friendOne.setId(friendOneId);
        friendOne.setFirstName("Milhouse");
        friendOne.setLastName("VanHouten");
        friendOne.setBirthDate(new LocalDate("1994-10-05"));

        friendTwo = new User();
        friendTwo.setId(friendTwoId);
        friendTwo.setFirstName("Nelson");
        friendTwo.setLastName("Muntz");
        friendTwo.setBirthDate(new LocalDate("1993-02-14"));
    }

    @After
    public void teardown(){
        testUtils.dropCollection(Constants.ACCOUNT_COLLECTION);
    }

    @Test
    public void testFindById() throws Exception{

        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);
        Account account = accountRepository.findOne(accountId);
        assertNotNull(account);
    }

    @Test
    public void testFindById_InvalidReturnsNull() throws Exception{

        String invalidAccountId = "59f5344e8d6aa4686a59e127";
        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);
        Account account = accountRepository.findOne(invalidAccountId);
        assertNull(account);
    }

    @Test
    public void testFindByEmail() throws Exception{

        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);
        Account account = accountRepository.findByEmail(email);
        assertNotNull(account);
        assertEquals(userId, account.getOwner().getId());
    }

    @Test
    public void testFindByEmail_InvalidReturnsNull() throws Exception{

        String invalidEmail = "bartsimpson@krustyland.org";
        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);
        Account account = accountRepository.findByEmail(invalidEmail);
        assertNull(account);
    }

    @Test
    public void testAccountExists() throws Exception{

        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);
        Boolean accountExists = accountRepository.accountExists(email);
        assertTrue(accountExists);
    }

    @Test
    public void testAccountDoesNotExists(){

        Boolean accountExists = accountRepository.accountExists(email);
        assertFalse(accountExists);
    }

    @Test
    public void testAddAccount(){

        List<User> friends = new ArrayList<>();
        friends.add(friendOne);
        friends.add(friendTwo);

        Account account = new Account();
        account.setOwner(accountOwner);
        account.setEmail(email);
        account.setFriends(friends);

        assertNull(account.getId());

        account = accountRepository.save(account);
        assertNotNull(account.getId());
        assertEquals(email, account.getEmail());
        assertEquals(accountOwner, account.getOwner());
        assertEquals(2, account.getFriends().size());
    }

    @Test
    public void testUpdateAccount() throws Exception{

        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);
        Account account = accountRepository.findOne(accountId);

        assertEquals(0, account.getFriends().size());
        account.getFriends().add(friendOne);

        accountRepository.save(account);
        Account updatedAccount = accountRepository.findOne(accountId);

        assertEquals(1, updatedAccount.getFriends().size());
    }

    @Test
    public void testDeleteAccount() throws Exception{

        testUtils.importTestData("src/test/resources/users/accountCollection.json", Account.class);

        Account account = accountRepository.findOne(accountId);
        assertNotNull(account);

        accountRepository.delete(accountId);

        account = accountRepository.findOne(accountId);
        assertNull(account);
    }
}
