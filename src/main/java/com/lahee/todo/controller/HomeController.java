package com.lahee.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String entryPoint() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(@SessionAttribute(name = "login_member", required = false)String name, Model model) {
        //TODO 홈 방명록 생성
        log.info("home.log {}", name);
        model.addAttribute("name", name);
        return "home";
    }
}
