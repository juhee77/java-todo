package com.lahee.todo.domain.dto;

import com.lahee.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoResponseDto {
    private long id;
    private String content;
    private String status;

    public static TodoResponseDto getInstance(Todo todo) {
        return new TodoResponseDto(todo.getId(), todo.getContent(), todo.getStatus().toString());
    }
}
