package com.theironyard.Controllers;

import com.theironyard.Entities.ToDoList;
import com.theironyard.Entities.User;
import com.theironyard.Repositories.ToDoListRepository;
import com.theironyard.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by darionmoore on 1/9/17.
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    @RequestMapping(path = "/users/", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(path = "/users/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user){
        userRepository.save(user);

        return user;
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable int userId) throws Exception {
        User user = userRepository.findOne(userId);
        if(user == null){
            throw new Exception();
        }
        return user;
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.PUT)
    public User replaceUser(@PathVariable int userId, @RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable int userId){
        userRepository.delete(userId);
        return null;
    }

    @RequestMapping(path = "/{userId}/todos/")
    public List<ToDoList> getToDoList(@PathVariable int userId) throws Exception {
        User user = userRepository.findOne(userId);
        if(user == null){
            throw new Exception();
        }
        return toDoListRepository.findAllByUser(user);
    }

    @RequestMapping(path = "/{userId}/todos", method = RequestMethod.POST)
    public ToDoList createToDoList(@PathVariable int userId, @RequestBody ToDoList toDoList) throws Exception {
        User user = validateUser(userId);
        toDoList.setUser(user);
        toDoListRepository.save(toDoList);
        return toDoList;
    }




        public User validateUser(int userId) throws Exception {
        User user = userRepository.findOne(userId);
        if(user == null){
            throw new Exception();
        }
            return user;
        }

    }









