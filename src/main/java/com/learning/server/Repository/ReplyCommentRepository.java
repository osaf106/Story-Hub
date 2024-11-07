package com.learning.server.Repository;

import com.learning.server.Model.ReplyCommentsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReplyCommentRepository extends MongoRepository<ReplyCommentsModel, String> {
    List<ReplyCommentsModel> findByMainCommentId(String mainCommentId);
}
