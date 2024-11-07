package com.learning.server.Controller;

import com.learning.server.Model.CommentsModel;
import com.learning.server.Service.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private static final Logger log = LoggerFactory.getLogger(CommentsController.class);
    @Autowired
    private CommentsService commentsService;


    @PostMapping("/save-comment/{id}")
    public ResponseEntity<String> sendComments(@RequestBody CommentsModel comment, @PathVariable String id)
    {
        try {
                CommentsModel commentsModel = commentsService.sendComment(comment, id);
            log.info("Save By to : {}", commentsModel.getSendBy());
            return new ResponseEntity<>(commentsModel.getSendBy(),HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Not Save Comment {}", comment.getSendBy());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-blog-comments/{id}")
    public ResponseEntity<List<CommentsModel>> getBlogComments(@PathVariable String id)
    {
        try {
            List<CommentsModel> commentsModel = commentsService.getBlogCommentsByID(id);
            log.info("List Of Comments By Id : {}", commentsModel);
            return new ResponseEntity<>(commentsModel, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("List Of Comments By Id Not found : {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
