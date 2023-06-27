package com.lahee.todo.config.security;

import com.lahee.todo.domain.User;
import com.lahee.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) {
        log.info("username{}", name);
        return userRepository.findOneWithAuthoritiesByName(name)
                .map(this::createUser)
                .orElseThrow(() -> new RuntimeException("jwt 로드 에러"));
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {

        log.info("유저 생성!!");

        //활성화 되어있지 않다면(탈퇴한 멤버라면)
        if (user.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(user.getRole().toString());
        log.info(grantedAuthorities.getAuthority());
        //유저 객체가 활성화 되어있다면 만들어서 리턴해준다.
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), List.of(grantedAuthorities));
    }
}
