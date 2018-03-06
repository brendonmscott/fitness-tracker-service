package com.bscott.fitness.tracker.translator;

import ma.glasnost.orika.metadata.Type;
import org.joda.time.LocalDate;
import org.junit.Assert;import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalDateConverterTest {

    private LocalDateConverter localDateConverter = new LocalDateConverter();

    @Test
    public void testConvertTo(){

        Type<String> destinationType = null;
        String date = localDateConverter.convertTo(new LocalDate("2016-04-09"), destinationType);

        assertEquals("04/09/2016", date);
    }

    @Test
    public void testConvertFrom(){

        Type<LocalDate> destinationType = null;
        LocalDate date = localDateConverter.convertFrom("04/09/2016", destinationType);

        Assert.assertEquals("2016-04-09", date.toString());
    }
}
