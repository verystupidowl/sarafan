package com.tgc.Sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.MessagePageDto;
import com.tgc.Sarafan.service.MessageService;
import com.tgc.Sarafan.service.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public final static int MESSAGES_PER_PAGE = 3;


    @Autowired
    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@AuthenticationPrincipal User user,
                               @PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return messageService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    @JsonView(Views.FullMessage.class)
    public Message create(@AuthenticationPrincipal User user, @RequestBody Message message) throws IOException {
        return messageService.create(message, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) throws IOException {
        return messageService.update(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }

}
