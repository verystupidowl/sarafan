package com.tgc.Sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.CommentDto;
import com.tgc.Sarafan.service.CommentService;
import com.tgc.Sarafan.service.CommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public CommentDto create(@RequestBody CommentDto comment, @AuthenticationPrincipal User user) {
        return commentService.create(comment, user);
    }
}