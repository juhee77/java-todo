package com.lahee.todo.controller;

import com.lahee.todo.dto.user.UserDto;
import com.lahee.todo.dto.user.UserRequestDto;
import com.lahee.todo.dto.user.UserSignInRequestDto;
import com.lahee.todo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String loginPost(HttpServletRequest request, UserRequestDto userRequestDto) {
        UserDto signup = userService.login(userRequestDto);

        //세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("login_member", signup.getName());
        return "redirect:/home";

    }

    @PostMapping("/signup")
    public String signupPost(UserSignInRequestDto userRequestDto) {
        userService.signup(userRequestDto);
        return "redirect:/login";
    }
}
