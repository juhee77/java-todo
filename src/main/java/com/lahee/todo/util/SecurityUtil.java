package com.lahee.todo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


@Slf4j
public class SecurityUtil {

    public static Optional<String> getCurrentUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.error("authentication {} /",authentication);
        if (authentication == null) {
            log.info("Secutiry context의 정보가 없음");
            return Optional.empty();
        }

        String userName = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userName = springSecurityUser.getUsername();
            log.error("1 : {}",userName);
        } else if (authentication.getPrincipal() instanceof String) {
            userName = (String) authentication.getPrincipal();
            log.error("2 : {}",userName);

        }
        return Optional.ofNullable(userName);
    }
}
