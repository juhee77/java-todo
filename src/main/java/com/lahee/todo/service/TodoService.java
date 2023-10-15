package com.lahee.todo.service;

import com.lahee.todo.domain.Status;
import com.lahee.todo.domain.Todo;
import com.lahee.todo.domain.User;
import com.lahee.todo.dto.todo.TodoDto;
import com.lahee.todo.dto.todo.TodoResponseDto;
import com.lahee.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;

    @Transactional
    public void save(TodoDto todoDto, String currentUsername) {
        User user = userService.getByUsername(currentUsername);
        Todo todo = todoRepository.save(Todo.getInstance(todoDto, user));
        log.info("saved todo : {}", todo);
    }

    public List<TodoResponseDto> readAll(String currentUsername) {
        User user = userService.getByUsername(currentUsername);
        return convertToDtoList(todoRepository.findAllByUser(user));
    }


    public List<TodoResponseDto> readTodos(String currentUsername) {
        User user = userService.getByUsername(currentUsername);
        return convertToDtoList(todoRepository.getTodoByUserAndStatusIs(user, Status.TODO)
                .orElseThrow(() -> new RuntimeException("TODO")));
    }

    public List<TodoResponseDto> readDones(String currentUsername) {
        User user = userService.getByUsername(currentUsername);
        return convertToDtoList(todoRepository.getTodoByUserAndStatusIs(user, Status.DONE)
                .orElseThrow(() -> new RuntimeException("TODO")));
    }

    private static List<TodoResponseDto> convertToDtoList(List<Todo> todo1) {
        return todo1
                .stream()
                .map(TodoResponseDto::getInstance)
                .toList();
    }

    @Transactional
    public void done(Long id) {
        getTodo(id).done();
    }

    @Transactional
    public void undone(Long id) {
        getTodo(id).undone();
    }

    @Transactional
    public void deleteDone(Long id) {
        getTodo(id).delete();
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
