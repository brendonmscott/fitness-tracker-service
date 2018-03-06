package com.bscott.fitness.tracker.translator;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectIdListConverter extends BidirectionalConverter<List<String>, List<ObjectId>> {

   public List<ObjectId> convertTo(List<String> source, Type<List<ObjectId>> destinationType) {

      List<ObjectId> objectIds = new ArrayList<>();
      for(String objectId : source){
         objectIds.add(new ObjectId(objectId));
      }

      return objectIds;
   }

   public List<String> convertFrom(List<ObjectId> source, Type<List<String>> destinationType) {

      List<String> objectIds = new ArrayList<>();
      for(ObjectId objectId : source){
         objectIds.add(objectId.toString());
      }

      return objectIds;
   }
}
