package com.learning.server.Service;

import com.learning.server.Model.UserModel;
import com.learning.server.Repository.UserRepository;
import com.learning.server.Service.JWTService.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public UserModel saveNewUser(UserModel newUser)
    {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);

        } catch (Exception e) {
            return null;
        }
    }
    public UserModel findByUserName(String username)
    {
        return userRepository.findByUserName(username);
    }
    public UserModel findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public String verifyUserThroughLogin(UserModel user)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            if(authentication.isAuthenticated())
            {
                return jwtService.generateToken(user.getUserName());
            }
        } catch (Exception e) {
           return "password is incorrect";
        }
        return "";
    }



}
