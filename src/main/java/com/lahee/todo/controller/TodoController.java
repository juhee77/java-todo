package com.lahee.todo.controller;

import com.lahee.todo.dto.todo.TodoDto;
import com.lahee.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.lahee.todo.util.SecurityUtils.getCurrentUsername;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public String create(@ModelAttribute TodoDto todoDto) {
        todoService.save(todoDto, getCurrentUsername());
        return "redirect:/todo";
    }

    @GetMapping
    public String getTodoview(Model model) {
        model.addAttribute("todos", todoService.readTodos(getCurrentUsername()));
        model.addAttribute("dones", todoService.readDones(getCurrentUsername()));
        model.addAttribute("all", todoService.readAll(getCurrentUsername()));
        log.info("{}", todoService.readTodos(getCurrentUsername()));
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
