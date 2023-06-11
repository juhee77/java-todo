package com.lahee.todo.repository;

import com.lahee.todo.domain.Status;
import com.lahee.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<List<Todo>> getTodoByStatusIs(Status status);
}
