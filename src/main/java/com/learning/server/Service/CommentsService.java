package com.learning.server.Service;

import com.learning.server.Model.CommentsModel;
import com.learning.server.Model.UserModel;
import com.learning.server.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private UserService userService;

    public CommentsModel sendComment(CommentsModel comment, String id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated())
        {
            UserModel userModel = userService.findByUserName(authentication.getName());
            comment.setSendBy(userModel.getFullName());
            comment.setBlogId(id);
            comment.setTimestamp(LocalDateTime.now());
            return commentsRepository.save(comment);
        }
        return null;
    }


    public List<CommentsModel> getBlogCommentsByID(String blogId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated())
        {
            List<CommentsModel> list = commentsRepository.findByBlogId(blogId);
            return list;
        }
        return null;

    }
}
