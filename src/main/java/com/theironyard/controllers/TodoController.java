package com.theironyard.controllers;

import com.theironyard.entities.Todo;
import com.theironyard.entities.User;
import com.theironyard.exceptions.TodoNotFoundException;
import com.theironyard.exceptions.UserNotAuthException;
import com.theironyard.exceptions.UserNotFoundException;
import com.theironyard.repositories.TodoRepository;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/todos")
public class TodoController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    TodoRepository todoRepo;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Todo> getTodos(@PathVariable int userId){
        User user = validateUser(userId);
        return todoRepo.findByUser(user);
    }

    @RequestMapping(path = "/{todoId}/", method = RequestMethod.GET)
    public Todo getTodo(@PathVariable int userId, @PathVariable int todoId){
        User user = validateUser(userId);
        Todo todo = validateTodo(todoId);

        return validateTodoUser(todo, user);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Todo createTodo(@PathVariable int userId, @RequestBody Todo todo){
        User user = validateUser(userId);
        todo.setUser(user);
        todoRepo.save(todo);
        return todo;
    }

    @RequestMapping(path = "/{todoId}/", method = RequestMethod.PUT)
    public Todo replaceTodo(@PathVariable int userId, @PathVariable int todoId, @RequestBody Todo todo){
        User user = validateUser(userId);
        Todo savedTodo = validateTodo(todoId);
        validateTodoUser(savedTodo, user);
        todo.setId(todoId);
        todo.setUser(user);
        todoRepo.save(todo);
        return todo;
    }

    @RequestMapping(path = "/{todoId}/", method = RequestMethod.DELETE)
    public void deleteTodo(@PathVariable int userId, @PathVariable int todoId){
        User user = validateUser(userId);
        Todo savedTodo = validateTodo(todoId);
        validateTodoUser(savedTodo, user);
        todoRepo.delete(todoId);
    }

    public Todo validateTodoUser(Todo todo, User user){
        if (todo.getUser() != user){
            throw new UserNotAuthException();
        }
        return todo;
    }

    public Todo validateTodo(int todoId){
        Todo todo = todoRepo.findOne(todoId);
        if (todo == null){
            throw new TodoNotFoundException();
        }
        return todo;
    }

    public User validateUser(int userId){
        User user = userRepo.findOne(userId);
        if (user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

}
