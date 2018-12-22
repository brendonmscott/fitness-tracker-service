package com.bscott.fitness.tracker.translator;

import com.bscott.fitness.tracker.dto.DailyMealRecordDto;
import com.bscott.fitness.tracker.model.DailyMealRecord;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealRecordTranslator extends ConfigurableMapper {

    private MapperFacade mapper;

    @Override
    protected synchronized void configure(MapperFactory factory) {
        factory.classMap(DailyMealRecord.class, DailyMealRecordDto.class)
                .byDefault()
                .register();

        mapper = factory.getMapperFacade();
    }

    public List<DailyMealRecordDto> toDtos(List<DailyMealRecord> dailyMealRecords) {

        List<DailyMealRecordDto> dailyMealRecordDtos = new ArrayList<>();

        if (dailyMealRecords != null) {
            for (DailyMealRecord dailyMealRecord : dailyMealRecords) {
                dailyMealRecordDtos.add(toDto(dailyMealRecord));
            }
        }

        return dailyMealRecordDtos;
    }

    public DailyMealRecordDto toDto(DailyMealRecord dailyMealRecord) {
        return mapper.map(dailyMealRecord, DailyMealRecordDto.class);
    }

    public DailyMealRecord toEntity(DailyMealRecordDto dailyMealRecordDto) {
        return mapper.map(dailyMealRecordDto, DailyMealRecord.class);
    }
}
