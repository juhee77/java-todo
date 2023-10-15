package com.lahee.todo.dto.todo;

import com.lahee.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TodoResponseDto {
    private long id;
    private String content;
    private String status;
    private LocalDate scheduleDate;

    public static TodoResponseDto getInstance(Todo todo) {
        return new TodoResponseDto(todo.getId(), todo.getContent(), todo.getStatus().toString(), todo.getScheduledDate());
    }
}
