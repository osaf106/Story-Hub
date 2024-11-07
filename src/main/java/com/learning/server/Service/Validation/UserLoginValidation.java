package com.learning.server.Service.Validation;

import com.learning.server.Controller.PublicController;
import com.learning.server.Model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLoginValidation {
    private static  final Logger logger = LoggerFactory.getLogger(UserLoginValidation.class);

    public ResponseEntity<?> validateUserLogin(UserModel userModel)
    {
        if(userModel.getUserName().isEmpty()  || userModel.getPassword().isEmpty())
        {
            logger.error("Each Field must Required");
            return new ResponseEntity<>("Each Field must Required", HttpStatus.BAD_REQUEST);
        }
        if(userModel.getPassword().length()<8)
        {
            logger.error("Password must contain 8 letters");
            return new ResponseEntity<>("Password must contain 8 letters", HttpStatus.BAD_REQUEST);
        }
        logger.info("Valid Pattern For Login");
        return new ResponseEntity<>("valid", HttpStatus.OK);
    }
}
