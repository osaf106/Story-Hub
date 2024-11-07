package com.learning.server.Service.Validation;

import com.learning.server.Controller.PublicController;
import com.learning.server.Model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserSignupValidation {
    private static  final Logger logger = LoggerFactory.getLogger(UserSignupValidation.class);

    public ResponseEntity<?> validateUserSignup(UserModel userModel)
    {
        if(userModel.getUserName().isEmpty() || userModel.getFullName().isEmpty() || userModel.getEmail().isEmpty() || userModel.getPassword().isEmpty())
        {
            logger.error("Each Field must Required");
            return new ResponseEntity<>("Each Field must Required", HttpStatus.BAD_REQUEST);
        }
        if(userModel.getFullName().length()<3)
        {
            logger.error("Name must Contain 3 letters");
            return new ResponseEntity<>("Name must Contain 3 letters", HttpStatus.BAD_REQUEST);
        }
        if(!isValidName(userModel.getFullName()))
        {
            logger.error("Name must Contain Alphabets");
            return new ResponseEntity<>("Name must Contain Alphabets", HttpStatus.BAD_REQUEST);
        }
        if(!isValidEmail(userModel.getEmail()))
        {
            logger.error("Email pattern must be valid");
            return new ResponseEntity<>("Email pattern must be valid", HttpStatus.BAD_REQUEST);
        }
        if(userModel.getPassword().length()<8)
        {
            logger.error("Password must contain 8 letters");
            return new ResponseEntity<>("Password must contain 8 letters", HttpStatus.BAD_REQUEST);
        }
        logger.info("Valid Pattern");
        return new ResponseEntity<>("valid", HttpStatus.OK);

    }

    private boolean isValidName(String name)
    {
        String regexp = "^[A-Za-z\\s]+$";
        return Pattern.compile(regexp).matcher(name).matches();
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

}
