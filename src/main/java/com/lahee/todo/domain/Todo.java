package com.lahee.todo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String content;
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Todo(String content, Status status) {
        this.content = content;
        this.status = status;
    }

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
