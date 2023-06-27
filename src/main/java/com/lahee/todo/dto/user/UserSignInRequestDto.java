package com.lahee.todo.dto.user;

import lombok.Data;

@Data
public class UserSignInRequestDto {
    private String username;
    private String password;
    private String email;

}
