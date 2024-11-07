package com.learning.server.Controller;

import com.learning.server.Model.ReplyCommentsModel;
import com.learning.server.Service.ReplyCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply-model")
public class ReplyCommentController {

    private static final Logger log = LoggerFactory.getLogger(ReplyCommentController.class);

    @Autowired
    private ReplyCommentService replyCommentService;


    @PostMapping(value = "/save-reply-comment/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveReplyComment(@RequestBody ReplyCommentsModel replyComments, @PathVariable String id){

        try {
            ReplyCommentsModel repCommentsModel = replyCommentService.saveReplyComment(replyComments, id);
            log.info("Save Reply Comment of Id : {}", repCommentsModel.getSendBy());
            return new ResponseEntity<>(repCommentsModel.getSendBy(), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Fail to Save Reply Comment of Id : {}", replyComments.getSendBy());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-all-reply/{id}")
    public ResponseEntity<List<ReplyCommentsModel>> getAllReply(@PathVariable String id)
    {
        try {
            List<ReplyCommentsModel> replyCommentsModel = replyCommentService.getBlogCommentsByID(id);
            log.info("Get All Replies Comments of Id : {}", replyCommentsModel);
            return new ResponseEntity<>(replyCommentsModel, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Fail to Get All Replies Comments of Id : {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
