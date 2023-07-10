package com.lahee.todo.service;

import com.lahee.todo.domain.CustomUserDetails;
import com.lahee.todo.domain.User;
import com.lahee.todo.dto.user.LoginDto;
import com.lahee.todo.dto.user.UserDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsManager manager;

    public UserDto login(LoginDto loginDto) {
        log.info("user 로그인 시도 ");


        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new RuntimeException("아이디가 잘못되었습니다."));
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return UserDto.fromEntity(user);
        }
        log.info("{} {}", user.getPassword(), loginDto.getPassword());
        throw new RuntimeException("비밀번호로그인이 잘못되었습니다.");
    }

    public void signup(UserSignInRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        manager.createUser(CustomUserDetails.builder().username(userRequestDto.getUsername()).password(encodedPassword).email(userRequestDto.getEmail()).build()); //security context에 저장한댜
//        User user = new User(userRequestDto.getUsername(), encodedPassword, userRequestDto.getEmail());
//        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
