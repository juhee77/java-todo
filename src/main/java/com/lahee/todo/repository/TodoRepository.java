package com.lahee.todo.repository;

import com.lahee.todo.domain.Todo;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Getter
public class TodoRepository {
    HashMap<Long, Todo> todos = new HashMap<>();


    public Todo save(Todo todo) {
        todos.put(todo.getId(), todo);
        return todos.get(todo.getId());
    }
}
