package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.exception.AuthenticationException;
import com.bscott.fitness.tracker.repository.AccountRepository;
import com.bscott.fitness.tracker.repository.LoginRepository;
import com.bscott.fitness.tracker.model.Account;
import com.bscott.fitness.tracker.model.LoginCredentials;
import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.exception.RegistrationException;
import com.bscott.fitness.tracker.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.joda.time.LocalDate;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private LoginRepository loginRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserService(userRepository, accountRepository, loginRepository);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String USER_ID = "583a9be33004dfd16b956697";
    private ExecutableValidator executableValidator;
    private User user;
    private List<User> userList;
    private LoginCredentials loginCredentials;

    @Before
    public void setup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        executableValidator = factory.getValidator().forExecutables();

        user = new User();
        user.setId(USER_ID);
        user.setFirstName("Homer");
        user.setLastName("Simpson");
        user.setBirthDate(new LocalDate("1975-12-19"));
        user.getRoles().add(Constants.ADMIN_ROLE);

        userList = new ArrayList<>();
        userList.add(user);

        loginCredentials = new LoginCredentials();
        loginCredentials.setUserName("homer.simpson@gmail.com");
        loginCredentials.setPassword("mmmDonuts");
        loginCredentials.setUserId("583a9be33004dfd16b956697");
    }

    @Test
    public void testFindUserById(){

        when(userRepository.findOne(USER_ID)).thenReturn(user);
        User foundUser = userService.findUserById(USER_ID);

        assertNotNull(foundUser);
        assertEquals(user.getFirstName(), foundUser.getFirstName());
        assertEquals(user.getLastName(), foundUser.getLastName());
        assertEquals(user.getBirthDate(), foundUser.getBirthDate());
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(USER_ID, foundUser.getId());
        assertEquals(user.getRoles().size(), foundUser.getRoles().size());
    }

    @Test
    public void testFindUserById_NullId() throws Exception{

        Method method = UserService.class.getMethod( "findUserById", String.class);
        Object[] parameterValues = { null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindUserById_BlankId() throws Exception{

        Method method = UserService.class.getMethod( "findUserById", String.class);
        Object[] parameterValues = { "" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindUsersByName(){

        when(userRepository.findUsersByFirstNameAndLastName("Homer", "Simpson")).thenReturn(userList);
        List<User> userList = userService.findUsersByName("Homer", "Simpson");

        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertThat(userList, Matchers.contains(Matchers.allOf(
                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Homer")),
                HasPropertyWithValue.hasProperty("lastName", CoreMatchers.is("Simpson"))
        )));
    }

    @Test
    public void testFindUsersByName_NullFirstName() throws Exception{

        Method method = UserService.class.getMethod( "findUsersByName", String.class, String.class);
        Object[] parameterValues = { null, "Simpson" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindUsersByName_NullLastName() throws Exception{

        Method method = UserService.class.getMethod( "findUsersByName", String.class, String.class);
        Object[] parameterValues = { "Homer", null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindUsersByName_NullFirstAndLastName() throws Exception{

        Method method = UserService.class.getMethod( "findUsersByName", String.class, String.class);
        Object[] parameterValues = { null, null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 2, violations.size() );
    }

    @Test
    public void testFindUsersByName_BlankFirstName() throws Exception{

        Method method = UserService.class.getMethod( "findUsersByName", String.class, String.class);
        Object[] parameterValues = { "", "Simpson" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindUsersByName_BlankLastName() throws Exception{

        Method method = UserService.class.getMethod( "findUsersByName", String.class, String.class);
        Object[] parameterValues = { "Homer", "" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testFindUsersByName_BlankFirstAndLastName() throws Exception{

        Method method = UserService.class.getMethod( "findUsersByName", String.class, String.class);
        Object[] parameterValues = { "", "" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 2, violations.size() );
    }

    @Test
    public void testRegisterUser() throws Exception {

        Account account = new Account();
        account.setOwner(user);
        account.setId(USER_ID);

        when(accountRepository.accountExists(loginCredentials.getUserName())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(accountRepository.save(account)).thenReturn(account);
        when(loginRepository.save(loginCredentials)).thenReturn(loginCredentials);

        Account newAccount = userService.registerUser(user, loginCredentials);

        assertNotNull(newAccount);
        assertEquals(account.getOwner(), newAccount.getOwner());
        assertTrue(account.getFriends().isEmpty());
    }

    @Test
    public void testRegisterUser_EmailExists() throws Exception {

        Account account = new Account();
        account.setOwner(user);
        account.setId(USER_ID);

        when(accountRepository.accountExists(loginCredentials.getUserName())).thenReturn(true);

        thrown.expect(RegistrationException.class);
        thrown.expectMessage(CoreMatchers.containsString("There is already an account with this userName"));
        Account newAccount = userService.registerUser(user, loginCredentials);

        assertNull(newAccount);
    }

    @Test
    public void testRegisterUser_NullInput() throws Exception{

        Method method = UserService.class.getMethod( "registerUser", User.class, LoginCredentials.class);
        Object[] parameterValues = { null, null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 2, violations.size() );
    }

    @Test
    public void testRegisterUser_NullUser() throws Exception{

        Method method = UserService.class.getMethod( "registerUser", User.class, LoginCredentials.class);
        Object[] parameterValues = { null, new LoginCredentials() };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 3, violations.size() );
    }

    @Test
    public void testRegisterUser_NullLoginCredentials() throws Exception{

        Method method = UserService.class.getMethod( "registerUser", User.class, LoginCredentials.class);
        Object[] parameterValues = { new User(), null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 4, violations.size() );
    }

    @Test
    public void testLogin() throws Exception{

        String userId = "testUser";
        String password = "testPassword";

        when(loginRepository.findLoginCredentialsByUserNameAndPassword(userId,password)).thenReturn(loginCredentials);
        when(userRepository.findOne(loginCredentials.getUserId())).thenReturn(user);

        User loginUser = userService.login(userId, password);

        assertNotNull(loginUser);
        assertEquals(user.getFirstName(), loginUser.getFirstName());
        assertEquals(user.getLastName(), loginUser.getLastName());
        assertEquals(user.getBirthDate(), loginUser.getBirthDate());
        assertEquals(user.getId(), loginUser.getId());
        assertEquals(user.getRoles().size(), loginUser.getRoles().size());
    }

    @Test
    public void testLogin_NullUser() throws Exception{

        Method method = UserService.class.getMethod( "login", String.class, String.class);
        Object[] parameterValues = { null, "testPassword" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testLogin_BlankUser() throws Exception{

        Method method = UserService.class.getMethod( "login", String.class, String.class);
        Object[] parameterValues = { "", "testPassword" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testLogin_NullPassword() throws Exception{

        Method method = UserService.class.getMethod( "login", String.class, String.class);
        Object[] parameterValues = { "testUser", null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testLogin_BlankPassword() throws Exception{

        Method method = UserService.class.getMethod( "login", String.class, String.class);
        Object[] parameterValues = { "testUser", "" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void testLogin_NullUserAndPassword() throws Exception{

        Method method = UserService.class.getMethod( "login", String.class, String.class);
        Object[] parameterValues = { null, null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 2, violations.size() );
    }

    @Test
    public void testLogin_BlankUserAndPassword() throws Exception{

        Method method = UserService.class.getMethod( "login", String.class, String.class);
        Object[] parameterValues = { "", "" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 2, violations.size() );
    }

    @Test
    public void testLogin_LoginCredentialsNotFound() throws Exception{

        String userId = "testUser";
        String password = "testPassword";

        thrown.expect(AuthenticationException.class);
        thrown.expectMessage(CoreMatchers.containsString("User not found"));

        when(loginRepository.findLoginCredentialsByUserNameAndPassword(userId,password)).thenReturn(null);

        userService.login(userId, password);
    }

    @Test
    public void test_UpdateUser() {

        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertEquals(user, updatedUser);
    }

    @Test
    public void test_UpdateUser_NullUser() throws Exception{
        Method method = UserService.class.getMethod( "updateUser", User.class);
        Object[] parameterValues = { null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void test_UpdateUser_InvalidUser() throws Exception{

        user.setFirstName(null);

        Method method = UserService.class.getMethod( "updateUser", User.class);
        Object[] parameterValues = { user };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void test_DeleteUser() {

        doNothing().when(userRepository).delete(USER_ID);
        userService.deleteUser(USER_ID);
        verify(userRepository, times(1)).delete(USER_ID);
    }

    @Test
    public void test_DeleteUser_NullId() throws Exception{
        Method method = UserService.class.getMethod( "deleteUser", String.class);
        Object[] parameterValues = { null };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }

    @Test
    public void test_DeleteUser_BlankId() throws Exception{
        Method method = UserService.class.getMethod( "deleteUser", String.class);
        Object[] parameterValues = { "" };
        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
                userService,
                method,
                parameterValues
        );

        assertEquals( 1, violations.size() );
    }
}
