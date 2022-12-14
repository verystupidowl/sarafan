package com.tgc.Sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.CommentDto;
import com.tgc.Sarafan.service.ProfileService;
import com.tgc.Sarafan.utils.CommentErrorResponse;
import com.tgc.Sarafan.exceptions.CommentNotCreatedException;
import com.tgc.Sarafan.service.CommentService;
import com.tgc.Sarafan.service.impl.CommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final ProfileService profileService;

    @Autowired
    public CommentController(CommentServiceImpl commentService, ProfileService profileService) {
        this.commentService = commentService;
        this.profileService = profileService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public CommentDto create(@RequestBody @Valid CommentDto comment, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (!bindingResult.hasErrors()) {
            User userById = profileService.findById(user.getId());
            return commentService.create(comment, userById);
        } else {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String errorMessage = exceptionMsgBuilder(fieldErrors);
            throw new CommentNotCreatedException(errorMessage);
        }
    }

    @ExceptionHandler
    private ResponseEntity<CommentErrorResponse> exceptionHandler(CommentNotCreatedException e) {
        CommentErrorResponse response = new CommentErrorResponse(
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
