package com.theironyard.controllers;

import com.theironyard.entities.Todo;
import com.theironyard.entities.User;
import com.theironyard.exceptions.UsersNotFoundException;
import com.theironyard.repositories.TodoRepository;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sparatan117 on 1/9/17.
 */
@RestController
@RequestMapping(path = "/users/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoRepository todoRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user){
        userRepository.save(user);

        return user;
    }

    @RequestMapping(path = "/{userId}/", method = RequestMethod.GET)
    public User getUser(@PathVariable int userId){
        User user = validateUser(userId);
        return user;
    }

    @RequestMapping(path = "/{userId}/", method = RequestMethod.PUT)
    public User replaceUser(@PathVariable int userId, @RequestBody User user){
        User savedUser = validateUser(userId);
        user.setId(userId);
        userRepository.save(savedUser);
        return user;
    }

    @RequestMapping(path = "/{userId}/", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int userId){
        userRepository.delete(userId);
    }

    @RequestMapping(path = "/{userId}/todos/", method = RequestMethod.GET)
    public List<Todo> getTodo(@PathVariable int userId){
        User user = validateUser(userId);

        return todoRepository.findByUser(user);
    }

    @RequestMapping(path = "/{userId}/todos/", method = RequestMethod.POST)
    public Todo createTodo(@PathVariable int userId, @RequestBody Todo todo){
        User user = validateUser(userId);
        todo.setUser(user);
        todoRepository.save(todo);
        return todo;
    }

    public User validateUser(int userId){
        User user = userRepository.findOne(userId);
        if(user == null){
            throw new UsersNotFoundException();
        }
        return user;
    }

}
