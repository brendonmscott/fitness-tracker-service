package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.dto.SignUpRequestDto;
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
    private SignUpRequestDto signUpRequestDto;
    private User user1, user2;
    private UserDto userDto;
    private List<User> userList;

    @Before
    public void setup(){

        signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setFirstName("Homer");
        signUpRequestDto.setLastName("Simpson");
        signUpRequestDto.setBirthDate("12/19/1975");
        signUpRequestDto.setUsername("homer.simpson@gmail.com");
        signUpRequestDto.setPassword("mmmDonuts");

        user1 = new User("Homer", "Simpson", new LocalDate("1975-12-19"),
                "MaxPower", "feelthegees@maxpower.com", "donut");
        user1.getRoles().add(Constants.ADMIN_USER);

        user2 = new User("Marge", "Simpson", new LocalDate("1976-08-20"),
                "marge", "marge@simpsons.com", "homey");
        user2.getRoles().add(Constants.ADMIN_USER);

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        userDto = new UserDto();
        userDto.setFirstName("Homer");
        userDto.setLastName("Simpson");
        userDto.setBirthDate("12/19/1975");
        userDto.getRoles().add(Constants.ADMIN_USER);
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
        assertThat(user1.getRoles(), CoreMatchers.hasItem(Constants.ADMIN_USER));
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
        assertThat(userDto.getRoles(), CoreMatchers.hasItem(Constants.ADMIN_USER));
    }

    @Test
    public void testCreateNewUser_NullDto(){

        User user = userTranslator.toEntity(null);
        assertNull(user);
    }

    @Test
    public void testGetLoginCredentials_NullDto(){

        LoginCredentials loginCredentials = userTranslator.getLoginCredentials(null);
        assertNull(loginCredentials);
    }

    @Test
    public void testGetLoginCredentials(){

        LoginCredentials loginCredentials = userTranslator.getLoginCredentials(signUpRequestDto);
        assertNotNull(loginCredentials);
        assertEquals(signUpRequestDto.getUsername(), loginCredentials.getUserName());
        assertEquals(signUpRequestDto.getPassword(), loginCredentials.getPassword());
    }
}
