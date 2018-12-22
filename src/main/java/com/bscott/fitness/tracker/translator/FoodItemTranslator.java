package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.dto.FoodItemDto;
import com.bscott.fitness.tracker.model.FoodItem;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodItemTranslator extends ConfigurableMapper {

    private MapperFacade mapper;

    @Override
    protected synchronized void configure(MapperFactory factory) {
        factory.classMap(FoodItem.class, FoodItemDto.class)
                .byDefault()
                .register();

        mapper = factory.getMapperFacade();
    }

    public List<FoodItemDto> toDtos(List<FoodItem> foodItems) {

        List<FoodItemDto> foodItemDtos = new ArrayList<>();

        if (foodItems != null) {
            for (FoodItem foodItem : foodItems) {
                foodItemDtos.add(toDto(foodItem));
            }
        }

        return foodItemDtos;
    }

    public FoodItemDto toDto(FoodItem foodItem) {
        return mapper.map(foodItem, FoodItemDto.class);
    }

    public FoodItem toEntity(FoodItemDto foodItemDto) {
        return mapper.map(foodItemDto, FoodItem.class);
    }
}
