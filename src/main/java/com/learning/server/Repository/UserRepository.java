package com.learning.server.Repository;

import com.learning.server.Model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel , String> {
    UserModel findByUserName(String username);
    UserModel findByEmail(String email);
}
