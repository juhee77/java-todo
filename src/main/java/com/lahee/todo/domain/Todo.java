package com.lahee.todo.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {
    @Id
    private Long id;
    private String content;
    private Status status;

    public void done() {
        this.status = Status.DONE;
    }

    public void undone() {
        this.status = Status.TODO;
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}
