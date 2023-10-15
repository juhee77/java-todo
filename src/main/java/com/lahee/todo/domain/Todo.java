package com.lahee.todo.domain;

import com.lahee.todo.dto.todo.TodoDto;
import com.lahee.todo.util.DateConversionUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDate scheduledDate;


    public static Todo getInstance(TodoDto todoDto, User user) {
        Todo todo = new Todo();
        todo.status = Status.TODO;
        todo.content = todoDto.getScheduleName();
        todo.scheduledDate = DateConversionUtil.stringToLocalDateTime(todoDto.getScheduledDate());
        todo.setUser(user);
        return todo;
    }

    private void setUser(User user) {
        if (!user.getTodos().contains(this)) {
            user.getTodos().add(this);
        }
        this.user = user;
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

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", user=" + user.getId() +
                ", scheduledDate=" + scheduledDate +
                '}';
    }
}
