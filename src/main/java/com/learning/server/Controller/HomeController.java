package com.learning.server.Controller;

import com.learning.server.Model.HomeModel;
import com.learning.server.Model.UserModel;
import com.learning.server.Service.HomeService;
import com.learning.server.Service.Validation.HomeValidations;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    private static final Logger logger =  LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeValidations homeValidations;
    @Autowired
    private HomeService homeService;

//    @Value("${project.image}")
//    private String path;

    @GetMapping("/health")
    public String checkServer()
    {
        return "Home Page Start";
    }

//    @PostMapping("/create-new-Blog")
//    public ResponseEntity<?> saveNewBlog(@RequestParam("title") String title,
//                                         @RequestParam("body") String body,
//                                         @RequestParam("image") MultipartFile imageFile)
//    {
//        ResponseEntity<?> valid = homeValidations.ValidationForAddingNewBlog(title,body,imageFile);
//
//        try {
//
//            if(Objects.equals(valid.getBody(),"valid"))
//            {
//
//                HomeModel saved = homeService.saveBlog(title,body, imageFile, path);
//                if(saved!=null)
//                {
//                    return new ResponseEntity<>("Successfully Added New Blog", HttpStatus.CREATED);
//                }
//            }
//
//        } catch (Exception e) {
//            return new ResponseEntity<>("Fail to Add New Blog" + e, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(valid.getBody(), HttpStatus.BAD_REQUEST);
//    }
    @PostMapping("/create-new-Blog")
    public ResponseEntity<?> saveNewBlog(@RequestBody HomeModel newBlog)
    {
//        System.out.println(newBlog);
        ResponseEntity<?> valid = homeValidations.ValidationForAddingNewBlog(newBlog);

        try {

            if(Objects.equals(valid.getBody(),"valid"))
            {

                HomeModel saved = homeService.saveBlog(newBlog);
                if(saved!=null)
                {
                    logger.info("Successfully Added New Blog");
                    return new ResponseEntity<>("Successfully Added New Blog", HttpStatus.CREATED);
                }
            }

        } catch (Exception e) {
            logger.error("Fail to Add New Blog");
            return new ResponseEntity<>("Fail to Add New Blog" + e, HttpStatus.BAD_REQUEST);
        }
        logger.error((String) Objects.requireNonNull(valid.getBody()).toString());
        return new ResponseEntity<>(valid.getBody(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all-blogs")
    public ResponseEntity<List<HomeModel>> getAllBlogs()
    {
        List<HomeModel> Blogs = homeService.getAllBlogs();
        if(Blogs!=null)
        {
//            Path filePath = Paths.get(path);
            logger.info("Blog List : ", Blogs);
            return new ResponseEntity<>(Blogs, HttpStatus.OK);
        }
        logger.error("Not Found Blogs");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/blog/{id}")
    public ResponseEntity<HomeModel> getBlogByBlogTitle(@PathVariable String id)
    {
        System.out.println(id);
            HomeModel blog = homeService.getBlogByTitle(id);
            if(blog!=null)
            {
                logger.info("Blog By Title : ", blog);
                return new ResponseEntity<>(blog, HttpStatus.OK);
            }
            logger.error("Not Found Blog by Title");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/blog/id/{id}")
    public ResponseEntity<HomeModel> getBlogByBlogId(@PathVariable String id)
    {
//        System.out.println(id);
        HomeModel blog = homeService.getBlogById(id);

        if(blog!=null)
        {
            logger.info("Blog : ", blog);
            return new ResponseEntity<>(blog, HttpStatus.OK);
        }
        logger.error("Not Found Blog by ID");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/blog/current-user")
    public ResponseEntity<String> getCurrentUser()
    {
        try {

            UserModel user = homeService.getLoginUserData();
            logger.info("UserName : {}", user.getUserName());
            return new ResponseEntity<>(user.getFullName(),HttpStatus.OK);
        } catch (Exception e) {
            logger.error("UserName Not found ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
