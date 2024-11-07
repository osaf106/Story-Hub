package com.learning.server.Service.ServiceUserFinder;

import com.learning.server.Model.Principal.userPrincipal;
import com.learning.server.Model.UserModel;
import com.learning.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUserName(username);
        if(user == null)
        {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user for Found"+ username);
        }
        return new userPrincipal(user);
    }
}
