package com.lahee.todo.service;

import com.lahee.todo.config.security.TokenProvider;
import com.lahee.todo.domain.Role;
import com.lahee.todo.domain.User;
import com.lahee.todo.dto.jwt.RefreshToken;
import com.lahee.todo.dto.jwt.TokenDto;
import com.lahee.todo.dto.jwt.TokenRequestDto;
import com.lahee.todo.dto.user.LoginDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.exception.CustomException;
import com.lahee.todo.repository.RefreshTokenRepository;
import com.lahee.todo.repository.UserRepository;
import com.lahee.todo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.lahee.todo.exception.ErrorCode.*;

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
    public TokenDto login(LoginDto loginDto)  {
        log.info("{}", loginDto);
        User user = userRepository.findUserByName(loginDto.getUsername()).orElseThrow(() -> new RuntimeException("아이디가 잘못되었습니다."));
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        log.info("tokenDTo : {}",authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authenticationManagerBuilder.getObject().authenticate(authenticationToken));

        //RefreshToken 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .key(authenticationManagerBuilder.getObject().authenticate(authenticationToken).getName())
                .value(tokenDto.getRefreshToken()).build());

        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(USER_INVALID_REFRESH_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName()).orElseThrow(() -> new CustomException(USER_NOT_ACTIVE));

        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(USER_INVALID_USER_REFRESH_TOKEN);
        }

        //새로운 토큰 발급
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        return tokenDto;
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
