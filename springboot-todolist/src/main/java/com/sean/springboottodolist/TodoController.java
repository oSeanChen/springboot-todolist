package com.sean.springboottodolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos/{todoId}")
    public Todo getTodo(@PathVariable Integer todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        return todo;
    }

    @PostMapping("/todos")
    public String create(@RequestBody Todo todo) {
        todo.setCreateTime(LocalDateTime.now());
        todoRepository.save(todo);
        return "create " + todo.getTask();
    }

    @PutMapping("/todos/{todoId}")
    public String update(@PathVariable Integer todoId, @RequestBody Todo updateTodo) {

        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo != null) {
            todo.setTask(updateTodo.getTask());
            todo.setStatus(updateTodo.getStatus());
            todo.setUpdateTime(LocalDateTime.now());
            todoRepository.save(todo);
            return "update id = " + todo.getId();
        } else {
            return "cannot find this task in database";
        }

    }

    @DeleteMapping("/todos/{todoId}")
    public String delete(@PathVariable Integer todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo != null) {
            todoRepository.deleteById(todoId);
            return "delete id = " + todoId;
        } else {
            return "cannot find this task in database";
        }
    }
}
