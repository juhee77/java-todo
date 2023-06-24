package com.lahee.todo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Todo extends BaseEntity {
    @Id
    @Column(name = "todo_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public void modifyContent(String msg) {
        this.content = msg;
    }
}
