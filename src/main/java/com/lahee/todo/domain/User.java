package com.lahee.todo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @OneToMany(fetch = LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todos;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = com.lahee.todo.domain.Authority.USER;
    }
}
