package com.lahee.todo.repository;

import com.lahee.todo.domain.Status;
import com.lahee.todo.domain.Todo;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
public class TodoRepository {
    HashMap<Long, Todo> allTodos = new HashMap<>();

    public Optional<List<Todo>> getTodos() {
        List<Todo> todos = new ArrayList<>();
        for (Long key : allTodos.keySet()) {
            Todo todo = allTodos.get(key);
            if (todo.getStatus() == Status.TODO) {
                todos.add(todo);
            }
        }
        return Optional.of(todos);
    }

    public Optional<List<Todo>> getDones() {
        List<Todo> dones = new ArrayList<>();
        for (Long key : allTodos.keySet()) {
            Todo todo = allTodos.get(key);
            if (todo.getStatus() == Status.DONE) {
                dones.add(todo);
            }
        }
        return Optional.of(dones);
    }

    public Optional<List<Todo>> getDeletes() {
        List<Todo> deletes = new ArrayList<>();
        for (Long key : allTodos.keySet()) {
            Todo todo = allTodos.get(key);
            if (todo.getStatus() == Status.DELETED) {
                deletes.add(todo);
            }
        }
        return Optional.of(deletes);
    }

    public Todo save(Todo todo) {
        allTodos.put(todo.getId(), todo);
        return allTodos.get(todo.getId());
    }

    public Todo findById(Long id) {
        return allTodos.get(id);
    }
}
