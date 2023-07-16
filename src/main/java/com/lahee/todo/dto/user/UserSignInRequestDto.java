package com.lahee.todo.dto.user;

import com.lahee.todo.domain.User;
import lombok.Data;

@Data
public class UserSignInRequestDto {
    private String username;
    private String password;
    private String email;


    public User getUserInstance() {
        return new User(this.username, this.password, this.email);
    }
}
