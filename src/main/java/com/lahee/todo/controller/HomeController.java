package com.lahee.todo.controller;

import com.lahee.todo.service.UserService;
import com.lahee.todo.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String entryPoint() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Optional<String> currentUserName = SecurityUtil.getCurrentUserName();
        if (currentUserName.isPresent()) {
            String name = currentUserName.get();
            log.info("home.log {}", name);
            model.addAttribute("name", name);
        }
        return "home";
    }


}
