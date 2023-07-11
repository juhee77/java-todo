package com.lahee.todo.controller;

import com.lahee.todo.dto.ResponseMessageDto;
import com.lahee.todo.exception.ResponseDto;
import com.lahee.todo.service.GuestBookMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final GuestBookMessageService guestBookMessageService;

    @GetMapping("/")
    public String entryPoint() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int limit) {

        if (authentication != null) {
            log.info("home.log {}", authentication.getName());
            model.addAttribute("name", authentication.getName());
        }
        Page<ResponseMessageDto> messages = guestBookMessageService.findByPage(page, limit);
        if (!messages.isEmpty()) {
            model.addAttribute("messages", messages);
        }
        return "home";
    }

    @PostMapping("/home/post")
    public String savePost(@RequestParam("name") String name, @RequestParam("content") String content) {
        log.info("save post");
        guestBookMessageService.save(name, content);
        return "redirect:/home";
    }

    @PostMapping("/home/like")
    public ResponseEntity<ResponseDto> likeMessage(@RequestParam("messageId") Long messageId) {
        guestBookMessageService.heartUp(messageId);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("하트수 증가");
        return ResponseEntity.ok(responseDto);
    }
}
