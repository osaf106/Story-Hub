package com.learning.server.Repository;

import com.learning.server.Model.CommentsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentsRepository extends MongoRepository<CommentsModel, String> {
    List<CommentsModel> findByBlogId(String blogId);
}
