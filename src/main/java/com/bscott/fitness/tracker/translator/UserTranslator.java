package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.dto.SignUpRequestDto;
import com.bscott.fitness.tracker.dto.UserDto;
import com.bscott.fitness.tracker.model.LoginCredentials;
import com.bscott.fitness.tracker.model.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTranslator extends ConfigurableMapper {

    private MapperFacade mapper;

    @Override
    protected synchronized void configure(MapperFactory factory) {

        factory.getConverterFactory().registerConverter(new LocalDateConverter());
        factory.getConverterFactory().registerConverter("objectIdListConverter", new ObjectIdListConverter());

        factory.classMap(User.class, UserDto.class)
                .fieldMap("favoriteFoodItems", "favoriteFoodItems").converter("objectIdListConverter").add()
                .byDefault()
                .register();

        mapper = factory.getMapperFacade();
    }

    public List<UserDto> toDtos(List<User> users) {

        List<UserDto> userDtos = new ArrayList<>();

        if (users != null) {
            for (User user : users) {
                userDtos.add(toDto(user));
            }
        }

        return userDtos;
    }

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User toEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public LoginCredentials getLoginCredentials(SignUpRequestDto signUpRequestDto) {

        if (signUpRequestDto == null) {
            return null;
        }

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUserName(signUpRequestDto.getUsername());
        loginCredentials.setPassword(signUpRequestDto.getPassword());

        return loginCredentials;
    }
}
