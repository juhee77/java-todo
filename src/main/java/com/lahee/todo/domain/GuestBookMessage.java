package com.lahee.todo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "guest-book-message")
public class GuestBookMessage extends BaseEntity {
    @Id
    @Column(name = "guest-book-message_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String content;
    private String name;
    private int heartCnt;

    public GuestBookMessage(String content, String name) {
        this.content = content;
        this.name = name;
        heartCnt = 0;
    }

    public void heartUp() {
        heartCnt++;
    }
}
