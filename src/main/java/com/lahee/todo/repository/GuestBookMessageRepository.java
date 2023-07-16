package com.lahee.todo.repository;

import com.lahee.todo.domain.GuestBookMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookMessageRepository extends JpaRepository<GuestBookMessage, Long> {

}
