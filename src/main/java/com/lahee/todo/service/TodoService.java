package com.lahee.todo.service;

import com.lahee.todo.domain.Status;
import com.lahee.todo.domain.Todo;
import com.lahee.todo.domain.dto.TodoResponseDto;
import com.lahee.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public void save(String msg) {
        Todo save = todoRepository.save(new Todo(msg, Status.TODO));
        log.info("saved todo : {}", save.getContent());
    }

    public List<TodoResponseDto> readAll() {
        return convertToDtoList(todoRepository.findAll());
    }


    public List<TodoResponseDto> readTodos() {
        return convertToDtoList(todoRepository.getTodoByStatusIs(Status.TODO)
                .orElseThrow(() -> new RuntimeException("TODO")));
    }

    public List<TodoResponseDto> readDones() {
        return convertToDtoList(todoRepository.getTodoByStatusIs(Status.DONE)
                .orElseThrow(() -> new RuntimeException("TODO")));
    }

    private static List<TodoResponseDto> convertToDtoList(List<Todo> todo1) {
        return todo1
                .stream()
                .map(todo -> new TodoResponseDto(todo.getContent(), todo.getStatus().toString()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void done(Long id) {
        Todo todo = getTodo(id);
        todo.done();
    }

    @Transactional
    public void undone(Long id) {
        Todo todo = getTodo(id);
        todo.undone();
    }

    @Transactional
    public void deleteDone(Long id) {
        Todo todo = getTodo(id);
        todo.delete();
    }

    private Todo getTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 TODO입니다."));
    }

    @Transactional
    public void modifyContent(Long id, String msg) {
        Todo todo = getTodo(id);
        todo.modifyContent(msg);
    }
}
