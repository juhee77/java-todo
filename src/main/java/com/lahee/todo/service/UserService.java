package com.lahee.todo.service;

import com.lahee.todo.domain.User;
import com.lahee.todo.dto.user.UserDto;
import com.lahee.todo.dto.user.UserRequestDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserDto signup(UserRequestDto userRequestDto) {
        User user = userRepository.findUserByName(userRequestDto.getUsername()).orElseThrow(() -> new RuntimeException("아이디가 잘못되었습니다."));
        if (!user.getPassword().equals(userRequestDto.getPassword())) {
            log.info("{} {}", user.getPassword(), userRequestDto.getPassword());
            throw new RuntimeException("비밀번호로그인이 잘못되었습니다.");
        }
        return UserDto.fromEntity(user);
    }

    public void signin(UserSignInRequestDto userRequestDto) {
        User user = userRequestDto.getUserInstance();
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByName(username);
    }
}
