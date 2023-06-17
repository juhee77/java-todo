package com.lahee.todo.controller;

import com.lahee.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {
    private final TodoService todoService;

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        return "it's page is work";
    }

    @PostMapping("/create")
    public String create(@RequestParam("todo-desc") String msg) {
        todoService.save(msg);
        return "redirect:/todo";
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("todos", todoService.readTodos());
        model.addAttribute("dones", todoService.readDones());
        model.addAttribute("all", todoService.readAll());
        log.info("{}", todoService.readTodos());
        return "todo";
    }

    @PostMapping("/modify/{id}")
    public String modifyContent(@PathVariable("id") Long id, @RequestParam("modified-content") String msg) {
        todoService.modifyContent(id, msg);
        return "redirect:/todo";
    }


    @PostMapping("/done/{id}")
    public String updateDone(@PathVariable("id") Long id) {
        todoService.done(id);
        return "redirect:/todo";
    }

    @PostMapping("/undone/{id}")
    public String updateUnDone(@PathVariable("id") Long id) {
        todoService.undone(id);
        return "redirect:/todo";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        todoService.deleteDone(id);
        return "redirect:/todo";
    }
}
