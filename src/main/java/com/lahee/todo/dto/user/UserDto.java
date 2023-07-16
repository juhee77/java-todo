package com.lahee.todo.dto.user;

import com.lahee.todo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;


    public static UserDto fromEntity(User user) {
        return new UserDto(user.getUsername(), user.getEmail());
    }
}
