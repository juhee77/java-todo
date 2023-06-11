package com.lahee.todo.controller;

import com.lahee.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/create")
    public String create(@RequestParam("todo-desc") String msg) {
        todoService.save(msg);
        return "redirect:/todo";
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("todos", todoService.readTodos());
        model.addAttribute("dones", todoService.readDones());

        return "/todo";
    }

    @PostMapping("/done/{id}")
    public String done(@PathVariable("id") Long id) {
        todoService.done(id);
        return "redirect:/todo";
    }


}
