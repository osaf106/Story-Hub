package com.learning.server.Service.Validation;

import com.learning.server.Controller.HomeController;
import com.learning.server.Model.HomeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HomeValidations {
    private static final Logger logger =  LoggerFactory.getLogger(HomeValidations.class);


//    public ResponseEntity<?> ValidationForAddingNewBlog(String title, String body, MultipartFile imageFile)
//    {
//        if(title.isEmpty() || body.isEmpty() ||imageFile.isEmpty())
//        {
//            return new ResponseEntity<>("Each Field must Required", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("valid", HttpStatus.OK);
//
//    }
    public ResponseEntity<?> ValidationForAddingNewBlog(HomeModel homeModel)
    {
        if(homeModel.getImage().isEmpty() || homeModel.getBody().isEmpty() ||homeModel.getTitle().isEmpty())
        {
            logger.error("Each Field must Required");
            return new ResponseEntity<>("Each Field must Required", HttpStatus.BAD_REQUEST);
        }
        logger.info("valid");
        return new ResponseEntity<>("valid", HttpStatus.OK);

    }
}
