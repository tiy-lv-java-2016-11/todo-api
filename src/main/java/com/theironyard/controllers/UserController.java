package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.exceptions.UserNotFoundException;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @RequestMapping(path = "/{userId}/", method = RequestMethod.GET)
    public User getUser(@PathVariable int userId){
        User user = validateUser(userId);
        return user;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user){
        userRepo.save(user);
        return user;
    }

    @RequestMapping(path = "/{userId}/", method = RequestMethod.PUT)
    public User replaceUser(@PathVariable int userId, @RequestBody User user){
        validateUser(userId);
        user.setId(userId);
        userRepo.save(user);
        return user;
    }

    @RequestMapping(path = "/{userId}/", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int userId){
        validateUser(userId);
        userRepo.delete(userId);
    }

    public User validateUser(int userId){
        User user = userRepo.findOne(userId);
        if (user == null){
            throw new UserNotFoundException();
        }
        return user;
    }
}
