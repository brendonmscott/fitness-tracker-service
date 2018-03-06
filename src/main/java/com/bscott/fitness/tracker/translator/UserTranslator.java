package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.Constants;
import com.bscott.fitness.tracker.dto.RegisterUserDto;
import com.bscott.fitness.tracker.dto.UserDto;
import com.bscott.fitness.tracker.model.LoginCredentials;
import com.bscott.fitness.tracker.model.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTranslator extends ConfigurableMapper{

    private MapperFacade mapper;

    @Override
    protected synchronized void configure(MapperFactory factory) {

        factory.getConverterFactory().registerConverter(new LocalDateConverter());
        factory.getConverterFactory().registerConverter("objectIdListConverter", new ObjectIdListConverter());

        factory.classMap(User.class, UserDto.class)
                .fieldMap("favoriteFoodItems", "favoriteFoodItems").converter("objectIdListConverter").add()
                .byDefault()
                .register();

        mapper =  factory.getMapperFacade();
    }

    public List<UserDto> toDtos(List<User> users){

        List<UserDto> userDtos = new ArrayList<>();

        if(users != null){
            for(User user : users){
                userDtos.add(toDto(user));
            }
        }

        return userDtos;
    }

    public UserDto toDto(User user){
        return mapper.map(user, UserDto.class);
    }

    public User toEntity(UserDto userDto){
        return mapper.map(userDto, User.class);
    }

    public User createNewUser(RegisterUserDto registerUserDto) {

        if(registerUserDto == null){
            return null;
        }

        User user = new User();
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());

        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/YYYY");
        user.setBirthDate(dtf.parseLocalDate(registerUserDto.getBirthDate()));

        user.getRoles().add(Constants.ADMIN_ROLE);

        return user;
    }

    public LoginCredentials getLoginCredentials(RegisterUserDto registerUserDto){

        if(registerUserDto == null){
            return null;
        }

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUserName(registerUserDto.getEmail());
        loginCredentials.setPassword(registerUserDto.getPassword());

        return loginCredentials;
    }
}
