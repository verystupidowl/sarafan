package com.tgc.Sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.MessagePageDto;
import com.tgc.Sarafan.service.ProfileService;
import com.tgc.Sarafan.utils.MessageErrorResponse;
import com.tgc.Sarafan.exceptions.MessageNotCreatedException;
import com.tgc.Sarafan.utils.UserErrorResponse;
import com.tgc.Sarafan.exceptions.UserNotAuthenticatedException;
import com.tgc.Sarafan.service.MessageService;
import com.tgc.Sarafan.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final ProfileService profileService;

    public final static int MESSAGES_PER_PAGE = 5;


    @Autowired
    public MessageController(MessageServiceImpl messageService, ProfileService profileService) {
        this.messageService = messageService;
        this.profileService = profileService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@AuthenticationPrincipal User user,
                               @PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        if (user != null)
            return messageService.findForUser(pageable, user);
        else
            throw new UserNotAuthenticatedException();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message create(@AuthenticationPrincipal User user, @RequestBody @Valid Message message, BindingResult bindingResult) throws IOException {
        if (!bindingResult.hasErrors()) {
            User userById = profileService.findById(user.getId());
            return messageService.create(message, userById);
        } else {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String errorMessage = exceptionMsgBuilder(fieldErrors);
            throw new MessageNotCreatedException(errorMessage);
        }
    }

    @PutMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody @Valid Message message, BindingResult bindingResult) throws IOException {
        if (!bindingResult.hasErrors()) {
            return messageService.update(messageFromDb, message);
        } else {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String errorMessage = exceptionMsgBuilder(fieldErrors);
            throw new MessageNotCreatedException(errorMessage);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> exceptionHandler(UserNotAuthenticatedException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(userErrorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<MessageErrorResponse> exceptionHandler(MessageNotCreatedException e) {
        MessageErrorResponse response = new MessageErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String exceptionMsgBuilder(List<FieldError> errors) {
        StringBuilder errorMsg = new StringBuilder();

        for (FieldError error : errors) {
            errorMsg
                    .append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage());
        }
        return errorMsg.toString();
    }

}
