package com.bscott.fitness.tracker;

import com.mongodb.MongoClient;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class TestUtils {

    private MongoTemplate mongoTemplate = getMongoTemplate();

    public <T> void importTestData(String filePath, Class<T> clazz) throws Exception{

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        List<T> data = gson.fromJson(reader, new ListOfJson<>(clazz));

        for(T item : data){
            mongoTemplate.save(item);
        }
    }

    public void dropCollection(String collection){
        mongoTemplate.dropCollection(collection);
    }

    public class ListOfJson<T> implements ParameterizedType
    {
        private Class<?> wrapped;

        ListOfJson(Class<T> wrapper)
        {
            this.wrapped = wrapper;
        }

        @Override
        public Type[] getActualTypeArguments()
        {
            return new Type[] { wrapped };
        }

        @Override
        public Type getRawType()
        {
            return List.class;
        }

        @Override
        public Type getOwnerType()
        {
            return null;
        }
    }

    private MongoTemplate getMongoTemplate() {
        return new MongoTemplate(new MongoClient("localhost"), "fitness-tracker-test");
    }
}
