package com.lahee.todo.service;

import com.lahee.todo.config.security.TokenProvider;
import com.lahee.todo.domain.Role;
import com.lahee.todo.domain.User;
import com.lahee.todo.dto.jwt.RefreshToken;
import com.lahee.todo.dto.jwt.TokenDto;
import com.lahee.todo.dto.user.LoginDto;
import com.lahee.todo.dto.user.UserDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.repository.RefreshTokenRepository;
import com.lahee.todo.repository.UserRepository;
import com.lahee.todo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        log.info("{}", loginDto);
        User user = userRepository.findUserByName(loginDto.getUsername()).orElseThrow(() -> new RuntimeException("아이디가 잘못되었습니다."));
        if (!validatePassword(user.getPassword(), loginDto.getPassword())) {
            log.info("{} {}", user.getPassword(), loginDto.getPassword());
            throw new RuntimeException("비밀번호로그인이 잘못되었습니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        //RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    private boolean validatePassword(String encodedPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    //현재 시큐리티 컨텍스에 있는 유저정보와 권환 정보를 준다
    public Optional<User> getMemberWithAuthorities() {
        Optional<String> currentUserName = SecurityUtil.getCurrentUserName();
        return currentUserName.flatMap(userRepository::findOneWithAuthoritiesByName);
    }

    @Transactional
    public void signup(UserSignInRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = User.builder()
                .role(Role.USER)
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getUsername())
                .password(encodedPassword)
                .build();
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByName(username);
    }
}
