package com.lahee.todo.config.security;

import com.lahee.todo.domain.User;
import com.lahee.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) {
        log.info("CUSTOM USER DETAIL SERVICE username{}", name);
        Optional<User> oneWithAuthoritiesByName = userRepository.findOneWithAuthoritiesByName(name);
        Optional<org.springframework.security.core.userdetails.User> user = oneWithAuthoritiesByName
                .map(this::createUser);
        org.springframework.security.core.userdetails.User user1 = user
                .orElseThrow(() -> new RuntimeException("CUSTOM USER DETAIL SERVICE 존재하지 않는 유저 아이디"));
        log.info("user INfo : {} ", user);
        return user1;
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {

        log.info("CUSTOM USER DETAIL SERVICE 유저 생성!!");

        //활성화 되어있지 않다면(탈퇴한 멤버라면)
        if (user.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(user.getRole().toString());
        log.info("{}", grantedAuthorities);
        //유저 객체가 활성화 되어있다면 만들어서 리턴해준다.
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), List.of(grantedAuthorities));
    }


}
