package com.learning.server.Repository;

import com.learning.server.Model.HomeModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HomeRepository extends MongoRepository<HomeModel, String> {
    HomeModel findByTitle(String title);
}
