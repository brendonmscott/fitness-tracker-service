// FIXME
//package com.bscott.fitness.tracker.controller;
//
//import com.bscott.fitness.tracker.Constants;
//import com.bscott.fitness.tracker.dto.AccountDto;
//import com.bscott.fitness.tracker.dto.LoginResponseDto;
//import com.bscott.fitness.tracker.dto.SignUpRequestDto;
//import com.bscott.fitness.tracker.dto.UserDto;
//import com.bscott.fitness.tracker.dto.LoginRequestDto;
//import com.bscott.fitness.tracker.model.Account;
//import com.bscott.fitness.tracker.model.LoginCredentials;
//import com.bscott.fitness.tracker.model.User;
//import com.bscott.fitness.tracker.exception.RegistrationException;
//import com.bscott.fitness.tracker.service.UserService;
//import com.bscott.fitness.tracker.translator.AccountTranslator;
//import com.bscott.fitness.tracker.translator.UserTranslator;
//import org.hamcrest.CoreMatchers;
//import org.joda.time.LocalDate;
//import org.junit.After;
//import org.junit.Assert;import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.ValidatorFactory;
//import javax.validation.executable.ExecutableValidator;
//
//import java.lang.reflect.Method;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserControllerTest {
//
//    @Mock
//    private UserService userService;
//    @Mock
//    private UserTranslator userTranslator;
//    @Mock
//    private AccountTranslator accountTranslator;
//
//    @InjectMocks
//    private UserController userController;
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    private Account account;
//    private AccountDto accountDto;
//    private User user;
//    private UserDto userDto;
//    private LoginRequestDto loginRequestDto;
//    private SignUpRequestDto signUpRequestDto;
//    private LoginCredentials loginCredentials;
//    private String accountId = "59e2cff8e4b06b700839b5fb";
//    private String email = "bart.simpson@gmail.com";
//    private String password = "krustyrulz";
//    private String userId = "59e2cff8e4b06b700839b5fa";
//    private ExecutableValidator executableValidator;
//
//    @Before
//    public void setup(){
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        executableValidator = factory.getValidator().forExecutables();
//
//        user = new User();
//        user.setFirstName("Bart");
//        user.setLastName("Simpson");
//        user.setBirthDate(new LocalDate("1994-09-01"));
//        user.setId(userId);
//
//        userDto = new UserDto();
//
//        loginRequestDto = new LoginRequestDto();
//        loginRequestDto.setEmail(email);
//        loginRequestDto.setPassword(password);
//
//        account = new Account();
//        account.setId(accountId);
//        account.setUserName(email);
//        account.setOwner(user);
//
//        accountDto = new AccountDto();
//        accountDto.setId(accountId);
//        accountDto.setUserName(email);
//        accountDto.setOwner(userDto);
//
//        loginCredentials = new LoginCredentials();
//        loginCredentials.setUserName(email);
//        loginCredentials.setPassword(password);
//        loginCredentials.setUserId(userId);
//    }
//
//    @After
//    public void cleanup(){
//        System.clearProperty(Constants.TOKEN_KEY_PROPERTY);
//    }
//
//    @Test
//    public void testFindUserById(){
//
//        when(userService.findUserById(anyString())).thenReturn(user);
//        when(userTranslator.toDto(any(User.class))).thenReturn(userDto);
//
//        ResponseEntity response = userController.findUserById(userId);
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//    }
//
//    @Test
//    public void testFindUserById_NotFound(){
//
//        when(userService.findUserById(anyString())).thenReturn(null);
//
//        ResponseEntity response = userController.findUserById(userId);
//
//        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
//    }
//
//    @Ignore("Fix when Spring Security Implemented")
//    @Test
//    public void testLogin() throws Exception{
//
//        System.setProperty(Constants.TOKEN_KEY_PROPERTY, "vEIszwWdElvZGKuIAMHR3BLiPfCmSWKUTeN8Eird453jsNoXg" +
//                "BHaiAnzwSK6B0fnaWNVh6gsG6fOYUtT4oycaJe4MysjvULaMk8NaPS7c23l6eBazxGtXvH4sOoaMq");
//
//        when(userService.login(anyString(), anyString())).thenReturn(user);
//
//        ResponseEntity response = userController.login(loginRequestDto);
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        LoginResponseDto loginResponseDto = (LoginResponseDto) response.getBody();
//        assertNotNull(loginResponseDto.getToken());
//    }
//
//    @Test
//    public void testLogin_UserNotFound() throws Exception{
//
//        System.setProperty(Constants.TOKEN_KEY_PROPERTY, "vEIszwWdElvZGKuIAMHR3BLiPfCmSWKUTeN8Eird453jsNoXg" +
//                "BHaiAnzwSK6B0fnaWNVh6gsG6fOYUtT4oycaJe4MysjvULaMk8NaPS7c23l6eBazxGtXvH4sOoaMq");
//
//        when(userService.login(anyString(), anyString())).thenReturn(null);
//
//        ResponseEntity response = userController.login(loginRequestDto);
//
//        Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
//    }
//
//    @Test
//    public void testLogin_InvalidUserName() throws Exception{
//
//        loginRequestDto.setEmail(null);
//        loginRequestDto.setPassword("password");
//
//        Method method = UserService.class.getMethod( "login", String.class, String.class);
//        Object[] parameterValues = { null, "password" };
//        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
//                userService,
//                method,
//                parameterValues
//        );
//
//        assertEquals( 1, violations.size() );
//    }
//
//    @Test
//    public void testLogin_InvalidPassword() throws Exception{
//
//        Method method = UserService.class.getMethod( "login", String.class, String.class);
//        Object[] parameterValues = { "username", null };
//        Set<ConstraintViolation<UserService>> violations = executableValidator.validateParameters(
//                userService,
//                method,
//                parameterValues
//        );
//
//        assertEquals( 1, violations.size() );
//    }
//
//    @Ignore("Fix when Spring Security Implemented")
//    @Test
//    public void testLogin_SystemPropertyMissing() throws Exception{
//
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(CoreMatchers.containsString("base64-encoded secret key cannot be null or empty"));
//
//        when(userService.login(anyString(), anyString())).thenReturn(user);
//
//        ResponseEntity response = userController.login(loginRequestDto);
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//    }
//
//    @Test
//    public void testRegisterUser() throws Exception{
//
//        signUpRequestDto = new SignUpRequestDto();
//        signUpRequestDto.setFirstName("Bart");
//        signUpRequestDto.setLastName("Simpson");
//        signUpRequestDto.setBirthDate("1994-09-01");
//        signUpRequestDto.setUserName("bart.simpson@gmail.com");
//        signUpRequestDto.setPassword("krustyrulz");
//
//        when(userService.registerUser(any(User.class), any(LoginCredentials.class))).thenReturn(account);
//        when(userTranslator.createNewUser(signUpRequestDto)).thenReturn(user);
//        when(userTranslator.getLoginCredentials(signUpRequestDto)).thenReturn(loginCredentials);
//        when(accountTranslator.toDto(any(Account.class))).thenReturn(accountDto);
//
//        ResponseEntity response = userController.registerUser(signUpRequestDto);
//        AccountDto responseAccount = (AccountDto) response.getBody();
//
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//        assertEquals(accountDto, responseAccount);
//    }
//
//    @Test
//    public void testRegisterUser_RegistrationException() throws Exception{
//
//        signUpRequestDto = new SignUpRequestDto();
//        signUpRequestDto.setFirstName("Bart");
//        signUpRequestDto.setLastName("Simpson");
//        signUpRequestDto.setBirthDate("1994-09-01");
//        signUpRequestDto.setUserName("bart.simpson@gmail.com");
//        signUpRequestDto.setPassword("krustyrulz");
//
//        when(userService.registerUser(any(User.class), any(LoginCredentials.class)))
//                .thenThrow(new RegistrationException("An error occurred trying to register a new user"));
//        when(userTranslator.createNewUser(signUpRequestDto)).thenReturn(user);
//        when(userTranslator.getLoginCredentials(signUpRequestDto)).thenReturn(loginCredentials);
//
//        ResponseEntity response = userController.registerUser(signUpRequestDto);
//
//        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    public void testUpdateUser(){
//
//        when(userTranslator.toEntity(userDto)).thenReturn(user);
//        when(userTranslator.toDto(user)).thenReturn(userDto);
//        when(userService.updateUser(user)).thenReturn(user);
//
//        ResponseEntity response = userController.updateUser(userDto);
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//    }
//
//    @Test
//    public void testDeleteUser(){
//
//        doNothing().when(userService).deleteUser(userId);
//
//        ResponseEntity response = userController.deleteUser(userId);
//        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
//    }
//}
