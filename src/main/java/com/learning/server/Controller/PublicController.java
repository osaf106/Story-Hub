package com.learning.server.Controller;

import com.learning.server.Model.UserModel;
import com.learning.server.Service.UserService;
import com.learning.server.Service.Validation.UserLoginValidation;
import com.learning.server.Service.Validation.UserSignupValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "http://localhost:3000")
public class PublicController {

    private static  final Logger logger = LoggerFactory.getLogger(PublicController.class);


    @Autowired
    private UserService userService;
    @Autowired
    UserSignupValidation userSignupValidation;
    @Autowired
    UserLoginValidation userLoginValidation;

    @GetMapping("/health")
    public String checkServer()
    {
        return "Server Start";
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody UserModel newUser)
    {
        ResponseEntity<?> valid = userSignupValidation.validateUserSignup(newUser);
        try {
            if (Objects.equals(valid.getBody(), "valid"))
            {
                UserModel userExistByName = userService.findByUserName(newUser.getUserName());
                UserModel userExistByEmail = userService.findByEmail(newUser.getEmail());
                if(userExistByName ==null && userExistByEmail==null)
                {
                    UserModel Saved = userService.saveNewUser(newUser);
                    if(Saved!=null)
                    {
//                        System.out.println(Saved);
                        logger.info("Successfully Created");
                        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
                    }
                }
                else
                {
                    logger.error("User Already Exist");
                    return new ResponseEntity<>("User Already Exist", HttpStatus.BAD_REQUEST);
                }


            }

        } catch (Exception e) {
            logger.error("Failed to create account");
            return new ResponseEntity<>("Failed to create account", HttpStatus.BAD_REQUEST);
        }
        logger.error(Objects.requireNonNull(valid.getBody()).toString());
        return new ResponseEntity<>(valid.getBody(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel loginUser)
    {
        ResponseEntity<?> valid = userLoginValidation.validateUserLogin(loginUser);
        try {
            if(Objects.equals(valid.getBody(),"valid"))
            {
                UserModel userExistByName = userService.findByUserName(loginUser.getUserName());
                if(userExistByName!=null)
                {
                    String verified = userService.verifyUserThroughLogin(loginUser);
                    if(!verified.equals("password is incorrect"))
                    {
                        logger.info("Login Successfully");
                        return new ResponseEntity<>(verified, HttpStatus.OK);
                    }
                    else
                    {
                        logger.error("password is incorrect");
                        return new ResponseEntity<>("password is incorrect", HttpStatus.BAD_REQUEST);
                    }
                }
                else
                {
                    logger.error("User Not found");
                    return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
                }

            }

        } catch (Exception e) {
            logger.error("Failed to Login account");
            return new ResponseEntity<>("Failed to Login account"+e, HttpStatus.BAD_REQUEST);
        }
        logger.error((String) Objects.requireNonNull(valid.getBody()).toString());
        return new ResponseEntity<>(valid.getBody(), HttpStatus.BAD_REQUEST);
    }


}
