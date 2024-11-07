package com.learning.server.Service;


import com.learning.server.Model.ReplyCommentsModel;
import com.learning.server.Model.UserModel;
import com.learning.server.Repository.ReplyCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReplyCommentService {

    @Autowired
    private ReplyCommentRepository replyCommentRepository;
    @Autowired
    private UserService userService;

    public ReplyCommentsModel saveReplyComment(ReplyCommentsModel replyComment, String mainCommentId)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.isAuthenticated())
            {
                UserModel user = userService.findByUserName(authentication.getName());
                if(user!=null)
                {
                    replyComment.setMainCommentId(mainCommentId);
                    replyComment.setSendBy(user.getFullName());
                    replyComment.setTimestamp(LocalDateTime.now());
                    return replyCommentRepository.save(replyComment);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    public List<ReplyCommentsModel> getBlogCommentsByID(String id) {
       try {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           if(authentication.isAuthenticated())
           {
               List<ReplyCommentsModel> list = replyCommentRepository.findByMainCommentId(id);
               return list;
           }
       } catch (Exception e) {
           return null;
       }

        return null;
    }
}
