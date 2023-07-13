package com.lahee.todo.service;

import com.lahee.todo.domain.GuestBookMessage;
import com.lahee.todo.dto.ResponseMessageDto;
import com.lahee.todo.repository.GuestBookMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GuestBookMessageService {
    private final GuestBookMessageRepository guestBookMessageRepository;

    public Page<ResponseMessageDto> findByPage(int page, int limit) {
        return guestBookMessageRepository.findAll(PageRequest.of(page, limit, Sort.by("id").reverse())).map(ResponseMessageDto::fromEntity);
    }

    @Transactional
    public void save(String name, String content) {
        GuestBookMessage guestBookMessage = new GuestBookMessage(content, name);
        guestBookMessageRepository.save(guestBookMessage);
    }

    @Transactional
    public void heartUp(Long messageId) {
        GuestBookMessage guestBookMessage = guestBookMessageRepository.findById(messageId).orElseThrow(() -> new IllegalAccessError("없는 id"));
        guestBookMessage.heartUp();
    }
}
