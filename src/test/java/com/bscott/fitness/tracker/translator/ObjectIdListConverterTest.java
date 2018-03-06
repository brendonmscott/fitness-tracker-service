package com.bscott.fitness.tracker.translator;

import ma.glasnost.orika.metadata.Type;
import org.bson.types.ObjectId;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ObjectIdListConverterTest {

    private ObjectIdListConverter objectIdListConverter = new ObjectIdListConverter();

    @Test
    public void testConvertTo(){


        Type<List<ObjectId>> destinationType = null;
        List<String> objectIdStrings = new ArrayList<>();
        objectIdStrings.add("583a9be33004dfd16b956697");
        objectIdStrings.add("583a9be33004dfd16b956698");
        objectIdStrings.add("583a9be33004dfd16b956699");

        List<ObjectId> objectIds = objectIdListConverter.convertTo(objectIdStrings, destinationType);

        assertEquals(3, objectIds.size());
        MatcherAssert.assertThat(objectIds, CoreMatchers.hasItem(new ObjectId("583a9be33004dfd16b956697")));
    }

    @Test
    public void testConvertFrom(){

        Type<List<String>> destinationType = null;
        List<ObjectId> objectIds = new ArrayList<>();
        objectIds.add(new ObjectId("583a9be33004dfd16b956697"));
        objectIds.add(new ObjectId("583a9be33004dfd16b956698"));
        objectIds.add(new ObjectId("583a9be33004dfd16b956699"));

        List<String> objectIdStrings = objectIdListConverter.convertFrom(objectIds, destinationType);

        assertEquals(3, objectIdStrings.size());
        MatcherAssert.assertThat(objectIdStrings, CoreMatchers.hasItem("583a9be33004dfd16b956697"));
    }
}
