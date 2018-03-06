package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.dto.RegisterUserDto;
import com.bscott.fitness.tracker.dto.UserDto;
import com.bscott.fitness.tracker.model.LoginCredentials;
import com.bscott.fitness.tracker.model.User;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserTranslatorTest {

    private UserTranslator userTranslator = new UserTranslator();
    private RegisterUserDto registerUserDto;
    private User user1, user2;
    private UserDto userDto;
    private List<User> userList;

    @Before
    public void setup(){

        registerUserDto = new RegisterUserDto();
        registerUserDto.setFirstName("Homer");
        registerUserDto.setLastName("Simpson");
        registerUserDto.setBirthDate("12/19/1975");
        registerUserDto.setEmail("homer.simpson@gmail.com");
        registerUserDto.setPassword("mmmDonuts");

        user1 = new User();
        user1.setFirstName("Homer");
        user1.setLastName("Simpson");
        user1.setBirthDate(new LocalDate("1975-12-19"));
        user1.getRoles().add(Constants.ADMIN_ROLE);

        user2 = new User();
        user2.setFirstName("Marge");
        user2.setLastName("Simpson");
        user2.setBirthDate(new LocalDate("1976-08-20"));
        user2.getRoles().add(Constants.ADMIN_ROLE);

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        userDto = new UserDto();
        userDto.setFirstName("Homer");
        userDto.setLastName("Simpson");
        userDto.setBirthDate("12/19/1975");
        userDto.getRoles().add(Constants.ADMIN_ROLE);
    }

    @Test
    public void testToDto_NullEntity(){

        UserDto userDto = userTranslator.toDto(null);
        assertNull(userDto);
    }

    @Test
    public void testToDto(){

        UserDto userDto = userTranslator.toDto(user1);
        assertEquals(user1.getFirstName(), userDto.getFirstName());
        assertEquals(user1.getLastName(), userDto.getLastName());
        assertEquals(user1.getBirthDate().toString("MM/dd/YYYY"), userDto.getBirthDate());
        assertThat(user1.getRoles(), CoreMatchers.hasItem(Constants.ADMIN_ROLE));
    }

    @Test
    public void testToDtos_NullUsers(){

        List<UserDto> userDtos = userTranslator.toDtos(null);
        assertNotNull(userDtos);
        assertTrue(userDtos.isEmpty());
    }

    @Test
    public void testToDtos(){

        List<UserDto> userDtos = userTranslator.toDtos(userList);
        assertThat(userDtos, Matchers.hasSize(2));
        assertThat(userDtos, Matchers.contains(
                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Homer")),
                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Marge"))
                ));
    }

    @Test
    public void testToEntity_NullDto(){

        User user = userTranslator.toEntity(null);
        assertNull(user);
    }

    @Test
    public void testToEntity(){

        User user = userTranslator.toEntity(userDto);
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getBirthDate(), user.getBirthDate().toString("MM/dd/YYYY"));
        assertThat(userDto.getRoles(), CoreMatchers.hasItem(Constants.ADMIN_ROLE));
    }

    @Test
    public void testCreateNewUser_NullDto(){

        User user = userTranslator.createNewUser(null);
        assertNull(user);
    }

    @Test
    public void testCreateNewUser(){

        User user = userTranslator.createNewUser(registerUserDto);
        assertNotNull(user);
        assertEquals(registerUserDto.getFirstName(), user.getFirstName());
        assertEquals(registerUserDto.getLastName(), user.getLastName());
        assertEquals("1975-12-19", user.getBirthDate().toString());
        assertThat(user.getRoles(), CoreMatchers.hasItem(Constants.ADMIN_ROLE));
    }

    @Test
    public void testGetLoginCredentials_NullDto(){

        LoginCredentials loginCredentials = userTranslator.getLoginCredentials(null);
        assertNull(loginCredentials);
    }

    @Test
    public void testGetLoginCredentials(){

        LoginCredentials loginCredentials = userTranslator.getLoginCredentials(registerUserDto);
        assertNotNull(loginCredentials);
        assertEquals(registerUserDto.getEmail(), loginCredentials.getUserName());
        assertEquals(registerUserDto.getPassword(), loginCredentials.getPassword());
    }
}
