package com.lahee.todo.controller;

import com.lahee.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/check-username")
    public @ResponseBody Map<String, Object> checkUsernameAvailability(@RequestParam("username") String username) {
        Map<String, Object> response = new HashMap<>();
        boolean available = !userService.existsByUsername(username);
        response.put("available", available);
        return response;
    }

}
