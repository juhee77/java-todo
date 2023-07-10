package com.lahee.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String entryPoint() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        //TODO 홈 방명록 생성

        if (authentication != null) {
            log.info("home.log {}", authentication.getName());
            model.addAttribute("name", authentication.getName());
        }
        return "home";
    }
}
