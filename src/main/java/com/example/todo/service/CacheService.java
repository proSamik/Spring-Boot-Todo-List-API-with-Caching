package com.example.todo.service;

import com.example.todo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    private final Map<Long, Todo> cache = new ConcurrentHashMap<>();

    public void putInCache(Todo todo) {
        cache.put(todo.getId(), todo);
    }

    public Todo getFromCache(Long id) {
        return cache.get(id);
    }

    public void removeFromCache(Long id) {
        cache.remove(id);
    }

    public void clearCache() {
        cache.clear();
    }
}