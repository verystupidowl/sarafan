package com.tgc.Sarafan.service;

import com.tgc.Sarafan.domain.Comment;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.EventType;
import com.tgc.Sarafan.dto.ObjectType;
import com.tgc.Sarafan.repositories.CommentRepository;
import com.tgc.Sarafan.utils.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepository commentRepository, WebSocketSender webSocketSender) {
        this.wsSender = webSocketSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);

        Comment commentFromDb = commentRepository.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);
        return commentFromDb;
    }
}
