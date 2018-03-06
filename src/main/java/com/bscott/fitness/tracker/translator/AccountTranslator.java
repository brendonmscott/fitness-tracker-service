package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.dto.AccountDto;
import com.bscott.fitness.tracker.model.Account;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountTranslator extends ConfigurableMapper{

    private MapperFacade mapper;

    @Override
    protected synchronized void configure(MapperFactory factory) {

        factory.getConverterFactory().registerConverter(new LocalDateConverter());
        factory.classMap(Account.class, AccountDto.class)
                .byDefault()
                .register();

        mapper = factory.getMapperFacade();
    }

    public List<AccountDto> toDtos(List<Account> entityList){

        if(entityList == null){
            return new ArrayList<>();
        }

        List<AccountDto> dtos = new ArrayList<>();

        for(Account entity : entityList){
            dtos.add(toDto(entity));
        }

        return dtos;
    }

    public AccountDto toDto(Account entity){
        return mapper.map(entity, AccountDto.class);
    }

    public Account toEntity(AccountDto dto){
        return mapper.map(dto, Account.class);
    }
}
