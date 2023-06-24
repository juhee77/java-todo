package com.lahee.todo.service;

import com.lahee.todo.domain.User;
import com.lahee.todo.dto.user.UserDto;
import com.lahee.todo.dto.user.UserRequestDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDto signup(UserRequestDto userRequestDto) {
        User user = userRepository.findUserByName(userRequestDto.getUsername()).orElseThrow(() -> new RuntimeException("아이디가 잘못되었습니다."));
        if (validatePassword(user.getPassword(), userRequestDto.getPassword())) {
            return UserDto.fromEntity(user);
        }
        log.info("{} {}", user.getPassword(), userRequestDto.getPassword());
        throw new RuntimeException("비밀번호로그인이 잘못되었습니다.");
    }

    private boolean validatePassword(String encodedPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void signin(UserSignInRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto.getUsername(), encodedPassword, userRequestDto.getEmail());
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByName(username);
    }
}
