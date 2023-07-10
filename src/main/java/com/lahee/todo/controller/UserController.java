package com.lahee.todo.controller;

import com.lahee.todo.dto.user.LoginDto;
import com.lahee.todo.dto.user.UserDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/users")
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

    @PostMapping("/login")
    public String login(LoginDto userRequestDto) {
        UserDto signup = userService.login(userRequestDto);

        log.debug("{} 로그인 성공 ", userRequestDto.getUsername());
        return "redirect:/home";

    }

    @PostMapping("/signup")
    public String signupPost(UserSignInRequestDto userRequestDto) {

        userService.signup(userRequestDto);
        return "redirect:/users/login";
    }

}
