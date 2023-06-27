package com.lahee.todo.controller;

import com.lahee.todo.dto.jwt.TokenDto;
import com.lahee.todo.dto.user.LoginDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/check-username")
    public @ResponseBody Map<String, Object> checkUsernameAvailability(@RequestParam("username") String username) {
        Map<String, Object> response = new HashMap<>();
        boolean available = !userService.existsByUsername(username);
        response.put("available", available);
        return response;
    }

    @PostMapping("/login")
    public String loginPost(HttpServletResponse res, LoginDto loginDto) {
        TokenDto tokenDto = userService.login(loginDto);

//        Map<String, Object> user = new HashMap<>();
//        session.setAttribute("name", loginDto.getUsername());
//        Cookie cookie = createRefreshTokenCookie(tokenDto.getRefreshToken());
//        res.addCookie(cookie);

        log.info("LOGIN {}", tokenDto.getAccessToken());
        res.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        res.setHeader("refreshtoken", "Bearer " + tokenDto.getRefreshToken());
        return "redirect:/home";

    }

    @GetMapping("/logout")
    public String loginPost(HttpServletResponse res) {
        Cookie cookie = new Cookie("login_token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return "redirect:/home";

    }

    @PostMapping("/signup")
    public String signupPost(UserSignInRequestDto userRequestDto) {
        userService.signup(userRequestDto);
        return "redirect:/login";
    }


    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30일 유효 기간 설정 (초 단위)
        return cookie;
    }
}
