package com.tgc.Sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.repositories.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageRepository messages;

    @Autowired
    public MessageController(MessageRepository messages) {
        this.messages = messages;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messages.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messages.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");

        return messages.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messages.delete(message);
    }
}
