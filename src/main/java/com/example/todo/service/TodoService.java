package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private CacheService cacheService;

    public Todo getTodo(Long id) {
        // First check cache
        Todo cachedTodo = cacheService.getFromCache(id);
        if (cachedTodo != null && !cachedTodo.isDeleted()) {
            return cachedTodo;
        }

        // If not in cache or deleted, get from MySQL
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null && !todo.isDeleted()) {
            // Store in cache for next time
            cacheService.putInCache(todo);
            return todo;
        }
        return null;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll().stream()
                .filter(todo -> !todo.isDeleted())
                .collect(Collectors.toList());
    }

    public Todo createTodo(Todo todo) {
        todo.setDeleted(false);  // Ensure new todos aren't deleted
        Todo savedTodo = todoRepository.save(todo);
        cacheService.putInCache(savedTodo);
        return savedTodo;
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null && !todo.isDeleted()) {
            todo.setTitle(todoDetails.getTitle());
            todo.setCompleted(todoDetails.isCompleted());
            Todo updatedTodo = todoRepository.save(todo);
            cacheService.putInCache(updatedTodo);
            return updatedTodo;
        }
        return null;
    }

    public boolean deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null && !todo.isDeleted()) {
            todo.setDeleted(true);
            todoRepository.save(todo);
            cacheService.removeFromCache(id);
            return true;
        }
        return false;
    }

    public boolean isTodoDeleted(Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        return todo != null && todo.isDeleted();
    }
}