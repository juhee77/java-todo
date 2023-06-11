package com.lahee.todo.service;

import com.lahee.todo.domain.Status;
import com.lahee.todo.domain.Todo;
import com.lahee.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;

    public void save(String msg) {
        Todo save = todoRepository.save(new Todo((long) todoRepository.getTodos().size(), msg, Status.TODO));
        log.info("saved todo : {}",save.getContent());
    }
}
