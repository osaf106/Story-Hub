package com.learning.server.Service;

import com.learning.server.Model.HomeModel;
import com.learning.server.Model.UserModel;
import com.learning.server.Repository.HomeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class HomeService {

    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserService userService;

//    public HomeModel saveBlog(String title, String body, MultipartFile imageFile, String path)
//    {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if(authentication!=null && authentication.isAuthenticated())
//            {
//                // make Folder
//                File uploadDir = new File(path);
//                if(!uploadDir.exists()) uploadDir.mkdirs();
//
//                // Save the image file to the local folder
//                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
//                Path filePath = Paths.get(path, fileName);
//                Files.write(filePath, imageFile.getBytes());
//
//
//                String username = authentication.getName();
//                UserModel createdByUser = userService.findByUserName(username);
//
//                HomeModel newBlog = new HomeModel();
//                newBlog.setCreatedBy(createdByUser.getFullName());
//                newBlog.setImage(filePath.toString());
//                newBlog.setTitle(title);
//                newBlog.setBody(body);
//                return homeRepository.save(newBlog);
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        return null;
//    }
    public HomeModel saveBlog(HomeModel newBlog)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication!=null && authentication.isAuthenticated())
            {
                String username = authentication.getName();
                UserModel createdByUser = userService.findByUserName(username);
                newBlog.setCreatedBy(createdByUser.getFullName());
                return homeRepository.save(newBlog);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public List<HomeModel> getAllBlogs()
    {
        try {
            return homeRepository.findAll();

        } catch (Exception e) {
            return null;
        }
    }

    public HomeModel getBlogByTitle(String title) {
        try{
            return homeRepository.findByTitle(title);
        } catch (Exception e) {
            return null;
        }
    }
    public HomeModel getBlogById(String title) {
        try{
            return homeRepository.findById(title).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
    public UserModel getLoginUserData()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated())
        {
            UserModel userModel = userService.findByUserName(authentication.getName());
            if(userModel!=null)
            {
                return userModel;
            }
        }
        return null;
    }
}
